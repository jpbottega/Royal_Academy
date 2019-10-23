package modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class InscripcionCursos implements Serializable{
	
	@Id 
	private int id;
	private String denominacion;
	private float criterioAprobacion; // este es el criterio para ver si se aprobo el curso completo
	@Id
	private int id_carrera;
	private boolean inscripto;
	private String carrera;
	private String sede;
	@Id
	private int id_sede;
	
	public InscripcionCursos() {}
	
	public InscripcionCursos(String denominacion, float criterioAprobacion, int id_carrera,boolean inscripto,String carrera,String sede,int id_sede) {
		super();
		this.denominacion = denominacion;
		//this.lstExamenes = new ArrayList<Examen>();
		this.criterioAprobacion = criterioAprobacion;
		this.id_carrera = id_carrera;
		this.inscripto=inscripto;
		this.carrera=carrera;
		this.sede=sede;
		this.id_sede=id_sede;
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
	
	public int getId_carrera() {
		return id_carrera;
	}

	public void setId_carrera(int id_carrera) {
		this.id_carrera = id_carrera;
	}

	
	
	public boolean isInscripto() {
		return inscripto;
	}

	public void setInscripto(boolean inscripto) {
		this.inscripto = inscripto;
	}

	public float getCriterioAprobacion() {
		return criterioAprobacion;
	}

	public void setCriterioAprobacion(float criterioAprobacion) {
		this.criterioAprobacion = criterioAprobacion;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public int getId_sede() {
		return id_sede;
	}

	public void setId_sede(int id_sede) {
		this.id_sede = id_sede;
	}
	
	
}
