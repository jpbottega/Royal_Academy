package modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Sede_Usuario implements Serializable{
	@Id
	private int id_sede;
	@Id
	private int id_usuario;
	
	public Sede_Usuario() {}

	public Sede_Usuario(int id_sede, int id_usuario) {
		super();
		this.id_sede = id_sede;
		this.id_usuario = id_usuario;
	}

	public int getId_sede() {
		return id_sede;
	}

	public void setId_sede(int id_sede) {
		this.id_sede = id_sede;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
}
