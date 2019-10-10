<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<CursoExamen> examenes = (List<CursoExamen>) request.getAttribute("examenes");
%>
<script type="text/javascript">selectCursoAlumno();</script>
<div class="row row-perfiles" style="overflow-y: auto">
	<div class="column-3-perfiles">
		<div class="form-group">
			<label for="exampleFormControlSelect1">Fecha Examen</label> <select
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
					onclick="rendirExamen(<%=((Usuario)request.getSession().getAttribute("usuario")).getId() %>);" 
						id="botonRendirExamen">Rendir Examen</button>
		</div>
	</div>
	<div class="col">
		<form id="resolucion_examen">
		
		
		
		
		</form>
	</div>
</div>