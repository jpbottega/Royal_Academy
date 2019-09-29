function selectCarreraCurso(){
	
	$.post("Preguntas?accion=selectCarreraCurso","id_carrera="+$("#select_carreras").val(),
			function(data) {
				
				$("#select_cursos").html(data.data.option_cursos);
				$("#container_preguntas").html(data.data.preguntas);
				$("#container-info-pregunta").html("");
	});
}
function selectCursoPregunta(){
	$.post("Preguntas?accion=selectCursoPregunta","id_carrera="+$("#select_carreras").val()+"&id_curso="+$("#select_cursos").val(),
			function(data) {
				
				
				$("#container_preguntas").html(data.data.preguntas);
				$("#container-info-pregunta").html("");
	});
}
function nuevaOpcion(){
	
	$("#cantidad_opciones").val(parseInt($("#cantidad_opciones").val())+1);
	var opcion = htmlOpcion(parseInt($("#cantidad_opciones").val()));
	
	$("#form-preguntas").append(opcion);
}
function nuevaPregunta(){
	$("#container-info-pregunta").load("./form_pregunta.jsp");
	
}
function htmlOpcion(numero_opcion){
	
	var opcion = "<div class= \"row\" id=\"row_"+numero_opcion+"\">"+
				"<div class=\"col-md-9\">"+
					"<div class=\"form-group\">"+
						"<label for=\"exampleInputEmail1\">Opción "+numero_opcion+"</label> <input "+
							"type=\"text\" class=\"form-control opcion-pregunta\" id=\"opcion_pregunta_"+numero_opcion+"\" "+
							"name=\"opcion_pregunta_"+numero_opcion+"\" placeholder=\"Ingrese la Opción\" value=\"\"> "+
					"</div> "+
				"</div> "+
				"<div class=\"col-md-2\"> "+
					"<div class=\"form-check opcion-correcta\"> "+

						"<input type=\"radio\" class=\"form-check-input checkbox-opcion\" "+
							"id=\"opcion_correcta_"+numero_opcion+"\" name=\"opcion_correcta\" value=\""+numero_opcion+"\"> <label "+
							"class=\"form-check-label\" for=\"exampleCheck1\">Correcta</label> "+
					"</div>"+
				"</div>"+
				//"<div class=\"col-md-1\"><img src=\"./images/trash-2-24.png\"  style=\"margin-top: 40px;cursor:pointer\" onclick=\"eliminarRow("+numero_opcion+");\"></div>";
			"</div>";
	
	return opcion;
}
function selectPregunta(id_pregunta){
	
	$(".style-preguntas-seleccionada").removeClass("style-preguntas-seleccionada");
	$("#card_"+id_pregunta+" .style-preguntas").addClass("style-preguntas-seleccionada");
	
	$.post("Preguntas?accion=selectPregunta","id_pregunta="+id_pregunta,
			function(data) {
				
				$("#container-info-pregunta").html(data);
		
	});
}

function validarGuardarPregunta(){
	var validado = true;
	
	$(".opcion-pregunta").each(function(index){
		
		console.log($(this).val());
		
		if($(this).val()==""){
			validado = false;
			console.log("vacia");
			var error = {
					cd_error : 1,
					ds_error : "No puede dejar en blanco una opción.",
					tipo:"error"
			}
			lanzarMensaje(error);
		
		}
		
	});
	
	if(validado){
		
		if($("#pregunta").val()==""){
			validado = false;
			
			var error = {
					cd_error : 1,
					ds_error : "La pregunta no puede estar en blanco.",
					tipo:"error"
			}
			lanzarMensaje(error);
		}
	}
	
	return validado;
	
}

function guardarPregunta(){
	console.log("pepe");
	if(validarGuardarPregunta()){
		
		$.post("Preguntas?accion=guardarPregunta","id_carrera="+$("#select_carreras").val()+"&id_curso="+$("#select_cursos").val()+"&"+$("#form-preguntas").serialize(),
				function(data) {
					
			lanzarMensaje(data.error);
			if(data.error.tipo="success"){
				
				$("#container_preguntas").html(data.data.preguntas);
				
			}
			
			
			
		});
	}
	
}
function eliminarPregunta(){
	
	$.post("Preguntas?accion=eliminarPregunta","id_carrera="+$("#select_carreras").val()+"&id_curso="+$("#select_cursos").val()+"&id_pregunta="+$("#id_pregunta").val(),
			function(data) {
				
		lanzarMensaje(data.error);
		if(data.error.tipo="success"){
			
			$("#container_preguntas").html(data.data.preguntas);
			$("#container-info-pregunta").html("");
		}
		
		
		
	});
	
}
function eliminarRow(row){
	console.log($("#row_"+row+" .checkbox-opcion").is(":checked"));
	if($("#row_"+row+" .checkbox-opcion").is(":checked")){
		var error = {
				cd_error : 1,
				ds_error : "No puede eliminar la respuesta correcta.",
				tipo:"error"
		}
		lanzarMensaje(error);
	}else{
		$("#row_"+row).remove();
	}
	
	
}
