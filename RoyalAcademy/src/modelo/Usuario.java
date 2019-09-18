package modelo;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	private int id;
	private String pass;
	private String nombre;
	private String apellido;
	private String telefono;
	//private List<Curso> cursosInscripto;
	private boolean verificado;
	private String email;
	private int id_rol;
	private Date fechaNacimiento;

	public Usuario() {}
	
	public Usuario(String email, String password, String nombre, String apellido, String telefono,
			boolean verificado, int rol, Date fechaNacimiento) {
		super();
		this.pass = password;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.verificado = verificado;
		this.email = email;
		this.id_rol = rol;
		this.fechaNacimiento = fechaNacimiento;
		//this.cursosInscripto = new ArrayList<Curso>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String password) {
		this.pass = password;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public boolean isVerificado() {
		return verificado;
	}

	public void setVerificado(boolean verificado) {
		this.verificado = verificado;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId_rol() {
		return id_rol;
	}

	public void setId_rol(int rol) {
		this.id_rol = rol;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	/*
	public List<Curso> getCursosInscripto() {
		return cursosInscripto;
	}

	public void setCursosInscripto(List<Curso> cursosInscripto) {
		this.cursosInscripto = cursosInscripto;
	}
	*/
}
