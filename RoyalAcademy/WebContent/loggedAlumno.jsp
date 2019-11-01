<!DOCTYPE html>
<html lang="en">
<head>
<title>Royal Academy</title>

<!-- Required meta tags -->
<meta charset="utf-8">
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
<link rel="stylesheet" href="logged-admin/resolucionExamen.css">
<link rel="stylesheet" href="logged-admin/toastr.min.css">
</head>

<body id="contenido-alumno" class="hero-content">
	<div>
		<header class="site-header container-navbar">
			<nav class="navbar navbar-expand-lg navbar-dark navbar-admin">
				<h1 class="site-title">
					<a href="http://localhost:8080/RoyalAcademy/Login?accion=login">Royal<span>
							Academy</span></a>
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

		<div class="hero-content-overlay">
			<div class="container">
				<div class="row">
					<div class="col-12">
						<div
							class="hero-content-wrap flex flex-column justify-content-center align-items-start">
							<header class="entry-header">
								<h4>Empieza tu curso online!</h4>
								<h1>
									el mejor sistema online de <br />Aprendizaje
								</h1>
							</header>
							<footer class="entry-footer read-more">
								<a href="#section-cursos">Aprende más</a>
							</footer>
							<!-- .entry-footer -->
						</div>
						<!-- .hero-content-wrap -->
					</div>
					<!-- .col -->
				</div>
				<!-- .row -->
			</div>
			<!-- .container -->
		</div>
		<!-- .hero-content-hero-content-overlay -->
	</div>
	<!-- .hero-content-hero-content-overlay -->

		<section class="featured-courses vertical-column courses-wrap"
			id="section-cursos">
			<div class="container">
				<div class="row mx-m-25"  id="header_inscripcion_cursos">
					
					
						<jsp:include page="inscripcionCursos.jsp" />
					

				</div>
				<!-- .row -->
			</div>
			<!-- .container -->
		</section>
		<!-- .courses-wrap -->

	<footer class="site-footer footer-style">
		<div class="footer-widgets">
			<div class="container">
				<div class="row">
					<div class="col-12 col-md-6 col-lg-3">
						<div class="foot-about">
							<h1 class="site-title">
								<a rel="home" style="color: #fff">Royal<span> Academy</span></a>
							</h1>


							<p>Excepteur sint occaecat cupidatat non proident, sunt in
								culpa qui officia dese mollit anim id est laborum.</p>
						</div>
						<!-- .foot-about -->
					</div>
					<!-- .col -->

					<div class="col-12 col-md-6 col-lg-3 mt-5 mt-md-0">
						<div class="foot-contact">
							<h2>Contact Us</h2>

							<ul>
								<li>Email: info.deertcreative@gmail.com</li>
								<li>Phone: (+88) 111 555 666</li>
								<li>Address: 40 Baria Sreet 133/2 NewYork City, US</li>
							</ul>
						</div>
						<!-- .foot-contact -->
					</div>
					<!-- .col -->

					<div class="col-12 col-md-6 col-lg-3 mt-5 mt-lg-0">
						<div class="quick-links flex flex-wrap">
							<h2 class="w-100">Quick Links</h2>

							<ul class="w-50">
								<li><a href="#">About </a></li>
								<li><a href="#">Terms of Use </a></li>
								<li><a href="#">Privacy Policy </a></li>
								<li><a href="#">Contact Us</a></li>
							</ul>

							<ul class="w-50">
								<li><a href="#">Documentation</a></li>
								<li><a href="#">Forums</a></li>
								<li><a href="#">Language Packs</a></li>
								<li><a href="#">Release Status</a></li>
							</ul>
						</div>
						<!-- .quick-links -->
					</div>
					<!-- .col -->

					<div class="col-12 col-md-6 col-lg-3 mt-5 mt-lg-0">
						<div class="follow-us">
							<h2>Follow Us</h2>

							<ul class="follow-us flex flex-wrap align-items-center">
								<li><a href="#"><i class="fa fa-facebook"></i></a></li>
								<li><a href="#"><i class="fa fa-google-plus"></i></a></li>
								<li><a href="#"><i class="fa fa-instagram"></i></a></li>
								<li><a href="#"><i class="fa fa-twitter"></i></a></li>
							</ul>
						</div>
						<!-- .quick-links -->
					</div>
					<!-- .col -->
				</div>
				<!-- .row -->
			</div>
			<!-- .container -->
		</div>
		<!-- .footer-widgets -->

		<div class="footer-bar">
			<div class="container">
				<div
					class="row flex-wrap justify-content-center justify-content-lg-between align-items-center">
					<div class="col-1 col-lg-2">
						<h4>Proximamente</h4>
					</div>
					<div class="col-5 col-lg-5">

						<div
							class="download-apps flex flex-wrap justify-content-center justify-content-lg-start align-items-center">
							<a href="#"><img src="logged-files/images/app-store.png"
								alt=""></a> <a href="#"><img
								src="logged-files/images/play-store.png" alt=""></a>
						</div>
						<!-- .download-apps -->

					</div>

					<div class="col-6 col-lg-5 mt-4 mt-lg-0">
						<div class="footer-bar-nav">
							<ul
								class="flex flex-wrap justify-content-center justify-content-lg-end align-items-center">
								<li><a href="#">DPA</a></li>
								<li><a href="#">Terms of Use</a></li>
								<li><a href="#">Privacy Policy</a></li>
							</ul>
						</div>
						<!-- .footer-bar-nav -->
					</div>
					<!-- .col-12 -->
				</div>
				<!-- .row -->
			</div>
			<!-- .container -->
		</div>
		<!-- .footer-bar -->
	</footer>
	<!-- .site-footer -->
	<script type='text/javascript' src='logged-files/js/jquery.js'></script>
	<script type='text/javascript' src='logged-files/js/swiper.min.js'></script>
	<script type='text/javascript'
		src='logged-files/js/masonry.pkgd.min.js'></script>
	<script type='text/javascript'
		src='logged-files/js/jquery.collapsible.min.js'></script>
	<script type='text/javascript' src='logged-files/js/custom.js'></script>
	<script type='text/javascript' src='scripts/logged-alumno.js'></script>
	<script type='text/javascript' src='scripts/inscripcionAlumnoCurso.js'></script>
	<script type='text/javascript' src='scripts/resolucionExamen.js'></script>
	<script type='text/javascript' src='scripts/logged-admin.js'></script>
	<script type='text/javascript' charset="UTF-8" src='scripts/toastr.js'></script>
	<script type='text/javascript' charset="UTF-8"
		src='scripts/logged-admin.js'></script>
	<script type="text/javascript" charset="UTF-8"
		src='scripts/bootstrap.min.js'></script>
</body>
</html>