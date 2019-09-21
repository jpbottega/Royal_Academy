package modelo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PermisoFunciones {

	@Id
	private int id_funcion;
	private String ds_funcion;
	private int habilitada;
	
	public PermisoFunciones() {
		super();
		this.id_funcion = 0;
		this.ds_funcion = "";
		this.habilitada = 0;
	}
	
	public PermisoFunciones(int id_funcion, String ds_funcion, int habilitada) {
		super();
		this.id_funcion = id_funcion;
		this.ds_funcion = ds_funcion;
		this.habilitada = habilitada;
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
	public int getHabilitada() {
		return habilitada;
	}
	public void setHabilitada(int habilitada) {
		this.habilitada = habilitada;
	}
	
	
}
