package modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="inscripcion_examen")
public class InscripcionExamen implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id 
	private int id_cursoexamen;
	@Id 
	private int id_usuario;
	private boolean entregado;
	private boolean aprobado;
	private float resultado;
	private boolean corregido;
	
	public InscripcionExamen() {}
	
	public InscripcionExamen(int id_cursoexamen, int id_usuario, boolean entregado, boolean aprobado, float resultado) {
		super();
		this.id_cursoexamen = id_cursoexamen;
		this.id_usuario = id_usuario;
		this.entregado = entregado;
		this.aprobado = aprobado;
		this.resultado = resultado;
	}

	public int getId_cursoexamen() {
		return id_cursoexamen;
	}

	public void setId_cursoexamen(int id_cursoexamen) {
		this.id_cursoexamen = id_cursoexamen;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public boolean isEntregado() {
		return entregado;
	}

	public void setEntregado(boolean entregado) {
		this.entregado = entregado;
	}

	public boolean isAprobado() {
		return aprobado;
	}

	public void setAprobado(boolean aprobado) {
		this.aprobado = aprobado;
	}

	public float getResultado() {
		return resultado;
	}

	public void setResultado(float resultado) {
		this.resultado = resultado;
	}

	public boolean isCorregido() {
		return corregido;
	}

	public void setCorregido(boolean corregido) {
		this.corregido = corregido;
	}
}
