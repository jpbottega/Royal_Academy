package servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServlet;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.CursoDao;
import dao.ExamenDao;
import dao.CarreraDao;
import dao.FuncionesDao;
import dao.PaisDao;
import dao.RolDao;
import dao.PreguntaDao;
import dao.SedeDao;
import modelo.Carrera;
import modelo.Examen;
import modelo.Curso;
import modelo.Funciones;
import modelo.Pais;
import modelo.Pregunta;
import modelo.Rol;
import modelo.Sede;
import modelo.Sede_Carrera;
/**
 * Servlet implementation class ServletLoggedAdmin
 */
@WebServlet("/ServletLoggedAlumno")

public class ServletLoggedAlumno extends ServletIncludeGoto {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	public ServletLoggedAlumno() {
	 */
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 */
	 *      response)
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

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		try {
			if (request.getParameter("accion") != null) {

				switch (request.getParameter("accion")) {
				case "perfil":
					perfil(request, response);
					break;
				case "homealumno":
					break;
					homealumno(request, response);
				
				case "inscripcionCursos":
					inscripcionCursoAlumno(request, response);
					break;
	
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void homealumno(HttpServletRequest request, HttpServletResponse response) {
		
		try {
		
			
			includePage("/loggedAlumno.jsp", request, response); // voy a panel inicio de administrativo


		} catch (Exception e) {
			e.printStackTrace();
	}
		}
	
	private void perfil(HttpServletRequest request, HttpServletResponse response) {
		
		try {
		
			
			includePage("/loggedAdmin.jsp", request, response); // voy a panel inicio de administrativo


		}
		} catch (Exception e) {
			e.printStackTrace();
	}
	
	private void inscripcionCursoAlumno(HttpServletRequest request, HttpServletResponse response) {
		CarreraDao carreraDao = new CarreraDao();
		CursoDao cursoDao = new CursoDao();
		CursoExamenDao cedao = new CursoExamenDao();
		try {
			List<Carrera> carreras = carreraDao.traerTodos(); // deberia traer solo las carreras de las sedes en las q esta registrado el alumno
			if(!carreras.isEmpty()) {
				List<Curso> cursos = cursoDao.traerCursoCarrera(carreras.get(0).getId()); //Traigo todos los cursos de la primer carrera
				List<CursoExamen> examenes = cedao.traerExamenesPorCurso(cursos.get(0).getId());
				request.setAttribute("cursos", cursos);
				request.setAttribute("carreras", carreras);
				request.setAttribute("examenes", examenes);
	
			}else {
				request.setAttribute("cursos", new ArrayList<Curso>());
				request.setAttribute("carreras", new ArrayList<Carrera>());	
			}
			
			includePage("/inscripcionCursoExamenAlumno.jsp", request, response);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
	