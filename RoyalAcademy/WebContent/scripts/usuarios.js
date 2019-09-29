function botonNuevoUsuario() {
	$("#form-usuario").show();
	$("#botonGuardarUsuario").show();
	$("#botonEliminarUsuario").hide();
	$("#botonModificarUsuario").hide();
	$("#apellidoUsuario").val(""); // reseteo los valores, no se si esta bien o si no hace falta
	$("#nombreUsuario").val("");
	$("#mailUsuario").val("");
	$("#id_usuario").val("0");
	$("#passUsuario").val("");
	$("#nacimientoUsuario").val("");
	$("#telefonoUsuario").val("");
	$("#dniUsuario").val("");
	$("#verificadoUsuario").val("false");
	$("#id_rol").val($("#select_roles").val());
	$("#id_carrera").val("0");
	$("#rol_usuario").val($("#select_roles").val());
	actualizarCursos();
	actualizarSedes();
	cambioRolAdminOrganizacion();
}

function selectRolesUsuario(){
	$("#form-usuario").hide();
	$("#id_rol").val($("#select_roles").val());
	$.post("Usuarios?accion=buscarUsuario&id_rol=" + $("#select_roles").val(),
			function(data) {
				if (data.error.tipo = "success") {
					$("#tarjetasUsuarios").html(data.data);
				}
			});
}

function cambioRolAdminOrganizacion(){
	// como puedo hacer para q esto no quede tan hardcodeado? = probar poniendo el if 
	// usar los permisos funciones asociados a los roles. 
	// deberiamos evitar q se agregue a un rol la funcion docente y logged-alumno y logged-admin
	$.post("Usuarios?accion=esRolDocAlumAdmin&rol_usuario=" + $("#rol_usuario").val(),
			function(data){
		if (data.data == 1){ // si devuelve 1 el rol seleccionado tiene permiso de alumno
			$("#adminSedesUsuario").show();
			$("#adminCursosUsuario").hide();
			$("#seleccion_carreras").hide();
		}
		else if (data.data == 2){ // si devuelve 2 el rol seleccionado tiene permiso de docente
			$("#adminSedesUsuario").hide();
			$("#adminCursosUsuario").show();
			$("#seleccion_carreras").show();
		}
		else if (data.data == 3){ // si devuelve 3 el rol seleccionado tiene permiso de admin
			$("#adminSedesUsuario").hide();
			$("#adminCursosUsuario").hide();
			$("#seleccion_carreras").hide();
		} else {
			var error = {
					cd_error : 1,
					ds_error : "El rol seleccionado no tiene permiso de alumno, docente ni administrador.",
					tipo:"error"
			}
			lanzarMensaje(error)
		}
	});
}

function changeRol(rol,id_usuario){
	
	$.post("Usuarios?accion=buscarUsuario&id_rol=" + rol,
			function(data) {
				if (data.error.tipo = "success") {
					$("#select_roles").val(rol);
					$("#tarjetasUsuarios").html(data.data);
					
					$(".card-selected").removeClass("card-selected");
					if(id_usuario!=0)
					$("#"+id_usuario).addClass("card-selected");
				}
			});
}

function guardarUsuario() {
	$.post("Usuarios?accion=guardarUsuario", $("#form-usuario").serialize(), 
			function(data) {
				console.log(data.error);
				lanzarMensaje(data.error);
				if(data.error.tipo=="success"){
					changeRol($("#rol_usuario").val(),$("#id_usuario").val());	
					$("#id_usuario").val(data.data.id);
					$("#nombreUsuario").val(data.data.nombre);
					$("#apellidoUsuario").val(data.data.apellido);
					$("#nacimientoUsuario").val(data.data.fechaNacimiento);
					$("#telefonoUsuario").val(data.data.telefono);
					$("#dniUsuario").val(data.data.dni);
					$("#mailUsuario").val(data.data.email);
					$("#id_rol").val(data.data.id_rol);
					$("#passUsuario").val(data.data.pass);
				}
			});
}

function selectTarjeta(id_usuario){
	$(".card-selected").removeClass("card-selected");
	$("#"+id_usuario).addClass("card-selected");
	
	$.post("Usuarios?accion=traerUsuario&id_usuario=" + id_usuario, function (data){
		$("#form-usuario").show();
		$("#form-usuario").html(data.data);
		$('#nacimientoUsuario').datetimepicker({
			format : "DD/MM/YYYY",
			pickTime : false
		});
		cambioRolAdminOrganizacion(); // si corrijo lo q se muestra inicialmente en el servlet esto no deberia ir
	});
}

function buscarUsuario(){
	if($("#buscarUsuario").val()!=""){
		setTimeout(() => {
				$("#tarjetasUsuarios .card-body").each(function( index ) { //recorro todas las tarjetas
					var encontre = false;
					$(this).find("div").each(function( index ) { //de cada tarjeta recorro los divs que la componen
						
						  if($(this).text().toLowerCase().indexOf($("#buscarUsuario").val().toLowerCase())  > -1 && !encontre){
							  encontre = true;
							  console.log("coincide");
						  }	
					});
					if(encontre){ //Si encontre que coincida con lo que busco, lo muestro
						$(this).parent().show();
					}else{ //Si no encontre que coincida con lo que busco, no lo muestro
						$(this).parent().hide();
					}
				});			
		}, 300);
	}else{
		$(".card-body").parent().show();
	}
}

function eliminarUsuario() {
	$.post("Usuarios?accion=eliminarUsuario", $("#form-usuario").serialize(), function (data) {
		$("#form-usuario").hide();
		$("#botonEliminarUsuario").hide();
		$("#botonGuardarUsuario").hide();
		
		lanzarMensaje(data.error);
		
		changeRol($("#select_roles").val(),0);
		
		
	});
}

function agregarSedeUsuario() {

	var sedes_seleccionadas = $("#sedes_disponibles_usuario").val();
	var string_sedes_seleccionadas = "";
	for (var i = 0; i < sedes_seleccionadas.length; i++) {

		string_sedes_seleccionadas = string_sedes_seleccionadas + ";"
				+ sedes_seleccionadas[i];

	}
	$.post("Usuarios?accion=agregarSede", 
			"string_sedes_seleccionadas="+string_sedes_seleccionadas+"&"+
			$("#form-usuario").serialize(),
			function(data) {
				
		if(data.error.tipo=="success"){
				$("#sedes_disponibles_usuario").html(data.data.sedes_disponibles);
				$("#sedes_habilitadas_usuario").html(data.data.sedes_habilitadas);
		}
				lanzarMensaje(data.error);

	
	});
}
function eliminarSedeUsuario() {
	var sedes_seleccionadas = $("#sedes_habilitadas_usuario").val();
	var string_sedes_seleccionadas = "";
	for (var i = 0; i < sedes_seleccionadas.length; i++) {

		string_sedes_seleccionadas = string_sedes_seleccionadas + ";"
				+ sedes_seleccionadas[i];

	}
	$.post("Usuarios?accion=eliminarSede", 
			"string_sedes_seleccionadas="+string_sedes_seleccionadas+"&"+
			$("#form-usuario").serialize(),
			function(data) {
				
		if(data.error.tipo=="success"){
			$("#sedes_disponibles_usuario").html(data.data.sedes_disponibles);
			$("#sedes_habilitadas_usuario").html(data.data.sedes_habilitadas);
	}				lanzarMensaje(data.error);

	
	});
}

function agregarCursoUsuario() {

	var sedes_seleccionadas = $("#cursos_disponibles_usuario").val();
	var string_sedes_seleccionadas = "";
	for (var i = 0; i < sedes_seleccionadas.length; i++) {

		string_sedes_seleccionadas = string_sedes_seleccionadas + ";"
				+ sedes_seleccionadas[i];

	}
	$.post("Usuarios?accion=agregarCurso", 
			"string_sedes_seleccionadas="+string_sedes_seleccionadas+"&"+
			$("#form-usuario").serialize(),
			function(data) {
				
		if(data.error.tipo=="success"){
				$("#cursos_disponibles_usuario").html(data.data.sedes_disponibles);
				$("#cursos_habilitadas_usuario").html(data.data.sedes_habilitadas);
		}
				lanzarMensaje(data.error);

	
	});
}
function eliminarCursoUsuario() {
	var sedes_seleccionadas = $("#cursos_habilitadas_usuario").val();
	var string_sedes_seleccionadas = "";
	for (var i = 0; i < sedes_seleccionadas.length; i++) {

		string_sedes_seleccionadas = string_sedes_seleccionadas + ";"
				+ sedes_seleccionadas[i];

	}
	$.post("Usuarios?accion=eliminarCurso", 
			"string_sedes_seleccionadas="+string_sedes_seleccionadas+"&"+
			$("#form-usuario").serialize(),
			function(data) {
				
		if(data.error.tipo=="success"){
			$("#cursos_disponibles_usuario").html(data.data.sedes_disponibles);
			$("#cursos_habilitadas_usuario").html(data.data.sedes_habilitadas);
	}				lanzarMensaje(data.error);

	
	});
}

function actualizarCursos(){
	$.post("Usuarios?accion=actualizarCursos&id_usuario=" + $("#id_usuario").val() + "&id_carrera=" + $("#id_carrera").val(),
			function(data) {
		if(data.error.tipo=="success"){
			$("#cursos_disponibles_usuario").html(data.data.sedes_disponibles);
			$("#cursos_habilitadas_usuario").html(data.data.sedes_habilitadas);
		}				
		//lanzarMensaje(data.error);
	});
}

function actualizarSedes(){
	$.post("Usuarios?accion=actualizarSedes&id_usuario=" + $("#id_usuario").val(),
			function(data) {
		if(data.error.tipo=="success"){
			$("#sedes_disponibles_usuario").html(data.data.sedes_disponibles);
			$("#sedes_habilitadas_usuario").html(data.data.sedes_habilitadas);
		}				
		//lanzarMensaje(data.error);
	});
}
