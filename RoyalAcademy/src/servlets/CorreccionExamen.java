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
import dao.CursoExamenDao;
import dao.ExamenDao;
import dao.PreguntaDao;
import dao.UsuarioDao;
import funciones.FuncionesVarias;
import modelo.ContenedorResponse;
import modelo.CursoExamen;
import modelo.Examen;
import modelo.ExamenResolucion;
import modelo.Opciones_Pregunta;
import modelo.Usuario;

/**
 * Servlet implementation class CorreccionExamen
 */
@WebServlet("/CorreccionExamen")
public class CorreccionExamen extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CorreccionExamen() {
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
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			if (request.getParameter("accion") != null) {

				switch (request.getParameter("accion")) {
				case "traerTarjetasExamenes":
					traerTarjetasExamenes(request, response);
					break;

				case "corregirExamen":
					corregirExamen(request, response);
					break;

				case "selectExamen":
					selectExamen(request, response);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void traerTarjetasExamenes(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		CursoExamenDao examenDao = new CursoExamenDao();
		String tarjetas = "";
		try {
			int id_curso = Integer.parseInt(request.getParameter("id_curso"));
			// traigo los examenes del curso y armo las tarjetas
			List<CursoExamen> examenes = examenDao.traerExamenesPorCurso(id_curso);
			for (CursoExamen e : examenes) {
				tarjetas += "<div class=\"row card card-styles\" onclick=\"selectExamenCorreccion(" + e.getId() + ");\">";
				tarjetas += "<div class=\"card-body\">" + "<div class=\"card-title\">" + e.getDescripcion() + "</div>"
						+ "<div class=\"card-text text-muted\">Fecha: "
						+ FuncionesVarias.getStringDate(e.getFecha(), 1) + "</div>"
						+ "</div>";
				tarjetas += "</div>";
			}
		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(tarjetas);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
	}

	private void corregirExamen(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExamenDao exDao = new ExamenDao();
		CursoExamenDao ceDao = new CursoExamenDao();
		try {
			int id_cursoExamen = Integer.parseInt(request.getParameter("id_examen"));
			CursoExamen cursoExamen = ceDao.traerCursoExamenPorId(id_cursoExamen);
			Examen ex = exDao.traerExamenPorId(cursoExamen.getId_examen());
			int criterioAprobacion = Integer.parseInt(request.getParameter("criterio_aprobacion"));
			ex.setCriterioAprobacion(criterioAprobacion);
			exDao.save_tabla(ex);
			selectExamen(request, response);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void selectExamen(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		CursoExamenDao examenDao = new CursoExamenDao();
		PreguntaDao pregDao = new PreguntaDao();
		UsuarioDao userDao = new UsuarioDao();
		ExamenDao exDao = new ExamenDao();
		String tarjetas = "";
		try {
			int idCursoExamen = Integer.parseInt(request.getParameter("id_examen"));
			// traigo los examenes del curso y armo las tarjetas
			List<modelo.InscripcionExamen> examenes = examenDao.traerInscripcionExamenPorCurso(idCursoExamen);
			CursoExamen ce = examenDao.traerCursoExamenPorId(idCursoExamen);
			Examen ex = exDao.traerExamenPorId(ce.getId_examen());
			float cantidadPreguntas = exDao.traerPreguntasHabilidatas(ce.getId_examen()).size();
			for (modelo.InscripcionExamen e : examenes) {
				float acumulador = 0;
				if (e.isEntregado() && e.getResultado() == 0) { // aca faltaria q no este corregido
					List<ExamenResolucion> examenResolucion = examenDao.traerExamenesResueltos(e.getId_usuario(), idCursoExamen);
					for (ExamenResolucion resuelto : examenResolucion) {
						Opciones_Pregunta op = pregDao.traerOpcionPreguntaPorId(resuelto.getId_respuesta());
						acumulador += (op.getRespuesta_correcta()) ? 1 : 0;
					}
					e.setResultado(Math.round(100*(acumulador/cantidadPreguntas)));
					pregDao.save_tabla(e);
				Usuario u = userDao.traerUsuarioPorId(e.getId_usuario());
				// ponerle un header a la tabla
				tarjetas += "<div class=\"row\">" +
								"<div class=\"col-6\">" + u.getApellido() + ", " + u.getNombre() + "</div>" +
								"<div class=\"col-3\">" + e.getResultado() + "</div>";
								if (ex.getCriterioAprobacion() >= 50) {
									tarjetas += "<div class=\"col-3\">" + ((e.getResultado() >= ex.getCriterioAprobacion()) ? "Aprobado" : "Desaprobado") + "</div>";
								}
								else {
									tarjetas += "<div class=\"col-3\">" + "N/D" + "</div>";

								}
				tarjetas += "</div>";
				}
			}
			tarjetas += "<div class=\"row mt-6\">" + 
					"<label>Criterio Aprobacion" + 
					"<input type=\"text\"" + 
						"class=\"form-control\" id=\"criterio_aprobacion\"" + 
						"name=\"criterio_aprobacion\" placeholder=\"Min. 50\">" + 
						"<button type=\"button\" class=\"btn btn-success\" onclick=\"corregirExamen(" + ce.getId() + ");\">Corregir</button>" +
					"</label>" +
					"</div>";
		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(tarjetas);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
	}
}
