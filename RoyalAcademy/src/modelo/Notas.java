package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Notas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private int id;
	private int id_alumno;
	private int id_curso;
	private float nota;
	private boolean esExamen;
	
	public Notas() {}
	
	public Notas(int id_alumno, int id_curso, float nota, boolean esExamen) {
		this.id_alumno = id_alumno;
		this.id_curso = id_curso;
		this.nota = nota;
		this.esExamen = esExamen;
	}
	
	public Notas(int id, int id_alumno, int id_curso, float nota, boolean esExamen) {
		this.id = id;
		this.id_alumno = id_alumno;
		this.id_curso = id_curso;
		this.nota = nota;
		this.esExamen = esExamen;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_alumno() {
		return id_alumno;
	}

	public void setId_alumno(int id_alumno) {
		this.id_alumno = id_alumno;
	}

	public int getId_curso() {
		return id_curso;
	}

	public void setId_curso(int id_curso) {
		this.id_curso = id_curso;
	}

	public float getNota() {
		return nota;
	}

	public void setNota(float nota) {
		this.nota = nota;
	}

	public boolean isEsExamen() {
		return esExamen;
	}

	public void setEsExamen(boolean esExamen) {
		this.esExamen = esExamen;
	}	
}
