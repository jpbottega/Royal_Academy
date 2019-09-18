package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletIncludeGoto
 */
@WebServlet("/ServletIncludeGoto")
public class ServletIncludeGoto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletIncludeGoto() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void includePage(String address,
            HttpServletRequest request,
            HttpServletResponse response)
			  throws ServletException, IOException {
	 			RequestDispatcher dispatcher =
	 			getServletContext().getRequestDispatcher(address);
	 			dispatcher.include(request, response);
	 }
    protected void gotoPage(String address,
	            HttpServletRequest request,
	            HttpServletResponse response)
		throws ServletException, IOException {
		RequestDispatcher dispatcher =
		getServletContext().getRequestDispatcher(address);
		dispatcher.forward(request, response);
	 }
}
