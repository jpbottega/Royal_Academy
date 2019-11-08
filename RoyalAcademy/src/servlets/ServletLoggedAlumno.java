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
import dao.CursoExamenDao;
import dao.CarreraDao;
import modelo.Carrera;
import modelo.Curso;
import modelo.CursoExamen;
import modelo.Curso_Usuario;
import modelo.InscripcionCursos;
import modelo.Usuario;

/**
 * Servlet implementation class ServletLoggedAdmin
 */
@WebServlet("/ServletLoggedAlumno")

public class ServletLoggedAlumno extends ServletIncludeGoto {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletLoggedAlumno() {
		super();
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
					homealumno(request, response);
					break;
				case "inscripcionCursos":
					inscripcionCursoAlumno(request, response);
					break;
				case "resolucionExamen":
					resolucionExamen(request, response);
					break;
				case "inscribirse":
					
					inscribirse(request,response);
					
					break;
				case "borrarInscribirse":
					
					borrarInscribirse(request,response);

					
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void resolucionExamen(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id_usuario = ((Usuario)request.getSession().getAttribute("usuario")).getId();
			CursoExamenDao ceDao = new CursoExamenDao();
			List<CursoExamen> lista = ceDao.traerExamenesPorAlumno(id_usuario);
			request.setAttribute("examenes_inscripto", lista);
			includePage("/resolucionExamen.jsp", request, response); // voy a panel inicio de administrativo

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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void inscribirse(HttpServletRequest request, HttpServletResponse response) {
		CursoDao cursoDao = new CursoDao();
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		response.setContentType("text/html");

		try {
		
			Curso_Usuario curso_usuario = new Curso_Usuario(Integer.parseInt(request.getParameter("curso")),usuario.getId(),Integer.parseInt(request.getParameter("sede")));
			
			cursoDao.save_tabla(curso_usuario);
			
			List<InscripcionCursos> cursos = cursoDao.traerCursoInscripcion(usuario.getId());
			
			request.setAttribute("cursos", cursos);
			
			includePage("/inscripcionCursos.jsp", request, response);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void borrarInscribirse(HttpServletRequest request, HttpServletResponse response) {
		CursoDao cursoDao = new CursoDao();
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		response.setContentType("text/html");

		try {
		
			Curso_Usuario curso_usuario = new Curso_Usuario(Integer.parseInt(request.getParameter("curso")),usuario.getId(),Integer.parseInt(request.getParameter("sede")));
			
			cursoDao.delete_tabla(curso_usuario);
			
			List<InscripcionCursos> cursos = cursoDao.traerCursoInscripcion(usuario.getId());
			
			request.setAttribute("cursos", cursos);
			
			includePage("/inscripcionCursos.jsp", request, response);
			
			
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
				List<CursoExamen> examenes = cedao.traerExamenesPorCursoAlumno(cursos.get(0).getId(), 
						((Usuario)request.getSession().getAttribute("usuario")).getId());
				
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
	