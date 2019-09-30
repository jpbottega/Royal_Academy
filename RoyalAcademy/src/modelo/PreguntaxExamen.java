package modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "preguntaxexamen")
public class PreguntaxExamen implements Serializable {
	@Id
	private int id_pregunta;
	@Id
	private int id_examen;
	
	public PreguntaxExamen() {}
	
	public PreguntaxExamen(int id_pregunta, int id_examen) {
		super();
		this.id_pregunta = id_pregunta;
		this.id_examen = id_examen;
	}

	public int getId_pregunta() {
		return id_pregunta;
	}

	public void setId_pregunta(int id_pregunta) {
		this.id_pregunta = id_pregunta;
	}

	public int getId_examen() {
		return id_examen;
	}

	public void setId_examen(int id_examen) {
		this.id_examen = id_examen;
	}
}
