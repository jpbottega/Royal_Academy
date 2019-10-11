<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<Carrera> carreras = (List<Carrera>) request.getAttribute("carreras");
	List<Curso> cursos = (List<Curso>) request.getAttribute("cursos");
	List<Pregunta> preguntas = (List<Pregunta>) request.getAttribute("preguntas");
%>


<div class="row h-100 p-3 m-0">
	<div class="col-sm-12 col-md-5 col-lg-4 col-xl-3 menu-preguntas">
		<div class="container-menu-preguntas">
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
					class="form-control" id="select_cursos"
					onchange="selectCursoPregunta();">
					<%
						for (Curso curso : cursos) {
					%>
					<option value="<%=curso.getId()%>"><%=curso.getDenominacion()%></option>
					<%
						}
					%>

				</select>
			</div>


			<div class="btn-group w-100" role="group" aria-label="Basic example">
				<button type="button" class="btn btn-primary w-100"
					onclick="nuevaPregunta();" id="button_nuevo">Nueva
					Pregunta</button>
			</div>

			<div class="menu-preguntas-lista mt-3">
				<div id="container_preguntas">
					<%
						if (!preguntas.isEmpty()) {
								for (Pregunta pregunta : preguntas) {
					%>
					<div class="row card card-styles" id="card_<%=pregunta.getId()%>">

						<div class="card-body" onclick="selectPregunta(<%=pregunta.getId()%>);">
							<div 
								id="pregunta_<%=pregunta.getId()%>"><%=pregunta.getPregunta()%></div>
						</div>

					</div>



					<%
						}
							} else {
					%>

					<div class="alert alert-danger mt-3" role="alert">No hay Preguntas</div>

					<%
						}
					%>
				</div>
				<%
					} else {
				%>

				<div class="alert alert-danger mt-3" role="alert">Asegúrese de que
					existan carreras y cursos</div>


				<%
					}
				%>
			</div>
		</div>
	</div>
	<div class="col-sm-12 col-md-7 col-lg-8 col-xl-9 h-100"
		id="container-info-pregunta"></div>
</div>