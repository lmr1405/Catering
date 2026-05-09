package es.ubu.lsi.model.catering;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase que representa la clave primaria compuesta de la entidad Compra. Está
 * formada por la fecha de la compra y el CIF del cliente.
 * 
 * Es necesaria para mapear la clave primaria compuesta en JPA.
 * 
 * @author Luis Menendez Ramos
 */
@Embeddable
public class CompraPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Fecha en la que se realiza la compra.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fecha;

	/**
	 * CIF del cliente que realiza la compra.
	 */
	private String cif;

	public CompraPK() {
	}

	public java.util.Date getFecha() {
		return this.fecha;
	}

	public void setFecha(java.util.Date fecha) {
		this.fecha = fecha;
	}

	public String getCif() {
		return this.cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CompraPK)) {
			return false;
		}
		CompraPK castOther = (CompraPK) other;
		return this.fecha.equals(castOther.fecha) && this.cif.equals(castOther.cif);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fecha.hashCode();
		hash = hash * prime + this.cif.hashCode();

		return hash;
	}

	@Override
	public String toString() {
		return "CompraPK [fecha=" + fecha + ", cif=" + cif + "]";
	}
}