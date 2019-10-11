<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<Curso> cursos = (List<Curso>) request.getAttribute("cursos");
%>
<script type="text/javascript">selectCursoExamenDocente();</script>
<div class="row row-perfiles" style="overflow-y: auto">
	<div class="col-md-3 column-3-perfiles" style="overflow-y: auto">
		<%
			if (!cursos.isEmpty()) {
		%>
		<div class="form-group">
			<label for="exampleFormControlSelect1">Mis Cursos</label> <select
				class="form-control" id="select_cursos" onchange="selectCursoExamenDocente();">
				<%
					for (Curso curso : cursos) {
				%>
				<option value="<%=curso.getId()%>"><%=curso.getDenominacion()%></option>
				<%
					}
			}
				%>

			</select>
		</div>
		<div id="container_examenes" class="contenedor-preguntas"></div>
		
		
	</div>
	<div class="col-md-9" id="container-info-examen"></div>
</div>