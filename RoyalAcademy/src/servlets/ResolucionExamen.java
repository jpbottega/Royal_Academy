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
import modelo.ContenedorResponse;
import modelo.CursoExamen;
import modelo.Examen;
import modelo.ExamenResolucion;
import modelo.Opciones_Pregunta;
import modelo.Pregunta;
import modelo.Usuario;

/**
 * Servlet implementation class ResolucionExamen
 */
@WebServlet("/ResolucionExamen")
public class ResolucionExamen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResolucionExamen() {
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
				case "rendirExamen":
					rendirExamen(request, response);
					break;
					
				case "entregarExamen":
					entregarExamen(request, response);
					break;
					
				case "actualizarExamenes":
					actualizarExamenes(request, response);
					break;

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void actualizarExamenes(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		CursoExamenDao cursoDao = new CursoExamenDao();
		String cadena = "";
		try {
			int id_usuario = ((Usuario)request.getSession().getAttribute("usuario")).getId();
			List<CursoExamen> lista = cursoDao.traerExamenesPorAlumno(id_usuario);
			for (CursoExamen ce : lista) {
				cadena += "<option value=\"" + ce.getId() + "\"><" + ce.getDescripcion() + "></option>";
			}
		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(cadena);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
	}
	
	private void rendirExamen(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		CursoExamenDao cursoDao = new CursoExamenDao();
		ExamenDao examenDao = new ExamenDao();
		PreguntaDao pregDao = new PreguntaDao();
		String cadena = "";
		try {
			CursoExamen ce = cursoDao.traerCursoExamenPorId(Integer.parseInt(request.getParameter("id_fechaExamen")));
			Examen e = examenDao.traerExamenPorId(ce.getId_examen());
			List<Pregunta> listaPreguntas = examenDao.traerPreguntasHabilidatas(e.getId());
			List<Opciones_Pregunta> respuestas = pregDao.bulkSelectOpciones(listaPreguntas);
			for (Pregunta p : listaPreguntas) {
				cadena += "<div class=\"form-group\"> <!-- Radio group !-->" +
							"<label class=\"control-label\">" + p.getPregunta() + "</label>";
				for (Opciones_Pregunta op : respuestas) {
					if (op.getId_pregunta() == p.getId()) {
						cadena += 
							"<div class=\"radio\">" + // estas son las respuestas
								"<label>" +
									"<input type=\"radio\" name=\"" + p.getId() + "\" value=\"" + op.getId_opcion() + "\">" +
									" " + op.getRespuesta() +
								"</label>" +
							"</div>";
					}
				}
				cadena +="</div>";		
			}
			cadena += "<div class=\"form-group row\"><button type=\"button\" class=\"btn btn-success pull-right\" "
					+ "onclick=\"entregarExamen();\" id=\"botonEntregarExamen\">Entregar Examen</button></div>";
			// envio al js el id del examen para hacer mas facil la consulta a la bd
			error.setCd_error(1);
			error.setDs_error("Rindiendo el parcial.");
			error.setTipo("success");

		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(cadena);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
	}
	
	private void entregarExamen(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		CursoExamenDao cursoDao = new CursoExamenDao();
		ExamenDao examenDao = new ExamenDao();
		PreguntaDao pregDao = new PreguntaDao();
		String cadena = "";
		try {
			CursoExamen ce = cursoDao.traerCursoExamenPorId(Integer.parseInt(request.getParameter("id_fechaExamen")));
			Examen e = examenDao.traerExamenPorId(ce.getId_examen());
			List<Pregunta> listaPreguntas = examenDao.traerPreguntasHabilidatas(e.getId());
			int id_usuario = ((Usuario)request.getSession().getAttribute("usuario")).getId();
			for (Pregunta p : listaPreguntas) {
				ExamenResolucion exRes = new ExamenResolucion();
				if (request.getParameter(String.valueOf(p.getId())) != null && request.getParameter(String.valueOf(p.getId())) != "") {
					exRes.setId_alumno(id_usuario);
					exRes.setId_curso_examen(ce.getId());
					exRes.setId_respuesta(Integer.parseInt(request.getParameter(String.valueOf(p.getId()))));
					examenDao.save_tabla(exRes);
				}
			}
			modelo.InscripcionExamen insc = cursoDao.traerInscripcionExamen(id_usuario, ce.getId());
			insc.setEntregado(true);
			insc.setResultado(0);
			insc.setAprobado(false);
			cursoDao.save_tabla(insc);
			
			error.setCd_error(1);
			error.setDs_error("Examen entregado!.");
			error.setTipo("success");

		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(cadena);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
	}

}
