<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<Curso> cursos = (List<Curso>) request.getAttribute("cursos");
	List<Carrera> carreras = (List<Carrera>) request.getAttribute("carreras");
%>


<div class="row h-100 p-3 m-0">
	<div class="col-sm-12 col-md-5 col-lg-4 col-xl-3 menu-cursos">
		<div class="container-menu-cursos">
			<div class="form-group">
				<label for="exampleFormControlSelect1">Cursos</label> <select
					class="form-control" id="select_cursos" onchange="selectCursos();">
					<%
						if (!cursos.isEmpty()) {
							for (Curso curso : cursos) {
					%>
					<option value="<%=curso.getId()%>"><%=curso.getDenominacion()%></option>
					<%
						}
						}
					%>

				</select>
			</div>
			<div class="btn-group w-100" role="group" aria-label="Basic example">
				<button type="button" class="btn btn-primary w-50"
					onclick="nuevoCurso();" id="button_nuevo">Nuevo Curso</button>
				<button type="button" class="btn btn-primary w-100"
					style="display: none" id="button_cancelar"
					onclick="cancelarNuevoCurso();">Cancelar</button>
				<button type="button" class="btn btn-danger w-50"
					onclick="eliminarCurso();" id="button_eliminar">Eliminar
					Curso</button>
			</div>
		</div>
	</div>
	<div class="col-sm-12 col-md-7 col-lg-8 col-xl-9">


		<form id="form-cursos">
			<div class="row panel-cursos">
				<div class="col-12 col-xl-6">
					<input id="id_curso" name="id_curso" type="hidden"
						value="${id_curso}">
					<div class="form-group">
						<label for="exampleInputEmail1">Descripción</label> <input
							type="text" class="form-control" id="ds_curso" name="ds_curso"
							placeholder="Ingrese Descripción del Curso" value="${ds_curso}">
					</div>
				</div>
				<div class="col-12 col-xl-6">
					<div class="form-group">
						<label for="exampleFormControlSelect1">Carrera</label> <select
							class="form-control" id="select_carrera" name="select_carrera">
							<%
								if (!carreras.isEmpty()) {
									for (Carrera carrera : carreras) {
							%>
							<option
								<%=(Integer.parseInt(request.getAttribute("carrera").toString()) == carrera.getId()
							? "selected"
							: "")%>
								value="<%=carrera.getId()%>"><%=carrera.getDenominacion()%></option>
							<%
								}
								}
							%>

						</select>
					</div>



				</div>
				<div class="col-12 float-right">
					<button type="button" class="btn btn-success pull-right"
						onclick="guardarCurso();">Guardar</button>
				</div>
			</div>
		</form>


	</div>
</div>