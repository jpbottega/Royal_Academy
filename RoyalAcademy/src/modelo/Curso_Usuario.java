package modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Curso_Usuario implements Serializable {
	@Id
	private int id_curso;
	@Id
	private int id_usuario;
	
	public Curso_Usuario() {}
	
	public Curso_Usuario(int id_curso, int id_usuario) {
		super();
		this.id_curso = id_curso;
		this.id_usuario = id_usuario;
	}

	public int getId_curso() {
		return id_curso;
	}

	public void setId_curso(int id_curso) {
		this.id_curso = id_curso;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
}
