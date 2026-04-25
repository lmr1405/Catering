package es.ubu.lsi.model.catering;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class DireccionPostal implements Serializable {
	
	private final static long serialVersionUID = 1L;
	
	private String ciudad;

	private String cp;
	
	private String direccion;
	
	public DireccionPostal() {
		
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

}
