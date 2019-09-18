package modelo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Funciones {

	@Id
	private int id_funcion;
	private String ds_funcion;
	
	public Funciones(int id_funcion, String ds_funcion) {
		this.id_funcion = id_funcion;
		this.ds_funcion = ds_funcion;
	}
	
	public Funciones() {
		this.id_funcion = 0;
		this.ds_funcion = "";
	}
	public int getId_funcion() {
		return id_funcion;
	}
	public void setId_funcion(int id_funcion) {
		this.id_funcion = id_funcion;
	}
	public String getDs_funcion() {
		return ds_funcion;
	}
	public void setDs_funcion(String ds_funcion) {
		this.ds_funcion = ds_funcion;
	}
	
	
	
}
