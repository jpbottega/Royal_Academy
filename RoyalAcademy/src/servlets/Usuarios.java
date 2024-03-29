package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import funciones.FuncionesVarias;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.CarreraDao;
import dao.CursoDao;
import dao.FuncionesDao;
import dao.RolDao;
import dao.UsuarioDao;
import modelo.Carrera;
import modelo.ContenedorResponse;
import modelo.Curso;
import modelo.Curso_Usuario;
import modelo.Funciones;
import modelo.Rol;
import modelo.Sede;
import modelo.Sede_Usuario;
import modelo.Select_Sedes_Carrera;
import modelo.Usuario;
import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;  
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
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
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
					
				case "agregarSede":
					agregarSede(request, response);
					break;
					
				case "eliminarSede":
					eliminarSede(request, response);
					break;
				
				case "agregarCurso":
					agregarCurso(request, response);
					break;
					
				case "eliminarCurso":
					eliminarCurso(request, response);
					break;
					
				case "actualizarCursos":
					actualizarCursos(request, response);
					break;
					
				case "actualizarSedes":
					actualizarSedes(request, response);
					break;
					
				case "esRolDocAlumAdmin":
					esRolDocAlumAdmin(request, response);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void esRolDocAlumAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		new UsuarioDao();
		FuncionesDao funcDao = new FuncionesDao();
		new Select_Sedes_Carrera();
		int funcionHabilitada = 0;
		try {
			if (request.getParameter("rol_usuario") != null) {
				// traigo las funciones que tiene asignado el usuario
				List<Funciones> funciones = funcDao.traerFuncionesHabilitadas(Integer.parseInt(request.getParameter("rol_usuario")));
				for (Funciones f : funciones) {
					if (f.getId_funcion() == 10 && funcionHabilitada == 0) { // la funcion admin en la bd
						funcionHabilitada = 3;
					} else if (f.getId_funcion() == 11 && funcionHabilitada == 0) { // la funcion docente en la bd
						funcionHabilitada = 2;
					} else if (f.getId_funcion() == 12 && funcionHabilitada == 0) { // la funcion alumno en la bd
						funcionHabilitada = 1;
					}
				}
			}
		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}
		contenedorResponse.setData(funcionHabilitada);
		contenedorResponse.setError(error);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
	}
	
	private void actualizarSedes(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		UsuarioDao userDao = new UsuarioDao();
		Select_Sedes_Carrera select_sedes_perfil = new Select_Sedes_Carrera();
		try {
				List<Sede> curso_disponible = userDao
						.traerSedesDisponibles(Integer.parseInt(request.getParameter("id_usuario")));
				List<Sede> curso_habilitado = userDao
						.traerSedesHabilitadas(Integer.parseInt(request.getParameter("id_usuario")));

				String options_habilitadas = "";
				String options_disponibles = "";

				for (Sede curso : curso_habilitado) {
					options_habilitadas = options_habilitadas + "<option value=\"" + curso.getId() + "\">"
							+ curso.getSede() + "</option>";
				}
				for (Sede curso : curso_disponible) {
					options_disponibles = options_disponibles + "<option value=\"" + curso.getId() + "\">"
							+ curso.getSede() + "</option>";
				}

				select_sedes_perfil.setSedes_disponibles(options_disponibles);
				select_sedes_perfil.setSedes_habilitadas(options_habilitadas);

				error.setCd_error(1);
				error.setDs_error("Actualizados los cursos mostrados.");
				error.setTipo("success");		

		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(select_sedes_perfil);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
	}

	private void actualizarCursos(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		UsuarioDao userDao = new UsuarioDao();
		Select_Sedes_Carrera select_sedes_perfil = new Select_Sedes_Carrera();
		try {
				List<Curso> curso_disponible = userDao
						.traerCursosDisponibles(Integer.parseInt(request.getParameter("id_usuario")), Integer.parseInt(request.getParameter("id_carrera")));
				List<Curso> curso_habilitado = userDao
						.traerCursosHabilitadas(Integer.parseInt(request.getParameter("id_usuario")));

				String options_habilitadas = "";
				String options_disponibles = "";

				for (Curso curso : curso_habilitado) {
					options_habilitadas = options_habilitadas + "<option value=\"" + curso.getId() + "\">"
							+ curso.getDenominacion() + "</option>";
				}
				for (Curso curso : curso_disponible) {
					options_disponibles = options_disponibles + "<option value=\"" + curso.getId() + "\">"
							+ curso.getDenominacion() + "</option>";
				}

				select_sedes_perfil.setSedes_disponibles(options_disponibles);
				select_sedes_perfil.setSedes_habilitadas(options_habilitadas);

				error.setCd_error(1);
				error.setDs_error("Actualizados los cursos mostrados.");
				error.setTipo("success");		

		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(select_sedes_perfil);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
	}
	
	private void eliminarCurso(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		UsuarioDao userDao = new UsuarioDao();
		Select_Sedes_Carrera select_sede_carrera = new Select_Sedes_Carrera();
		try {

			if (Integer.parseInt(request.getParameter("id_usuario")) == 0) {

				error.setCd_error(1);
				error.setDs_error("Debe primero guardar el usuario.");
				error.setTipo("error");

			} else {

				String[] cursos_seleccionados = request.getParameter("string_sedes_seleccionadas").split(";");

				for (String curso : cursos_seleccionados) {

					if (!curso.isEmpty()) {
						Curso_Usuario sede_carrera = new Curso_Usuario(Integer.parseInt(curso),
								Integer.parseInt(request.getParameter("id_usuario")), 0);

						userDao.delete_tabla(sede_carrera);
					}

				}

				List<Curso> sede_disponibles = userDao
						.traerCursosDisponibles(Integer.parseInt(request.getParameter("id_usuario")), Integer.parseInt(request.getParameter("id_carrera")));
				List<Curso> sede_habilitadas = userDao
						.traerCursosHabilitadas(Integer.parseInt(request.getParameter("id_usuario")));

				String options_habilitadas = "";
				String options_disponibles = "";

				for (Curso curso : sede_habilitadas) {

					options_habilitadas = options_habilitadas + "<option value=\"" + curso.getId() + "\">"
							+ curso.getDenominacion() + "</option>";

				}
				for (Curso curso : sede_disponibles) {

					options_disponibles = options_disponibles + "<option value=\"" + curso.getId() + "\">"
							+ curso.getDenominacion() + "</option>";
				}

				select_sede_carrera.setSedes_disponibles(options_disponibles);
				select_sede_carrera.setSedes_habilitadas(options_habilitadas);

				error.setCd_error(1);
				error.setDs_error("Se eliminaron los cursos del usuario.");
				error.setTipo("success");

			}

		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(select_sede_carrera);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
	}

	private void agregarCurso(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		UsuarioDao userDao = new UsuarioDao();
		Select_Sedes_Carrera select_sedes_perfil = new Select_Sedes_Carrera();
		try {

			if (Integer.parseInt(request.getParameter("id_usuario")) == 0) {

				error.setCd_error(1);
				error.setDs_error("Debe primero guardar el usuario.");
				error.setTipo("error");

			} else {
				Usuario u = userDao.traerUsuarioPorId(Integer.parseInt(request.getParameter("id_usuario")));
				if (u.getId_rol() == Integer.parseInt(request.getParameter("rol_usuario"))) {
				String[] cursos_seleccionados = request.getParameter("string_sedes_seleccionadas").split(";");

				for (String curso : cursos_seleccionados) {

					if (!curso.isEmpty()) {
						Curso_Usuario sede_carrera = new Curso_Usuario(Integer.parseInt(curso),
								Integer.parseInt(request.getParameter("id_usuario")), 0);

						userDao.save_tabla(sede_carrera);
					}

				}

				List<Curso> curso_disponible = userDao
						.traerCursosDisponibles(Integer.parseInt(request.getParameter("id_usuario")), Integer.parseInt(request.getParameter("id_carrera")));
				List<Curso> curso_habilitado = userDao
						.traerCursosHabilitadas(Integer.parseInt(request.getParameter("id_usuario")));

				String options_habilitadas = "";
				String options_disponibles = "";

				for (Curso curso : curso_habilitado) {

					options_habilitadas = options_habilitadas + "<option value=\"" + curso.getId() + "\">"
							+ curso.getDenominacion() + "</option>";

				}
				for (Curso curso : curso_disponible) {

					options_disponibles = options_disponibles + "<option value=\"" + curso.getId() + "\">"
							+ curso.getDenominacion() + "</option>";
				}

				select_sedes_perfil.setSedes_disponibles(options_disponibles);
				select_sedes_perfil.setSedes_habilitadas(options_habilitadas);

				error.setCd_error(1);
				error.setDs_error("Se agregaron los cursos al usuario.");
				error.setTipo("success");
				}
				else {
					error.setCd_error(1);
					error.setDs_error("Debe primero guardar el usuario.");
					error.setTipo("error");
				}
			}

		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(select_sedes_perfil);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
	}
	
	private void eliminarSede(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		UsuarioDao userDao = new UsuarioDao();
		Select_Sedes_Carrera select_sede_carrera = new Select_Sedes_Carrera();
		try {
			if (Integer.parseInt(request.getParameter("id_usuario")) == 0) {

				error.setCd_error(1);
				error.setDs_error("Debe primero guardar el usuario.");
				error.setTipo("error");

			} else {

				String[] sedes_seleccionadas = request.getParameter("string_sedes_seleccionadas").split(";");

				for (String sede : sedes_seleccionadas) {

					if (!sede.isEmpty()) {
						Sede_Usuario sede_carrera = new Sede_Usuario(Integer.parseInt(sede),
								Integer.parseInt(request.getParameter("id_usuario")));

						userDao.delete_tabla(sede_carrera);
					}

				}

				List<Sede> sede_disponibles = userDao
						.traerSedesDisponibles(Integer.parseInt(request.getParameter("id_usuario")));
				List<Sede> sede_habilitadas = userDao
						.traerSedesHabilitadas(Integer.parseInt(request.getParameter("id_usuario")));

				String options_habilitadas = "";
				String options_disponibles = "";

				for (Sede sede : sede_habilitadas) {

					options_habilitadas = options_habilitadas + "<option value=\"" + sede.getId() + "\">"
							+ sede.getSede() + "</option>";

				}
				for (Sede sede : sede_disponibles) {

					options_disponibles = options_disponibles + "<option value=\"" + sede.getId() + "\">"
							+ sede.getSede() + "</option>";
				}

				select_sede_carrera.setSedes_disponibles(options_disponibles);
				select_sede_carrera.setSedes_habilitadas(options_habilitadas);

				error.setCd_error(1);
				error.setDs_error("Se agregaron las sedes al usuario.");
				error.setTipo("success");

			}

		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(select_sede_carrera);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
	}

	private void agregarSede(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		UsuarioDao userDao = new UsuarioDao();
		Select_Sedes_Carrera select_sedes_perfil = new Select_Sedes_Carrera();
		try {

			if (Integer.parseInt(request.getParameter("id_usuario")) == 0) {

				error.setCd_error(1);
				error.setDs_error("Debe primero guardar el usuario.");
				error.setTipo("error");

			} else {

				String[] sedes_seleccionadas = request.getParameter("string_sedes_seleccionadas").split(";");
				Usuario u = userDao.traerUsuarioPorId(Integer.parseInt(request.getParameter("id_usuario")));
				if (u.getId_rol() == Integer.parseInt(request.getParameter("rol_usuario"))) {
					for (String sede : sedes_seleccionadas) {
	
						if (!sede.isEmpty()) {
							Sede_Usuario sede_carrera = new Sede_Usuario(Integer.parseInt(sede),
									Integer.parseInt(request.getParameter("id_usuario")));
	
							userDao.save_tabla(sede_carrera);
						}
	
					}
	
					List<Sede> sede_disponibles = userDao
							.traerSedesDisponibles(Integer.parseInt(request.getParameter("id_usuario")));
					List<Sede> sede_habilitadas = userDao
							.traerSedesHabilitadas(Integer.parseInt(request.getParameter("id_usuario")));
	
					String options_habilitadas = "";
					String options_disponibles = "";
	
					for (Sede sede : sede_habilitadas) {
	
						options_habilitadas = options_habilitadas + "<option value=\"" + sede.getId() + "\">"
								+ sede.getSede() + "</option>";
	
					}
					for (Sede sede : sede_disponibles) {
	
						options_disponibles = options_disponibles + "<option value=\"" + sede.getId() + "\">"
								+ sede.getSede() + "</option>";
					}
	
					select_sedes_perfil.setSedes_disponibles(options_disponibles);
					select_sedes_perfil.setSedes_habilitadas(options_habilitadas);
	
					error.setCd_error(1);
					error.setDs_error("Se agregaron las sedes al usuario.");
					error.setTipo("success");
				}
				else {
					error.setCd_error(1);
					error.setDs_error("Debe primero guardar el usuario.");
					error.setTipo("error");
				}
			}

		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(select_sedes_perfil);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
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
				for (Usuario usuario : usuarios) {
					datos += "<div class=\"row card card-styles\" onclick=\"selectTarjeta("+usuario.getId()+")\" id=\""+usuario.getId()+"\">" +  
								"<div class=\"card-body\">" +
										"<div class=\"card-title\">" +usuario.getNombre() + " " + usuario.getApellido() + "</div>" + 
										"<div class=\"card-subtitle mb-2 text-muted\">" + usuario.getEmail() + "</div>" + 
										"<div class=\"card-text text-muted\">Tel: " + usuario.getTelefono() + "</div>" + 
										"<div class=\"card-text text-muted\">DNI: " + usuario.getDni() + "</div>" + 
										"<div class=\"card-text text-muted\">Nacimiento: " + FuncionesVarias.getStringDate(usuario.getFechaNacimiento(), 1) + "</div>" +
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
					user.setFechaNacimiento(FuncionesVarias.getDateString(request.getParameter("nacimientoUsuario"),1));
					user.setId_rol(Integer.valueOf(request.getParameter("rol_usuario"))); // me esta llegando vacio
					user.setVerificado(Boolean.valueOf(request.getParameter("verificado")));
					user.setDni(request.getParameter("dniUsuario"));
					
				} else { // si es un usuario nuevo
					user.setNombre(request.getParameter("nombreUsuario"));
					user.setApellido(request.getParameter("apellidoUsuario"));
					user.setEmail(request.getParameter("mailUsuario"));
					user.setTelefono(request.getParameter("telefonoUsuario"));
					user.setPass(request.getParameter("dniUsuario"));					
					user.setFechaNacimiento(FuncionesVarias.getDateString(request.getParameter("nacimientoUsuario"),1));
					user.setId_rol(Integer.valueOf(request.getParameter("rol_usuario"))); // me esta llegando vacio	
					user.setDni(request.getParameter("dniUsuario"));
					user.setVerificado(false);
				}
				
				if (user.getId() == 0 && userDao.traerUsuarioPorMail(user.getEmail()) == null) { // si es un nuevo usuario y existe no lo deberia insertar
					enviarVerficacionUsuario(user); // si es un usuario nuevo le envio un mail con la pass
					if (userDao.save_tabla(user)) {
						user.setId(userDao.aux_select_int("select id from usuarios where email = '" + user.getEmail() + "';"));
						contenedorResponse.setData(user);
						error.setCd_error(1);
						error.setDs_error("Se guardo el usuario correctamente.");
						error.setTipo("success");
					} else { 
						error.setCd_error(1);
						error.setDs_error("No se ha podido guardar el usuario.");
						error.setTipo("error");
					} 
				} else if (user.getId() != 0) { // si es un usuario existente
					if (userDao.save_tabla(user)) {
						contenedorResponse.setData(user);
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
		} catch (MessagingException me) {
			error.setCd_error(1);
			error.setDs_error("El correo es invalido. Verifique la direccion ingresada.");
			error.setTipo("error");
			me.printStackTrace();
		}
		catch (Exception e) {
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

	@SuppressWarnings("unused")
	private void selectUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		UsuarioDao userDao = new UsuarioDao();
		RolDao rolDao = new RolDao();
		new CursoDao();
		CarreraDao carreraDao = new CarreraDao();
		try {
			Usuario u = userDao.traerUsuarioPorId(Integer.valueOf(request.getParameter("id_usuario")));
			List<Rol> roles = rolDao.traerTodos();
			List<Sede> sedesHabilitadas = userDao.traerSedesHabilitadas(u.getId());
			List<Sede> sedesDisponibles = userDao.traerSedesDisponibles(u.getId());
			List<Carrera> listaCarreras = carreraDao.traerTodos();
			List<Curso> cursosDisponibles = userDao.traerCursosDisponibles(u.getId(), 0);
			List<Curso> cursosHabilitados = userDao.traerCursosHabilitadas(u.getId());
			if (u != null) {
				String datos = "";
				datos += "<div class=\"row panel-usuarios\">";
				datos += "<input id=\"id_usuario\" name=\"id_usuario\" type=\"hidden\" value=\"" + u.getId() + "\">"+
						"<input id=\"id_rol\" name=\"id_rol\" type=\"hidden\" value=\"" + u.getId_rol() + "\">"+
						"<input type=\"hidden\" id=\"verificado\" name=\"verificado\" value=\"" + u.isVerificado() + "\">"+
						"<input type=\"hidden\" id=\"passUsuario\" name=\"passUsuario\" value=\"" + u.getPass() + "\">"+
								"<div class=\"col-md-12 col-lg-6\">" +
									"<div class=\"form-group\">"+
									"	<label for=\"exampleInputEmail1\">Nombre</label>" +
										"<input type=\"text\" class=\"form-control\" id=\"nombreUsuario\" name=\"nombreUsuario\" " +
										"placeholder=\"Nombre del usuario\" value=\"" + u.getNombre()+ "\">" +
									"</div>"+
								"</div>"+
								"<div class=\"col-md-12 col-lg-6\">" +
									"<div class=\"form-group\">"+
										"<label for=\"exampleInputEmail1\">Apellido</label>" +
										"<input type=\"text\" class=\"form-control\" id=\"apellidoUsuario\" name=\"apellidoUsuario\" " +
										"placeholder=\"Apellido del usuario\" value=\"" + u.getApellido() + "\">"+
									"</div>"+
								"</div>"+
								"<div class=\"col-md-12 col-lg-6\">" +
									"<div class=\"form-group\"> "+
										"<label for=\"exampleInputEmail1\">E-Mail</label>" +
										"<input type=\"email\" class=\"form-control\" id=\"mailUsuario\" name=\"mailUsuario\" " +
										"placeholder=\"Correo del usuario\" value=\"" + u.getEmail() + "\">" +
									"</div>"+
								"</div>"+
								"<div class=\"col-md-12 col-lg-6\">" +
									"<div class=\"form-group\"> "+
										"<label for=\"nacimientoUsuario\">Fecha de Nacimiento</label> <input "+
										"type='text'  id=\"nacimientoUsuario\" "+
										"name=\"nacimientoUsuario\" placeholder=\"dd/mm/aaaa\" class=\"form-control\" value=\"" + FuncionesVarias.getStringDate(u.getFechaNacimiento(), 1) + "\"/> "+
									"</div>"+
								"</div>"	+
								"<div class=\"col-md-12 col-lg-6\">" +
									"<div class=\"form-group\"> "+
										"<label for=\"exampleInputEmail1\">Telefono</label>" +
										"<input type=\"text\" class=\"form-control\" id=\"telefonoUsuario\" name=\"telefonoUsuario\" " +
										"placeholder=\"Telefono del usuario\" value=\"" + u.getTelefono() + "\">" +
									"</div>"	+
								"</div>"	+
								"<div class=\"col-md-12 col-lg-6\">" +
									"<div class=\"form-group\"> "+
										"<label for=\"exampleInputEmail1\">DNI</label>" +
										"<input type=\"text\" class=\"form-control\" id=\"dniUsuario\" name=\"dniUsuario\" " +
										"placeholder=\"Dni del usuario\" value=\"" + u.getDni() + "\">" +
									"</div>"	+
								"</div>" +
								"<div class=\"col-md-12 col-lg-6\">"+
								"<div class=\"form-group\">"+
									"<label for=\"exampleFormControlSelect1\">Roles</label> <select "+
										"class=\"form-control\" id=\"rol_usuario\" name=\"rol_usuario\" onchange=\"cambioRolAdminOrganizacion();\"> ";
										if (!roles.isEmpty()) {
											for (Rol rol : roles) {
										datos +="<option value=\""+rol.getId()+"\""+(rol.getId()==u.getId_rol()?"selected":"")+">"+rol.getDenominacion()+"</option> ";
											}
										}
										
										datos +="</select>"+
												"</div>"+
									"</div>"+
								"<div class=\"col-md-12 col-lg-6\" id=\"seleccion_carreras\">"+
									"<div class=\"form-group\">"+
								"<label for=\"exampleFormControlSelect1\">Carreras</label> <select "+
									"class=\"form-control\" id=\"id_carrera\" name=\"id_carrera\" onchange=\"actualizarCursos();\"> ";
										datos +="<option value=\"0\" selected>"+ "Todas" +"</option> ";
										if (!listaCarreras.isEmpty()) {
											for (Carrera carrera : listaCarreras) {
												datos +="<option value=\""+carrera.getId()+"\">"+carrera.getDenominacion()+"</option> ";
											}
										}
									
									datos +="</select>"+
									"</div>"+
							"</div>"+
							"<div class=\"col-12\">"+
								// para administrar sedes en q se inscribe al alumno:
								"<div class=\"row\" id=\"adminSedesUsuario\">" +
									"<div class=\"col-10 col-xl-5\">" +
										"<div class=\"form-group\">" +
											"<label for=\"exampleFormControlSelect1\">Sedes Disponibles</label> <select " +
												"class=\"form-control\" id=\"sedes_disponibles_usuario\" multiple>";
													if (!sedesDisponibles.isEmpty()) {
														for (Sede sede : sedesDisponibles) {
															datos += "<option value=\"" + sede.getId() + "\">" + sede.getSede() + "</option>";
														}
													}
											datos += "</select>" + 
										"</div>" +
									"</div>" +
									"<div class=\"col-2 col-xl-1 btn-funciones-usuarios\">" +
									"<div>"+
										"<button type=\"button\"" +
											"class=\"btn btn-default button-funciones-perfil\"" +
											"title=\"Agregar Sede\" onclick=\"agregarSedeUsuario();\">>></button>" +
										"<button type=\"button\"" +
											"class=\"btn btn-default button-funciones-perfil\"" +
											"title=\"Eliminar Sede\" onclick=\"eliminarSedeUsuario();\"><<</button>" +
											"</div>" +
											"</div>" +
									"<div class=\"col-10 col-xl-5\">" +
										"<div class=\"form-group\">" +
											"<label for=\"exampleFormControlSelect1\">Sedes Habilitadas</label> <select " +
												"class=\"form-control\" id=\"sedes_habilitadas_usuario\" multiple>";
													if (!sedesHabilitadas.isEmpty()) {
														for (Sede sede : sedesHabilitadas) {
															datos += "<option value=\"" + sede.getId() + "\">"+ sede.getSede() + "</option>";
														}
													}
											datos += "</select>" +
												"</div>" +
									"</div>" +
									"</div>" +
								"</div>";
						// para administrar cursos de los docentes:
						datos += "<div class=\"col-12\">"+
								 "<div class=\"row\" id=\"adminCursosUsuario\">" +
								"<div class=\"col-10 col-xl-5\">" +
									"<div class=\"form-group\">" +
										"<label for=\"exampleFormControlSelect1\">Cursos Disponibles</label> <select " +
											"class=\"form-control\" id=\"cursos_disponibles_usuario\" multiple>";
												if (!cursosDisponibles.isEmpty()) {
													for (Curso curso : cursosDisponibles) {
														datos += "<option value=\"" + curso.getId() + "\">" + curso.getDenominacion() + "</option>";
													}
												}
										datos += "</select>" + 
									"</div>" +
								"</div>" +
								"<div class=\"col-2 col-xl-1 btn-funciones-usuarios\">" +
								"<div>"+
									"<button type=\"button\"" +
										"class=\"btn btn-default button-funciones-perfil\"" +
										"title=\"Agregar Curso\" onclick=\"agregarCursoUsuario();\">>></button>" +
									"<button type=\"button\"" +
										"class=\"btn btn-default button-funciones-perfil\"" +
										"title=\"Eliminar Curso\" onclick=\"eliminarCursoUsuario();\"><<</button>" +
										"</div>" +
										"</div>" +
								"<div class=\"col-10 col-xl-5\">" +
									"<div class=\"form-group\">" +
										"<label for=\"exampleFormControlSelect1\">Cursos Habilitados</label> <select " +
											"class=\"form-control\" id=\"cursos_habilitadas_usuario\" multiple>";
												if (!cursosHabilitados.isEmpty()) {
													for (Curso curso : cursosHabilitados) {
														datos += "<option value=\"" + curso.getId() + "\">"+ curso.getDenominacion()+ "</option>";
													}
												}
										datos += "</select>" +
											"</div>" +
								"</div>" +
								"</div>" +
							"</div>";
						datos += "<div class=\"col-12 float-right\"><button type=\"button\" class=\"btn btn-success pull-right\"" +
							"onclick=\"guardarUsuario();\" id=\"botonGuardarUsuario\">Guardar</button>" +
						"<button type=\"button\" class=\"btn btn-danger pull-right\"" +
							"onclick=\"eliminarUsuario();\" id=\"botonEliminarUsuario\">Eliminar</button>";
						datos +="</div></div>";
				error.setCd_error(1);
				error.setDs_error("Habilitada modificacion para usuario " + u.getNombre() + " " + u.getApellido() + ".");
				error.setTipo("success");
				contenedorResponse.setData(datos);
			} 
			else {
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
	public class SMTPAuthenticator extends Authenticator
	{
	    public PasswordAuthentication getPasswordAuthentication()
	    {
	        return new PasswordAuthentication("royalacademyuna@gmail.com", "proyecto2019");
	    }
	}
}


