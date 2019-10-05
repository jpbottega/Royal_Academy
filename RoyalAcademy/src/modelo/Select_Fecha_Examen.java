package modelo;

public class Select_Fecha_Examen {
	private int id_fecha_examen;
	private String fecha;
	private String descripcion;
	private int id_examen;
	private String htmlOpcionesExamen;
	
	public Select_Fecha_Examen() {}
	
	public Select_Fecha_Examen(int id_fecha_examen, String fecha, String descripcion, int id_examen) {
		super();
		this.id_fecha_examen = id_fecha_examen;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.id_examen = id_examen;
	}

	public int getId_fecha_examen() {
		return id_fecha_examen;
	}

	public void setId_fecha_examen(int id_fecha_examen) {
		this.id_fecha_examen = id_fecha_examen;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getId_examen() {
		return id_examen;
	}

	public void setId_examen(int id_examen) {
		this.id_examen = id_examen;
	}

	public String getHtmlOpcionesExamen() {
		return htmlOpcionesExamen;
	}

	public void setHtmlOpcionesExamen(String htmlOpcionesExamen) {
		this.htmlOpcionesExamen = htmlOpcionesExamen;
	}
}
