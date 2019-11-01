function rendirExamen(){
	$.post("ResolucionExamen?accion=rendirExamen", "id_fechaExamen=" + $("#select_examen").val(), function(data){
		$("#resolucion_examen").html(data.data);
	});
}

function entregarExamen(){
	$.post("ResolucionExamen?accion=entregarExamen&id_fechaExamen=" + $("#select_examen").val(), $("#form_examen_resuelto").serialize(),
			function(data){
				$("#resolucion_examen").html("");
				actualizarFinalesDisponibles();
	});
}

function actualizarFinalesDisponibles(){
	$.post("ResolucionExamen?accion=actualizarExamenes", function(data){
				$("#select_examen").html(data.data);
	});
}

