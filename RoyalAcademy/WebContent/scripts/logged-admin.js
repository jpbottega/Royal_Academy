
function lanzarMensaje(error) {

	if($("#toast-container").lenght){
		
		setTimeout(() => {
			lanzarMensaje(error);
		}, 1000);
		
		
	}else{
			var options = {
				"closeButton" : true,
				"debug" : false,
				"newestOnTop" : false,
				"progressBar" : true,
				"positionClass" : "toast-top-right",
				"preventDuplicates" : false,
				"onclick" : null,
				"showDuration" : "11300",
				"hideDuration" : "1000",
				"timeOut" : "4000",
				// "extendedTimeOut": "1000",
				"showEasing" : "swing",
				"hideEasing" : "linear",
				"showMethod" : "fadeIn",
				"hideMethod" : "fadeOut"
			};
			if (error.ds_error != "") {
				
				switch (error.tipo) {
				case "error":
					toastr.error(error.ds_error,"",options);
					break;
				case "success":
					toastr.success(error.ds_error,"",options);	
					break;
				case "warning":
					toastr.warning(error.ds_error,"",options);
					break;
				case "info":
					toastr.info(error.ds_error,"",options);
					break;
			
				
				}
				
				
				if ($(window).scrollTop() > 88) {
					$(".toast-top-right").addClass("abajo");
				}
			
			}
		}

}

function gotoPerfiles(){
		$.post( "ServletLoggedAdmin?accion=perfiles", function( data ) {
			$("#functions-container").html(data); //seteo el data, que es el jsp perfiles, en el div con ID functions-container
			$("#functions-container").show(); //Lo muestro, asi se pone el fondo en blanco
		});
}
function gotoSedes(){
	$.post( "ServletLoggedAdmin?accion=sedes", function( data ) {
		$("#functions-container").html(data); //seteo el data, que es el jsp perfiles, en el div con ID functions-container
		$("#functions-container").show(); //Lo muestro, asi se pone el fondo en blanco
	});
}

function gotoUsuarios(){
	$.post("ServletLoggedAdmin?accion=adminUsuarios", function( data ) {
		$("#functions-container").html(data); //seteo el data, que es el jsp administradorAlumnos, en el div con ID functions-container
		$("#functions-container").show(); //Lo muestro, asi se pone el fondo en blanco
	});
}
function gotoCarreras(){
	$.post( "ServletLoggedAdmin?accion=carreras", function( data ) {
		$("#functions-container").html(data); //seteo el data, que es el jsp administradorAlumnos, en el div con ID functions-container
		$("#functions-container").show(); //Lo muestro, asi se pone el fondo en blanco
	});
	
}
function gotoCursos(){
	$.post( "ServletLoggedAdmin?accion=cursos", function( data ) {
		$("#functions-container").html(data); //seteo el data, que es el jsp administradorAlumnos, en el div con ID functions-container
		$("#functions-container").show(); //Lo muestro, asi se pone el fondo en blanco
	});
}
function gotoPreguntas(){
	$.post( "ServletLoggedAdmin?accion=preguntas", function( data ) {
		$("#functions-container").html(data); //seteo el data, que es el jsp administradorAlumnos, en el div con ID functions-container
		$("#functions-container").show(); //Lo muestro, asi se pone el fondo en blanco
	});
}
function gotoHome(){
	$("#functions-container").html("");
	$("#functions-container").hide();
}
function gotoExamenes(){
	$.post( "ServletLoggedAdmin?accion=examenes", function( data ) {
		$("#functions-container").html(data); //seteo el data, que es el jsp administradorAlumnos, en el div con ID functions-container
		$("#functions-container").show(); //Lo muestro, asi se pone el fondo en blanco
	});
}
function gotoCalendario(){
	$.post( "ServletLoggedAdmin?accion=calendario", function( data ) {
		$("#functions-container").html(data); //seteo el data, que es el jsp administradorAlumnos, en el div con ID functions-container
		$("#functions-container").show(); //Lo muestro, asi se pone el fondo en blanco
	});
}
function gotoHomeAlumno(){
	$.post( "ServletLoggedAlumno?accion=homealumno", function( data ) {
		$("body").html(data); //seteo el data, que es el jsp administradorAlumnos, en el div con ID functions-container
	});
}
function gotoCorreccionExamen(){
	$.post( "ServletLoggedAdmin?accion=correccionExamen", function( data ) {
		$("#functions-container").html(data); //seteo el data, que es el jsp administradorAlumnos, en el div con ID functions-container
		$("#functions-container").show(); //Lo muestro, asi se pone el fondo en blanco
	});
}
function gotoNotasCursos(){
	$.post( "ServletLoggedAdmin?accion=notas", function( data ) {
		$("#functions-container").html(data); //seteo el data, que es el jsp administradorAlumnos, en el div con ID functions-container
		$("#functions-container").show(); //Lo muestro, asi se pone el fondo en blanco
	});
}
function gotoReportes(){
	$.post( "ServletLoggedAdmin?accion=reportes", function( data ) {
		$("#functions-container").html(data); //seteo el data, que es el jsp administradorAlumnos, en el div con ID functions-container
		$("#functions-container").show(); //Lo muestro, asi se pone el fondo en blanco
	});
	
}