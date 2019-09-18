package modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Juan Patricio
 * Esta clase relaciona las preguntas con las respuestas elegidas por el alumno. se usa desde CursoExamen
 */
@Entity
@Table (name = "examenresolucion")
public class ExamenResolucion {
	//private List<PreguntaResuelta> lstRespuestas;
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int id_alumno;
	private float resultado; // uso un float porque se muestra el porcentaje de respuestas correctas sobre totales = XX.YY%
	private boolean entregado;
	private boolean aprobado;
	private int id_curso_examen;
	
	public ExamenResolucion() {}
		
	public ExamenResolucion(int id, int id_alumno, float resultado, boolean entregado, boolean aprobado,
			int id_curso_examen) {
		super();
		this.id = id;
		this.id_alumno = id_alumno;
		this.resultado = resultado;
		this.entregado = entregado;
		this.aprobado = aprobado;
		this.id_curso_examen = id_curso_examen;
	}

	public int getId_alumno() {
		return id_alumno;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setId_alumno(int alumno) {
		this.id_alumno = alumno;
	}

	public float getResultado() {
		return resultado;
	}

	public void setResultado(float resultado) {
		this.resultado = resultado;
	}

	public boolean isEntregado() {
		return entregado;
	}

	public void setEntregado(boolean entregado) {
		this.entregado = entregado;
	}

	public boolean isAprobado() {
		return aprobado;
	}

	public void setAprobado(boolean aprobado) {
		this.aprobado = aprobado;
	}

	public int getId_curso_examen() {
		return id_curso_examen;
	}

	public void setId_curso_examen(int id_curso_examen) {
		this.id_curso_examen = id_curso_examen;
	}
	
	/*
	public void corregirExamen() {
		float resultado = 0, contador = 0;
		if (entregado) {
			for (PreguntaResuelta p : this.getLstRespuestas()) {
				contador++;
				if (p.getPregunta().getRespuestaCorrecta().equals(p.getRespuesta())) {
					resultado++;
				}
			}
			this.resultado = resultado / contador;
		}
	}
	
	void responderPregunta(Pregunta p, Respuesta r) { // cambia la respuesta de una pregunta
		for (PreguntaResuelta pr : this.lstRespuestas) {
			if (pr.getPregunta().equals(p)) {
				pr.setRespuesta(r);
			}
		}
	}
	*/
}
