package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.ExamenDao;
import dao.PreguntaDao;
import dao.RespuestaDao;
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
				case "crearExamenManual":
					crearExamenManual(request, response);
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
			}
			else { // si no existe mando mensaje q no existe
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
				String options_habilitadas = "";
				String options_disponibles = "";
				int contador = 1;
				List<Opciones_Pregunta> respuestas = null;
				// preguntas disponibles
				for (Pregunta curso : curso_disponible) {
					respuestas = pregDao.traerOpciones(curso.getId());
					options_disponibles += // a cada boton le pongo el id de la pregunta
							"<div class=\"row\">" +
							"<li class=\"col-sm-10\"data-toggle=\"collapse\" data-target=\"#pd" + contador + "\">" + curso.getPregunta() +
									
									"<div class=\"row\">" +
									"<div id=\"pd" + contador + "\" class=\"collapse\">";
									for (Opciones_Pregunta op : respuestas) {
										options_disponibles += 
												(op.getRespuesta_correcta()) 
												? "<label class=\"row ml-4 font-weight-bold\" style=\"margin-bottom:0%\">- " + op.getRespuesta() + "</label>"
												: "<label class=\"row ml-4\" style=\"margin-bottom:0%\">- " + op.getRespuesta() + "</label>";
									}
										
									options_disponibles += "</div></div>" +
							"</li>"
							+ "<div class=\"col-sm-2\">" +
									"<button class=\"pull-right btn-xs btn-success\" style=\"border-radius:50%;font-size: 0.7em;\" onclick=\"agregarPreguntaExamen(" +
									curso.getId() +");\">+</button>" + 
							"</div></div>";
					contador++;
				}
				// preguntas habilitadas
				contador = 1;
				for (Pregunta curso : curso_habilitado) {
					respuestas = pregDao.traerOpciones(curso.getId());
					options_habilitadas += // a cada boton le pongo el id de la pregunta
							"<div class=\"row\">" +
							"<li class=\"col-sm-10\"data-toggle=\"collapse\" data-target=\"#ph" + contador + "\">" + curso.getPregunta() +
									
									"<div class=\"row\">" +
									"<div id=\"ph" + contador + "\" class=\"collapse\">";
									for (Opciones_Pregunta op : respuestas) {
										options_habilitadas += 
												(op.getRespuesta_correcta()) 
												? "<label class=\"row ml-4 font-weight-bold\" style=\"margin-bottom:0%\">- " + op.getRespuesta() + "</label>"
												: "<label class=\"row ml-4\" style=\"margin-bottom:0%\">- " + op.getRespuesta() + "</label>";
									}
										
									options_habilitadas += "</div></div>" +
							"</li>"
							+ "<div class=\"col-sm-2\">" +
							"<button class=\"pull-right btn-xs btn-danger\" style=\"border-radius:50%;font-size: 0.7em;\" onclick=\"eliminarPreguntaExamen(" +
							curso.getId() +");\">-</button>" + 
							"</div></div>";
					contador++;
				}
				select_examen.setPreguntas_disponibles(options_disponibles);
				select_examen.setPreguntas_habilitadas(options_habilitadas);
				
				error.setCd_error(1);
				error.setDs_error("Habilitada la edicion del examen.");
				error.setTipo("success");
			}
			else {
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
				tarjetas +="<div class=\"row\">";
					tarjetas += "<div class=\"col-md-auto ml-3 card card-styles\" onclick=\"selectExamen("+e.getId()+");\" id=\""+e.getId()+"\">" +  
								"<div class=\"card-body\">" +
										"<div class=\"card-title\">" +e.getDescripcion() + "</div>" + 
										"<div class=\"card-text text-muted\">Fecha Creacion: " + FuncionesVarias.getStringDate(e.getFechaCreacion(), 1) + "</div>" + 
										"<div class=\"card-text text-muted\">Creador: " + u.getNombre() + " " + u.getApellido() + "</div>" + 
								"</div>" +
							"</div>";
					tarjetas+="</div>";
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
			int id_examen = Integer.parseInt(request.getParameter("id_examen")), id_pregunta = Integer.parseInt(request.getParameter("id_pregunta"));
			// agrego la pregunta al examen
			if (examenDao.delete_tabla(new PreguntaxExamen(id_pregunta, id_examen))) {
				error.setCd_error(1);
				error.setDs_error("Se elimino la pregunta del examen.");
				error.setTipo("success");
				List<Pregunta> curso_disponible = examenDao.traerPreguntasDisponibles(id_examen);
				List<Pregunta> curso_habilitado = examenDao.traerPreguntasHabilidatas(id_examen);
				String options_habilitadas = "";
				String options_disponibles = "";
				int contador = 1;
				List<Opciones_Pregunta> respuestas = null;
				// preguntas disponibles
				for (Pregunta curso : curso_disponible) {
					respuestas = pregDao.traerOpciones(curso.getId());
					options_disponibles += // a cada boton le pongo el id de la pregunta
							"<div class=\"row\">" +
							"<li class=\"col-sm-10\"data-toggle=\"collapse\" data-target=\"#pd" + contador + "\">" + curso.getPregunta() +
									
									"<div class=\"row\">" +
									"<div id=\"pd" + contador + "\" class=\"collapse\">";
									for (Opciones_Pregunta op : respuestas) {
										options_disponibles += 
												(op.getRespuesta_correcta()) 
												? "<label class=\"row ml-4 font-weight-bold\" style=\"margin-bottom:0%\">- " + op.getRespuesta() + "</label>"
												: "<label class=\"row ml-4\" style=\"margin-bottom:0%\">- " + op.getRespuesta() + "</label>";
									}
										
									options_disponibles += "</div></div>" +
							"</li>"
							+ "<div class=\"col-sm-2\">" +
									"<button class=\"pull-right btn-xs btn-success\" style=\"border-radius:50%;font-size: 0.7em;\" onclick=\"agregarPreguntaExamen(" +
									curso.getId() +");\">+</button>" + 
							"</div></div>";
					contador++;
				}
				// preguntas habilitadas
				contador = 1;
				for (Pregunta curso : curso_habilitado) {
					respuestas = pregDao.traerOpciones(curso.getId());
					options_habilitadas += // a cada boton le pongo el id de la pregunta
							"<div class=\"row\">" +
							"<li class=\"col-sm-10\"data-toggle=\"collapse\" data-target=\"#ph" + contador + "\">" + curso.getPregunta() +
									
									"<div class=\"row\">" +
									"<div id=\"ph" + contador + "\" class=\"collapse\">";
									for (Opciones_Pregunta op : respuestas) {
										options_habilitadas += 
												(op.getRespuesta_correcta()) 
												? "<label class=\"row ml-4 font-weight-bold\" style=\"margin-bottom:0%\">- " + op.getRespuesta() + "</label>"
												: "<label class=\"row ml-4\" style=\"margin-bottom:0%\">- " + op.getRespuesta() + "</label>";
									}
										
									options_habilitadas += "</div></div>" +
							"</li>"
							+ "<div class=\"col-sm-2\">" +
							"<button class=\"pull-right btn-xs btn-danger\" style=\"border-radius:50%;font-size: 0.7em;\" onclick=\"eliminarPreguntaExamen(" +
							curso.getId() +");\">-</button>" + 
							"</div></div>";
					contador++;
				}
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
			int id_examen = Integer.parseInt(request.getParameter("id_examen")), id_pregunta = Integer.parseInt(request.getParameter("id_pregunta"));
			// agrego la pregunta al examen
			if (examenDao.save_tabla(new PreguntaxExamen(id_pregunta, id_examen))) {
				error.setCd_error(1);
				error.setDs_error("Se agrego la pregunta al examen.");
				error.setTipo("success");
				List<Pregunta> curso_disponible = examenDao.traerPreguntasDisponibles(id_examen);
				List<Pregunta> curso_habilitado = examenDao.traerPreguntasHabilidatas(id_examen);
				String options_habilitadas = "";
				String options_disponibles = "";
				int contador = 1;
				List<Opciones_Pregunta> respuestas = null;
				// preguntas disponibles
				for (Pregunta curso : curso_disponible) {
					respuestas = pregDao.traerOpciones(curso.getId());
					options_disponibles += // a cada boton le pongo el id de la pregunta
							"<div class=\"row\">" +
							"<li class=\"col-sm-10\"data-toggle=\"collapse\" data-target=\"#pd" + contador + "\">" + curso.getPregunta() +
									
									"<div class=\"row\">" +
									"<div id=\"pd" + contador + "\" class=\"collapse\">";
									for (Opciones_Pregunta op : respuestas) {
										options_disponibles += 
												(op.getRespuesta_correcta()) 
												? "<label class=\"row ml-4 font-weight-bold\" style=\"margin-bottom:0%\">- " + op.getRespuesta() + "</label>"
												: "<label class=\"row ml-4\" style=\"margin-bottom:0%\">- " + op.getRespuesta() + "</label>";
									}
										
									options_disponibles += "</div></div>" +
							"</li>"
							+ "<div class=\"col-sm-2\">" +
									"<button class=\"pull-right btn-xs btn-success\" style=\"border-radius:50%;font-size: 0.7em;\" onclick=\"agregarPreguntaExamen(" +
									curso.getId() +");\">+</button>" + 
							"</div></div>";
					contador++;
				}
				// preguntas habilitadas
				contador = 1;
				for (Pregunta curso : curso_habilitado) {
					respuestas = pregDao.traerOpciones(curso.getId());
					options_habilitadas += // a cada boton le pongo el id de la pregunta
							"<div class=\"row\">" +
							"<li class=\"col-sm-10\"data-toggle=\"collapse\" data-target=\"#ph" + contador + "\">" + curso.getPregunta() +
									
									"<div class=\"row\">" +
									"<div id=\"ph" + contador + "\" class=\"collapse\">";
									for (Opciones_Pregunta op : respuestas) {
										options_habilitadas += 
												(op.getRespuesta_correcta()) 
												? "<label class=\"row ml-4 font-weight-bold\" style=\"margin-bottom:0%\">- " + op.getRespuesta() + "</label>"
												: "<label class=\"row ml-4\" style=\"margin-bottom:0%\">- " + op.getRespuesta() + "</label>";
									}
										
									options_habilitadas += "</div></div>" +
							"</li>"
							+ "<div class=\"col-sm-2\">" +
							"<button class=\"pull-right btn-xs btn-danger\" style=\"border-radius:50%;font-size: 0.7em;\" onclick=\"eliminarPreguntaExamen(" +
							curso.getId() +");\">-</button>" + 
							"</div></div>";
					contador++;
				}
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
	
	
	private void guardarExamen(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		ExamenDao examenDao = new ExamenDao();
		try {
			if (request.getParameter("id_curso") != null && request.getParameter("id_carrera") != null && request.getParameter("id_examen") != null) {
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
						contenedorResponse.setData(examenDao.aux_select_int("select id from examenes where descripcion = '" + examen.getDescripcion() + "';"));
					}
				}
				else {
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
				List<Pregunta> curso_disponible = pregDao.traerPreguntaPorCarreraCurso(Integer.parseInt(request.getParameter("id_carrera")), 
						Integer.parseInt(request.getParameter("id_curso")));
				String options_habilitadas = "";
				String options_disponibles = "";
				int contador = 1;
				List<Opciones_Pregunta> respuestas = null;
				for (Pregunta curso : curso_disponible) {
					respuestas = pregDao.traerOpciones(curso.getId());
					options_disponibles += // a cada boton le pongo el id de la pregunta
							"<div class=\"row\">" +
							"<li class=\"col-sm-10\"data-toggle=\"collapse\" data-target=\"#pd" + contador + "\">" + curso.getPregunta() +
									
									"<div class=\"row\">" +
									"<div id=\"pd" + contador + "\" class=\"collapse\">";
									for (Opciones_Pregunta op : respuestas) {
										options_disponibles += 
												(op.getRespuesta_correcta()) 
												? "<label class=\"row ml-4 font-weight-bold\" style=\"margin-bottom:0%\">- " + op.getRespuesta() + "</label>"
												: "<label class=\"row ml-4\" style=\"margin-bottom:0%\">- " + op.getRespuesta() + "</label>";
									}
										
									options_disponibles += "</div></div>" +
							"</li>"
							+ "<div class=\"col-sm-2\">" +
								"<button class=\"pull-right btn-xs btn-success\" id=\"botoncito\" onclick=\"agregarPreguntaExamen(" +
								curso.getId() +");\" style=\"border-radius:50%;font-size: 0.7em;\" >+</button>" + 
							"</div></div>";
					contador++;
				}

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
}
