package es.ubu.lsi.model.catering;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the COMPRA database table.
 * 
 */
@Entity
@NamedQuery(name="Compra.findAll", query="SELECT c FROM Compra c")
public class Compra implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CompraPK id;

	private double importe;

	private BigDecimal personas;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="CIF")
	private Cliente cliente;

	//bi-directional many-to-one association to Menu
	@ManyToOne
	@JoinColumn(name="IDMENU")
	private Menu menu;

	public Compra() {
	}

	public CompraPK getId() {
		return this.id;
	}

	public void setId(CompraPK id) {
		this.id = id;
	}

	public double getImporte() {
		return this.importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public BigDecimal getPersonas() {
		return this.personas;
	}

	public void setPersonas(BigDecimal personas) {
		this.personas = personas;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Menu getMenu() {
		return this.menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}