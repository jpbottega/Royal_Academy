<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<Carrera> carreras = (List<Carrera>) request.getAttribute("carreras");
	List<Curso> cursos = (List<Curso>) request.getAttribute("cursos");
	List<CursoExamen> examenes = (List<CursoExamen>) request.getAttribute("examenes");
%>
<script type="text/javascript">selectCursoAlumno();</script>


<div>
	<header class="site-header container-navbar">
		<nav class="navbar navbar-expand-lg navbar-dark navbar-admin">
			<h1 class="site-title">
				<a href="http://localhost:9080/RoyalAcademy/Login?accion=login">Royal<span>
						Academy</span></a>
			</h1>


			<button class="navbar-toggler btn-menu" type="button"
				data-toggle="collapse" data-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			
		</nav>
	</header>




	<div class="container-fluid panel-admin">
		<div class="functions-alumno-container">
			<div class="row h-100 p-3 m-0">
				<div class="col-12 container-inscripcion p-3">
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
							class="form-control" id="select_cursos"
							onchange="selectCursoInscripcionExamen();">
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
						<div class="col-12">
							<button type="button" class="btn btn-success pull-right ml-2"
								onclick="inscribirAlumnoCurso(<%=((Usuario) request.getSession().getAttribute("usuario")).getId()%>);"
								id="botonInscribirAlumno">Inscribirse</button>
							<button type="button" class="btn btn-danger pull-right"
								onclick="desinscribirAlumnoCurso(<%=((Usuario) request.getSession().getAttribute("usuario")).getId()%>);"
								id="botonInscribirAlumno">Eliminar Inscripcion</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>