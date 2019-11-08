<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<Carrera> carreras = (List<Carrera>) request.getAttribute("carreras");
	List<Curso> cursos = (List<Curso>) request.getAttribute("cursos");
%>


<div class="row h-100 p-3 m-0">
	<div class="col-sm-12 col-md-5 col-lg-4 col-xl-3 menu-sedes">
		<div class="container-menu-sedes">
			<div class="form-group">
				<label for="exampleFormControlSelect1">Reportes</label> <select
					class="form-control" id="select_reportes"
					onchange="selectReportes();">

					<option value="1">Información de Cursos</option>


				</select>
			</div>

		</div>
	</div>
	<div class="col-sm-12 col-md-7 col-lg-8 col-xl-9">


		<form id="form-sedes">
			<div class="row panel-sedes">
				<div class="col-12 col-xl-6">
					<div class="form-group">
						<label for="exampleFormControlSelect1">Carrera</label> <select
							class="form-control" id="select_carrera" name="select_carrera"
							onchange="traerCurso();">
							<%
								if (!carreras.isEmpty()) {
									for (Carrera carrera : carreras) {
							%>
							<option value="<%=carrera.getId()%>"><%=carrera.getDenominacion()%></option>
							<%
								}
								}
							%>

						</select>
					</div>



				</div>
				<div class="col-12 col-xl-6" id="container_cursos">
					<jsp:include page="select_cursos.jsp" />
				</div>
				<div class="col-12 float-right">
					<button type="button" class="btn btn-success pull-right"
						onclick="emitirReporte();">Emitir</button>
				</div>
			</div>
		</form>


	</div>
</div>