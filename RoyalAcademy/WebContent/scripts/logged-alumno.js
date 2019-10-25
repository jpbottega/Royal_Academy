function miPerfil(){
	
		console.log("eee");
		$.post("ServletLoggedAlumno?accion=perfil", function( data ) {
			
			s$("body").html(data);
			
		});
}

function gotoInscripcionExamenAlumno() {
	$.post("ServletLoggedAlumno?accion=inscripcionCursos", function(data) {
		// $("#contenido-alumno").hide();
		$("body").html(data); // seteo el data, que es el jsp perfiles, en el
								// div con ID functions-container
		// $("#functions-container").show(); //Lo muestro, asi se pone el fondo
		// en blanco
	});
}
function inscribirse(curso,sede){
	
	var buscador = $("#buscarcurso").val();
	$.post("ServletLoggedAlumno?accion=inscribirse&curso="+curso+"&sede="+sede, function( data ) {
		
		$("#header_inscripcion_cursos").hide();
		
		$("#header_inscripcion_cursos").html(data);
		
		$("#buscarcurso").val(buscador);
		buscarCursos(1);
		
		
	});
}

function borrarInscribirse(curso,sede){
	var buscador = $("#buscarcurso").val();
	$.post("ServletLoggedAlumno?accion=borrarInscribirse&curso="+curso+"&sede="+sede, function( data ) {
		
		$("#header_inscripcion_cursos").hide();
		
		$("#header_inscripcion_cursos").html(data);
		
		$("#buscarcurso").val(buscador);
		buscarCursos(1);
		
		
		
	});
}
function gotoResolucionExamenAlumno(){
	$.post("ServletLoggedAlumno?accion=resolucionExamen", function( data ) {
		
		
		$("body").html(data);

	});
}
function buscarCursos(mostrar){
	
	setTimeout(() => {
		
		$(".header-curso").each(function(){
			
			var coincide = false;
			var padre = this;
			
				$(this).find("span").each(function(){
					
					
					if($(this).text().toLowerCase().indexOf($("#buscarcurso").val().toLowerCase())> -1 && coincide==false){
						
						
						coincide = true;
						
					}
				
				});
				if(coincide){
					$(padre).parent().parent().show();
				}else{
					$(padre).parent().parent().hide();
				}
				
		});
		
		if(mostrar==1){
			$("#header_inscripcion_cursos").show();

		}
	}, 300);
	
	
}