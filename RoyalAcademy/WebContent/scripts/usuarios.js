function botonNuevoUsuario() {
	// este lo tengo q uar para mostrar el form de crear alumno?
	$("#form-alumno-registrar").show();
}
function filtrarAlumnos() {
	$("#form-alumno-registrar").hide();
}

function selectRolesUsuario(){
	$("#form-alumno-registrar").hide();
	$("#id_rol").val($("#select_roles").val());
	$.post("Usuarios?accion=buscarUsuario&id_rol=" + $("#select_roles").val(),  $("#form-alumno-buscar").serialize(), 
			function(data) {
				lanzarMensaje(data.error);
				if (data.error.tipo = "success") {
					$("#tarjetasUsuarios").html(data.data);
				}
			});
}

function guardarAlumno() {
	$.post("Usuarios?accion=agregarUsuario", $("#form-alumno-registrar").serialize(), 
			function(data) {
				lanzarMensaje(data.error);
			});
}

function selectTarjeta(id_usuario){
	console.log("ACAAAA"+id_usuario)
}
function buscarUsuario(){
	
	if($("#buscarUsuario").val()!=""){
		setTimeout(() => {
			
				$("#tarjetasUsuarios .card-body").each(function( index ) { //recorro todas las tarjetas
					var encontre = false;
					$(this).find("div").each(function( index ) { //de cada tarjeta recorro los divs que la componen
						
						  if($(this).text().toLowerCase().indexOf($("#buscarUsuario").val().toLowerCase())  > -1 && !encontre){
							  encontre = true;
							  console.log("coincide");
						  }
						
					});
					console.log(encontre);
					if(encontre){ //Si encontre que coincida con lo que busco, lo muestro
						$(this).parent().show();
					}else{ //Si no encontre que coincida con lo que busco, no lo muestro
						$(this).parent().hide();
					}
				});
			
			
			
		}, 300);
	}else{
		$(".card-body").parent().show();
	}
}