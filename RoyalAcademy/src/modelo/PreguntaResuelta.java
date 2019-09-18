package modelo;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name = "preguntaresuelta")
public class PreguntaResuelta {
	private int id_pregunta; // la pregunta a responder, deberia venir desde el examen
	private int id_respuesta; // la respuesta elegida, deberia venir de la seleccion del alumno en la interfaz de resolucion de examen
	private int id_examen_resolucion;
	
	public PreguntaResuelta() {}
	
	public PreguntaResuelta(int id_pregunta, int id_respuesta, int id_examen_resolucion) {
		super();
		this.id_pregunta = id_pregunta;
		this.id_respuesta = id_respuesta;
		this.id_examen_resolucion = id_examen_resolucion;
	}

	public int getId_pregunta() {
		return id_pregunta;
	}

	public void setId_pregunta(int id_pregunta) {
		this.id_pregunta = id_pregunta;
	}

	public int getId_respuesta() {
		return id_respuesta;
	}

	public void setId_respuesta(int id_respuesta) {
		this.id_respuesta = id_respuesta;
	}

	public int getId_examen_resolucion() {
		return id_examen_resolucion;
	}

	public void setId_examen_resolucion(int id_examen_resolucion) {
		this.id_examen_resolucion = id_examen_resolucion;
	}
}
