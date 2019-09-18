package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.FuncionesDao;
import dao.RolDao;
import modelo.ContenedorResponse;
import modelo.Funciones;
import modelo.Funciones_Perfil;
import modelo.Rol;
import modelo.Select_Funciones_Perfil;

/**
 * Servlet implementation class ServletLoggedAdmin
 */
@WebServlet("/Perfiles")
public class Perfiles extends ServletIncludeGoto {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Perfiles() {
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
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
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
				case "guardarPerfil":
					guardarPerfil(request, response);
					break;
				case "selectPerfil":

					selectPerfil(request, response);

					break;
				case "agregarFuncion":

					agregarFuncion(request, response);

					break;
				case "eliminarFuncion":

					eliminarFuncion(request, response);

					break;
				case "eliminarPerfil":

					eliminarPerfil(request, response);

					break;

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void eliminarPerfil(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		FuncionesDao funcionesDao = new FuncionesDao();
		RolDao rolDao = new RolDao();
		try {

			int cantidad_de_usuarios = funcionesDao.aux_select_int("select id from usuarios where id_rol = "
					+ Integer.parseInt(request.getParameter("id_perfil"))+" limit 1");

			if (cantidad_de_usuarios > 0) {
				error.setCd_error(1);
				error.setDs_error("El perfil contiene usuarios asignados, no puede ser eliminado.");
				error.setTipo("error");
			} else {

				funcionesDao.aux_upd(
						"delete from roles where id=" + Integer.parseInt(request.getParameter("id_perfil")));
				
				funcionesDao.aux_upd(
						"delete from funciones_perfil where id_rol=" + Integer.parseInt(request.getParameter("id_perfil")));
				
				error.setCd_error(1);
				error.setDs_error("Se eliminó el perfil correctamente.");
				error.setTipo("success");

			}
			List<Rol> roles = rolDao.traerTodos();
			String options = "";
			for (Rol rol2 : roles) {
				options = options + "<option " + (rol2.getId() == roles.get(0).getId() ? "selected" : "") + " value=\""
						+ rol2.getId() + "\">" + rol2.getDenominacion() + "</option>";
			}
			contenedorResponse.setData(options);

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

	private void selectPerfil(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		FuncionesDao funcionesDao = new FuncionesDao();
		Select_Funciones_Perfil select_funciones_perfil = new Select_Funciones_Perfil();
		try {

			if (Integer.parseInt(request.getParameter("id_perfil")) != 0) {

				List<Funciones> funciones_habilitadas = funcionesDao
						.traerFuncionesHabilitadas(Integer.parseInt(request.getParameter("id_perfil")));

				String options_habilitadas = "";

				for (Funciones funciones : funciones_habilitadas) {

					options_habilitadas = options_habilitadas + "<option value=\"" + funciones.getId_funcion() + "\">"
							+ funciones.getDs_funcion() + "</option>";

				}

				select_funciones_perfil.setFunciones_habilitadas(options_habilitadas);

			}
			String options_disponibles = "";

			List<Funciones> funciones_disponibles = funcionesDao
					.traerFuncionesDisponibles(Integer.parseInt(request.getParameter("id_perfil")));

			for (Funciones funciones : funciones_disponibles) {

				options_disponibles = options_disponibles + "<option value=\"" + funciones.getId_funcion() + "\">"
						+ funciones.getDs_funcion() + "</option>";
			}

			select_funciones_perfil.setFunciones_disponibles(options_disponibles);

		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(select_funciones_perfil);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();

	}

	private void eliminarFuncion(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		FuncionesDao funcionesDao = new FuncionesDao();
		Select_Funciones_Perfil select_funciones_perfil = new Select_Funciones_Perfil();
		try {

			if (Integer.parseInt(request.getParameter("id_perfil")) == 0) {

				error.setCd_error(1);
				error.setDs_error("Debe primero guardar el perfil.");
				error.setTipo("error");

			} else {

				String[] funciones_seleccionadas = request.getParameter("string_funciones_seleccionadas").split(";");

				for (String funcion : funciones_seleccionadas) {

					if (!funcion.isEmpty()) {
						Funciones_Perfil funciones_perfil = new Funciones_Perfil(Integer.parseInt(funcion),
								Integer.parseInt(request.getParameter("id_perfil")));

						funcionesDao.delete_tabla(funciones_perfil);
					}

				}

				List<Funciones> funciones_disponibles = funcionesDao
						.traerFuncionesDisponibles(Integer.parseInt(request.getParameter("id_perfil")));
				List<Funciones> funciones_habilitadas = funcionesDao
						.traerFuncionesHabilitadas(Integer.parseInt(request.getParameter("id_perfil")));

				String options_habilitadas = "";
				String options_disponibles = "";

				for (Funciones funciones : funciones_habilitadas) {

					options_habilitadas = options_habilitadas + "<option value=\"" + funciones.getId_funcion() + "\">"
							+ funciones.getDs_funcion() + "</option>";

				}
				for (Funciones funciones : funciones_disponibles) {

					options_disponibles = options_disponibles + "<option value=\"" + funciones.getId_funcion() + "\">"
							+ funciones.getDs_funcion() + "</option>";
				}

				select_funciones_perfil.setFunciones_disponibles(options_disponibles);
				select_funciones_perfil.setFunciones_habilitadas(options_habilitadas);

				error.setCd_error(1);
				error.setDs_error("Se agregaron las funciones al perfil.");
				error.setTipo("success");

			}

		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(select_funciones_perfil);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
	}

	private void agregarFuncion(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		FuncionesDao funcionesDao = new FuncionesDao();
		Select_Funciones_Perfil select_funciones_perfil = new Select_Funciones_Perfil();
		try {

			if (Integer.parseInt(request.getParameter("id_perfil")) == 0) {

				error.setCd_error(1);
				error.setDs_error("Debe primero guardar el perfil.");
				error.setTipo("error");

			} else {

				String[] funciones_seleccionadas = request.getParameter("string_funciones_seleccionadas").split(";");

				for (String funcion : funciones_seleccionadas) {

					if (!funcion.isEmpty()) {
						Funciones_Perfil funciones_perfil = new Funciones_Perfil(Integer.parseInt(funcion),
								Integer.parseInt(request.getParameter("id_perfil")));

						funcionesDao.save_tabla(funciones_perfil);
					}

				}

				List<Funciones> funciones_disponibles = funcionesDao
						.traerFuncionesDisponibles(Integer.parseInt(request.getParameter("id_perfil")));
				List<Funciones> funciones_habilitadas = funcionesDao
						.traerFuncionesHabilitadas(Integer.parseInt(request.getParameter("id_perfil")));

				String options_habilitadas = "";
				String options_disponibles = "";

				for (Funciones funciones : funciones_habilitadas) {

					options_habilitadas = options_habilitadas + "<option value=\"" + funciones.getId_funcion() + "\">"
							+ funciones.getDs_funcion() + "</option>";

				}
				for (Funciones funciones : funciones_disponibles) {

					options_disponibles = options_disponibles + "<option value=\"" + funciones.getId_funcion() + "\">"
							+ funciones.getDs_funcion() + "</option>";
				}

				select_funciones_perfil.setFunciones_disponibles(options_disponibles);
				select_funciones_perfil.setFunciones_habilitadas(options_habilitadas);

				error.setCd_error(1);
				error.setDs_error("Se agregaron las funciones al perfil.");
				error.setTipo("success");

			}

		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(select_funciones_perfil);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
	}

	private void guardarPerfil(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		RolDao rolDao = new RolDao();
		try {

			Rol rol = new Rol();
			if (Integer.parseInt(request.getParameter("id_perfil")) != 0) {
				rol.setId(Integer.parseInt(request.getParameter("id_perfil")));
			}

			rol.setDenominacion(request.getParameter("ds_perfil"));

			if (rolDao.save_tabla(rol)) {

				error.setCd_error(1);
				error.setDs_error("Se guardaron los cambios en el perfil.");
				error.setTipo("success");

				List<Rol> roles = rolDao.traerTodos();
				String options = "";
				for (Rol rol2 : roles) {
					options = options + "<option " + (rol2.getId() == rol.getId() ? "selected" : "") + " value=\""
							+ rol2.getId() + "\">" + rol2.getDenominacion() + "</option>";
				}
				contenedorResponse.setData(options);
			} else {
				error.setCd_error(1);
				error.setDs_error("Error al guardar el perfil.");
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
