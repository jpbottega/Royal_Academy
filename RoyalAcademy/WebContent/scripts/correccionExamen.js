function selectCursoExamenDocente(){
	$.post("CorreccionExamen?accion=traerTarjetasExamenes", "id_curso=" + $("#select_cursos").val(), function(data){
		$("#container_examenes").html(data.data);
	});
}
function selectExamenCorreccion(id_examen){
	$.post("CorreccionExamen?accion=selectExamen", "id_examen=" +id_examen, function(data){
		$("#container-info-examen").html(data.data);
	});
}
function corregirExamen(id_examen){
	if ($("#criterio_aprobacion").val() != ""){
		var tmp = $("#criterio_aprobacion").val();
		$.post("CorreccionExamen?accion=corregirExamen", "id_examen=" +id_examen + "&criterio_aprobacion=" + $("#criterio_aprobacion").val(),
				function(data){
					$("#container-info-examen").html(data.data);
					$("#criterio_aprobacion").val(tmp)
		});
	}
	else {
		var error = {
				cd_error : 1,
				ds_error : "Debe establecer el criterio de aprobacion.",
				tipo:"error"
		}
		lanzarMensaje(error);
	}
}
	