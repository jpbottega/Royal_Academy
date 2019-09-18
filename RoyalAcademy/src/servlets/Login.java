package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FuncionesDao;
import dao.RolDao;
import dao.UsuarioDao;
import modelo.Funciones;
//import endec.StringEncrypter;
import modelo.Usuario;

/**
 * 
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends ServletIncludeGoto {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
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
		// TODO Auto-generated method stub

		try {
			if (request.getParameter("accion") != null) {

				switch (request.getParameter("accion")) {
				case "login":
					login(request, response);
					break;
				}

			} else {

				gotoPage("/index.jsp", request, response); // vuelvo al login

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void login(HttpServletRequest request, HttpServletResponse response) {
		try {
			// StringEncrypter crypto = new StringEncrypter("nosequevaaca");
			UsuarioDao userDao = new UsuarioDao();
			RolDao rolDao = new RolDao();
			FuncionesDao funcionesDao = new FuncionesDao();
			if (request.getParameter("email") != null && request.getParameter("pass") != null) {
				Usuario usuario = userDao.traerUsuarioPorMail(request.getParameter("email"));

				if (usuario != null && request.getParameter("pass").equals(usuario.getPass())) {

					List<Funciones> funciones_habilitadas = funcionesDao.traerFuncionesHabilitadas(usuario.getId_rol());

					for (Funciones funciones : funciones_habilitadas) {

						if (funciones.getId_funcion() == 1) {
							gotoPage("/loggedAdmin.jsp", request, response); // voy a panel inicio de administrativo
							break;
						}
						if (funciones.getId_funcion() == 2) {
							gotoPage("/loggedAlumno.jsp", request, response); // voy a panel inicio de alumno*/
							break;
						}
						gotoPage("/index.jsp", request, response); // vuelvo al login por que no encontre ninguna funcion
					}

				} else {
					gotoPage("/index.jsp", request, response); // vuelvo al login
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
