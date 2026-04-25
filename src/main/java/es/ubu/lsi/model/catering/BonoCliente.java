package es.ubu.lsi.model.catering;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * Entidad que representa la tabla BONOCLIENTE. Un bono define un tipo de
 * descuento que puede aplicarse a varios clientes.
 *
 * @author Luis Menendez Ramos
 */
@Entity
@NamedQuery(name = "BonoCliente.findAll", query = "SELECT b FROM BonoCliente b")
public class BonoCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idbonocliente;

	private String bono;

	private double descuento;

	/**
	 * Clientes asociados a este bono. Relación bidireccional uno a muchos (un bono
	 * puede tener varios clientes).
	 */
	@OneToMany(mappedBy = "bonoCliente")
	private Set<Cliente> clientes;

	public BonoCliente() {
		clientes = new HashSet<>();
	}

	public long getIdbonocliente() {
		return this.idbonocliente;
	}

	public void setIdbonocliente(long idbonocliente) {
		this.idbonocliente = idbonocliente;
	}

	public String getBono() {
		return this.bono;
	}

	public void setBono(String bono) {
		this.bono = bono;
	}

	public double getDescuento() {
		return this.descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public Set<Cliente> getClientes() {
		return this.clientes;
	}

	public void setClientes(Set<Cliente> clientes) {
		this.clientes = clientes;
	}

	/**
	 * Añade un cliente al bono y mantiene la relación bidireccional.
	 */
	public Cliente addCliente(Cliente cliente) {
		getClientes().add(cliente);
		cliente.setBonoCliente(this);

		return cliente;
	}

	/**
	 * Elimina un cliente del bono y mantiene la relación bidireccional.
	 */
	public Cliente removeCliente(Cliente cliente) {
		getClientes().remove(cliente);
		cliente.setBonoCliente(null);

		return cliente;
	}

	@Override
	public String toString() {
		return "BonoCliente [id=" + idbonocliente + ", bono=" + bono + ", descuento=" + descuento + "]";
	}

}