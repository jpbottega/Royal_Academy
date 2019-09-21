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

import dao.CarreraDao;
import dao.FuncionesDao;
import dao.RolDao;
import modelo.Carrera;
import modelo.ContenedorResponse;
import modelo.Funciones;
import modelo.Funciones_Perfil;
import modelo.Rol;
import modelo.Sede;
import modelo.Sede_Carrera;
import modelo.Select_Funciones_Perfil;
import modelo.Select_Sedes_Carrera;

/**
 * Servlet implementation class ServletLoggedAdmin
 */
@WebServlet("/Carreras")
public class Carreras extends ServletIncludeGoto {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Carreras() {
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
				case "guardarCarrera":
					guardarCarrera(request, response);
					break;
				case "selectCarrera":

					selectCarrera(request, response);

					break;
				case "agregarSede":

					agregarSede(request, response);

					break;
				case "eliminarSede":

					eliminarSede(request, response);

					break;
				case "eliminarCarrera":

					eliminarCarrera(request, response);

					break;

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void eliminarCarrera(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		CarreraDao CarreraDao = new CarreraDao();
		try {

			int cantidad_de_cursos = CarreraDao.aux_select_int("select id_curso from cursos where id_carrera = "
					+ Integer.parseInt(request.getParameter("id_carrera"))+" limit 1");

			if (cantidad_de_cursos > 0) {
				error.setCd_error(1);
				error.setDs_error("Hay cursos asignados a está carrera, no puede ser eliminada.");
				error.setTipo("error");
			} else {

				CarreraDao.aux_upd(
						"delete from carreras where id=" + Integer.parseInt(request.getParameter("id_carrera")));
				
				CarreraDao.aux_upd(
						"delete from sede_carrera where id_carrera=" + Integer.parseInt(request.getParameter("id_carrera")));
				
				error.setCd_error(1);
				error.setDs_error("Se eliminó la carrera correctamente.");
				error.setTipo("success");

			}
			List<Carrera> carreras = CarreraDao.traerTodos();
			String options = "";
			for (Carrera auxcarrera : carreras) {
				options = options + "<option " + (auxcarrera.getId() == carreras.get(0).getId() ? "selected" : "") + " value=\""
						+ auxcarrera.getId() + "\">" + auxcarrera.getDenominacion() + "</option>";
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

	private void selectCarrera(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		CarreraDao carreraDao = new CarreraDao();
		Select_Sedes_Carrera select_sedes_carrera = new Select_Sedes_Carrera();
		try {

			if (Integer.parseInt(request.getParameter("id_carrera")) != 0) {

				List<Sede> sedes_habilitadas = carreraDao
						.traerSedesHabilitadas(Integer.parseInt(request.getParameter("id_carrera")));

				String options_habilitadas = "";

				for (Sede sede : sedes_habilitadas) {

					options_habilitadas = options_habilitadas + "<option value=\"" + sede.getId() + "\">"
							+ sede.getSede() + "</option>";

				}

				select_sedes_carrera.setSedes_habilitadas(options_habilitadas);

			}
			String options_disponibles = "";

			List<Sede> sede_disponibles = carreraDao
					.traerSedesDisponibles(Integer.parseInt(request.getParameter("id_carrera")));

			for (Sede sede : sede_disponibles) {

				options_disponibles = options_disponibles + "<option value=\"" + sede.getId() + "\">"
						+ sede.getSede() + "</option>";
			}

			select_sedes_carrera.setSedes_disponibles(options_disponibles);

		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(select_sedes_carrera);
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
		CarreraDao carreraDao = new CarreraDao();
		Select_Sedes_Carrera select_sede_carrera = new Select_Sedes_Carrera();
		try {

			if (Integer.parseInt(request.getParameter("id_carrera")) == 0) {

				error.setCd_error(1);
				error.setDs_error("Debe primero guardar la carrera.");
				error.setTipo("error");

			} else {

				String[] sedes_seleccionadas = request.getParameter("string_sedes_seleccionadas").split(";");

				for (String sede : sedes_seleccionadas) {

					if (!sede.isEmpty()) {
						Sede_Carrera sede_carrera = new Sede_Carrera(Integer.parseInt(sede),
								Integer.parseInt(request.getParameter("id_carrera")));

						carreraDao.delete_tabla(sede_carrera);
					}

				}

				List<Sede> sede_disponibles = carreraDao
						.traerSedesDisponibles(Integer.parseInt(request.getParameter("id_carrera")));
				List<Sede> sede_habilitadas = carreraDao
						.traerSedesHabilitadas(Integer.parseInt(request.getParameter("id_carrera")));

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
				error.setDs_error("Se agregaron las sedes a la carrera.");
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
		CarreraDao carreraDao = new CarreraDao();
		Select_Sedes_Carrera select_sedes_perfil = new Select_Sedes_Carrera();
		try {

			if (Integer.parseInt(request.getParameter("id_carrera")) == 0) {

				error.setCd_error(1);
				error.setDs_error("Debe primero guardar la carrera.");
				error.setTipo("error");

			} else {

				String[] sedes_seleccionadas = request.getParameter("string_sedes_seleccionadas").split(";");

				for (String sede : sedes_seleccionadas) {

					if (!sede.isEmpty()) {
						Sede_Carrera sede_carrera = new Sede_Carrera(Integer.parseInt(sede),
								Integer.parseInt(request.getParameter("id_carrera")));

						carreraDao.save_tabla(sede_carrera);
					}

				}

				List<Sede> sede_disponibles = carreraDao
						.traerSedesDisponibles(Integer.parseInt(request.getParameter("id_carrera")));
				List<Sede> sede_habilitadas = carreraDao
						.traerSedesHabilitadas(Integer.parseInt(request.getParameter("id_carrera")));

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
				error.setDs_error("Se agregaron las sedes a la carrera.");
				error.setTipo("success");

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

	private void guardarCarrera(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		CarreraDao carreraDao = new CarreraDao();
		try {

			Carrera carrera = new Carrera();
			if (Integer.parseInt(request.getParameter("id_carrera")) != 0) {
				carrera.setId(Integer.parseInt(request.getParameter("id_carrera")));
			}

			carrera.setDenominacion(request.getParameter("ds_carrera"));

			if (carreraDao.save_tabla(carrera)) {

				error.setCd_error(1);
				error.setDs_error("Se guardaron los cambios en la carrera.");
				error.setTipo("success");

				List<Carrera> carreras = carreraDao.traerTodos();
				String options = "";
				for (Carrera auxcarrera : carreras) {
					options = options + "<option " + (auxcarrera.getId() == carrera.getId() ? "selected" : "") + " value=\""
							+ auxcarrera.getId() + "\">" + auxcarrera.getDenominacion() + "</option>";
				}
				contenedorResponse.setData(options);
			} else {
				error.setCd_error(1);
				error.setDs_error("Error al guardar la carrera.");
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
