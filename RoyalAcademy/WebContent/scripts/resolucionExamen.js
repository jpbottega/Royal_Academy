function rendirExamen(){
	$.post("ResolucionExamen?accion=rendirExamen", "id_fechaExamen=" + $("#select_examen").val(), function(data){
		$("#resolucion_examen").html(data.data);
	});
	backup();
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

function backup(){
	$.post("ResolucionExamen?accion=backupExamen&id_fechaExamen=" + $("#select_examen").val(), $("#form_examen_resuelto").serialize(), 
			function (data){
		if (data.error.tipo == "success") 	setTimeout(backup, 10000);
	});
}