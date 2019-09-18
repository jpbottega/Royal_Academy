package modelo;

public class Select_Sedes_Carrera {

	private String sedes_disponibles;
	private String sedes_habilitadas;
	
	public Select_Sedes_Carrera() {
		
		this.sedes_disponibles = "";
		this.sedes_habilitadas = "";
	}
	
	public Select_Sedes_Carrera(String sedes_disponibles, String sedes_habilitadas) {
		
		this.sedes_disponibles = sedes_disponibles;
		this.sedes_habilitadas = sedes_habilitadas;
	}
	
	public String getSedes_disponibles() {
		return sedes_disponibles;
	}
	public void setSedes_disponibles(String sedes_disponibles) {
		this.sedes_disponibles = sedes_disponibles;
	}
	public String getSedes_habilitadas() {
		return sedes_habilitadas;
	}
	public void setSedes_habilitadas(String sedes_habilitadas) {
		this.sedes_habilitadas = sedes_habilitadas;
	}
	
	
}
