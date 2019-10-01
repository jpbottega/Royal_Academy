<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<Carrera> carreras = (List<Carrera>) request.getAttribute("carreras");
	List<Curso> cursos = (List<Curso>) request.getAttribute("cursos");
%>
<script type="text/javascript">selectCursoExamen();</script>
<div class="row row-perfiles" style="overflow-y: auto">
	<div class="col-md-3 column-3-perfiles" style="overflow-y: auto">
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
				class="form-control" id="select_cursos" onchange="selectCursoExamen();">
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
			<label for="exampleFormControlSelect1">Cantidad Preguntas</label>
			<input type="text" class="form-control" id="cantidad_preguntas" name="cantidad_preguntas"
				placeholder="Max. 50">
		</div> 


		<div class="btn-group width-100" role="group"
			aria-label="Basic example">
			<button type="button" class="btn btn-primary width-100"
				onclick="creacionExamenManual();" id="button_nuevo">Creacion Manual</button>
		</div>

		<div class="btn-group width-100" role="group"
			aria-label="Basic example">
				<button type="button" class="btn btn-primary width-100"
				onclick="creacionExamenAutomatica();" id="button_nuevo">Creacion Automatica</button>
		</div> 


		<div id="container_examenes" class="contenedor-preguntas">
			
		</div>
		
		
	</div>
	<div class="col-md-9" id="container-info-examen" style="max-height:90%">
		<form id="form-examen">
			<input id="id_examen" name="id_examen" type="hidden" value="0"> 
			<input id="id_usuario_creador" name="id_usuario_creador" type="hidden" value=<%=((Usuario)request.getSession().getAttribute("usuario")).getId()%>> 
			<div class="form-group row">
				<div class="col">
					<label for="exampleInputEmail1">Descripcion</label> <input type="text"
						class="form-control" id="descripcion_examen" name="descripcion_examen"
						placeholder="Descripcion">
				</div>
			</div>
		</form>
		<!-- <div class="container">  -->
			<div class="form-group row" id="preguntas_examen" style="height:80%">
				<div class="col">
				<label for="exampleInputEmail1">Preguntas Disponibles</label> 
		        	<div class="border rounded" id="preguntas_disponibles" style="max-height:80%;overflow-y: scroll;overflow-x:hidden"></div>
		        </div>
				<div class="col margin-left-4">
				<label for="exampleInputEmail1">Preguntas Agregadas</label> 
		            <div class="border rounded" id="preguntas_agregadas" style="max-height:80%;overflow-y: scroll;overflow-x:hidden"></div>
		         </div>
			</div>
			<div class="form-group row">
				<div class="col">
					<button type="button" class="btn btn-success pull-right" onclick="guardarExamen();" id="botonGuardarExamen">Guardar</button>
					<button type="button" class="btn btn-danger pull-right" onclick="eliminarExamen();" id="botonEliminarExamen">Eliminar</button>
				</div>
			</div>
		<!-- </div> -->
	</div>
</div>