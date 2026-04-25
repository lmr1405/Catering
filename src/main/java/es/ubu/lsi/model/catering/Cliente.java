package es.ubu.lsi.model.catering;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


/**
 * The persistent class for the CLIENTE database table.
 * 
 */
@Entity
@NamedQuery(name="Cliente.findAll", query="SELECT c FROM Cliente c")
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String cif;

	private String descripcion;

	@Embedded
	private DireccionPostal direccionPostal;

	//bi-directional many-to-one association to Bonocliente
	@ManyToOne
	@JoinColumn(name="IDBONOCLIENTE")
	private BonoCliente bonoCliente;

	//bi-directional many-to-one association to Compra
	@OneToMany(mappedBy="cliente", fetch = FetchType.LAZY)
	private Set<Compra> compras;

	public Cliente() {
		compras = new HashSet<>();
	}

	public String getCif() {
		return this.cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BonoCliente getBonoCliente() {
		return this.bonoCliente;
	}

	public void setBonoCliente(BonoCliente bonoCliente) {
		this.bonoCliente = bonoCliente;
	}

	public DireccionPostal getDireccionPostal() {
		return direccionPostal;
	}

	public void setDireccionPostal(DireccionPostal direccionPostal) {
		this.direccionPostal = direccionPostal;
	}

	public Set<Compra> getCompras() {
		return this.compras;
	}

	public void setCompras(Set<Compra> compras) {
		this.compras = compras;
	}

	public Compra addCompra(Compra compra) {
		getCompras().add(compra);
		compra.setCliente(this);

		return compra;
	}

	public Compra removeCompra(Compra compra) {
		getCompras().remove(compra);
		compra.setCliente(null);

		return compra;
	}
	
	@Override
	public String toString() {
		return "Cliente [ cif= " + cif + ", descripcion= " + descripcion + " ]"; 
	}

}