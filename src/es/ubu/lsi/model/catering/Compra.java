package es.ubu.lsi.model.catering;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

/**
 * Entidad que representa la tabla Compra.
 * 
 * Contiene la información sobre una compra realizada por un cliente para un
 * menú en une fecha concreta.
 * 
 * @author Luis Menendez Ramos
 */
@Entity
@NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c")
public class Compra implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Clave primaria compuesta (fecha y CIF del cliente).
	 */
	@EmbeddedId
	private CompraPK id;
	/**
	 * Importe total de la compra (calculado en el servicio).
	 */
	private double importe;
	/**
	 * Numero de personas asociada a la compra (tiene que ser > 0).
	 */
	private long personas;

	/**
	 * Cliente que realiza la compra. Relacion ManyToOne. El CIF está en la clave
	 * primaria.
	 */
	@ManyToOne
	@JoinColumn(name = "CIF", insertable = false, updatable = false)
	private Cliente cliente;

	/**
	 * Menu asociado a la compra.
	 */
	@ManyToOne
	@JoinColumn(name = "IDMENU")
	private Menu menu;

	public Compra() {
		id = new CompraPK();
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

	public long getPersonas() {
		return this.personas;
	}

	public void setPersonas(long personas) {
		this.personas = personas;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	/**
	 * Asigna el cliente a la compra y sincroniza el CIF en la clave primaria.
	 * 
	 * @param cliente Cliente que realiza la compra.
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		if (cliente != null) {
			this.id.setCif(cliente.getCif());
		}
	}

	public Menu getMenu() {
		return this.menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@Override
	public String toString() {
		return "Compra [id= " + id + " personas= " + personas + " importe= " + importe + " ]";
	}

}