package es.ubu.lsi.model.catering;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * Clase embebida que representa la dirección postal de un cliente.
 * Sus atributos se almacenan en la misma tabla que la entidad que la contiene (Cliente).
 *
 * @author Luis Menendez Ramos
 */
@Embeddable
public class DireccionPostal implements Serializable {
	
	private final static long serialVersionUID = 1L;
	
	/**
	 * Ciudad del cliente
	 */
	private String ciudad;

/**
 * Código postal
 */
	private String cp;
	/**
	 * Dirección (calle, número, etc.)
	 */
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
	
	@Override
	public String toString() {
		return "DireccionPostal [ direccion= " + direccion + " CP= " + cp + " ciudad= " + ciudad +" ]";
	}

}
