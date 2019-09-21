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

import dao.CursoDao;
import dao.FuncionesDao;
import dao.RolDao;
import dao.SedeDao;
import modelo.ContenedorResponse;
import modelo.Curso;
import modelo.Funciones;
import modelo.Funciones_Perfil;
import modelo.Rol;
import modelo.Sede;
import modelo.Select_Funciones_Perfil;

/**
 * Servlet implementation class ServletLoggedAdmin
 */
@WebServlet("/Cursos")
public class Cursos extends ServletIncludeGoto {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Cursos() {
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
				case "guardarCurso":
					guardarCurso(request, response);
					break;
				case "selectCursos":
					selectCursos(request, response);
					break;

				case "eliminarCurso":
					eliminarCurso(request, response);
					break;

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void eliminarCurso(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		FuncionesDao funcionesDao = new FuncionesDao();
		CursoDao cursoDao = new CursoDao();
		try {

			

				funcionesDao.aux_upd("delete from cursos where id=" + Integer.parseInt(request.getParameter("id_curso")));
				error.setCd_error(1);
				error.setDs_error("Se eliminó el curso correctamente.");
				error.setTipo("success");

			
			List<Curso> cursos = cursoDao.traerTodos();
			String options = "";
			for (Curso auxcursos : cursos) {
				options = options + "<option " + (auxcursos.getId() == cursos.get(0).getId() ? "selected" : "")
						+ " value=\"" + auxcursos.getId() + "\">" + auxcursos.getDenominacion() + "</option>";
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

	private void selectCursos(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		CursoDao cursoDao = new CursoDao();
		try {

			Curso curso = cursoDao.traerCursoPorId(Integer.parseInt(request.getParameter("id_curso")));

			contenedorResponse.setData(curso);

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

	private void guardarCurso(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		CursoDao cursoDao = new CursoDao();
		try {

			Curso curso = new Curso();
			if (Integer.parseInt(request.getParameter("id_curso")) != 0) {
				curso.setId(Integer.parseInt(request.getParameter("id_curso")));
			}

			curso.setDenominacion(request.getParameter("ds_curso"));
			curso.setId_carrera(Integer.parseInt(request.getParameter("select_carrera")));

			if (cursoDao.save_tabla(curso)) {

				error.setCd_error(1);
				error.setDs_error("Se guardaron los cambios en el curso.");
				error.setTipo("success");

				List<Curso> cursos = cursoDao.traerTodos();
				String options = "";
				for (Curso auxcurso : cursos) {
					options = options + "<option " + (auxcurso.getId() == auxcurso.getId() ? "selected" : "") + " value=\""
							+ auxcurso.getId() + "\">" + auxcurso.getDenominacion() + "</option>";
				}
				contenedorResponse.setData(options);
			} else {
				error.setCd_error(1);
				error.setDs_error("Error al guardar el curso.");
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
