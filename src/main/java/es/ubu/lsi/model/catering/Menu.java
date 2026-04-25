package es.ubu.lsi.model.catering;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the MENU database table.
 * 
 */
@Entity
@NamedQuery(name="Menu.findAll", query="SELECT m FROM Menu m")
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idmenu;

	private String descripcion;

	private double precio;

	//bi-directional many-to-one association to Compra
	@OneToMany(mappedBy="menu")
	private List<Compra> compras;

	public Menu() {
	}

	public long getIdmenu() {
		return this.idmenu;
	}

	public void setIdmenu(long idmenu) {
		this.idmenu = idmenu;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return this.precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public List<Compra> getCompras() {
		return this.compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	public Compra addCompra(Compra compra) {
		getCompras().add(compra);
		compra.setMenu(this);

		return compra;
	}

	public Compra removeCompra(Compra compra) {
		getCompras().remove(compra);
		compra.setMenu(null);

		return compra;
	}

}