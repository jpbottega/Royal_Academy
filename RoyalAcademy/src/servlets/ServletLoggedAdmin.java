package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.CarreraDao;
import dao.CursoDao;
import dao.FuncionesDao;
import dao.PaisDao;
import dao.PreguntaDao;
import dao.RolDao;
import dao.SedeDao;
import modelo.Carrera;
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
@WebServlet("/ServletLoggedAdmin")
public class ServletLoggedAdmin extends ServletIncludeGoto {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletLoggedAdmin() {
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
				case "perfiles":
					perfiles(request, response);
					break;
				case "sedes":
					sedes(request, response);
					break;
				case "adminUsuarios":
					administradorUsuarios(request, response);
					break;
				case "carreras":
					carreras(request,response);
					break;
				case "cursos":
					
					cursos(request,response);
					
					break;
				case "preguntas":
					
					preguntas(request,response);
					
					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void preguntas(HttpServletRequest request, HttpServletResponse response) {
		CarreraDao carreraDao = new CarreraDao();
		CursoDao cursoDao = new CursoDao();
		PreguntaDao preguntaDao = new PreguntaDao();
		try {

			List<Carrera> carreras = carreraDao.traerTodos(); // Traigo todos las carreras que esten en la base de datos.
			
			if(!carreras.isEmpty()) {
				List<Curso> cursos = cursoDao.traerCursoCarrera(carreras.get(0).getId()); //Traigo todos los cursos
				request.setAttribute("cursos", cursos);
				request.setAttribute("carreras", carreras);
				List<Pregunta> preguntas = preguntaDao.traerPreguntaPorCarreraCurso(carreras.get(0).getId(),cursos.get(0).getId());
				
				request.setAttribute("preguntas", preguntas);
			}else {
				request.setAttribute("cursos", new ArrayList<Curso>());
				request.setAttribute("carreras", new ArrayList<Carrera>());
				request.setAttribute("preguntas", new ArrayList<Pregunta>());
				
				
			}
			request.setAttribute("id_pregunta", "0");
			request.setAttribute("pregunta", "");
			request.setAttribute("cantidad_opciones", 1);
			
			includePage("/preguntas.jsp", request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void carreras(HttpServletRequest request, HttpServletResponse response) {
		CarreraDao carreraDao = new CarreraDao();
		try {

			List<Carrera> carreras = carreraDao.traerTodos(); // Traigo todos las carreras que esten en la base de datos.

			request.setAttribute("carreras", carreras);
			
			List<Sede> disponibles = carreraDao.traerSedesDisponibles(carreras.get(0).getId());
			request.setAttribute("sedes_disponibles", disponibles);
			
			if (carreras.isEmpty()) {
				request.setAttribute("id_carrera", 0);
				request.setAttribute("ds_carrera", "");
				
			} else {
				
				List<Sede> habilitadas = carreraDao.traerSedesHabilitadas(carreras.get(0).getId());

				
				request.setAttribute("sedes_habilitadas", habilitadas);

				request.setAttribute("id_carrera", carreras.get(0).getId());
				request.setAttribute("ds_carrera", carreras.get(0).getDenominacion());
			}

			includePage("/carreras.jsp", request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void administradorUsuarios(HttpServletRequest request, HttpServletResponse response) {
		RolDao rolDao = new RolDao();
		SedeDao sedeDao = new SedeDao();
		CarreraDao carreraDao = new CarreraDao();
		CursoDao cursoDao = new CursoDao();
		try {
			List<Rol> roles = rolDao.traerTodos(); // Traigo todos los roles que esten en la base de datos.
			List<Sede> sedes = sedeDao.traerTodos();
			List<Carrera> carreras = carreraDao.traerTodos();
			List<Curso> cursos = cursoDao.traerTodos();
			request.setAttribute("roles", roles);
			request.setAttribute("id_rol", roles.get(0).getId());
			request.setAttribute("sedesDisponibles", sedes);
			request.setAttribute("carreras", carreras);
			request.setAttribute("cursos", cursos);
			includePage("/administradorUsuarios.jsp", request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void cursos(HttpServletRequest request, HttpServletResponse response) {
		CursoDao cursoDao = new CursoDao();
		CarreraDao carreraDao = new CarreraDao();
		try {

			List<Curso> cursos = cursoDao.traerTodos(); // Traigo todos los roles que esten en la base de datos.
			List<Carrera> carreras = carreraDao.traerTodos();

			request.setAttribute("cursos", cursos);
			request.setAttribute("carreras", carreras);
			
			if (cursos.isEmpty()) {
				request.setAttribute("id_curso", 0);
				request.setAttribute("ds_curso", "");
				request.setAttribute("carrera", 0);
			} else {
				request.setAttribute("id_curso", cursos.get(0).getId());
				request.setAttribute("ds_curso", cursos.get(0).getDenominacion());
				request.setAttribute("carrera", cursos.get(0).getId_carrera());
			}

			includePage("/cursos.jsp", request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void sedes(HttpServletRequest request, HttpServletResponse response) {
		SedeDao sedeDao = new SedeDao();
		PaisDao paisDao = new PaisDao();
		FuncionesDao funcionesDao = new FuncionesDao();
		try {

			List<Sede> sedes = sedeDao.traerTodos(); // Traigo todos los roles que esten en la base de datos.
			List<Pais> paises = paisDao.traerTodos();

			request.setAttribute("sedes", sedes);
			request.setAttribute("paises", paises);
			if (sedes.isEmpty()) {
				request.setAttribute("id_sede", 0);
				request.setAttribute("ds_sede", "");
				request.setAttribute("pais", 0);
				request.setAttribute("paises", paises);
			} else {
				request.setAttribute("id_sede", sedes.get(0).getId());
				request.setAttribute("ds_sede", sedes.get(0).getSede());
				request.setAttribute("pais", sedes.get(0).getId_pais());

			}

			includePage("/sedes.jsp", request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void perfiles(HttpServletRequest request, HttpServletResponse response) {
		RolDao rolDao = new RolDao();
		FuncionesDao funcionesDao = new FuncionesDao();
		try {

			List<Rol> roles = rolDao.traerTodos(); // Traigo todos los roles que esten en la base de datos.

			request.setAttribute("roles", roles);

			if (roles.isEmpty()) {
				request.setAttribute("id_rol", 0);
				request.setAttribute("ds_rol", "");
				
				request.setAttribute("funciones_disponibles", "");
				request.setAttribute("funciones_habilitadas", "");
			} else {
				List<Funciones> funciones_disponibles = funcionesDao.traerFuncionesDisponibles(roles.get(0).getId());
				List<Funciones> funciones_habilitadas = funcionesDao.traerFuncionesHabilitadas(roles.get(0).getId());

				request.setAttribute("funciones_disponibles", funciones_disponibles);
				request.setAttribute("funciones_habilitadas", funciones_habilitadas);

				request.setAttribute("id_rol", roles.get(0).getId());
				request.setAttribute("ds_rol", roles.get(0).getDenominacion());
			}

			includePage("/perfiles.jsp", request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
