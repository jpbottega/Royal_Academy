package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "carreras")
public class Carrera {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(updatable = false, nullable = false)
	private int id;
	private String denominacion;
	
	public Carrera() {
		super();
		this.id = 0;
		this.denominacion = "";
	}
	
	public Carrera(int id, String denominacion) {
		super();
		this.id = id;
		this.denominacion = denominacion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

}
