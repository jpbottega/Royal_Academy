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
import modelo.PermisoFunciones;
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
					if (!usuario.isVerificado()) { // si es usuario no esta verificado lo marco como verificado
						usuario.setVerificado(true);
						userDao.save_tabla(usuario);
					}
					List<PermisoFunciones> permiso_funciones = funcionesDao.traerPermisoFunciones(usuario.getId_rol());
					
					request.getSession().setAttribute("permisoFunciones", permiso_funciones);
					request.getSession().setAttribute("usuario", usuario);
					
					if (permiso_funciones.get(0).getHabilitada() == 1) {
						gotoPage("/loggedAdmin.jsp", request, response); // voy a panel inicio de administrativo
					}
					else if (permiso_funciones.get(1).getHabilitada() == 1) {
						gotoPage("/loggedAlumno.jsp", request, response); // voy a panel inicio de alumno*/
						
					}else {
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
