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


		<div class="col-md-12 scrollable" style="height: 400px;">
			<table class="table" id="tarjetasUsuarios"></table>
		</div>

	</div>
	<div class="col-md-7 ml-4 mt-4">
		<form id="form-usuario">
			<input id="id_usuario" name="id_usuario" type="hidden"> <input
				id="id_rol" name="id_rol" type="hidden" value="${id_rol}"> <input
				type="hidden" id="verificado" name="verificado">
			<div class="form-group row">
				<div class="col">
					<label for="exampleInputEmail1">Nombre</label> <input type="text"
						class="form-control" id="nombreUsuario" name="nombreUsuario"
						placeholder="Nombre del usuario">
				</div>
				<div class="col">
					<label for="exampleInputEmail1">Apellido</label> <input type="text"
						class="form-control" id="apellidoUsuario" name="apellidoUsuario"
						placeholder="Apellido del usuario">
				</div>
			</div>
			<div class="form-group row">
				<div class="col">
					<label for="exampleInputEmail1">E-Mail</label> <input type="email"
						class="form-control" id="mailUsuario" name="mailUsuario"
						placeholder="Correo del usuario">
				</div>
				<div class="col">
					<div class="form-group">
						<label for="nacimientoUsuario">Fecha de Nacimiento</label> <input
							type='text' id="nacimientoUsuario" name="nacimientoUsuario"
							placeholder="Fecha de nacimiento" class="form-control" />


					</div>

				</div>
			</div>
			<div class="form-group row">
				<div class="col">
					<label for="exampleInputEmail1">Telefono</label> <input type="text"
						class="form-control" id="telefonoUsuario" name="telefonoUsuario"
						placeholder="Telefono del usuario">
				</div>
				<div class="col">
					<label for="exampleInputEmail1">Contraseña</label> <input
						type="password" class="form-control" id="passUsuario"
						name="passUsuario" placeholder="Contraseña del usuario">
				</div>
			</div>
			<div class="form-group row">
				<div class="col-md-6">
					<label for="exampleFormControlSelect1">Roles</label> <select
						class="form-control" id="rol_usuario" name="rol_usuario">
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
			</div>
			<button type="button" class="btn btn-success pull-right"
				onclick="guardarUsuario();" id="botonGuardarUsuario">Guardar</button>
			<button type="button" class="btn btn-danger pull-right"
				onclick="eliminarUsuario();" id="botonEliminarUsuario">Eliminar</button>
		</form>
	</div>

</div>
<script>
	$('#nacimientoUsuario').datetimepicker({
		format : "DD/MM/YYYY",
		pickTime : false
	});
</script>