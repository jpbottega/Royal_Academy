package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import funciones.FuncionesVarias;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.RolDao;
import dao.UsuarioDao;
import modelo.ContenedorResponse;
import modelo.Rol;
import modelo.Usuario;
import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*;  
/**
 * Servlet implementation class Usuarios
 */
@WebServlet("/Usuarios")
public class Usuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Usuarios() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			if (request.getParameter("accion") != null) {

				switch (request.getParameter("accion")) {
				case "guardarUsuario":
					agregarUsuario(request, response);
					break;

				case "buscarUsuario":
					buscarUsuario(request, response);
					break;

				case "eliminarUsuario":
					eliminarUsuario(request, response);
					break;
				case "traerUsuario":
					selectUsuario(request, response);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		UsuarioDao userDao = new UsuarioDao();
		Usuario user = new Usuario();
		try {
			user.setId(Integer.valueOf((request.getParameter("id_usuario"))));
			if (userDao.traerUsuarioPorId(user.getId()) != null) { // si el usuario existe en la bd (puede no hacer falta esto pero por las dudas)
				if (userDao.eliminarUsuario(user)) {
					error.setCd_error(1);
					error.setDs_error("Se elimino el usuario correctamente.");
					error.setTipo("success");
				} else {
					error.setCd_error(1);
					error.setDs_error("No se ha podido eliminar el usuario.");
					error.setTipo("error");
				}
			} else {
				error.setCd_error(1);
				error.setDs_error("El usuario no existe en la base de datos.");
				error.setTipo("error");
			}
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

	private void buscarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		UsuarioDao userDao = new UsuarioDao();

		try {
		
			List<Usuario> usuarios = userDao.traerTodos(Integer.parseInt(request.getParameter("id_rol")));
			if (usuarios.isEmpty()) {
				error.setCd_error(1);
				error.setDs_error("No se han encontrado usuarios con los datos ingresados.");
				error.setTipo("error");
			} else {
				error.setCd_error(1);
				error.setDs_error("Se han encontrado " + usuarios.size() + " resultados.");
				error.setTipo("success");
				String datos = "";
				datos="<div class=\"row\">";
				for (Usuario usuario : usuarios) {
					datos += "<div class=\"col-12 card card-styles\" onclick=\"selectTarjeta("+usuario.getId()+")\" id=\""+usuario.getId()+"\">" +  
								"<div class=\"card-body\">" +
										"<div class=\"card-title\">" +usuario.getNombre() + " " + usuario.getApellido() + "</div>" + 
										"<div class=\"card-subtitle mb-2 text-muted\">" + usuario.getEmail() + "</div>" + 
										"<div class=\"card-text text-muted\">Tel: " + usuario.getTelefono() + "</div>" + 
										"<div class=\"card-text text-muted\">DNI: " + usuario.getDni() + "</div>" + 
										"<div class=\"card-text text-muted\">Nacimiento: " + FuncionesVarias.getStringDate(usuario.getFechaNacimiento(), 1) + "</div>" +
								"</div>" +
							"</div>";
				}
				datos+="</div>";
				contenedorResponse.setData(datos);
			}

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

	private void agregarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		UsuarioDao userDao = new UsuarioDao();
		Usuario user = new Usuario();
		FuncionesVarias funciones = new FuncionesVarias();
		try {
			
			if (request.getParameter("nombreUsuario") != "" && request.getParameter("nombreUsuario") != "" 
					&& request.getParameter("telefonoUsuario") != "" && request.getParameter("mailUsuario") != ""
					&& request.getParameter("dniUsuario") != "" &&  request.getParameter("nacimientoUsuario") != null) {
				
				if(Integer.parseInt(request.getParameter("id_usuario")) != 0) { // si es un usuario a modificar
					user.setId(Integer.valueOf((request.getParameter("id_usuario"))));
					user.setNombre(request.getParameter("nombreUsuario"));
					user.setApellido(request.getParameter("apellidoUsuario"));
					user.setEmail(request.getParameter("mailUsuario"));
					user.setTelefono(request.getParameter("telefonoUsuario"));
					user.setPass(request.getParameter("passUsuario"));
					user.setFechaNacimiento(funciones.getDateString(request.getParameter("nacimientoUsuario"),1));
					user.setId_rol(Integer.valueOf(request.getParameter("rol_usuario"))); // me esta llegando vacio
					user.setVerificado(Boolean.valueOf(request.getParameter("verificado")));
					user.setDni(request.getParameter("dniUsuario"));
					
				} else { // si es un usuario nuevo
					user.setNombre(request.getParameter("nombreUsuario"));
					user.setApellido(request.getParameter("apellidoUsuario"));
					user.setEmail(request.getParameter("mailUsuario"));
					user.setTelefono(request.getParameter("telefonoUsuario"));
					user.setPass(request.getParameter("dniUsuario"));					
					user.setFechaNacimiento(funciones.getDateString(request.getParameter("nacimientoUsuario"),1));
					user.setId_rol(Integer.valueOf(request.getParameter("rol_usuario"))); // me esta llegando vacio	
					user.setDni(request.getParameter("dniUsuario"));
					user.setVerificado(false);
				}
				
				if (user.getId() == 0 && userDao.traerUsuarioPorMail(user.getEmail()) == null) { // si es un nuevo usuario y existe no lo deberia insertar
					if (userDao.save_tabla(user)) {
						error.setCd_error(1);
						error.setDs_error("Se guardo el usuario correctamente.");
						error.setTipo("success");
						enviarVerficacionUsuario(user); // si es un usuario nuevo le envio un mail con la pass
					} else { 
						error.setCd_error(1);
						error.setDs_error("No se ha podido guardar el usuario.");
						error.setTipo("error");
					} 
				} else if (user.getId() != 0) { // si es un usuario existente
					if (userDao.save_tabla(user)) {
						error.setCd_error(1);
						error.setDs_error("Se guardo el usuario correctamente.");
						error.setTipo("success");
						//if (user.getId() == 0) enviarVerficacionUsuario(user); // si es un usuario nuevo le envio un mail
					} else { // si es un usuario existente
						error.setCd_error(1);
						error.setDs_error("No se ha podido guardar el usuario.");
						error.setTipo("error");
					} 
				} else {
					error.setCd_error(1);
					error.setDs_error("Ya existe un usuario con ese correo.");
					error.setTipo("error");
				}
			} else {
				error.setCd_error(1);
				error.setDs_error("Faltan completar datos del usuario, no puede ser guardado.");
				error.setTipo("error");
			}
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

	private void selectUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		UsuarioDao userDao = new UsuarioDao();
		RolDao rolDao = new RolDao();
		try {
			Usuario u = userDao.traerUsuarioPorId(Integer.valueOf(request.getParameter("id_usuario")));
			List<Rol> roles = rolDao.traerTodos();
			if (u != null) {
				String datos = "";
				datos = "<input id=\"id_usuario\" name=\"id_usuario\" type=\"hidden\" value=\"" + u.getId() + "\">"+
						"<input id=\"id_rol\" name=\"id_rol\" type=\"hidden\" value=\"" + u.getId_rol() + "\">"+
						"<input type=\"hidden\" id=\"verificado\" name=\"verificado\" value=\"" + u.isVerificado() + "\">"+
						"<input type=\"hidden\" id=\"passUsuario\" name=\"passUsuario\" value=\"" + u.getPass() + "\">"+
							"<div class=\"form-group row\">"+
								"<div class=\"col\">" +
									"<label for=\"exampleInputEmail1\">Nombre</label>" +
										"<input type=\"text\" class=\"form-control\" id=\"nombreUsuario\" name=\"nombreUsuario\" " +
										"placeholder=\"Nombre del usuario\" value=\"" + u.getNombre()+ "\">" +
								"</div>"+
								"<div class=\"col\">" +
									"<label for=\"exampleInputEmail1\">Apellido</label>" +
										"<input type=\"text\" class=\"form-control\" id=\"apellidoUsuario\" name=\"apellidoUsuario\" " +
										"placeholder=\"Apellido del usuario\" value=\"" + u.getApellido() + "\">"+
								"</div>"	+
							"</div>"+
							"<div class=\"form-group row\">"+
								"<div class=\"col\">" +
									"<label for=\"exampleInputEmail1\">E-Mail</label>" +
										"<input type=\"email\" class=\"form-control\" id=\"mailUsuario\" name=\"mailUsuario\" " +
										"placeholder=\"Correo del usuario\" value=\"" + u.getEmail() + "\">" +
								"</div>"+
								"<div class=\"col\">" +
								"<div class=\"form-group\"> "+
								"<label for=\"nacimientoUsuario\">Fecha de Nacimiento</label> <input "+
									"type='text'  id=\"nacimientoUsuario\" "+
								"name=\"nacimientoUsuario\" placeholder=\"Fecha de nacimiento\" class=\"form-control\" value=\"" + FuncionesVarias.getStringDate(u.getFechaNacimiento(), 1) + "\"/> "+

							"</div>"+
								"</div>"	+
							"</div>"+
							"<div class=\"form-group row\">"+
								"<div class=\"col\">" +
									"<label for=\"exampleInputEmail1\">Telefono</label>" +
										"<input type=\"text\" class=\"form-control\" id=\"telefonoUsuario\" name=\"telefonoUsuario\" " +
										"placeholder=\"Telefono del usuario\" value=\"" + u.getTelefono() + "\">" +
								"</div>"	+
								"<div class=\"col\">" +
									"<label for=\"exampleInputEmail1\">DNI</label>" +
										"<input type=\"text\" class=\"form-control\" id=\"dniUsuario\" name=\"dniUsuario\" " +
										"placeholder=\"Dni del usuario\" value=\"" + u.getDni() + "\">" +
								"</div>" +
							"</div>" +
							"<div class=\"form-group row\">"+
								"<div class=\"col-md-6\">"+
									"<label for=\"exampleFormControlSelect1\">Roles</label> <select "+
										"class=\"form-control\" id=\"rol_usuario\" name=\"rol_usuario\"> ";
										
											if (!roles.isEmpty()) {
												for (Rol rol : roles) {
									
										datos +="<option value=\""+rol.getId()+"\""+(rol.getId()==u.getId_rol()?"selected":"")+">"+rol.getDenominacion()+"</option> ";
										
											}
											}
										
										datos +="</select>"+
								"</div>"+
							"</div>"+
						"<button type=\"button\" class=\"btn btn-success pull-right\"" +
							"onclick=\"guardarUsuario();\" id=\"botonGuardarUsuario\">Guardar</button>" +
						"<button type=\"button\" class=\"btn btn-danger pull-right\"" +
							"onclick=\"eliminarUsuario();\" id=\"botonEliminarUsuario\">Eliminar</button>";
				error.setCd_error(1);
				error.setDs_error("Habilitada modificacion para usuario " + u.getNombre() + " " + u.getApellido() + ".");
				error.setTipo("success");
				contenedorResponse.setData(datos);
			} else {
				error.setCd_error(1);
				error.setDs_error("El usuario ha sido eliminado de la base de datos.");
				error.setTipo("error");
			}
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

	private void enviarVerficacionUsuario(Usuario u) throws MessagingException {
		String  d_email = "royalacademyunla@gmail.com",
		        d_uname = "royalacademyunla",
		        d_password = "proyecto2019",
		        d_host = "smtp.gmail.com",
		        d_port  = "465", //465,587
		        m_to = u.getEmail(),
		        m_subject = "Bienvenido a Royal Academy",
		        m_text = "Bienvenido " + u.getNombre() + ".\nSu usuario es: " + u.getEmail() + "\nContraseña: " + u.getPass();

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
}


