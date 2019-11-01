<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<Carrera> carreras = (List<Carrera>) request.getAttribute("carreras");
	List<Curso> cursos = (List<Curso>) request.getAttribute("cursos");
%>
<script type="text/javascript">
	selectCursoExamen();
</script>
<div class="row h-100 p-3 m-0">
	<div class="col-sm-12 col-md-5 col-lg-4 col-xl-3 menu-examenes">
		<div class="container-menu-examenes">
			<%
				if (!carreras.isEmpty() && !cursos.isEmpty()) {
			%>
			<div class="form-group">
				<label for="exampleFormControlSelect1">Carreras</label> <select
					class="form-control" id="select_carreras"
					onchange="selectCarreraCursoExamen();">
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
					onchange="selectCursoExamen();">
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
				<label for="exampleFormControlSelect1">Cantidad Preguntas</label> <input
					type="text" class="form-control" id="cantidad_preguntas"
					name="cantidad_preguntas" placeholder="Max. 50">
			</div>


			<div class="btn-group w-100" role="group" aria-label="Basic example">
				<button type="button" class="btn btn-primary w-50"
					onclick="creacionExamenManual();" id="button_nuevo">
					Manual</button>

				<button type="button" class="btn btn-secondary w-50"
					onclick="creacionExamenAutomatica();" id="button_nuevo">
					Automatica</button>
			</div>

			<div class="menu-examenes-lista mt-3">
				<div id="container_examenes" class="contenedor-preguntas"></div>
			</div>
		</div>
	</div>


	<div class="col-sm-12 col-md-7 col-lg-8 col-xl-9 h-100"
		id="container-info-examen">
		<form id="form-examen">
			<input id="id_examen" name="id_examen" type="hidden" value="0">
			<input id="id_usuario_creador" name="id_usuario_creador"
				type="hidden"
				value=<%=((Usuario) request.getSession().getAttribute("usuario")).getId()%>>




			<div class="row mr-0 mt-3">
				<div class="col-12">
					<div class="form-group">
						<label for="exampleInputEmail1">Descripcion</label> <input
							type="text" class="form-control" id="descripcion_examen"
							name="descripcion_examen" placeholder="Descripcion">
					</div>
				</div>
			</div>


		</form>




		<div class=row " id="preguntas_examen">

			<div class="col-12 col-xl-6">
				<div class="form-group container-panel-examenes">
					<label for="exampleInputEmail1">Preguntas Disponibles</label>
					<div id="preguntas_disponibles" class="header-examen"></div>
				</div>
			</div>
			<div class="col-12 col-xl-6">
				<div class="form-group container-panel-examenes">
					<label id="cantidad_preguntas_agregadas" for="exampleInputEmail1">Preguntas Agregadas</label>
					<div id="preguntas_agregadas" class="header-examen"></div>
				</div>
			</div>
		</div>


		<div class="row">
			<div class="col-12 float-right">
				<button type="button" class="btn btn-success ml-2 pull-right"
					onclick="guardarExamen();" id="botonGuardarExamen">Guardar</button>
				<button type="button" class="btn btn-danger pull-right"
					onclick="eliminarExamen();" id="botonEliminarExamen">Eliminar</button>
			</div>
		</div>

	</div>

</div>