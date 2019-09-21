function nuevoCurso() {

	$("#button_nuevo").hide();
	$("#button_cancelar").show();
	$("#button_eliminar").hide();
			
	$("#id_curso").val(0);
	$("#ds_curso").val("");

	
}
function cancelarNuevoCurso(){
	$("#button_nuevo").show();
	$("#button_cancelar").hide();
	$("#button_eliminar").show();
}
function eliminarCurso(){
	
	$.post("Cursos?accion=eliminarCurso","id_curso="+$("#select_cursos").val(),
			function(data) {
			
		lanzarMensaje(data.error);
		if(data.error.tipo="success"){
			$("#select_cursos").html(data.data);
			selectCursos();
			
		}
		
		
	
	});
	
}
function selectCursos() {

	cancelarNuevoCurso();

	$.post("Cursos?accion=selectCursos","id_curso="+$("#select_cursos").val(),
			function(data) {
				
				
				$("#id_curso").val(data.data.id);
				$("#ds_curso").val(data.data.denominacion);
				$("#select_carrera").val(data.data.id_carrera);
	
	});
	
}
function guardarCurso() {
	if(!dsCursoVacio() && !existeCurso()){
	$.post("Cursos?accion=guardarCurso", $("#form-cursos").serialize(),
			function(data) {

		lanzarMensaje(data.error);
		
		if(data.error.tipo="success"){
			$("#select_cursos").html(data.data);
			$("#id_curso").val($("#select_cursos").val());
			
			$("#button_nuevo").show();
			$("#button_cancelar").hide();
			$("#button_eliminar").show();
			
		}
			});
	}
}
function dsCursoVacio(){
	
	var vacio = false;
	if($("#ds_curso").val()==""){
		
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
function existeCurso(){
	
	var existe_perfil = false;
	
	$('#select_curso').children('option').each(function () {
		console.log($(this).text());
		if($(this).text()==$("#ds_curso").val() && $("#id_curso").val()!=$(this).val()){
			//Si hay un perfil con esa descrpcion, y el id no coincide, no lo dejo guardar
			existe_perfil=true;
			
			var error = {
					cd_error : 1,
					ds_error : "Ya existe ese Curso.",
					tipo:"error"
			}
			lanzarMensaje(error);
		}
		
		
	});
	return existe_perfil;
}