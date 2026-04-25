package es.ubu.lsi.model.catering;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the BONOCLIENTE database table.
 * 
 */
@Entity
@NamedQuery(name="Bonocliente.findAll", query="SELECT b FROM Bonocliente b")
public class BonoCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idbonocliente;

	private String bono;

	private double descuento;

	//bi-directional many-to-one association to Cliente
	@OneToMany(mappedBy="bonocliente")
	private List<Cliente> clientes;

	public BonoCliente() {
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

	public List<Cliente> getClientes() {
		return this.clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente addCliente(Cliente cliente) {
		getClientes().add(cliente);
		cliente.setBonoCliente(this);

		return cliente;
	}

	public Cliente removeCliente(Cliente cliente) {
		getClientes().remove(cliente);
		cliente.setBonoCliente(null);

		return cliente;
	}

}