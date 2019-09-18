package modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "respuestas")
public class Respuesta {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	private int id;
	private String respuesta;
	private int id_pregunta;
	
	public Respuesta() {}
	
	public Respuesta(String respuesta, int id_pregunta) {
		super();
		this.respuesta = respuesta;
		this.id_pregunta = id_pregunta;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getRespuesta() {
		return respuesta;
	}
	
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
	public int getId_pregunta() {
		return id_pregunta;
	}

	public void setId_pregunta(int id_pregunta) {
		this.id_pregunta = id_pregunta;
	}

	@Override
	public boolean equals(Object obj) {
		// comparo el string de la respuesta para ver si es correcta. se podria hacer por id?
		return this.getRespuesta().compareToIgnoreCase(((Respuesta)obj).getRespuesta()) == 0;
	}	
}
