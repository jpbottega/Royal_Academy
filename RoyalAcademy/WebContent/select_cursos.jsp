<%@ page import="modelo.*,java.util.List,java.util.ArrayList"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<Curso> cursos = (List<Curso>) request.getAttribute("cursos");
%>

					<div class="form-group">
						<label for="exampleFormControlSelect1">Curso</label> <select
							class="form-control" id="select_curso" name="select_curso">
							<%
								if (!cursos.isEmpty()) {
									for (Curso curso : cursos) {
							%>
							<option value="<%=curso.getId()%>"><%=curso.getDenominacion()%></option>
							<%
								}
								}
							%>

						</select>
					</div>


