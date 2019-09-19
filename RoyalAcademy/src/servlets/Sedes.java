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
import dao.SedeDao;
import modelo.ContenedorResponse;
import modelo.Funciones;
import modelo.Funciones_Perfil;
import modelo.Rol;
import modelo.Sede;
import modelo.Select_Funciones_Perfil;

/**
 * Servlet implementation class ServletLoggedAdmin
 */
@WebServlet("/Sedes")
public class Sedes extends ServletIncludeGoto {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Sedes() {
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
				case "guardarSede":
					guardarSede(request, response);
					break;
				case "selectSedes":

					selectSedes(request, response);

					break;
				
				case "eliminarSede":

					eliminarSede(request, response);

					break;

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void eliminarSede(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		FuncionesDao funcionesDao = new FuncionesDao();
		SedeDao sedeDao = new SedeDao();
		try {

			int cantidad_de_carreras = funcionesDao.aux_select_int("select id_sede from sede_carrera where id_sede = "
					+ Integer.parseInt(request.getParameter("id_sede"))+" limit 1");

			if (cantidad_de_carreras > 0) {
				error.setCd_error(1);
				error.setDs_error("La sede contiene carreras asignadas, no puede ser eliminada.");
				error.setTipo("error");
			} else {

				funcionesDao.aux_upd(
						"delete from sedes where id=" + Integer.parseInt(request.getParameter("id_sede")));
				error.setCd_error(1);
				error.setDs_error("Se eliminó la sede correctamente.");
				error.setTipo("success");

			}
			List<Sede> sedes = sedeDao.traerTodos();
			String options = "";
			for (Sede auxsedes : sedes) {
				options = options + "<option " + (auxsedes.getId() == sedes.get(0).getId() ? "selected" : "") + " value=\""
						+ auxsedes.getId() + "\">" + auxsedes.getSede() + "</option>";
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

	private void selectSedes(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		SedeDao sedeDao = new SedeDao();
		try {
			
			Sede sede = sedeDao.traerSedePorId(Integer.parseInt(request.getParameter("id_sede")));
			
			
			contenedorResponse.setData(sede);
			
			
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

	

	private void guardarSede(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		SedeDao sedeDao = new SedeDao();
		try {

			Sede sede = new Sede();
			if (Integer.parseInt(request.getParameter("id_sede")) != 0) {
				sede.setId(Integer.parseInt(request.getParameter("id_sede")));
			}

			sede.setSede(request.getParameter("ds_sede"));
			sede.setId_pais(Integer.parseInt(request.getParameter("select_pais")));

			if (sedeDao.save_tabla(sede)) {

				error.setCd_error(1);
				error.setDs_error("Se guardaron los cambios en la sede.");
				error.setTipo("success");

				List<Sede> sedes = sedeDao.traerTodos();
				String options = "";
				for (Sede auxsede : sedes) {
					options = options + "<option " + (auxsede.getId() == sede.getId() ? "selected" : "") + " value=\""
							+ auxsede.getId() + "\">" + auxsede.getSede() + "</option>";
				}
				contenedorResponse.setData(options);
			} else {
				error.setCd_error(1);
				error.setDs_error("Error al guardar la sede.");
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
