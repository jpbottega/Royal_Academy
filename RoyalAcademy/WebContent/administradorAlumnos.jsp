<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	Rol rol = (Rol) request.getAttribute("rol");
	//ME PARECE Q LO DE ABAJO ES MEDIO BRUTO, PREGUNTAR A GONZA COMO HACERLO BIEN
%>
<script>
	botonNuevoAlumno();
</script>

<div class="row row-perfiles">
	<div class="col-md-3 column-3-perfiles">
		<div class="form-group">
			<label for="exampleFormControlSelect1">Alumnos</label>
			<div class="btn-group width-100" role="group"
				aria-label="Basic example">
				<button type="button" class="btn btn-primary width-50"
					onclick="botonNuevoAlumno();" id="button_nuevo">Nuevo
					Alumno</button>
				<button type="button" class="btn btn-success width-50"
					onclick="botonBuscarAlumno();" id="button_eliminar">Buscar
					Alumno</button>
			</div>
		</div>
	</div>
	<div class="col-md-9">
		<form id="form-alumno-registrar">
			<input type="hidden" id="id_rol" name="id_rol" value="<%=rol.getId()%>"> 
			<input type="hidden" id="verificado" name="verificado" value="false">
			<div class="form-group">
				<label for="exampleInputEmail1">Nombre</label> <input type="text"
					class="form-control" id="nombreAlumno" name="nombreAlumno"
					placeholder="Nombre del alumno"> <label
					for="exampleInputEmail1">Apellido</label> <input type="text"
					class="form-control" id="apellidoAlumno" name="apellidoAlumno"
					placeholder="Apellido del alumno"> <label
					for="exampleInputEmail1">E-Mail</label> <input type="email"
					class="form-control" id="mailAlumno" name="mailAlumno"
					placeholder="Correo del alumno"> <label
					for="exampleInputEmail1">Telefono</label> <input type="text"
					class="form-control" id="telefonoAlumno" name="telefonoAlumno"
					placeholder="Telefono del alumno"> <label
					for="exampleInputEmail1">Contraseña</label> <input type="text"
					class="form-control" id="passAlumno" name="passAlumno"
					placeholder="Contraseña del alumno"> <label
					for="exampleInputEmail1">Fecha de Nacimiento</label> <input
					type="date" class="form-control" id="nacimientoAlumno"
					name="nacimientoAlumno" placeholder="Fecha de nacimiento">
			</div>

			<button type="button" class="btn btn-success pull-right"
				onclick="guardarAlumno();">Guardar</button>
		</form>

		<form id="form-alumno-buscar">
			<input type="hidden" id="id_rol" name="id_rol" value="<%=rol.getId()%>">
			<div class="form-group">
				<label for="exampleInputEmail1">Nombre</label> <input type="text"
					class="form-control" id="nombreAlumno" name="nombreAlumno"
					placeholder="Nombre del alumno"> <label
					for="exampleInputEmail1">Apellido</label> <input type="text"
					class="form-control" id="apellidoAlumno" name="apellidoAlumno"
					placeholder="Apellido del alumno"> <label
					for="exampleInputEmail1">E-Mail</label> <input type="email"
					class="form-control" id="mailAlumno" name="mailAlumno"
					placeholder="Correo del alumno">
			</div>

			<button type="button" class="btn btn-success pull-right"
				onclick="buscarAlumno();">Buscar</button>
		</form>
  		<br></br>
		<table class="table" id="table-busqueda">
			<thead class="thead-dark">
				<tr>
					<th scope="col">Id</th>
					<th scope="col">Nombre</th>
					<th scope="col">Apellido</th>
					<th scope="col">E-Mail</th>
					<th scope="col">Telefono</th>
					<th scope="col">Fecha Nacimiento</th>
				</tr>
			</thead>
			<tbody id="table-cuerpo">
				
			</tbody>
		</table>
	</div>
</div>