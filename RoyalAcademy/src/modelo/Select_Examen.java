package modelo;

public class Select_Examen {
	private int id_examen;
	private String descripcion;
	private String preguntas_disponibles;
	private String preguntas_habilitadas;
	
	public Select_Examen() {}
	
	public int getId_examen() {
		return id_examen;
	}
	public void setId_examen(int id_examen) {
		this.id_examen = id_examen;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getPreguntas_disponibles() {
		return preguntas_disponibles;
	}
	public void setPreguntas_disponibles(String preguntas_disponibles) {
		this.preguntas_disponibles = preguntas_disponibles;
	}
	public String getPreguntas_habilitadas() {
		return preguntas_habilitadas;
	}
	public void setPreguntas_habilitadas(String preguntas_habilitadas) {
		this.preguntas_habilitadas = preguntas_habilitadas;
	}
	
	
}
