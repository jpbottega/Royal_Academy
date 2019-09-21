function selectCarrera(){

	$("#button_nuevo").show();
	$("#button_cancelar").hide();
	$("#button_eliminar").show();
	

	$.post("Carreras?accion=selectCarrera","id_carrera="+$("#select_carreras").val(),
			function(data) {
				
				$("#sedes_disponibles").html(data.data.sedes_disponibles);
				$("#sedes_habilitadas").html(data.data.sedes_habilitadas);
				
				$("#id_carrera").val($("#select_carreras").val());
				$("#ds_carrera").val($("#select_carreras option:selected").text());

	
	});
	

}
function cancelarNuevaCarrera(){
	$("#button_nuevo").show();
	$("#button_cancelar").hide();
	$("#button_eliminar").show();
	selectCarrera();
}
function nuevaCarrera() {

	$("#button_nuevo").hide();
	$("#button_cancelar").show();
	$("#button_eliminar").hide();
			
	$.post("Carreras?accion=selectCarrera","id_carrera=0",
			function(data) {
				
				$("#sedes_disponibles").html(data.data.sedes_disponibles);
				$("#sedes_habilitadas").html("");
				
				$("#id_carrera").val(0);
				$("#ds_carrera").val("");
	});
}


function dsCarreraVacio(){
	
	var vacio = false;
	if($("#ds_sede").val()==""){
		
		vacio = true;
		
		var error = {
				cd_error : 1,
				ds_error : "Debe completar todos los campos.",
				tipo:"error"
		}
		lanzarMensaje(error);
	}
	return vacio;
}
function existeCarrera(){
	
	var existe_perfil = false;
	
	$('#select_carreras').children('option').each(function () {
		console.log($(this).text());
		if($(this).text()==$("#ds_carrera").val() && $("#id_carrera").val()!=$(this).val()){
			//Si hay un perfil con esa descrpcion, y el id no coincide, no lo dejo guardar
			existe_perfil=true;
			
			var error = {
					cd_error : 1,
					ds_error : "Ya existe esa Carrera.",
					tipo:"error"
			}
			lanzarMensaje(error);
		}
		
		
	});
	return existe_perfil;
}

function guardarCarrera() {
	if(!dsCarreraVacio() && !existeCarrera()){
	$.post("Carreras?accion=guardarCarrera", $("#form-carrera").serialize(),
			function(data) {

		lanzarMensaje(data.error);
		
		if(data.error.tipo="success"){
			$("#select_carreras").html(data.data);
			$("#id_carrera").val($("#select_carreras").val());
			
			$("#button_nuevo").show();
			$("#button_cancelar").hide();
			$("#button_eliminar").show();
			
		}
			});
	}
}
function agregarSede() {

	var sedes_seleccionadas = $("#sedes_disponibles").val();
	var string_sedes_seleccionadas = "";
	for (var i = 0; i < sedes_seleccionadas.length; i++) {

		string_sedes_seleccionadas = string_sedes_seleccionadas + ";"
				+ sedes_seleccionadas[i];

	}
	$.post("Carreras?accion=agregarSede", 
			"string_sedes_seleccionadas="+string_sedes_seleccionadas+"&"+
			$("#form-carrera").serialize(),
			function(data) {
				
		if(data.error.tipo=="success"){
				$("#sedes_disponibles").html(data.data.sedes_disponibles);
				$("#sedes_habilitadas").html(data.data.sedes_habilitadas);
		}
				lanzarMensaje(data.error);

	
	});
}
function eliminarSede() {

	var sedes_seleccionadas = $("#sedes_habilitadas").val();
	var string_sedes_seleccionadas = "";
	for (var i = 0; i < sedes_seleccionadas.length; i++) {

		string_sedes_seleccionadas = string_sedes_seleccionadas + ";"
				+ sedes_seleccionadas[i];

	}
	$.post("Carreras?accion=eliminarSede", 
			"string_sedes_seleccionadas="+string_sedes_seleccionadas+"&"+
			$("#form-carrera").serialize(),
			function(data) {
				
		if(data.error.tipo=="success"){
			$("#sedes_disponibles").html(data.data.sedes_disponibles);
			$("#sedes_habilitadas").html(data.data.sedes_habilitadas);
	}				lanzarMensaje(data.error);

	
	});
}
function eliminarCarrera(){
	
	$.post("Carreras?accion=eliminarCarrera","id_carrera="+$("#select_carreras").val(),
			function(data) {
			
		lanzarMensaje(data.error);
		if(data.error.tipo="success"){
			$("#select_carreras").html(data.data);
			selectCarrera();
			
		}
		
		
	
	});
	
}