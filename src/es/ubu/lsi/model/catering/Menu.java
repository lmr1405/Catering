package es.ubu.lsi.model.catering;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * Entidad que representa la tabla MENU. Define los menús disponibles en el
 * catering junto con su precio.
 *
 * @author Luis Menendez Ramos
 */
@Entity
@NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m")
public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private long idmenu;

	private String descripcion;

	private double precio;

	/**
	 * Compras asociadas a este menú. Relación uno a muchos.
	 */
	@OneToMany(mappedBy = "menu")
	private Set<Compra> compras;

	public Menu() {
		compras = new HashSet<>();
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

	public Set<Compra> getCompras() {
		return this.compras;
	}

	public void setCompras(Set<Compra> compras) {
		this.compras = compras;
	}

	public Compra addCompra(Compra compra) {
		getCompras().add(compra);
		compra.setMenu(this);

		return compra;
	}

	/**
	 * Añade una compra al menú y mantiene la relación bidireccional. También asigna
	 * este menú a la compra.
	 *
	 * @param compra Compra a añadir
	 * @return la compra añadida
	 */
	public Compra removeCompra(Compra compra) {
		getCompras().remove(compra);
		compra.setMenu(null);

		return compra;
	}

	/**
	 * Elimina una compra del menú y mantiene la relación bidireccional. También
	 * elimina la referencia al menú en la compra.
	 *
	 * @param compra Compra a eliminar
	 * @return la compra eliminada
	 */
	@Override
	public String toString() {
		return "Menu [id=" + idmenu + ", descripcion=" + descripcion + ", precio=" + precio + "]";
	}

}