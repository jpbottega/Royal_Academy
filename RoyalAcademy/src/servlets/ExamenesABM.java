package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.ExamenDao;
import dao.PreguntaDao;
import dao.UsuarioDao;
import funciones.FuncionesVarias;
import modelo.ContenedorResponse;
import modelo.Examen;
import modelo.Opciones_Pregunta;
import modelo.Pregunta;
import modelo.PreguntaxExamen;
import modelo.Select_Examen;
import modelo.Select_Sedes_Carrera;
import modelo.Usuario;

/**
 * Servlet implementation class ExamenesABM
 */
@WebServlet("/ExamenesABM")
public class ExamenesABM extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExamenesABM() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			if (request.getParameter("accion") != null) {

				switch (request.getParameter("accion")) {
				case "crearExamenManual":
					crearExamenManual(request, response);
					break;

				case "crearExamenAutomatico":
					crearExamenAutomatico(request, response);
					break;

				case "guardarExamen":
					guardarExamen(request, response);
					break;

				case "agregarPregunta":
					agregarPregunta(request, response);
					break;

				case "eliminarPregunta":
					eliminarPregunta(request, response);
					break;

				case "selectExamen":
					selectExamen(request, response); // este para cuando selecciona la tarjeta
					break;

				case "selectCursoExamen":
					selectCursoExamen(request, response); // este para generar las tarjetas
					break;

				case "eliminarExamen":
					eliminarExamen(request, response);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void crearExamenAutomatico(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		PreguntaDao pregDao = new PreguntaDao();
		ExamenDao examenDao = new ExamenDao();
		Select_Examen select_examen = new Select_Examen();
		// int cant_preguntas =
		// Integer.parseInt(request.getParameter("cant_preguntas"));
		Random random = new Random();
		try {
			int cant_preguntas = Integer.parseInt(request.getParameter("cant_preguntas"));
			if (cant_preguntas > 50) {
				cant_preguntas = 50;
				error.setCd_error(1);
				error.setDs_error("Autogenerado nuevo examen, solo se pueden agregar 50 preguntas.");
				error.setTipo("warning");
			}
			int id_curso = Integer.parseInt(request.getParameter("id_curso")),
			id_creador = Integer.parseInt(request.getParameter("id_creador"));
			// agrego la pregunta al examen
			Examen e = new Examen();
			e.setId_curso(id_curso);
			e.setFechaCreacion(new Date());
			e.setId_usuario_creador(id_creador);
			e.setDescripcion("Examen Autogenerado: " + e.getFechaCreacion().getTime()); // tengo q hacer esto unico

			examenDao.save_tabla(e); // guardo el examen en la bd
			e.setId(examenDao.aux_select_int("select id from examenes where descripcion = '" + e.getDescripcion() + "';"));
			// uso la descripcion para traerlo, seria mejor usar un stored procedure
			select_examen.setId_examen(e.getId());
			select_examen.setDescripcion(e.getDescripcion());

			List<Pregunta> curso_disponible = examenDao.traerPreguntasDisponibles(e.getId());
			List<Pregunta> curso_habilitado = new ArrayList<Pregunta>();

			for (int i = 0; i < cant_preguntas; i++) {
				if (!curso_disponible.isEmpty()) {
					Pregunta p = curso_disponible
							.remove((curso_disponible.size() > 1) ? random.nextInt(curso_disponible.size() - 1) : 0);
					curso_habilitado.add(p); // saco una pregunta al azar de las disponibles
					// y la pongo en el habilitado
					examenDao.save_tabla(new PreguntaxExamen(p.getId(), e.getId()));
				}
			}

			curso_disponible = examenDao.traerPreguntasDisponibles(e.getId());

			String options_habilitadas = this.traerHtmlPreguntasHabilitadas(curso_habilitado, pregDao);
			String options_disponibles = this.traerHtmlPreguntasDisponibles(curso_disponible, pregDao);
			select_examen.setPreguntas_disponibles(options_disponibles);
			select_examen.setPreguntas_habilitadas(options_habilitadas);
			if (error.getCd_error() != 1) {
				error.setCd_error(1);
				error.setDs_error("Autogenerado nuevo examen.");
				error.setTipo("success");
			}
		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(select_examen);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
	}

	private void eliminarExamen(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		ExamenDao examenDao = new ExamenDao();
		try {
			int id_examen = Integer.parseInt(request.getParameter("id_examen"));
			Examen examen = examenDao.traerExamenPorId(id_examen);
			if (examen != null) { // si el examen existe en la bd lo borro
				if (examenDao.delete_tabla(examen)) {
					error.setCd_error(1);
					error.setDs_error("Se ha eliminado el examen.");
					error.setTipo("success");
					examenDao.eliminarPreguntasAsociadas(id_examen);
				}
			} else { // si no existe mando mensaje q no existe
				error.setCd_error(1);
				error.setDs_error("El examen no existe en la base de datos.");
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

	private void selectExamen(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		PreguntaDao pregDao = new PreguntaDao();
		ExamenDao examenDao = new ExamenDao();
		Select_Examen select_examen = new Select_Examen();
		try {
			int id_examen = Integer.parseInt(request.getParameter("id_examen"));
			// agrego la pregunta al examen
			Examen e = examenDao.traerExamenPorId(id_examen);
			if (e != null) {
				select_examen.setId_examen(e.getId());
				select_examen.setDescripcion(e.getDescripcion());
				List<Pregunta> curso_disponible = examenDao.traerPreguntasDisponibles(id_examen);
				List<Pregunta> curso_habilitado = examenDao.traerPreguntasHabilidatas(id_examen);
				String options_habilitadas = this.traerHtmlPreguntasHabilitadas(curso_habilitado, pregDao);
				String options_disponibles = this.traerHtmlPreguntasDisponibles(curso_disponible, pregDao);
				select_examen.setPreguntas_disponibles(options_disponibles);
				select_examen.setPreguntas_habilitadas(options_habilitadas);

				error.setCd_error(1);
				error.setDs_error("Habilitada la edicion del examen.");
				error.setTipo("success");
			} else {
				error.setCd_error(1);
				error.setDs_error("El examen no existe en la base de datos.");
				error.setTipo("error");
			}
		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}

		contenedorResponse.setError(error);
		contenedorResponse.setData(select_examen);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
	}

	private void selectCursoExamen(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		UsuarioDao userDao = new UsuarioDao();
		ExamenDao examenDao = new ExamenDao();
		String tarjetas = "";
		try {
			int id_curso = Integer.parseInt(request.getParameter("id_curso"));
			// traigo los examenes del curso y armo las tarjetas
			List<Examen> examenes = examenDao.traerExamenPorCurso(id_curso);
			for (Examen e : examenes) {
				Usuario u = userDao.traerUsuarioPorId(e.getId_usuario_creador());
				tarjetas += "<div class=\"row card card-styles\" onclick=\"selectExamen(" + e.getId() + ");\" id=\"" + e.getId()
						+ "\">";
				tarjetas += "<div class=\"card-body\">" + "<div class=\"card-title\">" + e.getDescripcion() + "</div>"
						+ "<div class=\"card-text text-muted\">Fecha Creacion: "
						+ FuncionesVarias.getStringDate(e.getFechaCreacion(), 1) + "</div>"
						+ "<div class=\"card-text text-muted\">Creador: " + u.getNombre() + " " + u.getApellido()
						+ "</div>"  + "</div>";
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

	private void eliminarPregunta(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		PreguntaDao pregDao = new PreguntaDao();
		ExamenDao examenDao = new ExamenDao();
		Select_Sedes_Carrera select_sedes_perfil = new Select_Sedes_Carrera();
		try {
			int id_examen = Integer.parseInt(request.getParameter("id_examen")),
					id_pregunta = Integer.parseInt(request.getParameter("id_pregunta"));
			// agrego la pregunta al examen
			if (examenDao.delete_tabla(new PreguntaxExamen(id_pregunta, id_examen))) {
				List<Pregunta> curso_disponible = examenDao.traerPreguntasDisponibles(id_examen);
				List<Pregunta> curso_habilitado = examenDao.traerPreguntasHabilidatas(id_examen);
				String options_habilitadas = this.traerHtmlPreguntasHabilitadas(curso_habilitado, pregDao);
				String options_disponibles = this.traerHtmlPreguntasDisponibles(curso_disponible, pregDao);
				select_sedes_perfil.setSedes_disponibles(options_disponibles);
				select_sedes_perfil.setSedes_habilitadas(options_habilitadas);
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

	private void agregarPregunta(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		PreguntaDao pregDao = new PreguntaDao();
		ExamenDao examenDao = new ExamenDao();
		Select_Sedes_Carrera select_sedes_perfil = new Select_Sedes_Carrera();
		try {
			int id_examen = Integer.parseInt(request.getParameter("id_examen")),
					id_pregunta = Integer.parseInt(request.getParameter("id_pregunta"));
			// agrego la pregunta al examen
			int cant_preguntas = examenDao.traerCantidadPreguntasHabilidatas(id_examen);
			if (cant_preguntas < 50) {
				examenDao.save_tabla(new PreguntaxExamen(id_pregunta, id_examen));
			}
			else {
				error.setCd_error(1);
				error.setDs_error("Solo pueden agregarse hasta 50 preguntas.");
				error.setTipo("error");
			}
			List<Pregunta> curso_disponible = examenDao.traerPreguntasDisponibles(id_examen);
			List<Pregunta> curso_habilitado = examenDao.traerPreguntasHabilidatas(id_examen);
			String options_habilitadas = this.traerHtmlPreguntasHabilitadas(curso_habilitado, pregDao);
			String options_disponibles = this.traerHtmlPreguntasDisponibles(curso_disponible, pregDao);
			select_sedes_perfil.setSedes_disponibles(options_disponibles);
			select_sedes_perfil.setSedes_habilitadas(options_habilitadas);
			
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

	private void guardarExamen(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		ExamenDao examenDao = new ExamenDao();
		try {
			if (request.getParameter("id_curso") != null && request.getParameter("id_carrera") != null
					&& request.getParameter("id_examen") != null) {
				Examen examen = new Examen();
				examen.setId_curso(Integer.parseInt(request.getParameter("id_curso")));
				examen.setCriterioAprobacion(0);
				examen.setDescripcion(request.getParameter("descripcion_examen"));
				examen.setFechaCreacion(new Date());
				examen.setId_usuario_creador(Integer.parseInt(request.getParameter("id_usuario_creador")));
				int id_examen = Integer.parseInt(request.getParameter("id_examen"));
				if (id_examen == 0) { // examen nuevo
					if (examenDao.save_tabla(examen)) {
						error.setCd_error(1);
						error.setDs_error("Se ha guardado el examen.");
						error.setTipo("success");
						contenedorResponse.setData(examenDao.aux_select_int(
								"select id from examenes where descripcion = '" + examen.getDescripcion() + "';"));
					}
				} else {
					examen.setId(id_examen);
					if (examenDao.save_tabla(examen)) {
						error.setCd_error(1);
						error.setDs_error("Se ha guardado el examen.");
						error.setTipo("success");
						contenedorResponse.setData(examen.getId());
					}
				}
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

	private void crearExamenManual(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// prueba
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		PreguntaDao pregDao = new PreguntaDao();
		Select_Sedes_Carrera select_sedes_perfil = new Select_Sedes_Carrera();
		try {
			if (request.getParameter("id_curso") != null && request.getParameter("id_carrera") != null) {
				List<Pregunta> curso_disponible = pregDao.traerPreguntaPorCarreraCurso(
						Integer.parseInt(request.getParameter("id_carrera")),
						Integer.parseInt(request.getParameter("id_curso")));
				String options_habilitadas = "";
				String options_disponibles = this.traerHtmlPreguntasDisponibles(curso_disponible, pregDao);

				select_sedes_perfil.setSedes_disponibles(options_disponibles);
				select_sedes_perfil.setSedes_habilitadas(options_habilitadas);

				error.setCd_error(1);
				error.setDs_error("Nuevo examen listo para editar.");
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

	private String traerHtmlPreguntasDisponibles(List<Pregunta> curso_disponible, PreguntaDao pregDao) {
		String options_disponibles = "";
		int contador = 1;
		List<Opciones_Pregunta> respuestas = null;
		List<Opciones_Pregunta> respuestasSinFiltro = pregDao.bulkSelectOpciones(curso_disponible);
		for (Pregunta curso : curso_disponible) {
			//respuestas = pregDao.traerOpciones(curso.getId());
			respuestas = respuestasSinFiltro.stream().filter(p -> p.getId_pregunta() == curso.getId()).collect(Collectors.toList()); // filtro la lista

			options_disponibles += // a cada boton le pongo el id de la pregunta
					"<div class=\"row mb-2 container-pregunta-examen\">" + "<div class=\"col-9\"data-toggle=\"collapse\" data-target=\"#pd"
							+ contador + "\">" + "<span class=\"title-pregunta\">"+ curso.getPregunta() + "</span>" +

							 "<div id=\"pd" + contador + "\" class=\"collapse\">";
			for (Opciones_Pregunta op : respuestas) {
				options_disponibles += (op.getRespuesta_correcta())
						? "<div class=\"pregunta-correcta\"><span class=\"icon-pregunta fas fa-check\"/>" + op.getRespuesta()
						+ "</div>"
				: "<div class=\"pregunta-incorrecta\"><span class=\"icon-pregunta fas fa-times\"/> " + op.getRespuesta() + "</div>";
			}

			options_disponibles += "</div>" + "</div>" + "<div class=\"col-3 container-btn-pregunta\">"
					+ "<button class=\"btn btn-success btn-examenes fas fa-plus\" id=\"botoncito\" onclick=\"agregarPreguntaExamen("
					+ curso.getId() + ");\" ></button>" + "</div></div>";
			contador++;
		}
		return options_disponibles;
	}

	private String traerHtmlPreguntasHabilitadas(List<Pregunta> curso_habilitado, PreguntaDao pregDao) {
		int contador = 1;
		String options_habilitadas = "";
		List<Opciones_Pregunta> respuestas = null;
		List<Opciones_Pregunta> respuestasSinFiltro = pregDao.bulkSelectOpciones(curso_habilitado);
		for (Pregunta curso : curso_habilitado) {
			//respuestas = pregDao.traerOpciones(curso.getId());
			respuestas = respuestasSinFiltro.stream().filter(p -> p.getId_pregunta() == curso.getId()).collect(Collectors.toList()); // filtro la lista
			options_habilitadas += // a cada boton le pongo el id de la pregunta
					"<div class=\"row mb-2 container-pregunta-examen\">" + "<div class=\"col-9\"data-toggle=\"collapse\" data-target=\"#ph"
							+ contador + "\">" + "<span class=\"title-pregunta\">" + curso.getPregunta() + "</span>" +

							 "<div id=\"ph" + contador + "\" class=\"collapse\">";
			for (Opciones_Pregunta op : respuestas) {
				options_habilitadas += (op.getRespuesta_correcta())
						? "<div class=\"pregunta-correcta\"><span class=\"icon-pregunta fas fa-check\"/>" + op.getRespuesta()
								+ "</div>"
						: "<div class=\"pregunta-incorrecta\"><span class=\"icon-pregunta fas fa-times\"/>" + op.getRespuesta() + "</div>";
			}

			options_habilitadas += "</div>" + "</div>" + "<div class=\"col-3 container-btn-pregunta\">"
					+ "<button class=\"btn btn-danger btn-examenes fas fa-minus\"  onclick=\"eliminarPreguntaExamen("
					+ curso.getId() + ");\"></button>" + "</div></div>";
			contador++;
		}
		return options_habilitadas;
	}
}
