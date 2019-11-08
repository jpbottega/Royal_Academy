package modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class InfoCursos implements Serializable{
	
	@Id
	private int id_alumno;
	@Id
	private int id_curso;
	private float nota_final;
	private Boolean aprobado;
	private Boolean notificado;
	private String nombre;
	private String apellido;
	private String email;
	private String sede;
	private String curso;
	
	public InfoCursos() {
		super();
		this.id_alumno = 0;
		this.id_curso = 0;
		this.nota_final = 0;
		this.aprobado = false;
		this.notificado = false;
		this.nombre = "";
		this.apellido =  "";
		this.email =  "";
		this.sede =  "";
		this.curso="";
	}
	
	public InfoCursos(int id_alumno, int id_curso, float nota_final, Boolean aprobado, Boolean notificado,
			String nombre, String apellido, String email, String sede,String curso) {
		super();
		this.id_alumno = id_alumno;
		this.id_curso = id_curso;
		this.nota_final = nota_final;
		this.aprobado = aprobado;
		this.notificado = notificado;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.sede = sede;
		this.curso=curso;
	}
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
	public Boolean getAprobado() {
		return aprobado;
	}
	public void setAprobado(Boolean aprobado) {
		this.aprobado = aprobado;
	}
	public Boolean getNotificado() {
		return notificado;
	}
	public void setNotificado(Boolean notificado) {
		this.notificado = notificado;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSede() {
		return sede;
	}
	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}
	
	
}
