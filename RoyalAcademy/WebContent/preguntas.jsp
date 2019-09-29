<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<Carrera> carreras = (List<Carrera>) request.getAttribute("carreras");
	List<Curso> cursos = (List<Curso>) request.getAttribute("cursos");
	List<Pregunta> preguntas = (List<Pregunta>) request.getAttribute("preguntas");
%>


<div class="row row-perfiles" style="overflow-y: auto">
	<div class="col-md-3 column-3-perfiles">
		<%
			if (!carreras.isEmpty() && !cursos.isEmpty()) {
		%>
		<div class="form-group">
			<label for="exampleFormControlSelect1">Carreras</label> <select
				class="form-control" id="select_carreras"
				onchange="selectCarreraCurso();">
				<%
					for (Carrera carrera : carreras) {
				%>
				<option value="<%=carrera.getId()%>"><%=carrera.getDenominacion()%></option>
				<%
					}
				%>

			</select>
		</div>

		<div class="form-group">
			<label for="exampleFormControlSelect1">Cursos</label> <select
				class="form-control" id="select_cursos" onchange="selectCursoPregunta();">
				<%
					for (Curso curso : cursos) {
				%>
				<option value="<%=curso.getId()%>"><%=curso.getDenominacion()%></option>
				<%
					}
				%>

			</select>
		</div>


		<div class="btn-group width-100" role="group"
			aria-label="Basic example">
			<button type="button" class="btn btn-primary width-100"
				onclick="nuevaPregunta();" id="button_nuevo">Nueva Pregunta</button>
		</div>
		<div id="container_preguntas" class="contenedor-preguntas">
			<%
				if (!preguntas.isEmpty()) {
						for (Pregunta pregunta : preguntas) {
			%>
			<div class="row" id="card_<%=pregunta.getId()%>">
				<div class="col-md-12">
					<div class="style-preguntas"
						onclick="selectPregunta(<%=pregunta.getId()%>);"
						id="pregunta_<%=pregunta.getId()%>"><%=pregunta.getPregunta()%></div>
				</div>
			</div>



			<%
				}
					} else {
			%>

			<div class="alert alert-danger" role="alert">No hay Preguntas</div>

			<%
				}
			%>
		</div>
		<%
			} else {
		%>

		<div class="alert alert-danger" role="alert">Asegúrese de que
			existan carreras y cursos</div>


		<%
			}
		%>
	</div>
	<div class="col-md-9" id="container-info-pregunta"></div>
</div>