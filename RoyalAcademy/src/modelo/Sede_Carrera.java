package modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Sede_Carrera implements Serializable{

	@Id
	private int id_sede;
	@Id
	private int id_carrera;
	
	public Sede_Carrera() {
		this.id_sede = 0;
		this.id_carrera = 0;
	}
	
	public Sede_Carrera(int id_sede, int id_carrera) {
		super();
		this.id_sede = id_sede;
		this.id_carrera = id_carrera;
	}
	public int getId_sede() {
		return id_sede;
	}
	public void setId_sede(int id_sede) {
		this.id_sede = id_sede;
	}
	public int getId_carrera() {
		return id_carrera;
	}
	public void setId_carrera(int id_carrera) {
		this.id_carrera = id_carrera;
	}
	
	
	
}
