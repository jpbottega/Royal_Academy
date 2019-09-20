function selectPerfiles() {

	$("#button_nuevo").show();
	$("#button_cancelar").hide();
	$("#button_eliminar").show();

	$.post("Perfiles?accion=selectPerfil","id_perfil="+$("#select_perfiles").val(),
			function(data) {
				
				$("#funciones_disponibles").html(data.data.funciones_disponibles);
				$("#funciones_habilitadas").html(data.data.funciones_habilitadas);
				
				$("#id_perfil").val($("#select_perfiles").val());
				$("#ds_perfil").val($("#select_perfiles option:selected").text());
	});
}
function nuevoPerfil() {

	$("#button_nuevo").hide();
	$("#button_cancelar").show();
	$("#button_eliminar").hide();
	
	
	$.post("Perfiles?accion=selectPerfil","id_perfil=0",
			function(data) {
				
				$("#funciones_disponibles").html(data.data.funciones_disponibles);
				$("#funciones_habilitadas").html(data.data.funciones_habilitadas);
				
				$("#id_perfil").val(0);
				$("#ds_perfil").val("");

	
	});
	
}
function eliminarPerfil(){
	
	$.post("Perfiles?accion=eliminarPerfil","id_perfil="+$("#select_perfiles").val(),
			function(data) {
			
		lanzarMensaje(data.error);
		if(data.error.tipo="success"){
			$("#select_perfiles").html(data.data);
			selectPerfiles();
			
		}
		
		
	
	});
	
}
function cancelarNuevoPerfil(){
	$("#button_nuevo").show();
	$("#button_cancelar").hide();
	$("#button_eliminar").show();
	
	selectPerfiles();
}
function dsPerfilVacio(){
	
	var vacio = false;
	if($("#ds_perfil").val()==""){
		
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
function existePerfil(){
	
	var existe_perfil = false;
	
	$('#select_perfiles').children('option').each(function () {
		console.log($(this).text());
		if($(this).text()==$("#ds_perfil").val() && $("#id_perfil").val()!=$(this).val()){
			//Si hay un perfil con esa descrpcion, y el id no coincide, no lo dejo guardar
			existe_perfil=true;
			
			var error = {
					cd_error : 1,
					ds_error : "Ya existe ese perfil.",
					tipo:"error"
			}
			lanzarMensaje(error);
		}
		
		
	});
	return existe_perfil;
}
function guardarPerfil() {
	if(!dsPerfilVacio() && !existePerfil()){
	$.post("Perfiles?accion=guardarPerfil", $("#form-perfiles").serialize(),
			function(data) {

		lanzarMensaje(data.error);
		
		if(data.error.tipo="success"){
			$("#select_perfiles").html(data.data);
			$("#id_perfil").val($("#select_perfiles").val());
			
			$("#button_nuevo").show();
			$("#button_cancelar").hide();
			$("#button_eliminar").show();
			
		}
			});
	}
}
function agregarFuncion() {

	var funciones_seleccionadas = $("#funciones_disponibles").val();
	var string_funciones_seleccionadas = "";
	for (var i = 0; i < funciones_seleccionadas.length; i++) {

		string_funciones_seleccionadas = string_funciones_seleccionadas + ";"
				+ funciones_seleccionadas[i];

	}
	$.post("Perfiles?accion=agregarFuncion", 
			"string_funciones_seleccionadas="+string_funciones_seleccionadas+"&"+
			$("#form-perfiles").serialize(),
			function(data) {
				
		if(data.error.tipo=="success"){
				$("#funciones_disponibles").html(data.data.funciones_disponibles);
				$("#funciones_habilitadas").html(data.data.funciones_habilitadas);
		}
				lanzarMensaje(data.error);

	
	});
}
function eliminarFuncion() {

	var funciones_seleccionadas = $("#funciones_habilitadas").val();
	var string_funciones_seleccionadas = "";
	for (var i = 0; i < funciones_seleccionadas.length; i++) {

		string_funciones_seleccionadas = string_funciones_seleccionadas + ";"
				+ funciones_seleccionadas[i];

	}
	$.post("Perfiles?accion=eliminarFuncion", 
			"string_funciones_seleccionadas="+string_funciones_seleccionadas+"&"+
			$("#form-perfiles").serialize(),
			function(data) {
				
		if(data.error.tipo=="success"){
			$("#funciones_disponibles").html(data.data.funciones_disponibles);
			$("#funciones_habilitadas").html(data.data.funciones_habilitadas);
	}				lanzarMensaje(data.error);

	
	});
}
