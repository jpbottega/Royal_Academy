package modelo;

public class Select_Funciones_Perfil {

	private String funciones_disponibles;
	private String funciones_habilitadas;
	
	public Select_Funciones_Perfil() {
		
		this.funciones_disponibles = "";
		this.funciones_habilitadas = "";
	}
	
	public Select_Funciones_Perfil(String funciones_disponibles, String funciones_habilitadas) {
		
		this.funciones_disponibles = funciones_disponibles;
		this.funciones_habilitadas = funciones_habilitadas;
	}
	
	public String getFunciones_disponibles() {
		return funciones_disponibles;
	}
	public void setFunciones_disponibles(String funciones_disponibles) {
		this.funciones_disponibles = funciones_disponibles;
	}
	public String getFunciones_habilitadas() {
		return funciones_habilitadas;
	}
	public void setFunciones_habilitadas(String funciones_habilitadas) {
		this.funciones_habilitadas = funciones_habilitadas;
	}
	
	
}
