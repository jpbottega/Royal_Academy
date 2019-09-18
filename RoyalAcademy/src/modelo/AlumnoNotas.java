package modelo;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name = "alumnonotas")
public class AlumnoNotas {
	private int id_alumno;
	private int id_curso;
	//private List<Float> lstNotas;
	private float nota_final;
	private boolean aprobado;
	
	public AlumnoNotas() {}
	
	public AlumnoNotas(int alumno, int curso) {
		super();
		this.id_alumno = alumno;
		this.id_curso = curso;
		//this.lstNotas = new ArrayList<Float>();
		this.aprobado = false;
		this.nota_final = 0;
	}	
	/*
	public List<Float> getLstNotas() {
		return lstNotas;
	}
	
	public void setLstNotas(List<Float> lstNotas) {
		this.lstNotas = lstNotas;
	}
	*/

	public int getId_alumno() {
		return id_alumno;
	}

	public void setId_alumno(int id_alumno) {
		this.id_alumno = id_alumno;
	}

	public int getId_curso() {
		return id_curso;
	}

	public void setId_curso(int id_curso) {
		this.id_curso = id_curso;
	}

	public float getNota_final() {
		return nota_final;
	}

	public void setNota_final(float nota_final) {
		this.nota_final = nota_final;
	}

	public boolean isAprobado() {
		return aprobado;
	}

	public void setAprobado(boolean aprobado) {
		this.aprobado = aprobado;
	}
	/*
	public boolean agregarNota(float nota) {
		return this.lstNotas.add(nota);
	}

	public void calcularNotafinal() {
		float acumulador = 0;
		for (Float nota : this.lstNotas) {
			acumulador += nota;
		}
		this.notaFinal = acumulador / this.lstNotas.size();
		if (this.notaFinal >= this.curso.getCriterioAprobacion()) {
			this.setAprobado(true);
		}
	}
	*/
}
