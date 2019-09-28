package modelo;

import java.util.ArrayList;
import java.util.List;

public class CursoPreguntas {

	private String option_cursos;
	private String preguntas;
	
	public CursoPreguntas() {
		super();
		this.option_cursos = "";
		this.preguntas = "";
	}
	
	
	public CursoPreguntas(String option_cursos, String preguntas) {
		super();
		this.option_cursos = option_cursos;
		this.preguntas = preguntas;
	}
	public String getOption_cursos() {
		return option_cursos;
	}
	public void setOption_cursos(String option_cursos) {
		this.option_cursos = option_cursos;
	}
	public String getPreguntas() {
		return preguntas;
	}
	public void setPreguntas(String preguntas) {
		this.preguntas = preguntas;
	}
	
	
}

