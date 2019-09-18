package modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "preguntas")
public class Pregunta {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String pregunta;
	private int id_respuesta_correcta;
	//private List<Respuesta> respuestasIncorrectas;
	private String descripcion;
	
	public Pregunta() {}
	
	public Pregunta(String pregunta, int respuestaCorrecta, String descripcion) {
		super();
		this.pregunta = pregunta;
		this.id_respuesta_correcta = respuestaCorrecta;
		//this.respuestasIncorrectas = new ArrayList<Respuesta>();
		this.descripcion = descripcion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public int getId_respuesta_correcta() {
		return id_respuesta_correcta;
	}

	public void setId_respuesta_correcta(int respuestaCorrecta) {
		this.id_respuesta_correcta = respuestaCorrecta;
	}
	/*
	public List<Respuesta> getRespuestasIncorrectas() {
		return respuestasIncorrectas;
	}

	public void setRespuestasIncorrectas(List<Respuesta> respuestasIncorrectas) {
		this.respuestasIncorrectas = respuestasIncorrectas;
	}
	*/
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public boolean equals(Object obj) {
		// comparo la pregunta por id o por la pregunta en si
		return this.getId() == ((Pregunta)obj).getId() || this.getPregunta().compareToIgnoreCase(((Pregunta)obj).getPregunta()) == 0;
	}
	
	
}
