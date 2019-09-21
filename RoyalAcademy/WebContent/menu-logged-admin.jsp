<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

List<PermisoFunciones> permisoFunciones = (List<PermisoFunciones>) 	request.getSession().getAttribute("permisoFunciones");
System.out.println(permisoFunciones.size());
%>
	<%if(permisoFunciones.get(2).getHabilitada()==1){ %>
		<div class="dropdown menu-option">
			<span onclick="gotoPerfiles();">Perfiles</span>
		</div>
	<%} %>
	<%if(permisoFunciones.get(3).getHabilitada()==1){ %>
		<div class="dropdown menu-option">
			<span onclick="gotoUsuarios();">Usuarios</span>
		</div>
	<%} %>
	<%if(permisoFunciones.get(4).getHabilitada()==1 || permisoFunciones.get(5).getHabilitada()==1 || permisoFunciones.get(6).getHabilitada()==1){ %>
	<div class="dropdown menu-option">
		<span>Organización</span>
		<div class="dropdown-content">
		<%if(permisoFunciones.get(4).getHabilitada()==1){ %>
			<div class="option" onclick="gotoSedes();">Sedes</div>
		<%}if(permisoFunciones.get(5).getHabilitada()==1){ %>
			<div class="option" onclick="gotoCarreras();">Carreras</div>
		<%}if(permisoFunciones.get(6).getHabilitada()==1){ %>
			<div class="option">Cursos</div>
		<%} %>	
		</div>
	</div>
	<%} %>	
<div class="dropdown menu-option">
	<span><a href="index.jsp">Salir</a></span>
</div>