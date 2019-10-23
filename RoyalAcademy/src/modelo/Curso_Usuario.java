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
	@Id
	private int id_sede;
	
	public Curso_Usuario() {}
	
	public Curso_Usuario(int id_curso, int id_usuario,int id_sede) {
		super();
		this.id_curso = id_curso;
		this.id_usuario = id_usuario;
		this.id_sede=id_sede;
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

	public int getId_sede() {
		return id_sede;
	}

	public void setId_sede(int id_sede) {
		this.id_sede = id_sede;
	}
	
}
