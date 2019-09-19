function botonNuevoAlumno() {
	// este lo tengo q uar para mostrar el form de crear alumno?
	$("#form-alumno-buscar").hide();
	$("#form-alumno-registrar").show();
	$("#table-busqueda").hide();
}
function botonBuscarAlumno() {
	// este lo tengo q uar para mostrar el form de crear alumno?
	$("#form-alumno-buscar").show();
	$("#form-alumno-registrar").hide();
	$("#table-busqueda").hide();
}

function guardarAlumno() {
	$.post("Usuarios?accion=agregarAlumno", $("#form-alumno-registrar").serialize(), function(data) {
		lanzarMensaje(data.error);
	});
}

function buscarAlumno() {
	$.post("Usuarios?accion=buscarAlumno", $("#form-alumno-buscar").serialize(), function(data) {
		// aca tengo q agregar la tabla q me vuelve o lo armo en el servlet???
		lanzarMensaje(data.error);
		if(data.error.tipo="success"){
			$("#table-cuerpo").html(data.data);	
			$("#table-busqueda").show();
		}
	});
}