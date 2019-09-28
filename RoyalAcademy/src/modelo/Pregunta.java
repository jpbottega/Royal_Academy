package modelo;

import javax.persistence.Column;
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
	@Column(updatable = false, nullable = false)
	private int id;
	private String pregunta;
	private int id_carrera;
	private int id_curso;

	public Pregunta() {
		this.id = 0;
		this.pregunta = "";
		this.id_carrera = 0;
		this.id_curso = 0;

	}

	public Pregunta(int id, String pregunta, int id_carrera, int id_curso) {
		super();
		this.id = id;
		this.pregunta = pregunta;
		this.id_carrera = id_carrera;
		this.id_curso = id_curso;
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

	@Override
	public boolean equals(Object obj) {
		// comparo la pregunta por id o por la pregunta en si
		return this.getId() == ((Pregunta) obj).getId()
				|| this.getPregunta().compareToIgnoreCase(((Pregunta) obj).getPregunta()) == 0;
	}

	public int getId_carrera() {
		return id_carrera;
	}

	public void setId_carrera(int id_carrera) {
		this.id_carrera = id_carrera;
	}

	public int getId_curso() {
		return id_curso;
	}

	public void setId_curso(int id_curso) {
		this.id_curso = id_curso;
	}

}
