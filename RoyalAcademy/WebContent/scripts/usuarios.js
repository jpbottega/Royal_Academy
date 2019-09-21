function botonNuevoUsuario() {
	// aca esta hecho bien a lo bestia jajajaja
	$("#form-usuario").show();
	$("#botonGuardarUsuario").show();
	$("#botonEliminarUsuario").hide();
	$("#botonModificarUsuario").hide();
	$("#apellidoUsuario").val(""); // reseteo los valores, no se si esta bien o si no hace falta
	$("#nombreUsuario").val("");
	$("#mailUsuario").val("");
	$("#id_usuario").val("0");
	$("#passUsuario").val("");
	$("#nacimientoUsuario").val("");
	$("#telefonoUsuario").val("");
	$("#verificadoUsuario").val("false")
	$("#id_rol").val($("#select_roles").val());
}

function selectRolesUsuario(){
	$("#form-usuario").hide();
	$("#id_rol").val($("#select_roles").val());
	$.post("Usuarios?accion=buscarUsuario&id_rol=" + $("#select_roles").val(),
			function(data) {
				lanzarMensaje(data.error);
				if (data.error.tipo = "success") {
					$("#tarjetasUsuarios").html(data.data);
				}
			});
}

function guardarUsuario() {
	$.post("Usuarios?accion=guardarUsuario", $("#form-usuario").serialize(), 
			function(data) {
				lanzarMensaje(data.error);
			});
}

function selectTarjeta(id_usuario){
	$.post("Usuarios?accion=traerUsuario&id_usuario=" + id_usuario, function (data){
		$("#form-usuario").show();
		$("#form-usuario").html(data.data);
		lanzarMensaje(data.error);
	});
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

function eliminarUsuario() {
	$.post("Usuarios?accion=eliminarUsuario", $("#form-usuario").serialize(), function (data) {
		$("#form-usuario").hide();
		$("#botonEliminarUsuario").hide();
		$("#botonGuardarUsuario").hide();
		lanzarMensaje(data.error);
	});
}