<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<CursoExamen> examenes = (List<CursoExamen>) request.getAttribute("examenes_inscripto");
%>
<div class="row row-perfiles" style="overflow-y: auto">
	<div class="ml-5 column-3-perfiles">
		<div class="form-group">
			<div for="exampleFormControlSelect1">Fecha Examen</div> <select
				class="form-control" id="select_examen">
				<%
					for (CursoExamen examen : examenes) {
				%>
				<option value="<%=examen.getId()%>"><%=examen.getDescripcion()%></option>
				<%
					}
				%>

			</select>
		</div>
		<div class="form-group row">
				<button type="button" class="btn btn-success pull-right" 
					onclick="rendirExamen();" 
						id="botonRendirExamen">Rendir Examen</button>
		</div>
		<div class="form-group row">
			<button type="button" class="btn btn-danger pull-right" 
						onclick="gotoHomeAlumno();" 
							id="botonVolver">Volver</button>
		</div>
	</div>
	<div class="col ml-5">
		<form id="resolucion_examen">
		</form>
	</div>
</div>