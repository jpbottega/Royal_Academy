package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cursos")
public class Curso {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(updatable = false, nullable = false)
	private int id;
	private String denominacion;
	private float criterioAprobacion; // este es el criterio para ver si se aprobo el curso completo
	private int id_carrera;
	//private List<Examen> lstExamenes; // se puede poner el curso en el examen
	
	public Curso() {}
	
	public Curso(String denominacion, float criterioAprobacion, int id_carrera) {
		super();
		this.denominacion = denominacion;
		//this.lstExamenes = new ArrayList<Examen>();
		this.criterioAprobacion = criterioAprobacion;
		this.id_carrera = id_carrera;
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

	/*
	public List<Examen> getLstExamenes() {
		return lstExamenes;
	}

	public void setLstExamenes(List<Examen> lstExamenes) {
		this.lstExamenes = lstExamenes;
	}
	*/
	public float getCriterioAprobacion() {
		return criterioAprobacion;
	}

	public void setCriterioAprobacion(float criterioAprobacion) {
		this.criterioAprobacion = criterioAprobacion;
	}
}
