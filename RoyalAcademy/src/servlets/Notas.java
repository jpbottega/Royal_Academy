package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.CursoExamenDao;
import dao.UsuarioDao;
import funciones.FuncionesVarias;
import modelo.ContenedorResponse;
import modelo.CursoExamen;
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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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

				case "calcularNotaFinal":
					calcularNotaFinal(request, response);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		String tabla = "<table class=\"table\">" +
				  "<thead>" +
				   	"<tr>" +
				      "<th scope=\"col\">#</th>" +
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
				tabla += this.traerHtmlTabla(u, notas.stream().filter(p -> p.getId_alumno() == u.getId()).collect(Collectors.toList()));
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
	
	private void calcularNotaFinal(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		String tabla = "";
		try {
			int id_curso = Integer.parseInt(request.getParameter("id_curso"));
		
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
		String json = "";
		String tabla = "";
		try {
			int id_curso = Integer.parseInt(request.getParameter("id_curso"));
		
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
	
	private String traerHtmlTabla(Usuario u, List<modelo.Notas> notas) {
		String retorno = "";
		int contador = 1;
		String notaPracti = "";
		String notaExamen = "";
		if (notas != null) {
			retorno += "<tr><th scope=\"row\">" + contador + "</th>" + "<td>" + u.getApellido() + ", " + u.getNombre() + "<\td>";
			for (modelo.Notas n : notas) {
				if (u.getId() == n.getId_alumno() && n.isEsExamen()) {
					notaExamen = "<td>" + n.getNota() + "</td>";
				}
				else if (u.getId() == n.getId_alumno() && !n.isEsExamen()) {
					notaPracti = "<td><input type=\"text\" value=\"" + n.getNota() + "\" id=\"" + n.getId() + "\"</input></td>";
				}
			}
		}
		retorno += notaPracti + notaExamen + "</tr>";    		    
		return retorno;
	}
}
