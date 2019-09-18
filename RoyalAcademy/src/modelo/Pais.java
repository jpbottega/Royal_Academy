package modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Pais {
	
	@Id 
	private int id_pais;
	private String denominacion;
	
	public Pais() {}
	
	public Pais(int id_pais, String denominacion) {
		super();
		this.id_pais = id_pais;
		this.denominacion = denominacion;
	}

	public int getId_pais() {
		return id_pais;
	}

	public void setId_pais(int id_pais) {
		this.id_pais = id_pais;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

}
