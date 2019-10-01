function selectExamen(id_examen){
	$.post("ExamenesABM?accion=selectExamen","id_examen="+id_examen,
			function(data) {
				$("#container-info-examen").show();
				$("#id_examen").val(data.data.id_examen);
				$("#descripcion_examen").val(data.data.descripcion);
				$("#preguntas_disponibles").html(data.data.preguntas_disponibles);
				$("#preguntas_agregadas").html(data.data.preguntas_habilitadas);
				lanzarMensaje(data.error);
	});
}
function selectCarreraCursoExamen(){
	$.post("Preguntas?accion=selectCarreraCurso","id_carrera="+$("#select_carreras").val(),
			function(data) {
				$("#select_cursos").html(data.data.option_cursos);
				$("#container-info-examen").hide();
				selectCursoExamen();
	});
	
}
function selectCursoExamen(){
	$.post("ExamenesABM?accion=selectCursoExamen","id_carrera="+$("#select_carreras").val()+"&id_curso="+$("#select_cursos").val(),
			function(data) {
			if (data.data == ""){
				$("#container_examenes").html("<div class=\"alert alert-danger\" role=\"alert\">Asegúrese de que existan carreras y cursos</div>");
			}else {
				$("#container_examenes").html(data.data);
			}
				$("#container-info-examen").hide();
	});
}
function creacionExamenManual(){
	$("#container-info-examen").show();
	$("#id_examen").val("0");
	$("#descripcion_examen").val("");
	$.post("ExamenesABM?accion=crearExamenManual","id_curso="+$("#select_cursos").val()+"&id_carrera="+$("#select_carreras").val(), 
			function (data){
				$("#preguntas_disponibles").html(data.data.sedes_disponibles);
				$("#preguntas_agregadas").html(data.data.sedes_habilitadas);
	});
}
function guardarExamen(){
	if ($("#descripcion_examen").val() != ""){
	$.post("ExamenesABM?accion=guardarExamen&id_curso="+$("#select_cursos").val()+"&id_carrera="+$("#select_carreras").val(), $("#form-examen").serialize(), 
			function(data){
				$("#id_examen").val(data.data);
				lanzarMensaje(data.error);
			$.post("ExamenesABM?accion=selectCursoExamen","id_carrera="+$("#select_carreras").val()+"&id_curso="+$("#select_cursos").val(),
			function(data) {
				if (data.data == ""){
					$("#container_examenes").html("<div class=\"alert alert-danger\" role=\"alert\">Asegúrese de que existan carreras y cursos</div>");
				}else {
					$("#container_examenes").html(data.data);
				}
			});
		});	
	}
	else {
		var error = {
				cd_error : 1,
				ds_error : "Debe completar la descripcion del examen.",
				tipo:"error"
		}
		lanzarMensaje(error);
	}
}
function agregarPreguntaExamen(id_pregunta){
	if ($("#id_examen").val() != "0"){
		$.post("ExamenesABM?accion=agregarPregunta","id_examen="+$("#id_examen").val()+"&id_pregunta="+id_pregunta, 
				function (data){
					$("#preguntas_disponibles").html(data.data.sedes_disponibles);
					$("#preguntas_agregadas").html(data.data.sedes_habilitadas);
					lanzarMensaje(data.error);
		});
	}
	else {
		var error = {
				cd_error : 1,
				ds_error : "Debe guardar el examen.",
				tipo:"error"
		}
		lanzarMensaje(error);
	}
}
function eliminarPreguntaExamen(id_pregunta){
	if ($("#id_examen").val() != "0"){
		$.post("ExamenesABM?accion=eliminarPregunta","id_examen="+$("#id_examen").val()+"&id_pregunta="+id_pregunta, 
				function (data){
					$("#preguntas_disponibles").html(data.data.sedes_disponibles);
					$("#preguntas_agregadas").html(data.data.sedes_habilitadas);
					lanzarMensaje(data.error);
		});
	}
	else {
		var error = {
				cd_error : 1,
				ds_error : "Debe guardar el examen.",
				tipo:"error"
		}
		lanzarMensaje(error);
	}
}
function eliminarExamen(){
	if ($("#id_examen") != "0"){
		$.post("ExamenesABM?accion=eliminarExamen&id_examen="+$("#id_examen").val(), 
				function(data){
					$("#id_examen").val("0");
					lanzarMensaje(data.error);
					$.post("ExamenesABM?accion=selectCursoExamen","id_carrera="+$("#select_carreras").val()+"&id_curso="+$("#select_cursos").val(),
							function(data) {
							if (data.data == ""){
								$("#container_examenes").html("<div class=\"alert alert-danger\" role=\"alert\">Asegúrese de que existan carreras y cursos</div>");
							}else {
								$("#container_examenes").html(data.data);
							}
						});
			});	
	}
	else {
		var error = {
				cd_error : 1,
				ds_error : "No se puede eliminar un examen no guardado.",
				tipo:"error"
		}
		lanzarMensaje(error);
	}
	$("#container-info-examen").hide();
}
function creacionExamenAutomatica(){
	if ($("#cantidad_preguntas").val() != 0){
		$.post("ExamenesABM?accion=crearExamenAutomatico","id_curso="+$("#select_cursos").val()+"&id_creador="+$("#id_usuario_creador").val()+
				"&cant_preguntas="+$("#cantidad_preguntas").val(),
				function(data) {
					$("#container-info-examen").show();
					$("#id_examen").val(data.data.id_examen);
					$("#descripcion_examen").val(data.data.descripcion);
					$("#preguntas_disponibles").html(data.data.preguntas_disponibles);
					$("#preguntas_agregadas").html(data.data.preguntas_habilitadas);
					lanzarMensaje(data.error);
					$.post("ExamenesABM?accion=selectCursoExamen","id_carrera="+$("#select_carreras").val()+"&id_curso="+$("#select_cursos").val(),
							function(data) {
								if (data.data == ""){
									$("#container_examenes").html("<div class=\"alert alert-danger\" role=\"alert\">Asegúrese de que existan carreras y cursos</div>");
								}else {
									$("#container_examenes").html(data.data);
								}
							});
		});
	}
	else {
		var error = {
				cd_error : 1,
				ds_error : "Debe seleccionar la cantidad de preguntas.",
				tipo:"error"
		}
		lanzarMensaje(error);
	}
}