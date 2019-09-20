<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<Carrera> carreras = (List<Carrera>) request.getAttribute("carreras");
	List<Sede> sedes_disponibles = new ArrayList<Sede>();
	List<Sede> sedes_habilitadas =new ArrayList<Sede>();
	
	if(request.getAttribute("sedes_disponibles")!=null){
		sedes_disponibles = (List<Sede>) request.getAttribute("sedes_disponibles");
	}
	if(request.getAttribute("sedes_habilitadas")!=null){
		sedes_habilitadas = (List<Sede>) request.getAttribute("sedes_habilitadas");
	}
%>


<div class="row row-perfiles">
	<div class="col-md-3 column-3-perfiles">
		<div class="form-group">
			<label for="exampleFormControlSelect1">Carreras</label> <select
				class="form-control" id="select_carreras"
				onchange="selectCarrera();">
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
		<div class="btn-group width-100" role="group"
			aria-label="Basic example">
			<button type="button" class="btn btn-primary width-50"
				onclick="nuevaCarrera();" id="button_nuevo">Nueva Carrera</button>
			<button type="button" class="btn btn-primary width-100"
				style="display: none" id="button_cancelar"
				onclick="cancelarNuevaCarrera();">Cancelar</button>
			<button type="button" class="btn btn-danger width-50"
				onclick="eliminarCarrera();" id="button_eliminar">Eliminar
				Carrera</button>
		</div>
	</div>
	<div class="col-md-9">

		<form id="form-carrera">
			<input id="id_carrera" name="id_carrera"  type="hidden"
				value="${id_carrera}">
			<div class="form-group">
				<label for="exampleInputEmail1">Descripción</label> <input
					type="text" class="form-control" id="ds_carrera" name="ds_carrera"
					placeholder="Ingrese Descripción de la carrera" value="${ds_carrera}">
			</div>
			<div class="row">
				<div class="col-md-5">
					<div class="form-group">
						<label for="exampleFormControlSelect1">Sedes Disponibles</label> <select
							class="form-control" id="sedes_disponibles" multiple>
							<%
								if (!sedes_disponibles.isEmpty()) {
									for (Sede sede : sedes_disponibles) {
							%>
							<option value="<%=sede.getId()%>"><%=sede.getSede()%></option>
							<%
								}
								}
							%>

						</select>
					</div>
				</div>
				<div class="col-md-1 col-funciones-perfil">
					<button type="button"
						class="btn btn-default button-funciones-perfil"
						title="Agregar Funcion" onclick="agregarSede();">>></button>
					<button type="button"
						class="btn btn-default button-funciones-perfil"
						title="Eliminar Funcion" onclick="eliminarSede();"><<</button>
				</div>
				<div class="col-md-5">
					<div class="form-group">
						<label for="exampleFormControlSelect1">Sedes Habilitadas</label> <select
							class="form-control" id="sedes_habilitadas" multiple>
							<%
								if (!sedes_habilitadas.isEmpty()) {
									for (Sede sede : sedes_habilitadas) {
							%>
							<option value="<%=sede.getId()%>"><%=sede.getSede()%></option>
							<%
								}
								}
							%>

						</select>
					</div>
				</div>
			</div>
			<button type="button" class="btn btn-success pull-right"
				onclick="guardarCarrera();">Guardar</button>
		</form>

	</div>
</div>