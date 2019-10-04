package modelo;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Esta es la clase que relaciona un examen con su fecha de realizacion y todos
 * los alumnos que lo rindieron. esta es la q usamos para rendir el examen
 **/
@Entity
@Table (name = "cursoexamen")
public class CursoExamen {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int id_examen;
	private int id_curso;
	private Date fecha;
	private String descripcion;

	public CursoExamen() {}

	public CursoExamen(int id, int id_examen, int id_curso, Date fecha) {
		super();
		this.id = id;
		this.id_examen = id_examen;
		this.id_curso = id_curso;
		this.fecha = fecha;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_examen() {
		return id_examen;
	}

	public void setId_examen(int id_examen) {
		this.id_examen = id_examen;
	}

	public int getId_curso() {
		return id_curso;
	}

	public void setId_curso(int id_curso) {
		this.id_curso = id_curso;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/*
	public List<ExamenResolucion> getLstExamenesResueltos() {
		return lstExamenesResueltos;
	}

	public void setLstExamenesResueltos(List<ExamenResolucion> lstExamenesResueltos) {
		this.lstExamenesResueltos = lstExamenesResueltos;
	}

	public boolean agregarExamenResuelto(ExamenResolucion ex) {
		return this.lstExamenesResueltos.add(ex);
	}

	public void generarExamenesParaAlumnos(List<Usuario> alumnos) {
		for (Usuario u : alumnos) { // genero un examen a resolver para cada usuario
			this.lstExamenesResueltos.add(new ExamenResolucion(u));
		}
		for (ExamenResolucion e : this.lstExamenesResueltos) { // a cada examen generado antes le agrego las preguntas
																// del examen
			for (Pregunta p : this.examen.getLstPreguntas()) {
				e.agregarRespuesta(new PreguntaResuelta(p, new Respuesta()));
			}
		}
	}

	// establece si se aprobo el examen resuelto
	public void evaluarExamenesResueltos() {
		if (this.examen.getCriterioAprobacion() == 0) {
			for (ExamenResolucion ex : this.lstExamenesResueltos) {
				ex.setAprobado(ex.isEntregado() && ex.getResultado() >= this.examen.getCriterioAprobacion());
			}
		} // else por si no se puso el criterio de aprobacion del examen ??
	}
	*/
}
