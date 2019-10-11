<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<Rol> roles = (List<Rol>) request.getAttribute("roles");
	List<Sede> sedesDisponibles = (List<Sede>) request.getAttribute("sedesDisponibles");
	List<Carrera> listaCarreras = (List<Carrera>) request.getAttribute("carreras");
	List<Curso> cursosDisponibles = (List<Curso>) request.getAttribute("cursos");
%>
<script>
	selectRolesUsuario();
</script>

<div class="row h-100 p-3 m-0">
	<div class="col-sm-12 col-md-5 col-lg-4 col-xl-3 menu-usuarios">
		<div class="container-menu-usuarios">
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

			<div class="btn-group w-100" role="group" aria-label="Basic example">
				<button type="button" class="btn btn-primary w-100"
					onclick="botonNuevoUsuario();" id="button_nuevo">Nuevo
					Usuario</button>
			</div>

			<div class="form-group mt-3">
				<label for="exampleInputEmail1">Buscar</label> <input type="text"
					class="form-control" id="buscarUsuario" onkeyup="buscarUsuario();"
					placeholder="Buscar...">
			</div>


			<div class="menu-usuarios-lista">
				<div id="tarjetasUsuarios"></div>
			</div>
		</div>
	</div>


	<div class="col-sm-12 col-md-7 col-lg-8 col-xl-9">
		<form id="form-usuario">
			<div class="row panel-usuarios">
				<input id="id_usuario" name="id_usuario" type="hidden" value="0">
				<input id="id_rol" name="id_rol" type="hidden" value="${id_rol}">
				<input type="hidden" id="verificado" name="verificado"> <input
					type="hidden" id="passUsuario" name="passUsuario" value="">

				<div class="col-md-12 col-lg-6">
					<div class="form-group">
						<label for="exampleInputEmail1">Nombre</label> <input type="text"
							class="form-control" id="nombreUsuario" name="nombreUsuario"
							placeholder="Nombre del usuario">
					</div>
				</div>
				<div class="col-md-12 col-lg-6">
					<div class="form-group">
						<label for="exampleInputEmail1">Apellido</label> <input
							type="text" class="form-control" id="apellidoUsuario"
							name="apellidoUsuario" placeholder="Apellido del usuario">
					</div>
				</div>


				<div class="col-md-12 col-lg-6">
					<div class="form-group">
						<label for="exampleInputEmail1">E-Mail</label> <input type="email"
							class="form-control" id="mailUsuario" name="mailUsuario"
							placeholder="Correo del usuario">
					</div>
				</div>
				<div class="col-md-12 col-lg-6">
					<div class="form-group">
						<label for="nacimientoUsuario">Fecha de Nacimiento</label> <input
							type='text' id="nacimientoUsuario" name="nacimientoUsuario"
							placeholder="dd/mm/aaaa" class="form-control" />
					</div>
				</div>


				<div class="col-md-12 col-lg-6">
					<div class="form-group">
						<label for="exampleInputEmail1">Telefono</label> <input
							type="text" class="form-control" id="telefonoUsuario"
							name="telefonoUsuario" placeholder="Telefono del usuario">
					</div>
				</div>
				<div class="col-md-12 col-lg-6">
					<div class="form-group">
						<label for="exampleInputEmail1">DNI</label> <input type="text"
							class="form-control" id="dniUsuario" name="dniUsuario"
							placeholder="Dni del usuario">
					</div>
				</div>


				<div class="col-md-12 col-lg-6">
					<div class="form-group">
						<label for="exampleFormControlSelect1">Roles</label> <select
							class="form-control" id="rol_usuario" name="rol_usuario"
							onchange="cambioRolAdminOrganizacion();">
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
				<div class="col-md-12 col-lg-6" id="seleccion_carreras">
					<label for="exampleFormControlSelect1">Carreras</label> <select
						class="form-control" id="id_carrera" name="id_carrera"
						onchange="actualizarCursos();">
						<option value="0" selected>Todas</option>
						<%
							if (!listaCarreras.isEmpty()) {
								for (Carrera carrera : listaCarreras) {
						%>
						<option value="<%=carrera.getId()%>"><%=carrera.getDenominacion()%></option>
						<%
							}
							}
						%>
					</select>
				</div>

				<div class="col-12">

					<div class="row" id="adminSedesUsuario">
						<div class="col-10 col-xl-5">
							<div class="form-group">
								<label for="exampleFormControlSelect1">Sedes Disponibles</label>
								<select class="form-control" id="sedes_disponibles_usuario"
									multiple>
									<%
										if (!sedesDisponibles.isEmpty()) {
											for (Sede sede : sedesDisponibles) {
									%>
									<option value=<%=sede.getId()%>>
										<%=sede.getSede()%>
									</option>
									<%
										}
										}
									%>
								</select>
							</div>
						</div>
						<div class="col-2 col-xl-1 btn-funciones-usuarios">
						<div>
							<button type="button"
								class="btn btn-default button-funciones-perfil"
								title="Agregar Sede" onclick="agregarSedeUsuario();">>></button>
							<button type="button"
								class="btn btn-default button-funciones-perfil"
								title="Eliminar Sede" onclick="eliminarSedeUsuario();"><<</button>
						</div>
						</div>
						<div class="col-10 col-xl-5">
							<div class="form-group">
								<label for="exampleFormControlSelect1">Sedes Habilitadas</label>
								<select class="form-control" id="sedes_habilitadas_usuario"
									multiple>
								</select>
							</div>
						</div>
					</div>

				</div>

				<div class="col-12">
					<div class="row" id="adminCursosUsuario">
						<div class="col-10 col-xl-5">
							<div class="form-group">
								<label for="exampleFormControlSelect1">Cursos
									Disponibles</label> <select class="form-control"
									id="cursos_disponibles_usuario" multiple>
									<%
										if (!cursosDisponibles.isEmpty()) {
											for (Curso curso : cursosDisponibles) {
									%>
									<option value="<%=curso.getId()%>"><%=curso.getDenominacion()%></option>
									<%
										}
										}
									%>
								</select>
							</div>
						</div>
						<div class="col-2 col-xl-1 btn-funciones-usuarios">
						<div>
							<button type="button"
								class="btn btn-default button-funciones-perfil"
								title="Agregar Curso" onclick="agregarCursoUsuario();">>></button>
							<button type="button"
								class="btn btn-default button-funciones-perfil"
								title="Eliminar Curso" onclick="eliminarCursoUsuario();"><<</button>
						</div>
						</div>
						<div class="col-10 col-xl-5">
							<div class="form-group">
								<label for="exampleFormControlSelect1">Cursos
									Habilitados</label> <select class="form-control"
									id="cursos_habilitadas_usuario" multiple>
								</select>
							</div>
						</div>
					</div>
				</div>

				<div class="col-12 float-right">
					<button type="button" class="btn btn-success pull-right"
						onclick="guardarUsuario();" id="botonGuardarUsuario">Guardar</button>
					<button type="button" class="btn btn-danger pull-right"
						onclick="eliminarUsuario();" id="botonEliminarUsuario">Eliminar</button>
				</div>
			</div>
		</form>
	</div>

</div>
<script>
	$('#nacimientoUsuario').datetimepicker({
		format : "DD/MM/YYYY",
		pickTime : false
	});
</script>