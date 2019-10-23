
<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	List<InscripcionCursos> cursos = (List<InscripcionCursos>) request.getAttribute("cursos");
	System.out.println(cursos.size());
%>
<div class="col-12 px-25">
	<header
		class="heading flex flex-wrap justify-content-between align-items-center">
		<h2 class="entry-title">Cursos Actuales</h2>
		<span class="form-group">
				<input type="text" class="form-control" id="buscarcurso"
					aria-describedby="emailHelp" placeholder="Buscar..." onkeyup="buscarCursos();">
			</span>

		<!-- .courses-menu -->
	</header>
	<!-- .heading -->
</div>
<%
	if (cursos != null && !cursos.isEmpty()) {
		for (InscripcionCursos c : cursos) {
%>
<div class="col-12 col-md-6 col-lg-4 px-25">
	<div class="course-content">
		<figure class="course-thumbnail">
			<a href="#"><img src="logged-files/images/1.jpg" alt=""></a>
		</figure>
		<!-- .course-thumbnail -->

		<div class="course-content-wrap">
			<header class="entry-header header-curso">
				<h2 class="entry-title">
					<a><span><%=c.getDenominacion()%></span></a>
				</h2>

				<div class="entry-meta flex align-items-center">
					<div class="row">
						<div class="col-md-12">
							<div class="course-author w-100">
								<a><span><%=c.getCarrera()%></span> </a>
							</div>
						</div>
						<div class="col-md-12">
							<div class="course-author w-100">
								<a><span><%=c.getSede()%></span> </a>
							</div>
						</div>
					</div>
				</div>
				<!-- .course-date -->
			</header>
			<!-- .entry-header -->

			<footer
				class="entry-footer flex justify-content-between align-items-center">
				<div class="row">
					<div class="col-md-12">
						<div class="course-cost">
							$4500 <span class="price-drop">$6800</span>
						</div>
						<!-- .course-cost -->
					</div>
					<div class="col-md-12">
						<div
							class="course-ratings flex justify-content-end align-items-center">
							<span class="course-ratings-count w-100"> <%
 	if (c.isInscripto()) {
 %><button class="btn btn-danger w-100"
									onclick="borrarInscribirse(<%=c.getId()%>,<%=c.getId_sede()%>);">Borrar
									Inscripción</button> <%
 	} else {
 %><button class="btn btn-success w-100"
									onclick="inscribirse(<%=c.getId()%>,<%=c.getId_sede()%>);">Inscribirse</button> <%
 	}
 %>
							</span>
						</div>
					</div>
				</div>
				<!-- .course-ratings -->
			</footer>
			<!-- .entry-footer -->
		</div>
		<!-- .course-content-wrap -->
	</div>
	<!-- .course-content -->
</div>
<!-- .col -->
<%
	}
	}
%>