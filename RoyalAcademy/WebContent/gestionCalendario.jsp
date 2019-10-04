<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<Carrera> carreras = (List<Carrera>) request.getAttribute("carreras");
	List<Curso> cursos = (List<Curso>) request.getAttribute("cursos");
%>
<script type="text/javascript">selectCursoFechaExamen();</script>
<div class="row row-perfiles" style="overflow-y: auto">
	<div class="col-md-3 column-3-perfiles" style="overflow-y: auto">
		<%
			if (!carreras.isEmpty() && !cursos.isEmpty()) {
		%>
				<div class="form-group">
					<label for="exampleFormControlSelect1">Carreras</label> <select
						class="form-control" id="select_carreras"
						onchange="selectCarreraCursoFechaExamen();">
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
				class="form-control" id="select_cursos" onchange="selectCursoFechaExamen();">
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

		<div class="btn-group width-100" role="group"
			aria-label="Basic example">
			<button type="button" class="btn btn-primary width-100"
				onclick="creacionFechaExamen();" id="button_nuevo">Nueva fecha</button>
		</div>

		<div id="container_fechas_examen" class="contenedor-preguntas">
			
		</div>
	</div>
	<div class="col-md-9" id="container-info-fecha-examen" style="max-height:90%">
		<form id="form-fecha-examen">
			<input type="hidden" id="id_fecha_examen" name="id_fecha_examen" value="0">
			<div class="form-group row">
					<label for="exampleInputEmail1">Descripcion</label> <input type="text"
						class="form-control" id="descripcion_fecha_examen" name="descripcion_fecha_examen"
						placeholder="Descripcion">
			</div>
			<div class="form-group row">
				<div class="form-group">
					<label for="fecha_examen">Fecha</label> <input
						type='text' id="fecha_examen" name="fecha_examen"
						placeholder="dd/mm/aaaa" class="form-control" />
				</div>
				<div class="form-group">
					<label for="fecha_examen">Examen</label> 
					<select class="form-control" id="select_examen">
						
					</select>
					<!-- aca van los examenes del curso -->
				</div>
			</div>
				<button type="button" class="btn btn-success pull-right"
					onclick="guardarFechaExamen();" id="botonGuardarFechaExamen">Guardar</button>
				<button type="button" class="btn btn-danger pull-right"
					onclick="eliminarFechaExamen();" id="botonEliminarFechaExamen">Eliminar</button>
		</form>
	</div>
</div>
<script>
	$('#fecha_examen').datetimepicker({
		dateFormat : "DD/MM/YYYY",
		//timeFormat : "HH:mm",
		//timePicker : true,
		//datePicker : true
		pickTime : false
	});
</script>