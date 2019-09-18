<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<Rol> roles = (List<Rol>) request.getAttribute("roles");
	List<Funciones> funciones_disponibles = (List<Funciones>) request.getAttribute("funciones_disponibles");
	List<Funciones> funciones_habilitadas = (List<Funciones>) request.getAttribute("funciones_habilitadas");
%>


<div class="row row-perfiles">
	<div class="col-md-3 column-3-perfiles">
		<div class="form-group">
			<label for="exampleFormControlSelect1">Perfiles</label> <select
				class="form-control" id="select_perfiles"
				onchange="selectPerfiles();">
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
			<button type="button" class="btn btn-primary width-50" onclick="nuevoPerfil();" id="button_nuevo">Nuevo
				Perfil</button>
			<button type="button" class="btn btn-primary width-100" style="display:none" id="button_cancelar" onclick="cancelarNuevoPerfil();">Cancelar</button>
			<button type="button" class="btn btn-danger width-50" onclick="eliminarPerfil();" id="button_eliminar">Eliminar
				Perfil</button>
		</div>
	</div>
	<div class="col-md-9">

		<form id="form-perfiles">
			<input id="id_perfil" name="id_perfil" type="hidden"
				value="${id_rol}">
			<div class="form-group">
				<label for="exampleInputEmail1">Descripción</label> <input
					type="text" class="form-control" id="ds_perfil" name="ds_perfil"
					placeholder="Ingrese Descripción del Perfil" value="${ds_rol}">
			</div>
			<div class="row">
				<div class="col-md-5">
					<div class="form-group">
						<label for="exampleFormControlSelect1">Funciones Disponibles</label> <select
							class="form-control" id="funciones_disponibles" multiple>
							<%
								if (!funciones_disponibles.isEmpty()) {
									for (Funciones funcion : funciones_disponibles) {
							%>
							<option value="<%=funcion.getId_funcion()%>"><%=funcion.getDs_funcion()%></option>
							<%
								}
								}
							%>

						</select>
					</div>
				</div>
				<div class="col-md-1 col-funciones-perfil">
				<button type="button" class="btn btn-default button-funciones-perfil" title="Agregar Funcion" onclick="agregarFuncion();">>></button>
				<button type="button" class="btn btn-default button-funciones-perfil" title="Eliminar Funcion" onclick="eliminarFuncion();"><<</button>
				</div>
				<div class="col-md-5">
					<div class="form-group">
						<label for="exampleFormControlSelect1">Funciones Habilitadas</label> <select
							class="form-control" id="funciones_habilitadas" multiple>
							<%
								if (!funciones_habilitadas.isEmpty()) {
									for (Funciones funcion : funciones_habilitadas) {
							%>
							<option value="<%=funcion.getId_funcion()%>"><%=funcion.getDs_funcion()%></option>
							<%
								}
								}
							%>

						</select>
					</div>
				</div>
			</div>
			<button type="button" class="btn btn-success pull-right"
				onclick="guardarPerfil();">Guardar</button>
		</form>

	</div>
</div>