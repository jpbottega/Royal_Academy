function inscribirAlumnoCurso(id_usuario){
	$.post("InscripcionExamen?accion=inscribir","id_examen="+$("#select_examen").val() + "&id_usuario=" + id_usuario,
			function(data) {
				lanzarMensaje(data.error);
	});
}

function desinscribirAlumnoCurso(id_usuario){
	$.post("InscripcionExamen?accion=desinscribir","id_examen="+$("#select_examen").val() + "&id_usuario=" + id_usuario,
			function(data) {
				lanzarMensaje(data.error);
	});	
}

function selectCarreraCursoInscripcionExamen(){
	$.post("InscripcionExamen?accion=traerCursos","id_carrera="+$("#select_carreras").val(),
			function(data) {
				$("#select_cursos").html(data.data);
				selectCursoInscripcionExamen();

	});
}

function selectCursoInscripcionExamen(){
	$.post("InscripcionExamen?accion=traerExamenes","id_curso="+$("#select_cursos").val(),
			function(data) {
				$("#select_examen").html(data.data);

	});
}