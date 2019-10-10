function gotoInscripcionCursos(){
		$.post("ServletLoggedAlumno?accion=inscripcionCursos", 
				function( data ) {
					//$("#contenido-alumno").hide();
					$("#contenido-alumno").html(data); //seteo el data, que es el jsp perfiles, en el div con ID functions-container
					//$("#functions-container").show(); //Lo muestro, asi se pone el fondo en blanco
		});
}