package modelo;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "examenes")
public class Examen {
	// TODO Auto-generated constructor stub
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String descripcion;
	//private List<Pregunta> lstPreguntas;
	private int id_usuario_creador;
	private float criterioAprobacion;
	private Date fechaCreacion; // este no se si ponerlo o no
	
	public Examen() {}

	public Examen(int id, String descripcion, int id_usuario_creador, float criterioAprobacion, Date fechaCreacion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.id_usuario_creador = id_usuario_creador;
		this.criterioAprobacion = criterioAprobacion;
		this.fechaCreacion = fechaCreacion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getId_usuario_creador() {
		return id_usuario_creador;
	}

	public void setId_usuario_creador(int id_usuario_creador) {
		this.id_usuario_creador = id_usuario_creador;
	}

	public float getCriterioAprobacion() {
		return criterioAprobacion;
	}

	public void setCriterioAprobacion(float criterioAprobacion) {
		this.criterioAprobacion = criterioAprobacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	/*
	public List<Pregunta> getLstPreguntas() {
		return lstPreguntas;
	}

	public void setLstPreguntas(List<Pregunta> lstPreguntas) {
		this.lstPreguntas = lstPreguntas;
	}
	*/
}
