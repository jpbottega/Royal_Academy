package modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Funciones_Perfil implements Serializable {

	@Id
	private int id_funcion;
	@Id
	private int id_rol;
	
	public Funciones_Perfil() {
		this.id_funcion = 0;
		this.id_rol = 0;
	}
	
	public Funciones_Perfil(int id_funcion, int id_rol) {
		this.id_funcion = id_funcion;
		this.id_rol = id_rol;
	}
	public int getId_funcion() {
		return id_funcion;
	}
	public void setId_funcion(int id_funcion) {
		this.id_funcion = id_funcion;
	}
	public int getId_rol() {
		return id_rol;
	}
	public void setId_rol(int id_rol) {
		this.id_rol = id_rol;
	}

	
	
}
