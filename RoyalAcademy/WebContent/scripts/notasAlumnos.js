function selectCursoNotas(){
	$("#container-info-notas").hide();
}

function verNotasCurso(){
	$.post("Notas?accion=verNotas","id_curso="+$("#select_cursos").val(),
			function(data) {
				$("#container-info-notas").show();
				$("#form-notas").html(data.data);
	});
}

function guardarNotas(){
	$.post("Notas?accion=guardarNotas&id_curso="+$("#select_cursos").val(), 
			function(data) {
				$("#container-info-notas").show();
				$("#form-notas").html(data.data);
				lanzarMensaje(data.error);
	});
}

function notificarNotasAlumnos(){
	$.post("Notas?accion=notificar&id_curso="+$("#select_cursos").val(), $("#form-notas").serialize(),
			function(data) {
				lanzarMensaje(data.error);
	});
}