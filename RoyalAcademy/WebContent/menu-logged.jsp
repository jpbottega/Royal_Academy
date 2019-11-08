<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

List<PermisoFunciones> permisoFunciones = (List<PermisoFunciones>) 	request.getSession().getAttribute("permisoFunciones");
System.out.println(permisoFunciones.size());
%>
	<%if(permisoFunciones.get(2).getHabilitada()==1){ %>
		<li class="nav-item">
			<div class="dropdown menu-option">
				<span onclick="gotoPerfiles();">Perfiles</span>
			</div>
		</li>
	<%} %>
	<%if(permisoFunciones.get(15).getHabilitada()==1){ %>
		<li class="nav-item">
			<div class="dropdown menu-option">
				<span onclick="gotoReportes();">Reportes</span>
			</div>
		</li>
	<%} %>
	<%if(permisoFunciones.get(12).getHabilitada()==1){ %>
		<li class="nav-item">	
			<div class="dropdown menu-option">
				<span onclick="miPerfil();">Mi Perfil</span>
			</div>
		</li>
	<%} %>
	<%if(permisoFunciones.get(12).getHabilitada()==1){ %>
	<li class="nav-item">	
			<div class="dropdown menu-option">
				<span><a href="#section-cursos">Cursos</a></span>
			</div>
		</li>
	<%}%>
	<%if(permisoFunciones.get(3).getHabilitada()==1){ %>
	<li class="nav-item">
		<div class="dropdown menu-option">
			<span onclick="gotoUsuarios();">Usuarios</span>
		</div>
		</li>
	<%} %>
	<%if(permisoFunciones.get(4).getHabilitada()==1 || permisoFunciones.get(5).getHabilitada()==1 || permisoFunciones.get(6).getHabilitada()==1){ %>
	<li class="nav-item">
	<div class="dropdown menu-option">
		<span>Organización</span>
		<div class="dropdown-content">
		<%if(permisoFunciones.get(4).getHabilitada()==1){ %>
			<div class="option" onclick="gotoSedes();">Sedes</div>
		<%}if(permisoFunciones.get(5).getHabilitada()==1){ %>
			<div class="option" onclick="gotoCarreras();">Carreras</div>
		<%}if(permisoFunciones.get(6).getHabilitada()==1){ %>
			<div class="option" onclick="gotoCursos();">Cursos</div>
		<%} %>	
		</div>
	</div>
	</li>
	<%} %>	
	<%if(permisoFunciones.get(7).getHabilitada()==1 || permisoFunciones.get(8).getHabilitada()==1 || permisoFunciones.get(14).getHabilitada()==1){ %>
	<li class="nav-item">
	<div class="dropdown menu-option">
		<span>Examenes</span>
		<div class="dropdown-content">
		<%if(permisoFunciones.get(7).getHabilitada()==1){ %>
			<div class="option" onclick="gotoPreguntas();">Preguntas</div>
		<%}if(permisoFunciones.get(8).getHabilitada()==1){ %>
			<div class="option" onclick="gotoExamenes();">Examenes</div>
		<%}if(permisoFunciones.get(14).getHabilitada()==1){ %>
			<div class="option" onclick="gotoCorreccionExamen();">Correccion</div>
			<div class="option" onclick="gotoNotasCursos();">Notas</div>
		<%} %>	
		</div>
	</div>
	</li>
	<%} %>	
	<%if(permisoFunciones.get(13).getHabilitada()==1){%>
	<li class="nav-item">
		<div class="dropdown menu-option">
				<span onclick="gotoCalendario();">Calendario</span>
		</div>
	</li>
	<%} %>	
	<%if(permisoFunciones.get(12).getHabilitada()==1){ %>
	<li class="nav-item">
		<div class="dropdown menu-option">
			<span>Examenes</span>
			<div class="dropdown-content">
				<div class="option" onclick="gotoInscripcionExamenAlumno();">Inscripcion</div>
				<div class="option" onclick="gotoResolucionExamenAlumno();">Rendir Examen</div>
			</div>
		</div>
	</li>
	<%} %>	
<li class="nav-item">
<div class="dropdown menu-option">
	<span><a href="index.jsp">Salir</a></span>
</div>
</li>