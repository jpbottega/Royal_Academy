package servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IEngineTask;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.RenderOption;

import dao.ReportesDao;
import modelo.InfoCursos;





/**
 * Servlet implementation class Reportes
 */
@WebServlet("/Reportes")
public class Reportes extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
	private IReportEngine birtReportEngine = null;
	public Reportes() {
		super();
	}

	public void destroy() {
		super.destroy(); 
		BirtEngine.destroyBirtEngine();
	}
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
	}

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	
    		    request.setCharacterEncoding("UTF-8");
    		    response.setCharacterEncoding("UTF-8");
    		    
    		    processRequest(request, response);   
    			
    				
    	}


    public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	   	
			processRequest(request, response);   		
		
	}
 
   	
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    		//boolean hayreg = false;
    		    try
    			{

    				IRunAndRenderTask task=	setTask(request);
    				
    				SetReport(Integer.parseInt(request.getParameter("numr")),request,response,task);	  
    				
    			}catch (Exception e){
    				e.printStackTrace();
    				throw new ServletException( e );
    			}
    		    
    		    
    }
    private void SetReport(int nrpt,HttpServletRequest req,HttpServletResponse resp,IRunAndRenderTask task)
 		   throws ServletException, IOException {
    	
 		try
 		{

 			
 			switch (nrpt) {
 				case 1:setReporteInfoCurso(req,resp,task);
 					break;
 				
 			}
 			
 		}catch (Exception e){
 			e.printStackTrace();
 			throw new ServletException( e );
 		}
 		
    }

    
	private void setReporteInfoCurso(HttpServletRequest req, HttpServletResponse resp, IRunAndRenderTask task) throws ServletException, IOException {
	 	
		ReportesDao reportesDao = new ReportesDao();
	    
	    List<InfoCursos> lista= reportesDao.traerInfoCursos(Integer.parseInt(req.getParameter("id_curso")));
		
	    ImageInputStream fs_small = null;
		BufferedImage bufferedImage_small = null;
	   
	   
        if(lista.size()>0){      
	        task= SetOutPutFormatReport(task, req,"PDF", resp,Integer.parseInt(req.getParameter("numr")));
	        	//task.setParameterValue("@titulo", "Reporte de Vigiladores pendientes de Alta desde "+filterfdesde+" hasta "+filterfhasta);
	        task.getAppContext().put("lista", lista);
		    task.getAppContext().put("session", req.getSession());		    			    
		    task.setParameterValue("@fecha",req.getParameter("fecha"));
	  		try {
	  		    task.run();
	  			String xerror = verStatus(task);
			    task.close();
			
			    task = null;	
			
			} catch (EngineException e) {
				e.printStackTrace();
			}
  	    }else {
  	    	
			gotoPage("/error_reporte.jsp", req, resp); 
			
  	    }

	}

	private String NameReportExport(int nrpt,HttpServletRequest request)
    {
    	String nombre= "";   	
    		switch (nrpt) {
				case 1: nombre="Reporte Información de Cursos";
				break;
    		
			}
    	return nombre;
    }

	
	@SuppressWarnings("unchecked")
	private IRunAndRenderTask setTask(HttpServletRequest req) 
	{
		String reportName ="";
		//req.getParameter("ReportName");
		//String[] reportExportName = reportName.split(".");
		
		switch (Integer.parseInt(req.getParameter("numr"))) {
				case 1: reportName="rpt_info_cursos.rptdesign";
				break;
				

			}
		ServletContext sc = req.getSession().getServletContext();
		this.birtReportEngine = BirtEngine.getBirtEngine(sc);
		
		IRunAndRenderTask task = null;
	
		IReportRunnable design;
		try
		{
		design = birtReportEngine.openReportDesign( sc.getRealPath("/Reports")+"/"+reportName );
		
		//create task to run and render report
	
		task = birtReportEngine.createRunAndRenderTask( design );		
		task.getAppContext().put("BIRT_VIEWER_HTTPSERVLET_REQUEST", req );
		}catch (Exception e){
    		e.printStackTrace();
    		try {
				throw new ServletException( e );
			} catch (ServletException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
		return task;
	}
	private IRunAndRenderTask  SetOutPutFormatReport(IRunAndRenderTask task,HttpServletRequest req, String tipo ,HttpServletResponse resp,int numrpt)
    	{
    	   
       
	try{
    		
    		IRenderOption options = new RenderOption();
    	
    	   if(tipo.equals("PDF")){
    			resp.setContentType( "application/pdf" );
    			options.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_PDF);
    			resp.setHeader(	"Content-Disposition", "inline; filename=\""+NameReportExport(numrpt,req)+".pdf\"" );	    			
    		}
    		if(tipo.equals("Excel")){
    			resp.setContentType( "application/vnd.ms-excel" );
    			 options.setOutputFormat("xls");
    			 resp.setHeader("Content-Disposition", "inline; filename=\""+NameReportExport(numrpt,req)+".xls\"" );    			  
    		}
    		if(tipo.equals("doc")){
    	        resp.setContentType("application/msword;charset=utf-8");
    	        options.setOutputFormat("doc");
    	        resp.setHeader("Content-Disposition", "inline; filename=\""+NameReportExport(numrpt,req)+".doc\"" );   		
    		}
    		options.setOutputStream(resp.getOutputStream());
    		task.setRenderOption(options);
    		
       }catch (Exception e){
    		e.printStackTrace();
    		try {
				throw new ServletException( e );
			} catch (ServletException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
    		return task;
       }
	 @SuppressWarnings({ "unused", "unchecked" })
	private String verStatus(IRunAndRenderTask task) {
			String msg = "";  
		    if (task.getStatus() == IEngineTask.STATUS_CANCELLED) {
		        List<EngineException> errors = task.getErrors();
		        if (!errors.isEmpty()) {
		            msg = "Se produjo un error al mostrar el reporte.\n Avise al administrador del sistema.";//errors.get(errors.size() - 1).getMessage();
		        }
		        task.getAppContext().clear();
		        
		    }
		    return msg;
		  }
	private void gotoPage(String address,
	        HttpServletRequest request,
	        HttpServletResponse response)
	throws ServletException, IOException {
	RequestDispatcher dispatcher =
	getServletContext().getRequestDispatcher(address);
	dispatcher.forward(request, response);
	}
	@SuppressWarnings("unused")
	private void includePage(String address,
             HttpServletRequest request,
             HttpServletResponse response)
			  throws ServletException, IOException {
	 			RequestDispatcher dispatcher =
	 			getServletContext().getRequestDispatcher(address);
	 			dispatcher.include(request, response);
	 }
}
