package es.ubu.lsi.service.catering;

import java.util.Date;
import java.util.List;

import es.ubu.lsi.model.catering.Menu;
import es.ubu.lsi.service.PersistenceException;

/**
 * Transaction service.
 * 
 * 
 * @author <a href="mailto:pgdiaz@ubu.es">Pablo García</a>
 * @since 1.0
 *
 */
public interface Service {

	/**
	 * Alta de una nueva incidencia sobre un conductor.
	 * 
	 * @param fecha    fecha
	 * @param cidf     cif
	 * @param idMenu   identificador del menu
	 * @param personas númeo de personas
	 * @throws PersistenceException si se produce un error
	 * @see es.ubu.lsi.service.catering.IncidentError
	 */
	public void insertarCompra(Date fecha, String cif, long idMenu, long personas) throws PersistenceException;

	/**
	 * Asigna todas las compras de un cif a descuento 0 y devuelve el importe total
	 * que se le ha ajustado de mas
	 * 
	 * @param cif cif
	 * @throws PersistenceException si se produce un error
	 * @see es.ubu.lsi.service.catering.IncidentError
	 */
	public float quitarDescuento(String cif) throws PersistenceException;

	/**
	 * Consulta menús. En este caso en particular es importante recuperar toda la
	 * información vinculada a los menús
	 * 
	 * @return menus
	 * @throws PersistenceException si se produce un error
	 */
	public List<Menu> consultarMenu(long idMenu) throws PersistenceException;

}
