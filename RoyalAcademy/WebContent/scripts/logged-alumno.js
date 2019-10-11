function miPerfil(){
	
		console.log("eee");
		$.post("ServletLoggedAlumno?accion=perfil", function( data ) {
			
			$("body").html(data);
			
		});
}

function gotoInscripcionExamenAlumno(){
		$.post("ServletLoggedAlumno?accion=inscripcionCursos", 
				function( data ) {
					//$("#contenido-alumno").hide();
					$("body").html(data); //seteo el data, que es el jsp perfiles, en el div con ID functions-container
					//$("#functions-container").show(); //Lo muestro, asi se pone el fondo en blanco
		});
}

function gotoResolucionExamenAlumno(){
	$.post("ServletLoggedAlumno?accion=resolucionExamen", function( data ) {
		
		$("body").html(data);
		
	});
}
