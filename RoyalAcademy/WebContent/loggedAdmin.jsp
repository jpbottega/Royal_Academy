<!DOCTYPE html>
<html lang="en">
<head>
<title>Royal Academy</title>

<!-- Required meta tags -->
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="logged-files/css/bootstrap.min.css">

<!-- FontAwesome CSS -->
<link rel="stylesheet" href="logged-files/css/font-awesome.min.css">

<!-- ElegantFonts CSS -->
<link rel="stylesheet" href="logged-files/css/elegant-fonts.css">

<!-- themify-icons CSS -->
<link rel="stylesheet" href="logged-files/css/themify-icons.css">

<!-- Swiper CSS -->
<link rel="stylesheet" href="logged-files/css/swiper.min.css">

<!-- Styles -->
<link rel="stylesheet" href="logged-files/style.css">
<link rel="stylesheet" href="logged-admin/css-logged-admin.css">
<link rel="stylesheet" href="logged-admin/perfiles.css">
<link rel="stylesheet" href="logged-admin/sedes.css">
<link rel="stylesheet" href="logged-admin/carreras.css">
<link rel="stylesheet" href="logged-admin/cursos.css">
<link rel="stylesheet" href="logged-admin/toastr.min.css">
<link rel="stylesheet" href="logged-admin/usuarios.css">
<link rel="stylesheet"
	href="logged-admin/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="logged-admin/preguntas.css">
<link rel="stylesheet" href="logged-admin/examenes.css">
<link rel="stylesheet" href="logged-admin/correccion.css">
<link rel="stylesheet" href="logged-admin/calendario.css">
<link rel="stylesheet" href="fontawesome-free-5.11.2-web/css/all.css">

<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	List<PermisoFunciones> permisoFunciones = (List<PermisoFunciones>) request.getSession()
			.getAttribute("permisoFunciones");
%>
</head>
<body class="hero-content">

	<div>
		<header class="site-header container-navbar">
			<nav class="navbar navbar-expand-lg navbar-dark navbar-admin">
				<h1 class="site-title">
					
					<a href="http://localhost:9080/RoyalAcademy/Login?accion=login">Royal<span>Academy</span></a>
					
					
				</h1>
				<button class="navbar-toggler btn-menu" type="button"
					data-toggle="collapse" data-target="#navbarNav"
					aria-controls="navbarNav" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarNav">
					<ul class="navbar-nav navbar-menu">
						<jsp:include page="menu-logged.jsp" />
					</ul>
				</div>
			</nav>
		</header>

		<div class="container-fluid panel-admin">
			<div class="functions-container" id="functions-container"></div>
		</div>
	</div>

	<script type='text/javascript' charset="UTF-8" src='logged-files/js/jquery.js'></script>
	<script type='text/javascript' charset="UTF-8" src='logged-files/js/swiper.min.js'></script>
	<script type='text/javascript' charset="UTF-8" src='logged-files/js/masonry.pkgd.min.js'></script>
	<script type='text/javascript' charset="UTF-8" src='logged-files/js/jquery.collapsible.min.js'></script>
	<script type='text/javascript' charset="UTF-8" src='logged-files/js/custom.js'></script>
	<script type='text/javascript' charset="UTF-8" src='scripts/toastr.js'></script>
	<script type='text/javascript' charset="UTF-8" src='scripts/moment.js'></script>
	<script type='text/javascript' charset="UTF-8" src='scripts/bootstrap-datetimepicker.min.js'></script>
	<script type='text/javascript' charset="UTF-8" src='scripts/logged-admin.js'></script>
	<script type='text/javascript' charset="UTF-8" src='scripts/perfiles.js'></script>
	<script type='text/javascript' charset="UTF-8" src='scripts/sede.js'></script>
	<script type='text/javascript' charset="UTF-8" src='scripts/carrera.js'></script>
	<script type='text/javascript' charset="UTF-8" src='scripts/usuarios.js'></script>
	<script type='text/javascript' charset="UTF-8" src='scripts/cursos.js'></script>
	<script type='text/javascript' charset="UTF-8" src='scripts/preguntas.js'></script>
	<script type="text/javascript" charset="UTF-8" src='scripts/bootstrap.min.js'></script>
	<script type="text/javascript" charset="UTF-8" src='scripts/examenesABM.js'></script>
	<script type="text/javascript" charset="UTF-8" src='scripts/gestionCalendario.js'></script>
	<script type="text/javascript" charset="UTF-8" src='scripts/correccionExamen.js'></script>
	<script type="text/javascript" charset="UTF-8" src='scripts/notasAlumnos.js'></script>
</body>
</html>