<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<Rol> roles = (List<Rol>) request.getAttribute("roles");
	//ME PARECE Q LO DE ABAJO ES MEDIO BRUTO, PREGUNTAR A GONZA COMO HACERLO BIEN
%>
<script>
	selectRolesUsuario();
</script>

<div class="row row-perfiles">
	<div class="col-md-4 column-3-perfiles">
		<div class="form-group">
			<label for="exampleFormControlSelect1">Roles</label> <select
				class="form-control" id="select_roles"
				onchange="selectRolesUsuario();">
				<%
					if (!roles.isEmpty()) {
						for (Rol rol : roles) {
				%>
				<option value="<%=rol.getId()%>"><%=rol.getDenominacion()%></option>
				<%
					}
					}
				%>
			</select>
		</div>

		<div class="btn-group width-100" role="group"
			aria-label="Basic example">
			<button type="button" class="btn btn-primary width-100"
				onclick="botonNuevoUsuario();" id="button_nuevo">Nuevo
				Usuario</button>

		</div>

		<div class="form-group">
			<label for="exampleInputEmail1">Buscar</label> <input type="text"
				class="form-control" id="buscarUsuario" onkeyup="buscarUsuario();"
				placeholder="Buscar...">
		</div>


		<div class="col-md-12" style="height: 16rem;" id="tarjetasUsuarios"></div>

	</div>
	<div class="col-md-8">
		<form id="form-alumno-registrar">
			<input id="id_rol" name="id_rol" type="hidden" value="${id_rol}">
			<input type="hidden" id="verificado" name="verificado" value="false">
			<div class="form-group">
				<label for="exampleInputEmail1">Nombre</label> <input type="text"
					class="form-control" id="nombreUsuario" name="nombreUsuario"
					placeholder="Nombre del usuario"> <label
					for="exampleInputEmail1">Apellido</label> <input type="text"
					class="form-control" id="apellidoUsuario" name="apellidoUsuario"
					placeholder="Apellido del usuario"> <label
					for="exampleInputEmail1">E-Mail</label> <input type="email"
					class="form-control" id="mailUsuario" name="mailUsuario"
					placeholder="Correo del usuario"> <label
					for="exampleInputEmail1">Telefono</label> <input type="text"
					class="form-control" id="telefonoUsuario" name="telefonoUsuario"
					placeholder="Telefono del usuario"> <label
					for="exampleInputEmail1">Contraseña</label> <input type="text"
					class="form-control" id="passUsuario" name="passUsuario"
					placeholder="Contraseña del usuario"> <label
					for="exampleInputEmail1">Fecha de Nacimiento</label> <input
					type="date" class="form-control" id="nacimientoUsuario"
					name="nacimientoUsuario" placeholder="Fecha de nacimiento">
			</div>
			<button type="button" class="btn btn-success pull-right"
				onclick="guardarAlumno();">Guardar</button>
		</form>
	</div>
</div>