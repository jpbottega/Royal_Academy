package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.CursoDao;
import dao.FuncionesDao;
import dao.UsuarioDao;
import modelo.ContenedorResponse;
import modelo.Curso;
import modelo.Curso_Usuario;
import modelo.PermisoFunciones;
import modelo.Usuario;

/**
 * Servlet implementation class Notas
 */
@WebServlet("/Notas")
public class Notas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Notas() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			if (request.getParameter("accion") != null) {

				switch (request.getParameter("accion")) {
				case "verNotas":
					verNotas(request, response);
					break;
					
				case "guardarNotas":
					guardarNotas(request, response);
					break;
					
				case "notificar":
					notificarNotas(request, response);
					break;

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void notificarNotas(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		UsuarioDao userDao = new UsuarioDao();
		FuncionesDao fDao = new FuncionesDao();
		CursoDao cDao = new CursoDao();
		try {
			int id_curso = Integer.parseInt(request.getParameter("id_curso"));
			Curso curso = cDao.traerCursoPorId(id_curso);
			List<Usuario> usuarios = userDao.traerUsuariosPorCurso(id_curso);
			for (Usuario u : usuarios) {
				List<PermisoFunciones> permisos = fDao.traerPermisoFunciones(u.getId_rol());
				permisos = permisos.stream().filter(p -> ((p.getId_funcion() == 2 || p.getId_funcion() == 12) && p.getHabilitada() == 1)).collect(Collectors.toList());
				if (!permisos.isEmpty()) {
					Curso_Usuario cu = userDao.traerCursoUsuarioPorUsuario(u.getId(), id_curso);
					if (cu.getNotaFinal() >= 4) enviarMailResultado(u, cu.getNotaFinal(), curso); // aca envio el mail
				}
			}
			error.setCd_error(1);
			error.setDs_error("Se ha notificado a los alumnos.");
			error.setTipo("success");
			
		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
	}
	
	private void enviarMailResultado(Usuario u, float resultado, Curso c) throws AddressException, MessagingException {
		String  d_email = "royalacademyunla@gmail.com",
		        d_uname = "royalacademyunla",
		        d_password = "proyecto2019",
		        d_host = "smtp.gmail.com",
		        d_port  = "465", //465,587
		        m_to = u.getEmail(),
		        m_subject = "Resultado de curso " + c.getDenominacion(),
		        m_text = "Felicitaciones " + u.getNombre() + ".\nHa aprobado el curso: " + c.getDenominacion() + " con una nota final de " + resultado;

		Properties props = new Properties();
		props.put("mail.smtp.user", d_email);
		props.put("mail.smtp.host", d_host);
		props.put("mail.smtp.port", d_port);
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", d_port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		SMTPAuthenticator auth = new SMTPAuthenticator();
		Session session = Session.getInstance(props, auth);
		session.setDebug(true);

		MimeMessage msg = new MimeMessage(session);
		msg.setText(m_text);
		msg.setSubject(m_subject);
		msg.setFrom(new InternetAddress(d_email));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(m_to));

		Transport transport = session.getTransport("smtps");
		transport.connect(d_host, 465, d_uname, d_password);
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();
    }
	private class SMTPAuthenticator extends Authenticator
	{
	    public PasswordAuthentication getPasswordAuthentication()
	    {
	        return new PasswordAuthentication("royalacademyuna@gmail.com", "proyecto2019");
	    }
	}
	
	private void verNotas(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		UsuarioDao userDao = new UsuarioDao();
		FuncionesDao fDao = new FuncionesDao();
		String tabla = "<table class=\"table\">" +
				  "<thead>" +
				   	"<tr>" +
				      "<th scope=\"col\">Nombre Apellido</th>" +
				      "<th scope=\"col\">Nota Practicos</th>" +
				      "<th scope=\"col\">Nota Examen</th>" +
				      "<th scope=\"col\">Nota Final</th>" +
				    "</tr>" +
				  "</thead>" + // header
				  "<tbody>";
		try {
			int id_curso = Integer.parseInt(request.getParameter("id_curso"));
			List<Usuario> usuarios = userDao.traerUsuariosPorCurso(id_curso);
			List<modelo.Notas> notas = userDao.bulkSelectNotas(usuarios);
			for (Usuario u : usuarios) {
				List<PermisoFunciones> permisos = fDao.traerPermisoFunciones(u.getId_rol());
				permisos = permisos.stream().filter(p -> ((p.getId_funcion() == 2 || p.getId_funcion() == 12) && p.getHabilitada() == 1)).collect(Collectors.toList());
				if (!permisos.isEmpty())
				tabla += this.traerHtmlTabla(u, notas.stream().filter(p -> p.getId_alumno() == u.getId() && p.getId_curso() == id_curso).collect(Collectors.toList()), userDao.traerCursoUsuarioPorUsuario(u.getId(), id_curso));
			}
			tabla += "</tbody>" + "</table>";
		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(tabla);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
	}
	
	private void guardarNotas(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		UsuarioDao userDao = new UsuarioDao();
		FuncionesDao fDao = new FuncionesDao();
		String json = "";
		String tabla = "<table class=\"table\">" +
				  "<thead>" +
				   	"<tr>" +
				      "<th scope=\"col\">Nombre Apellido</th>" +
				      "<th scope=\"col\">Nota Practicos</th>" +
				      "<th scope=\"col\">Nota Examen</th>" +
				      "<th scope=\"col\">Nota Final</th>" +
				    "</tr>" +
				  "</thead>" + // header
				  "<tbody>";
		try {
			int id_curso = Integer.parseInt(request.getParameter("id_curso"));
			List<Usuario> usuarios = userDao.traerUsuariosPorCurso(id_curso);
			List<modelo.Notas> notas = userDao.bulkSelectNotas(usuarios);
			for (Usuario u : usuarios) {
				List<PermisoFunciones> permisos = fDao.traerPermisoFunciones(u.getId_rol());
				permisos = permisos.stream().filter(p -> ((p.getId_funcion() == 2 || p.getId_funcion() == 12) && p.getHabilitada() == 1)).collect(Collectors.toList());
				//String parametro = request.getParameter(String.valueOf(u.getId()));
				if (request.getParameter(String.valueOf(u.getId())) != null && request.getParameter(String.valueOf(u.getId())) != "" && !permisos.isEmpty()) {
					int aux = userDao.aux_select_int("select id from notas where id_alumno = " + u.getId() + " and id_curso = " + id_curso + " and esexamen = 0");
					float nota = Float.parseFloat(request.getParameter(String.valueOf(u.getId())));
					userDao.save_tabla(new modelo.Notas(aux, u.getId(), id_curso, nota, false));
					for (modelo.Notas n : notas) if (n.getId() == aux) n.setNota(nota);
				}
				if (!permisos.isEmpty()) 
					tabla += this.traerHtmlTabla(u, 
						notas.stream().filter(p -> p.getId_alumno() == u.getId() && p.getId_curso() == id_curso).collect(Collectors.toList()), // aca para uqe la nota se actualice
						userDao.traerCursoUsuarioPorUsuario(u.getId(), id_curso));
			}
			tabla += "</tbody>" + "</table>";
			error.setCd_error(1);
			error.setDs_error("Se han guardado las notas.");
			error.setTipo("success");
		
		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(tabla);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
	}
	
	private String traerHtmlTabla(Usuario u, List<modelo.Notas> notas, Curso_Usuario ce) {
		String retorno = "";
		String notaPracti = "", notaExamen = "", notaFin = "";
		float notaP = -1, notaE = -1;
		if (notas != null) {
			retorno += "<tr><td>" + u.getApellido() + ", " + u.getNombre() + "</td>";
			for (modelo.Notas n : notas) {
				if (u.getId() == n.getId_alumno() && n.isEsExamen()) {
					notaExamen = "<td>" + n.getNota() + "</td>";
					notaE = n.getNota();
				}
				else if (u.getId() == n.getId_alumno() && !n.isEsExamen()) {
					notaPracti = "<td><input type=\"text\" value=\"" + n.getNota() + "\" id=\"" + u.getId() + "\" name=\"" + u.getId() + "\"></input></td>";
					notaP = n.getNota();
				}
			}
		}
		notaFin = this.notaFinal(notaP,  notaE, ce);
		if (notaExamen == "") {
			notaExamen = "<td>N/D</td>";
		}
		if (notaPracti == "") {
			notaPracti = "<td><input type=\"text\" id=\"" + u.getId() + "\" name=\"" + u.getId() + "\"></input></td>";
		}
		retorno += notaPracti + notaExamen + notaFin + "</tr>";    		    
		return retorno;
	}
	
	private String notaFinal(float n1, float n2, Curso_Usuario ce) {
		String nota = "<td>N/D</td>";
		UsuarioDao uDao = new UsuarioDao();
		if (n1 != -1 && n2 != -1) {
			nota = "<td>" + (n1+n2) / 2 + "</td>";
			ce.setNotaFinal(Math.round((n1+n2)/2));
			uDao.save_tabla(ce);
		}
		return nota;
	}
}
