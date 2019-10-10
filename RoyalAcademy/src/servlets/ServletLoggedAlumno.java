package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CarreraDao;
import dao.CursoDao;
import dao.CursoExamenDao;
import modelo.Carrera;
import modelo.Curso;
import modelo.CursoExamen;

/**
 * Servlet implementation class ServletLoggedAlumno
 */
@WebServlet("/ServletLoggedAlumno")
public class ServletLoggedAlumno extends ServletIncludeGoto {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletLoggedAlumno() {
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
				case "inscripcionCursos":
					inscripcionCursoAlumno(request, response);
					break;
	
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
