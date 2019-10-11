package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import dao.PreguntaDao;
import dao.RolDao;
import modelo.ContenedorResponse;
import modelo.Curso;
import modelo.CursoPreguntas;
import modelo.Funciones;
import modelo.Funciones_Perfil;
import modelo.Opciones_Pregunta;
import modelo.Pregunta;
import modelo.Rol;
import modelo.Select_Funciones_Perfil;

/**
 * Servlet implementation class ServletLoggedAdmin
 */
@WebServlet("/Preguntas")
public class Preguntas extends ServletIncludeGoto {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Preguntas() {
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
				
				case "selectCarreraCurso":

					selectCarreraCurso(request, response);

					break;
				case "selectPregunta":
					
					selectPregunta(request,response);
					
					break;
				case "guardarPregunta":
					
					guardarPregunta(request,response);
					
					break;
				case "selectCursoPregunta":
					
					selectCursoPregunta(request,response);
					break;
				case "eliminarPregunta":
					eliminarPregunta(request,response);
				
				break;
				

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void eliminarPregunta(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		CursoDao cursoDao = new CursoDao();
		PreguntaDao preguntaDao = new PreguntaDao();
		String option_cursos = "";
		CursoPreguntas cursoPreguntas = new CursoPreguntas();
		String html_preguntas = "<div class=\"alert alert-danger mt-3\" role=\"alert\">No hay Preguntas</div>";
		try {
		
			if(Integer.parseInt(request.getParameter("id_pregunta"))!=0) {
				
				preguntaDao.aux_upd("delete from opciones_pregunta where id_pregunta=" + Integer.parseInt(request.getParameter("id_pregunta")));
				preguntaDao.aux_upd("delete from preguntas where id=" + Integer.parseInt(request.getParameter("id_pregunta")));

			}

			
			List<Pregunta> preguntas = preguntaDao.traerPreguntaPorCarreraCurso(
					Integer.parseInt(request.getParameter("id_carrera")),
					Integer.parseInt(request.getParameter("id_curso")));

			if (!preguntas.isEmpty()) {
				html_preguntas = "";
			}

			for (Pregunta aux_pregunta : preguntas) {

				html_preguntas += "<div class=\"row card card-styles\" id=\"card_"+aux_pregunta.getId()+"\"> "		
						+ "<div class=\"card-body\" onclick=\"selectPregunta(" + aux_pregunta.getId()+ ");\">" + "<div " + "id=\"pregunta_" + aux_pregunta.getId() + "\">" + aux_pregunta.getPregunta()
						+ "</div>" +  "</div>"  + "</div>";

			}

			cursoPreguntas.setPreguntas(html_preguntas);

			error.setCd_error(1);
			error.setDs_error("Se eliminó la pregunta correctamente.");
			error.setTipo("success");

		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(cursoPreguntas);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();

	}

	private void guardarPregunta(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		CursoDao cursoDao = new CursoDao();
		PreguntaDao preguntaDao = new PreguntaDao();
		String option_cursos = "";
		CursoPreguntas cursoPreguntas = new CursoPreguntas();
		String html_preguntas = "<div class=\"alert alert-danger mt-3\" role=\"alert\">No hay Preguntas</div>";
		try {
			Boolean pregunta_nueva = true;
			int cantidad_opciones = Integer.parseInt(request.getParameter("cantidad_opciones"));

			Pregunta pregunta = new Pregunta();

			pregunta.setId_carrera(Integer.parseInt(request.getParameter("id_carrera")));
			pregunta.setId_curso(Integer.parseInt(request.getParameter("id_curso")));
			pregunta.setPregunta(request.getParameter("pregunta"));

			if (Integer.parseInt(request.getParameter("id_pregunta")) != 0) {
				pregunta.setId(Integer.parseInt(request.getParameter("id_pregunta")));
				pregunta_nueva = false;
			}

			preguntaDao.save_tabla(pregunta);

			preguntaDao.aux_upd("delete from opciones_pregunta where id_pregunta=" + pregunta.getId());

			for (int i = 1; i < cantidad_opciones + 1; i++) {

				if(request.getParameter("opcion_pregunta_" + i)!=null) {
					Opciones_Pregunta opcion = new Opciones_Pregunta();
	
					opcion.setId_pregunta(pregunta.getId());
					opcion.setRespuesta(request.getParameter("opcion_pregunta_" + i));
	
					if (i == Integer.parseInt(request.getParameter("opcion_correcta"))) {
						opcion.setRespuesta_correcta(true);
					}
	
					preguntaDao.save_tabla(opcion);
				}

			}

			List<Pregunta> preguntas = preguntaDao.traerPreguntaPorCarreraCurso(
					Integer.parseInt(request.getParameter("id_carrera")),
					Integer.parseInt(request.getParameter("id_curso")));

			if (!preguntas.isEmpty()) {
				html_preguntas = "";
			}

			for (Pregunta aux_pregunta : preguntas) {

				html_preguntas += "<div class=\"row card card-styles\" id=\"card_"+aux_pregunta.getId()+"\"> " 
						+ "<div class=\"card-body\"  onclick=\"selectPregunta(" + aux_pregunta.getId()+ ");\" ><div id=\"pregunta_" + aux_pregunta.getId() + "\">" + aux_pregunta.getPregunta()
						+ "</div>" +  "</div>" +  "</div>";

			}

			cursoPreguntas.setPreguntas(html_preguntas);

			error.setCd_error(1);
			error.setDs_error("Se guardó la pregunta correctamente.");
			error.setTipo("success");

		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(cursoPreguntas);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();

	}

	private void selectPregunta(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("text/html");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		PreguntaDao preguntaDao = new PreguntaDao();
		String option_cursos = "";
		try {

			Pregunta pregunta = preguntaDao.traerPreguntaPorId(Integer.parseInt(request.getParameter("id_pregunta")));
			List<Opciones_Pregunta> opciones = preguntaDao
					.traerOpciones(Integer.parseInt(request.getParameter("id_pregunta")));

			request.setAttribute("pregunta", pregunta.getPregunta());
			request.setAttribute("id_pregunta", pregunta.getId());
			request.setAttribute("cantidad_opciones", (opciones.size() == 0 ? 1 : opciones.size()));
			request.setAttribute("opciones", opciones);

			includePage("/form_pregunta.jsp", request, response);

		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

	}

	private void selectCarreraCurso(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		CursoDao cursoDao = new CursoDao();
		PreguntaDao preguntaDao = new PreguntaDao();
		String option_cursos = "";
		CursoPreguntas cursoPreguntas = new CursoPreguntas();
		String html_preguntas = "<div class=\"alert alert-danger mt-3\" role=\"alert\">No hay Preguntas</div>";
		try {

			List<Curso> cursos = cursoDao.traerCursoCarrera(Integer.parseInt(request.getParameter("id_carrera")));

			for (Curso curso : cursos) {

				option_cursos += "<option value=\"" + curso.getId() + "\">" + curso.getDenominacion() + "</option>";

			}
			if (!cursos.isEmpty()) {

				List<Pregunta> preguntas = preguntaDao.traerPreguntaPorCarreraCurso(
						Integer.parseInt(request.getParameter("id_carrera")), cursos.get(0).getId());

				if (!preguntas.isEmpty()) {
					html_preguntas = "";
				}

				for (Pregunta pregunta : preguntas) {

					html_preguntas += "<div class=\"row card card-styles\" id=\"card_"+pregunta.getId()+"\"> "
							+ "<div class=\"card-body\" onclick=\"selectPregunta(" + pregunta.getId()+ ");\"><div id=\"pregunta_" + pregunta.getId() + "\">" + pregunta.getPregunta() + "</div>"
							+ "</div>" + "</div>";

				}

			}

			cursoPreguntas.setPreguntas(html_preguntas);
			cursoPreguntas.setOption_cursos(option_cursos);

		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(cursoPreguntas);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();

	}

	private void selectCursoPregunta(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		CursoDao cursoDao = new CursoDao();
		PreguntaDao preguntaDao = new PreguntaDao();
		String option_cursos = "";
		CursoPreguntas cursoPreguntas = new CursoPreguntas();
		String html_preguntas = "<div class=\"alert alert-danger mt-3\" role=\"alert\">No hay Preguntas</div>";
		try {

			List<Pregunta> preguntas = preguntaDao.traerPreguntaPorCarreraCurso(
					Integer.parseInt(request.getParameter("id_carrera")),
					Integer.parseInt(request.getParameter("id_curso")));

			if (!preguntas.isEmpty()) {
				html_preguntas = "";
			}

			for (Pregunta pregunta : preguntas) {

				html_preguntas += "<div class=\"row card card-styles\" id=\"card_"+pregunta.getId()+"\"> "
						+ "<div class=\"card-body\" onclick=\"selectPregunta(" + pregunta.getId()+");\"><div id=\"pregunta_" + pregunta.getId() + "\">" + pregunta.getPregunta() + "</div>" + "</div>"
						+ "</div>";

			}

			cursoPreguntas.setPreguntas(html_preguntas);

		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(cursoPreguntas);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();

	}

}
