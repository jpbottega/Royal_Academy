package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Opciones_Pregunta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(updatable = false, nullable = false)
	private int id_opcion;
	private int id_pregunta;
	private Boolean respuesta_correcta;
	private String respuesta;
	
	public Opciones_Pregunta() {
		super();
		this.id_opcion = 0;
		this.id_pregunta = 0;
		this.respuesta_correcta = false;
		this.respuesta = "";
	}
	
	public Opciones_Pregunta(int id_opcion, int id_pregunta, Boolean respuesta_correcta, String respuesta) {
		super();
		this.id_opcion = id_opcion;
		this.id_pregunta = id_pregunta;
		this.respuesta_correcta = respuesta_correcta;
		this.respuesta = respuesta;
	}
	
	public int getId_opcion() {
		return id_opcion;
	}
	public void setId_opcion(int id_opcion) {
		this.id_opcion = id_opcion;
	}
	public int getId_pregunta() {
		return id_pregunta;
	}
	public void setId_pregunta(int id_pregunta) {
		this.id_pregunta = id_pregunta;
	}
	public Boolean getRespuesta_correcta() {
		return respuesta_correcta;
	}
	public void setRespuesta_correcta(Boolean respuesta_correcta) {
		this.respuesta_correcta = respuesta_correcta;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
	
}
