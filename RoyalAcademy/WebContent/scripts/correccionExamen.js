function selectCursoExamenDocente(){
	$.post("CorreccionExamen?accion=traerTarjetasExamenes", "id_curso=" + $("#select_cursos").val(), function(data){
		$("#container_examenes").html(data.data);
	});
}