<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<Opciones_Pregunta> opciones = (List<Opciones_Pregunta>) request.getAttribute("opciones");
%>

<form id="form-preguntas">
	<input type="hidden" id="cantidad_opciones" name="cantidad_opciones"
		value="<%=(request.getAttribute("cantidad_opciones") != null ? request.getAttribute("cantidad_opciones")
					: "1")%>">

	<div class="row">
		<div class="col-md-12">
			<input id="id_pregunta" name="id_pregunta" type="hidden"
				value="<%=(request.getAttribute("id_pregunta") != null ? request.getAttribute("id_pregunta") : "0")%>">
			<div class="form-group">
				<label for="exampleInputEmail1">Pregunta</label> <input type="text"
					class="form-control" id="pregunta" name="pregunta"
					placeholder="Ingrese la pregunta" value="${pregunta}">
			</div>
		</div>

	</div>

	<%
		if (request.getAttribute("opciones") != null && !opciones.isEmpty()) {
			int loop = 1;
			for (Opciones_Pregunta opcion : opciones) {
	%>
	<div class="row" id="row_<%=loop%>">
		<div class="col-md-9">
			<div class="form-group">
				<label for="exampleInputEmail1">Opción <%=loop%></label> <input
					type="text" class="form-control opcion-pregunta"
					id="opcion_pregunta_<%=loop%>" name="opcion_pregunta_<%=loop%>"
					placeholder="Ingrese la Opción" value="<%=opcion.getRespuesta()%>">
			</div>
		</div>
		<div class="col-md-2">
			<div class="form-check opcion-correcta">

				<input type="radio" class="form-check-input checkbox-opcion"
					id="opcion_correcta_<%=loop%>" name="opcion_correcta"
					<%=(opcion.getRespuesta_correcta() ? "checked" : "")%>
					value="<%=loop%>"> <label class="form-check-label"
					for="exampleCheck1">Correcta</label>
			</div>
		</div>
		<%if(loop>1){ %>
		<!-- <div class="col-md-1"><img src="./images/trash-2-24.png" style="margin-top: 40px;;cursor:pointer" onclick="eliminarRow(<%=loop%>);"></div> -->
		<%} %>
	</div>
	<%
		loop++;
			}
		} else {
	%>

	<div class="row" id="row">
		<div class="col-md-9">
			<div class="form-group">
				<label for="exampleInputEmail1">Opción 1</label> <input type="text"
					class="form-control opcion-pregunta" id="opcion_pregunta_1"
					name="opcion_pregunta_1" placeholder="Ingrese la Opción" value="">
			</div>
		</div>
		<div class="col-md-2">
			<div class="form-check opcion-correcta">

				<input type="radio" class="form-check-input checkbox-opcion"
					id="opcion_correcta_1" name="opcion_correcta" checked value="1">
				<label class="form-check-label" for="exampleCheck1">Correcta</label>
			</div>
		</div>
		
	</div>

	<%
		}
	%>

</form>
<div class="row">
	<div class="col-md-12">
		<button type="button" class="btn btn-primary" style="width: 100%"
			onclick="nuevaOpcion();">Nueva Opción</button>
	</div>
</div>
<button type="button" class="btn btn-success pull-right"
	style="margin-top: 10px" onclick="guardarPregunta();">Guardar</button>
<button type="button" class="btn btn-danger pull-right" style="margin-top: 10px"
	onclick="eliminarPregunta();" id="button_eliminar">Eliminar
	Pregunta</button>