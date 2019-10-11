<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<Sede> sedes = (List<Sede>) request.getAttribute("sedes");
	List<Pais> paises = (List<Pais>) request.getAttribute("paises");
%>


<div class="row h-100 p-3 m-0">
	<div class="col-sm-12 col-md-5 col-lg-4 col-xl-3 menu-sedes">
		<div class="container-menu-sedes">
			<div class="form-group">
				<label for="exampleFormControlSelect1">Sedes</label> <select
					class="form-control" id="select_sedes" onchange="selectSedes();">
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
			<div class="btn-group w-100" role="group" aria-label="Basic example">
				<button type="button" class="btn btn-primary w-50"
					onclick="nuevaSede();" id="button_nuevo">Nueva Sede</button>
				<button type="button" class="btn btn-primary w-100"
					style="display: none" id="button_cancelar"
					onclick="cancelarNuevaSede();">Cancelar</button>
				<button type="button" class="btn btn-danger w-50"
					onclick="eliminarSede();" id="button_eliminar">Eliminar
					Sede</button>
			</div>
		</div>
	</div>
	<div class="col-sm-12 col-md-7 col-lg-8 col-xl-9">


		<form id="form-sedes">
			<div class="row panel-sedes">
				<div class="col-12 col-xl-6">
					<input id="id_sede" name="id_sede" type="hidden" value="${id_sede}">
					<div class="form-group">
						<label for="exampleInputEmail1">Descripción</label> <input
							type="text" class="form-control" id="ds_sede" name="ds_sede"
							placeholder="Ingrese Descripción de la Sede" value="${ds_sede}">
					</div>
				</div>
				<div class="col-12 col-xl-6">
					<div class="form-group">
						<label for="exampleFormControlSelect1">País</label> <select
							class="form-control" id="select_pais" name="select_pais">
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
				<div class="col-12 float-right">
					<button type="button" class="btn btn-success pull-right"
						onclick="guardarSede();">Guardar</button>
				</div>
			</div>
		</form>


	</div>
</div>