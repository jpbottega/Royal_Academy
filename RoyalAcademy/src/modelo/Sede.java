package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sedes")
public class Sede {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(updatable = false, nullable = false)
	private int id;
	private int id_pais;
	private String sede;
	
	public Sede() {}
	
	public Sede(int id, int pais, String sede) {
		super();
		this.id = id;
		this.id_pais = pais;
		this.sede = sede;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_pais() {
		return id_pais;
	}

	public void setId_pais(int pais) {
		this.id_pais = pais;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	
}
