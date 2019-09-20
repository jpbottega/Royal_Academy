function botonNuevoUsuario() {
	// este lo tengo q uar para mostrar el form de crear alumno?
	$("#form-alumno-registrar").show();
}
function filtrarAlumnos() {
	$("#form-alumno-registrar").hide();
}

function selectRolesUsuario(){
	$("#form-alumno-registrar").hide();
	$("#id_rol").val($("#select_roles").val());
	$.post("Usuarios?accion=buscarUsuario&id_rol=" + $("#select_roles").val(),  $("#form-alumno-buscar").serialize(), 
			function(data) {
				lanzarMensaje(data.error);
				if (data.error.tipo = "success") {
					$("#tarjetasUsuarios").html(data.data);
				}
			});
}

function guardarAlumno() {
	$.post("Usuarios?accion=agregarUsuario", $("#form-alumno-registrar").serialize(), 
			function(data) {
				lanzarMensaje(data.error);
			});
}

