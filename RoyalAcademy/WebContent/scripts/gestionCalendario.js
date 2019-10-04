function selectCarreraCursoFechaExamen(){
	$.post("Preguntas?accion=selectCarreraCurso","id_carrera="+$("#select_carreras").val(),
			function(data) {
				$("#select_cursos").html(data.data.option_cursos);
				$("#container-info-fecha-examen").hide();
				selectCursoFechaExamen();
	});
}
function selectCursoFechaExamen(){
	$.post("GestionCalendario?accion=traerFechas","id_curso="+$("#select_cursos").val(),
			function(data) {
			if (data.data == ""){
				$("#container_fechas_examen").html("<div class=\"alert alert-danger\" role=\"alert\">Asegúrese de que existan carreras y cursos</div>");
			} else {
				$("#container_fechas_examen").html(data.data);
			}
				$("#container-info-fecha-examen").hide();
	});
}
function creacionFechaExamen(){
	$("#container-info-fecha-examen").show();
	$.post("GestionCalendario?accion=crearFechaExamen", "id_curso=" + $("#select_cursos").val(), function(data){
		$("#select_examen").html(data.data);
		lanzarMensaje(data.error);
	});
}
function guardarFechaExamen(){
	$.post("GestionCalendario?accion=guardarFecha&id_curso=" + $("#select_cursos").val() + "&id_examen=" + $("#select_examen").val(), $("#form-fecha-examen").serialize(), 
			function(data){
				lanzarMensaje(data.error);
				$("#id_fecha_examen").val(data.data);
	});
}
function eliminarFechaExamen(){
	$.post("GestionCalendario?accion=eliminarFecha", $("#form-fecha-examen").serialize(), 
			function(data){
				lanzarMensaje(data.error);
				$("#id_fecha_examen").val("0");
	});
}
function selectFechaExamen(id_fecha){
	$("#container-info-fecha-examen").show();
	$.post("GestionCalendario?accion=selectFechaExamen", "id_fecha_examen=" + id_fecha, // por alguna razon no esta haciendo el post
			function(data){
				lanzarMensaje(data.error);
				$("#id_fecha_examen").val(id_fecha);
				$("#descripcion_fecha_examen").val(data.data.descripcion);
				$("#fecha_examen").val(data.data.fecha);
				$("#select_examen").html(data.data.htmlOpcionesExamen);
				$("#select_examen").val(data.data.id_examen);
	});
}