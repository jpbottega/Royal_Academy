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
import dao.CursoExamenDao;
import dao.ExamenDao;
import funciones.FuncionesVarias;
import modelo.ContenedorResponse;
import modelo.CursoExamen;
import modelo.Examen;
import modelo.Select_Fecha_Examen;


/**
 * Servlet implementation class GestionCalendario
 */
@WebServlet("/GestionCalendario")
public class GestionCalendario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionCalendario() {
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
				case "crearFechaExamen":
					crearFechaExamen(request, response);
					break;
					
				case "guardarFecha":
					guardarExamen(request, response);
					break;
					
				case "eliminarFecha":
					eliminarExamen(request, response);
					break;
					
				case "traerFechas":
					traerFechasExamen(request, response);
					break;
					
				case "selectFechaExamen":
					selectFechaExamen(request, response);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void selectFechaExamen(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("hola");
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		CursoExamenDao ceDao = new CursoExamenDao();
		ExamenDao examenDao = new ExamenDao();
		Select_Fecha_Examen selectFecha = new Select_Fecha_Examen();
		try {
			CursoExamen ce = ceDao.traerCursoExamenPorId(Integer.parseInt(request.getParameter("id_fecha_examen")));
			if (ce != null) {
				selectFecha.setDescripcion(ce.getDescripcion());
				selectFecha.setFecha(FuncionesVarias.getStringDate(ce.getFecha(), 1));
				selectFecha.setId_examen(ce.getId_examen());
				selectFecha.setId_fecha_examen(ce.getId());
				selectFecha.setHtmlOpcionesExamen(this.traerHtmlExamenesDisponibles(examenDao.traerExamenPorCurso(ce.getId_curso())));
				error.setCd_error(1);
				error.setDs_error("Habilitada la modificacion para la fecha de examen.");
				error.setTipo("success");
			}
			else {
				error.setCd_error(1);
				error.setDs_error("La fecha de examen ya no existe en la base de datos.");
				error.setTipo("error");
			}
			
		} catch (Exception e) {
			error.setCd_error(1);
			error.setDs_error("Error interno en el servidor.");
			error.setTipo("error");
			e.printStackTrace();
		}
		contenedorResponse.setError(error);
		contenedorResponse.setData(selectFecha);
		json = gson.toJson(contenedorResponse);
		out.print(json);
		out.flush();
	}
	
	private void traerFechasExamen(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		CursoExamenDao ceDao = new CursoExamenDao();
		List<CursoExamen> fechasExamen = new ArrayList<CursoExamen>();
		String tarjetas = "";
		try {
			if (request.getParameter("id_curso") != null && request.getParameter("id_curso") != "") {
				fechasExamen = ceDao.traerExamenesPorCurso(Integer.parseInt(request.getParameter("id_curso")));
				tarjetas = this.traerHtmlTarjetas(fechasExamen);
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
	
	private void eliminarExamen(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		ExamenDao examenDao = new ExamenDao();
		CursoExamen examen = new CursoExamen();
		try {
			if (request.getParameter("id_fecha_examen").compareTo("0") == 0) {
				error.setCd_error(1);
				error.setDs_error("Debe primero guardar el examen.");
				error.setTipo("error");
			}
			else if (request.getParameter("id_fecha_examen") != "" && request.getParameter("id_fecha_examen") != null) {
				examen.setId(Integer.parseInt(request.getParameter("id_fecha_examen")));
				if (examenDao.delete_tabla(examen)) {
					error.setCd_error(1);
					error.setDs_error("Se ha eliminado la fecha de examen.");
					error.setTipo("success");
				}
				else {
					error.setCd_error(1);
					error.setDs_error("No se ha podido eliminar el examen.");
					error.setTipo("error");
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
	
	private void guardarExamen(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		ExamenDao examenDao = new ExamenDao();
		CursoExamen examen = new CursoExamen();
		try {
			if (request.getParameter("id_curso") != null && request.getParameter("id_curso") != "") {
				if (request.getParameter("id_fecha_examen") != "0") { // si es uno a modificar
					examen.setId(Integer.parseInt(request.getParameter("id_fecha_examen")));
				}
				examen.setDescripcion(request.getParameter("descripcion_fecha_examen"));
				examen.setFecha(FuncionesVarias.getDateString(request.getParameter("fecha_examen"),1));
				examen.setId_curso(Integer.parseInt(request.getParameter("id_curso")));
				examen.setId_examen(Integer.parseInt(request.getParameter("id_examen")));
				if (examenDao.save_tabla(examen)) {
					error.setCd_error(1);
					error.setDs_error("Se ha guardado la fecha de examen.");
					error.setTipo("success");
					int id = examenDao.aux_select_int("select id from cursoexamen where descripcion = '" + examen.getDescripcion() + "' and id_curso = " + 
							examen.getId_curso() + " and id_examen = " + examen.getId_examen());
					contenedorResponse.setData(id);
				}
				else {
					error.setCd_error(1);
					error.setDs_error("No se ha podido guardar el examen.");
					error.setTipo("error");
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

	private void crearFechaExamen(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		ContenedorResponse contenedorResponse = new ContenedorResponse();
		ContenedorResponse.Error error = new ContenedorResponse.Error();
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
		String json = "";
		ExamenDao examenDao = new ExamenDao();
		String cadena = "";
		try {
			if (request.getParameter("id_curso") != null && request.getParameter("id_curso") != "") {
				List<Examen> examenes = examenDao.traerExamenPorCurso(Integer.parseInt(request.getParameter("id_curso")));
				cadena = this.traerHtmlExamenesDisponibles(examenes);
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
	
	private String traerHtmlExamenesDisponibles(List<Examen> examenes) {
		String cadena = "";
		for (Examen e : examenes) {
			cadena += "<option value=\"" + e.getId() + "\">" + e.getDescripcion() + "</option>";
		}
		return cadena;
	}

	private String traerHtmlTarjetas(List<CursoExamen> examenes) {
		String cadena = "";
			
		for (CursoExamen e : examenes) {
			cadena +="<div class=\"row\">";
			cadena += "<div class=\"col-md-auto ml-3 card card-styles\" onclick=\"selectFechaExamen("+e.getId()+");\" id=\""+e.getId()+"\">" +  
						"<div class=\"card-body\">" +
								"<div class=\"card-title\">" + e.getDescripcion() + "</div>" + 
								"<div class=\"card-text text-muted\">Fecha: " + FuncionesVarias.getStringDate(e.getFecha(), 1) + "</div>" + 
						"</div>" +
					"</div>";
			cadena +="</div>";
		}
		
		return cadena;
	}
}
