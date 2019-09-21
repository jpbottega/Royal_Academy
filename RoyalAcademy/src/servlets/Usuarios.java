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
				case "guardarUsuario":
					guardarUsuario(request, response);
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
	
	private void guardarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (request.getParameter("id_usuario").compareToIgnoreCase("0") == 0) {
			agregarUsuario(request, response);
		}
		else {
			modificarUsuario(request, response);
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
				datos="<div class=\"row\">";
				for (Usuario usuario : usuarios) {
					datos += "<div class=\"col-12 card card-styles\" onclick=\"selectTarjeta("+usuario.getId()+")\" id=\""+usuario.getId()+"\">" +  
								"<div class=\"card-body\">" +
										"<div class=\"card-title\">" +usuario.getNombre() + " " + usuario.getApellido() + "</div>" + 
										"<div class=\"card-subtitle mb-2 text-muted\">" + usuario.getEmail() + "</div>" + 
										"<div class=\"card-text text-muted\">Tel: " + usuario.getTelefono() + "</div>" + 
										"<div class=\"card-text text-muted\">Nacimiento: " + usuario.getFechaNacimiento() + "</div>" +
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

	private void modificarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
				if (userDao.save_tabla(user)) {
					error.setCd_error(1);
					error.setDs_error("Se agrego el usuario correctamente.");
					error.setTipo("success");
				} else {
					error.setCd_error(1);
					error.setDs_error("No se ha podido modificar el usuario.");
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

	private void selectUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		UsuarioDao userDao = new UsuarioDao();
		try {
			Usuario u = userDao.traerUsuarioPorId(Integer.valueOf(request.getParameter("id_usuario")));
			if (u != null) {
				String datos = "";
				datos = "<input id=\"id_usuario\" name=\"id_usuario\" type=\"hidden\" value=\"" + u.getId() + "\">"+
						"<input id=\"id_rol\" name=\"id_rol\" type=\"hidden\" value=\"" + u.getId_rol() + "\">"+
						"<input type=\"hidden\" id=\"verificado\" name=\"verificado\" value=\"" + u.isVerificado() + "\">"+
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
									"<label for=\"exampleInputEmail1\">Fecha de Nacimiento</label>" +
										"<input type=\"date\" class=\"form-control\" id=\"nacimientoUsuario\" name=\"nacimientoUsuario\" " +
										"placeholder=\"Fecha de nacimiento\" value=\"" + FuncionesVarias.getStringDate(u.getFechaNacimiento(), 1) + "\"> "+
								"</div>"	+
							"</div>"+
							"<div class=\"form-group row\">"+
								"<div class=\"col\">" +
									"<label for=\"exampleInputEmail1\">Telefono</label>" +
										"<input type=\"text\" class=\"form-control\" id=\"telefonoUsuario\" name=\"telefonoUsuario\" " +
										"placeholder=\"Telefono del usuario\" value=\"" + u.getTelefono() + "\">" +
								"</div>"	+
								"<div class=\"col\">" +
									"<label for=\"exampleInputEmail1\">Contraseña</label>" +
										"<input type=\"text\" class=\"form-control\" id=\"passUsuario\" name=\"passUsuario\" " +
										"placeholder=\"Contraseña del usuario\" value=\"" + u.getPass() + "\">" +
								"</div>" +
							"</div>" +
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

}
