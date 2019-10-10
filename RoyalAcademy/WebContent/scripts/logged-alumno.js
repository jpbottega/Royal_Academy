function miPerfil(){
	
		console.log("eee");
		$.post("ServletLoggedAlumno?accion=perfil", function( data ) {
			
			$("body").html(data);
			
		});
	
	
}
