<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<CursoExamen> examenes = (List<CursoExamen>) request.getAttribute("examenes_inscripto");
%>

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
				<div class="col-sm-12 col-md-5 col-lg-4 col-xl-3 menu-resolucion">
					<div class="container-menu-resolucion">
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



						<button type="button" class="btn btn-success pull-right w-100"
							onclick="rendirExamen();" id="botonRendirExamen">Rendir
							Examen</button>


					</div>
				</div>
				<div class="col-sm-12 col-md-7 col-lg-8 col-xl-9 h-100"
					id="resolucion_examen"></div>


			</div>
		</div>
	</div>
</div>