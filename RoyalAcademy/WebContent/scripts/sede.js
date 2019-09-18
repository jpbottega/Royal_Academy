function nuevaSede() {

	$("#button_nuevo").hide();
	$("#button_cancelar").show();
	$("#button_eliminar").hide();
			
	$("#id_sede").val(0);
	$("#ds_sede").val("");

	
}
function selectSedes() {

	cancelarNuevaSede();

	$.post("Sedes?accion=selectSedes","id_sede="+$("#select_sedes").val(),
			function(data) {
				
				
				$("#id_sede").val(data.data.id);
				$("#ds_sede").val(data.data.sede);
				$("#select_pais").val(data.data.id_pais);
	
	});
	
}
function cancelarNuevaSede(){
	$("#button_nuevo").show();
	$("#button_cancelar").hide();
	$("#button_eliminar").show();
}
function dsSedeVacio(){
	
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
function existeSede(){
	
	var existe_perfil = false;
	
	$('#select_sedes').children('option').each(function () {
		console.log($(this).text());
		if($(this).text()==$("#ds_sede").val() && $("#id_sede").val()!=$(this).val()){
			//Si hay un perfil con esa descrpcion, y el id no coincide, no lo dejo guardar
			existe_perfil=true;
			
			var error = {
					cd_error : 1,
					ds_error : "Ya existe esa Sede.",
					tipo:"error"
			}
			lanzarMensaje(error);
		}
		
		
	});
	return existe_perfil;
}
function eliminarSede(){
	
	$.post("Sedes?accion=eliminarSede","id_sede="+$("#select_sedes").val(),
			function(data) {
			
		lanzarMensaje(data.error);
		if(data.error.tipo="success"){
			$("#select_sedes").html(data.data);
			selectSedes();
			
		}
		
		
	
	});
	
}
function guardarSede() {
	if(!dsSedeVacio() && !existeSede()){
	$.post("Sedes?accion=guardarSede", $("#form-sedes").serialize(),
			function(data) {

		lanzarMensaje(data.error);
		
		if(data.error.tipo="success"){
			$("#select_sedes").html(data.data);
			$("#id_sede").val($("#select_sedes").val());
			
			$("#button_nuevo").show();
			$("#button_cancelar").hide();
			$("#button_eliminar").show();
			
		}
			});
	}
}