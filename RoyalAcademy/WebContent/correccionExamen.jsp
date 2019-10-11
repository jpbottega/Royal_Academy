<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<Curso> cursos = (List<Curso>) request.getAttribute("cursos");
%>
<script type="text/javascript">
	selectCursoExamenDocente();
</script>
<div class="row h-100 p-3 m-0">
	<div class="col-sm-12 col-md-5 col-lg-4 col-xl-3 menu-examenes">
		<div class="container-menu-examenes">
			<%
				if (!cursos.isEmpty()) {
			%>

			<div class="form-group">
				<label for="exampleFormControlSelect1">Cursos</label> <select
					class="form-control" id="select_cursos"
					onchange="selectCursoExamenDocente();">
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


			<div class="menu-examenes-lista mt-3">
				<div id="container_examenes" class="contenedor-preguntas"></div>
			</div>
		</div>
	</div>


	<div class="col-sm-12 col-md-7 col-lg-8 col-xl-9 h-100"	id="container-info-examen"></div>

</div>