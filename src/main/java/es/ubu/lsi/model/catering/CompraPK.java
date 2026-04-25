package es.ubu.lsi.model.catering;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the COMPRA database table.
 * 
 */
@Embeddable
public class CompraPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fecha;

	@Column(insertable=false, updatable=false)
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
		CompraPK castOther = (CompraPK)other;
		return 
			this.fecha.equals(castOther.fecha)
			&& this.cif.equals(castOther.cif);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fecha.hashCode();
		hash = hash * prime + this.cif.hashCode();
		
		return hash;
	}
}