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
import dao.CursoExamenDao;
import modelo.AlumnoNotas;
import modelo.ContenedorResponse;
import modelo.Curso;
import modelo.CursoExamen;
import modelo.Usuario;

/**
 * Servlet implementation class InscripcionExamen
 */
@WebServlet("/InscripcionExamen")
public class InscripcionExamen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InscripcionExamen() {
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
				case "traerCursos":
					traerCursos(request, response);
					break;
					
				case "traerExamenes":
					traerExamenes(request, response);
					break;
					
				case "inscribir":
					inscribirExamen(request, response);
					break;
					
				case "desinscribir":
					desinscribirExamen(request, response);
					break;

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void traerCursos(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		CursoDao cursoDao = new CursoDao();
		String option_cursos = "";
		try {

			List<Curso> cursos = cursoDao.traerCursoCarrera(Integer.parseInt(request.getParameter("id_carrera")));

			for (Curso curso : cursos) {

				option_cursos += "<option value=\"" + curso.getId() + "\">" + curso.getDenominacion() + "</option>";

			}


		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(option_cursos);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
	}
	
	private void traerExamenes(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//falta validacion de no traer examenes de cursos que esten aprobados
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		CursoExamenDao cursoDao = new CursoExamenDao();
		String option_cursos = "";
		try {

			List<CursoExamen> cursos = cursoDao.traerExamenesPorCursoAlumno(Integer.parseInt(request.getParameter("id_curso")), 
					((Usuario) request.getSession().getAttribute("usuario")).getId());

			for (CursoExamen curso : cursos) {

				option_cursos += "<option value=\"" + curso.getId() + "\">" + curso.getDescripcion() + "</option>";

			}


		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(option_cursos);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
	}
	
	private void inscribirExamen(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		CursoDao cursoDao = new CursoDao();
		try {

			modelo.InscripcionExamen insc = new modelo.InscripcionExamen();
			insc.setId_cursoexamen(Integer.parseInt(request.getParameter("id_examen")));
			insc.setId_usuario(Integer.parseInt(request.getParameter("id_usuario")));
			insc.setAprobado(false);
			insc.setEntregado(false);
			insc.setResultado(0);
			
			// esto deberia ir en la inscripcion a un curso
			CursoExamenDao ceDao = new CursoExamenDao();
			int id_curso = ceDao.traerCursoExamenPorId(insc.getId_cursoexamen()).getId_curso();
			ceDao.save_tabla(new AlumnoNotas(insc.getId_usuario(), id_curso));
			//
			
			if (cursoDao.save_tabla(insc)) {
				error.setCd_error(1);
				error.setDs_error("Se ha registrado la inscripcion al examen.");
				error.setTipo("success");
			}
			else {
				error.setCd_error(1);
				error.setDs_error("No se pudo registrar la inscripcion al examen.");
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
	
	private void desinscribirExamen(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		CursoDao cursoDao = new CursoDao();
		try {
			modelo.InscripcionExamen insc = new modelo.InscripcionExamen();
			insc.setId_cursoexamen(Integer.parseInt(request.getParameter("id_examen")));
			insc.setId_usuario(Integer.parseInt(request.getParameter("id_usuario")));
			if (cursoDao.delete_tabla(insc)) {
				error.setCd_error(1);
				error.setDs_error("Se ha eliminado la inscripcion al final.");
				error.setTipo("success");
			}
			else {
				error.setCd_error(1);
				error.setDs_error("No se pudo eliminar la inscripcion al final.");
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
