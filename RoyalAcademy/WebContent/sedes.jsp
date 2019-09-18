<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<Sede> sedes = (List<Sede>) request.getAttribute("sedes");
	List<Pais> paises = (List<Pais>) request.getAttribute("paises");
%>


<div class="row row-perfiles">
	<div class="col-md-3 column-3-perfiles">
		<div class="form-group">
			<label for="exampleFormControlSelect1">Sedes</label> <select
				class="form-control" id="select_sedes"
				onchange="selectSedes();">
				<%
					if (!sedes.isEmpty()) {
						for (Sede sede : sedes) {
				%>
				<option value="<%=sede.getId()%>"><%=sede.getSede()%></option>
				<%
					}
					}
				%>

			</select>
		</div>
		<div class="btn-group width-100" role="group"
			aria-label="Basic example">
			<button type="button" class="btn btn-primary width-50"
				onclick="nuevaSede();" id="button_nuevo">Nueva Sede</button>
			<button type="button" class="btn btn-primary width-100"
				style="display: none" id="button_cancelar"
				onclick="cancelarNuevaSede();">Cancelar</button>
			<button type="button" class="btn btn-danger width-50"
				onclick="eliminarSede();" id="button_eliminar">Eliminar
				Sede</button>
		</div>
	</div>
	<div class="col-md-9">


		<form id="form-sedes">
			<div class="row">
				<div class="col-md-6">
					<input id="id_sede" name="id_sede" type="hidden" value="${id_sede}">
					<div class="form-group">
						<label for="exampleInputEmail1">Descripción</label> <input
							type="text" class="form-control" id="ds_sede" name="ds_sede"
							placeholder="Ingrese Descripción de la Sede" value="${ds_sede}">
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="exampleFormControlSelect1">País</label> <select
							class="form-control" id="select_pais" name="select_pais"
							>
							<%
								if (!paises.isEmpty()) {
									for (Pais pais : paises) {
							%>
							<option
								<%=(Integer.parseInt(request.getAttribute("pais").toString()) == pais.getId_pais() ? "selected"
							: "")%>
								value="<%=pais.getId_pais()%>"><%=pais.getDenominacion()%></option>
							<%
								}
								}
							%>

						</select>
					</div>

				</div>
			</div>
		</form>
		<button type="button" class="btn btn-success pull-right"
			onclick="guardarSede();">Guardar</button>

	</div>
</div>