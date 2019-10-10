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
<link rel="stylesheet" href="logged-admin/toastr.min.css">
<link rel="stylesheet" href="logged-admin/usuarios.css">
<link rel="stylesheet" href="logged-admin/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="logged-admin/preguntas.css">
<link rel="stylesheet" href="logged-admin/examenes.css">
<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

List<PermisoFunciones> permisoFunciones = (List<PermisoFunciones>) 	request.getSession().getAttribute("permisoFunciones");
%>
</head>
<body>
	<div class="hero-content">
		<header class="site-header">

			<div class="nav-bar nav-bar-admin">
				<div class="container container-modif">
					<div class="row">
						<div class="col-3 col-lg-3">
							<div class="site-branding">
								<h1 class="site-title">
									<%if(permisoFunciones.get(12).getHabilitada()==0){%>
								
									<a href="javascript:gotoHome();" style="color: #fff !important;">Royal<span>Academy</span></a>
									<%}else{ %>	
									<a href="javascript:gotoHomeAlumno();" style="color: #fff !important;">Royal<span>Academy</span></a>
									
									<%} %>	
								</h1>
							</div>
							<!-- .site-branding -->
						</div>
						<!-- .col -->

						<div
							class="col-9 col-lg-9 flex justify-content-end align-content-center">
							<nav
								class="site-navigation flex justify-content-end align-items-center"
								style="width: 100%">

								<jsp:include page="menu-logged.jsp" />



								<!-- .header-bar-search -->
							</nav>
							<!-- .site-navigation -->
						</div>
						<!-- .col -->
					</div>
					<!-- .row -->
				</div>
				<!-- .container -->
			</div>			<!-- .nav-bar -->
		</header>
		<div class="container container-modif container-data"><div class="div-data"><div class="functions-container" id="functions-container"></div></div></div>
		<!-- .site-header -->
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
	<script type="text/javascript" charset="UTF-8" src='scripts/bootstrap.min.js'></script>
	<script type="text/javascript" charset="UTF-8" src='scripts/examenesABM.js'></script>
	<script type="text/javascript" charset="UTF-8" src='scripts/gestionCalendario.js'></script>
	
</body>
</html>