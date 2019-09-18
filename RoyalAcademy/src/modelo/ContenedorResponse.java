package modelo;

public class ContenedorResponse {
	
	private Object data;
	private Error error;
	
	public ContenedorResponse(Error error) {
		this.error = error;
	}

	public ContenedorResponse(Object responseContent) {
		this();
		this.data = responseContent;
	}
	public ContenedorResponse() {
		this.data =new Object();
		this.error = new Error(ETipoMensaje.info);
	}

	public ContenedorResponse(Object responseContent, Error error) {
		this.data = responseContent;
		this.error = error;
	}
	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	
	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}


	public enum ETipoMensaje{
		info,
		success,
		warning,
		error
	}
	public static class Error{

		private int cd_error;
		private String tipo;
		private String ds_error;
		private int hayNotif;
		
		
		public Error(){
			this.cd_error=0;
			ds_error="";
			this.tipo="";
			this.setHayNotif(0);
		}
		public Error(ETipoMensaje tipo){
			cd_error=0;
			ds_error="";
			this.tipo=tipo.name();
			this.hayNotif = 0;
		}
		public String getDs_error() {
			return ds_error;
		}
		public void setDs_error(String ds_error) {
			this.ds_error = ds_error;
		}
		
		public Error(ETipoMensaje tipo,int cd_error,String ds_error){
			this.cd_error=cd_error;
			this.ds_error=ds_error;
			this.tipo=tipo.name();
		}
		public String getTipo() {
			return tipo;
		}

		public void setTipo(String tipo) {
			this.tipo = tipo;
		}
		/**
		 * @return the cd_error
		 */
		public int getCd_error() {
			return cd_error;
		}
		/**
		 * @param cd_error the cd_error to set
		 */
		public void setCd_error(int cd_error) {
			this.cd_error = cd_error;
		}
		public int getHayNotif() {
			return hayNotif;
		}
		public void setHayNotif(int hayNotif) {
			this.hayNotif = hayNotif;
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContenedorResponse other = (ContenedorResponse) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		return true;
	}
	
}
