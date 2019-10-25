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
import modelo.Notas;
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
				tarjetas += "<div class=\"row card card-styles\" onclick=\"selectExamenCorreccion(" + e.getId()
						+ ");\">";
				tarjetas += "<div class=\"card-body\">" + "<div class=\"card-title\">" + e.getDescripcion() + "</div>"
						+ "<div class=\"card-text text-muted\">Fecha: " + FuncionesVarias.getStringDate(e.getFecha(), 1)
						+ "</div>" + "</div>";
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
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		ExamenDao exDao = new ExamenDao();
		CursoExamenDao ceDao = new CursoExamenDao();
		CursoExamenDao examenDao = new CursoExamenDao();
		PreguntaDao pregDao = new PreguntaDao();
		UsuarioDao userDao = new UsuarioDao();
		String tabla = "";
		try {
			int idCursoExamen = Integer.parseInt(request.getParameter("id_examen"));
			CursoExamen cursoExamen = ceDao.traerCursoExamenPorId(idCursoExamen);
			Examen ex = exDao.traerExamenPorId(cursoExamen.getId_examen());
			int criterioAprobacion = Integer.parseInt(request.getParameter("criterio_aprobacion"));
			ex.setCriterioAprobacion(criterioAprobacion);
			exDao.save_tabla(ex);
			// traigo los examenes del curso
			List<modelo.InscripcionExamen> examenes = examenDao.traerInscripcionExamenPorCurso(idCursoExamen);
			for (modelo.InscripcionExamen e : examenes) {
				if (e.isEntregado() && !e.isCorregido()) {
					e.setAprobado(e.getResultado() >= criterioAprobacion);
					e.setCorregido(true);
					pregDao.save_tabla(e); // deberia guardar en masa
					Usuario u = userDao.traerUsuarioPorId(e.getId_usuario());
					tabla += this.traerHtmlTablaExamen(u, e, ex);
					// agrego la nota del examen
					pregDao.save_tabla(
							new Notas(u.getId(), cursoExamen.getId_curso(), Math.round(e.getResultado() / 10), true)); // deberia
																														// guardar
																														// en
																														// masa
				}
			}
		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}
		contenedorResponse.setError(error);
		contenedorResponse.setData(tabla);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
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
			tarjetas += "<div class=\"col-12 col-lg-3 busqueda-correccion\">";
			tarjetas += "<div class=\"row mt-6 pr-3\">" + 
					"<label class=\"w-100\">Criterio Aprobacion" + 
					"<input type=\"text\"" + 
						"class=\"form-control\" id=\"criterio_aprobacion\"" + 
						"name=\"criterio_aprobacion\" placeholder=\"Min. 50\">" + 
						"<button type=\"button\" class=\"btn btn-success w-100 mt-3\" onclick=\"corregirExamen(" + ce.getId() + ");\">Corregir</button>" +
					"</label>" +
					"</div></div>";
			tarjetas += "<div class=\"col-12 col-lg-9 mt-3\">"
					 + "<table class=\"table table-striped table-hover table-bordered text-center\">"
			         + "  <thead>"  
			         +"    <tr>"  
			         +"      <th scope=\"col\">Alumno</th>" 
			         +"  <th scope=\"col\">Nota</th>"  
			         +"<th scope=\"col\">Estado</th>"  
			         +"</tr>"
			         +"</thead><tbody>";
			
			for (modelo.InscripcionExamen e : examenes) {
				float acumulador = 0;
				if (e.isEntregado() && !e.isCorregido()) { // aca faltaria q no este corregido
					List<ExamenResolucion> examenResolucion = examenDao.traerExamenesResueltos(e.getId_usuario(),
							idCursoExamen);
					for (ExamenResolucion resuelto : examenResolucion) {
						Opciones_Pregunta op = pregDao.traerOpcionPreguntaPorId(resuelto.getId_respuesta());
						acumulador += (op.getRespuesta_correcta()) ? 1 : 0;
					}
					e.setResultado(Math.round(100 * (acumulador / cantidadPreguntas))); // pongo 100 pq muestra
																						// resultado de 0 a 100. cambiar
																						// si se quiere usar otro
					pregDao.save_tabla(e);
					Usuario u = userDao.traerUsuarioPorId(e.getId_usuario());
					tarjetas += this.traerHtmlTablaExamen(u, e, ex);
				}
			}
			tarjetas += "</tbody></table></div>";
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

	private String traerHtmlTablaExamen(Usuario u, modelo.InscripcionExamen e, Examen ex) {
		String cadena = "";
		cadena += "<tr><th scope=\"row\">" + u.getApellido() + ", " + u.getNombre() + "</th>"
				+ "<td>" + e.getResultado() + "</td>";
		if (ex.getCriterioAprobacion() >= 50) {
			cadena += "<td>" + (e.isAprobado() ? "Aprobado" : "Desaprobado") + "</td>";
		} else {
			cadena += "<td>" + "N/D" + "</td>";

		}
		cadena += "</tr>";
		return cadena;
	}
}
