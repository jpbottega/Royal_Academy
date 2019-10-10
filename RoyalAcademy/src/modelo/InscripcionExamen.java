package modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="inscripcion_examen")
public class InscripcionExamen implements Serializable{
	@Id 
	private int id_cursoexamen;
	@Id 
	private int id_usuario;
	
	public InscripcionExamen() {}
	
	public InscripcionExamen(int id_cursoexamen, int id_usuario) {
		super();
		this.id_cursoexamen = id_cursoexamen;
		this.id_usuario = id_usuario;
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
}
