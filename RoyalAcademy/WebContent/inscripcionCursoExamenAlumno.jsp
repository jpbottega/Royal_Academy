<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<Carrera> carreras = (List<Carrera>) request.getAttribute("carreras");
	List<Curso> cursos = (List<Curso>) request.getAttribute("cursos");
	List<CursoExamen> examenes = (List<CursoExamen>) request.getAttribute("examenes");
%>
<script type="text/javascript">selectCursoAlumno();</script>
<div class="row row-perfiles" style="overflow-y: auto">
	<div class="col-md-3 column-3-perfiles" style="overflow-y: auto">
		<%
			if (!carreras.isEmpty() && !cursos.isEmpty()) {
		%>
				<div class="form-group">
					<label for="exampleFormControlSelect1">Carreras</label> <select
						class="form-control" id="select_carreras"
						onchange="selectCarreraCursoInscripcionExamen();">
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
				class="form-control" id="select_cursos" onchange="selectCursoInscripcionExamen();">
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
					onclick="inscribirAlumnoCurso(<%=((Usuario)request.getSession().getAttribute("usuario")).getId() %>);" 
						id="botonInscribirAlumno">Inscribirse</button>
				<button type="button" class="btn btn-danger pull-right" 
					onclick="desinscribirAlumnoCurso(<%=((Usuario)request.getSession().getAttribute("usuario")).getId() %>);" 
						id="botonInscribirAlumno">Eliminar Inscripcion</button>
		</div>
	</div>
</div>