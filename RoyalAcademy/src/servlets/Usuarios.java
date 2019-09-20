package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

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
import modelo.Usuario;

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
				case "agregarUsuario":
					agregarUsuario(request, response);
					break;

				case "buscarUsuario":
					buscarUsuario(request, response);
					break;

				case "modificarAlumno":
					// eliminarFuncion(request, response);
					break;
				/*
				 * case "agregarDocente": eliminarPerfil(request, response); break;
				 * 
				 * case "eliminarDocente": eliminarPerfil(request, response); break;
				 * 
				 * case "buscarDocente": eliminarPerfil(request, response); break; }
				 * 
				 * case "modificarDocente": eliminarPerfil(request, response); break;
				 */
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			String nombre = (request.getParameter("nombreAlumno") == null || request.getParameter("nombreAlumno") == "")
					? ""
					: request.getParameter("nombreAlumno");
			String apellido = (request.getParameter("apellidoAlumno") == null
					|| request.getParameter("apellidoAlumno") == "") ? "" : request.getParameter("apellidoAlumno");
			String mail = (request.getParameter("mailAlumno") == null || request.getParameter("mailAlumno") == "") ? ""
					: request.getParameter("mailAlumno");
			int id_rol = (request.getParameter("id_rol") == null || request.getParameter("id_rol") == "") ? 0
					: Integer.valueOf(request.getParameter("id_rol"));

			List<Usuario> usuarios = userDao.traerUsuariosPorNomApeMail(nombre, apellido, mail, id_rol);
			if (usuarios.isEmpty()) {
				error.setCd_error(1);
				error.setDs_error("No se han encontrado usuarios con los datos ingresados.");
				error.setTipo("error");
			} else {
				error.setCd_error(1);
				error.setDs_error("Se han encontrado " + usuarios.size() + " resultados.");
				error.setTipo("success");
				String datos = "";
				for (Usuario u : usuarios) {
					datos += "<div class=\"card\" style=\"width: 16rem;\" id=\"\">" +  
								"<div class=\"card-body\">" +
										"<div class=\"card-title\">" + u.getId() + " - " + u.getNombre() + " " + u.getApellido() + "</div>" + 
										"<div class=\"card-subtitle mb-2 text-muted\">" + u.getEmail() + "</div>" + 
										"<div class=\"card-text\">Tel: " + u.getTelefono() + "</div>" + 
										"<div class=\"card-text\">Nacimiento: " + this.fechaSimple(u.getFechaNacimiento()) + "</div>" +
								"</div>" +
							"</div>";
				}
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

	private String fechaSimple(Date date) {
		return date.getDate() + "/" + (date.getMonth() + 1) + "/" + (date.getYear() + 1900);
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
		try {
			user.setNombre(request.getParameter("nombreUsuario"));
			user.setApellido(request.getParameter("apellidoUsuario"));
			user.setEmail(request.getParameter("mailUsuario"));
			user.setTelefono(request.getParameter("telefonoUsuario"));
			user.setPass(request.getParameter("passUsuario"));
			String f = request.getParameter("nacimientoUsuario");
			// si se les ocurre algo mejor para parsear la fecha diganme porfavor
			int anio = 0, mes = 0, dia = 0;
			if (f != null && f != "") {
				anio = Integer.valueOf(f.substring(0, f.indexOf("-")));
				mes = Integer.valueOf(f.substring(f.indexOf("-") + 1, f.indexOf("-", f.indexOf("-") + 1)));
				dia = Integer.valueOf(f.substring(f.indexOf("-", f.indexOf("-") + 1) + 1));
			}
			user.setFechaNacimiento(new Date(anio - 1900, mes - 1, dia));
			user.setId_rol(Integer.valueOf(request.getParameter("id_rol"))); // me esta llegando vacio

			user.setVerificado(Boolean.getBoolean(request.getParameter("verificado")));

			if (user.getNombre() != "" && user.getApellido() != "" && user.getTelefono() != "" && user.getEmail() != ""
					&& user.getPass() != "" && user.getFechaNacimiento() != null) {
				if (userDao.insertarUsuario(user)) {
					error.setCd_error(1);
					error.setDs_error("Se agrego el usuario correctamente.");
					error.setTipo("success");
				} else {
					error.setCd_error(1);
					error.setDs_error("Ya se encuentra registrado un usuario con ese Email.");
					error.setTipo("error");
				}
			} else {
				error.setCd_error(1);
				error.setDs_error("Faltan completar datos del usuario, no puede ser creado.");
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
}
