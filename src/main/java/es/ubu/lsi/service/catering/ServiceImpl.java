package es.ubu.lsi.service.catering;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ubu.lsi.dao.catering.ClienteDAO;
import es.ubu.lsi.dao.catering.CompraDAO;
import es.ubu.lsi.dao.catering.MenuDAO;
import es.ubu.lsi.model.catering.BonoCliente;
import es.ubu.lsi.model.catering.Cliente;
import es.ubu.lsi.model.catering.Compra;
import es.ubu.lsi.model.catering.CompraPK;
import es.ubu.lsi.model.catering.Menu;
import es.ubu.lsi.service.PersistenceException;
import es.ubu.lsi.service.PersistenceService;

public class ServiceImpl extends PersistenceService implements Service {

	private static final Logger logger = LoggerFactory.getLogger(ServiceImpl.class);

	public ServiceImpl() {

	}

	/**
	 * Insertar una nueva compra en el sistema
	 * 
	 * Realiza validaciones básicas de los parametros de entrada antes de acceder a
	 * la base de datos.
	 * 
	 * @param fecha fecha de la compra
	 * @param cif identificador del cliente
	 * @param idMenu identificador del menu
	 * @param personas numero de personas asociada a la compra
	 * @throws PersistenceException si se produce un error en la persistencia
	 * @throws IncidentException si los datos de entrada no son valido
	 */
	@Override
	public void insertarCompra(Date fecha, String cif, long idMenu, long personas) throws PersistenceException {
		EntityManager em = null;
		try {
			// Crear el EntityManager para gestionar la sesion con la base de datos
			em = createSession();
			// Iniciar la transaccion
			beginTransaction(em);

			// validar que la fecha no sea nula
			if (fecha == null)
				throw new IncidentException(IncidentError.ERROR_IN_DATE);
			//Validar que el numero de personas sea mayor que cero
			if (personas <= 0 )
				throw new IncidentException(IncidentError.NEGATIVE_OR_ZERO_PEOPLE);
			
			// Creamos los DAO
			ClienteDAO clienteDao = new ClienteDAO(em);
			MenuDAO menuDao = new MenuDAO(em);
			CompraDAO compraDao = new CompraDAO(em);
			
			
			Cliente cliente = clienteDao.findById(cif);
			// Comprobamos que el cliente existe
			if (cliente == null) {
				throw new IncidentException(IncidentError.NOT_EXISTS_CLIENT);
			}
			
			Menu menu = menuDao.findById(idMenu);
			// Comprobamos que el menu existe
			if (menu == null) {
				throw new IncidentException(IncidentError.NOT_EXISTS_MENU);
			}
			
			// creamos las claves
			CompraPK compraPk = new CompraPK();
			compraPk.setFecha(fecha);
			compraPk.setCif(cif);
			
			Compra compraExistente = compraDao.findById(compraPk);
			// validamos que la compra no existe
			if (compraExistente != null) {
				throw new IncidentException(IncidentError.EXISTS_PURCHASE);
			}
			
			// calculamos el importe
			double precioMenu = menu.getPrecio();
			double importe = personas * precioMenu;
			
			// aplicamos descuento
			BonoCliente bonoCliente = cliente.getBonoCliente();
			double descuento = 0;
			if (bonoCliente != null) {
				descuento = bonoCliente.getDescuento();
			}
			double importeFinal = importe - (importe * (descuento / 100));
			// validamos el importeFinal
			if (importeFinal <=0) {
				throw new IncidentException(IncidentError.NEGATIVE_OR_ZERO_IMPORT);
			}
			
			// Creamos la compra
			Compra compra = new Compra();
			// Asignamos todos los datos a la compra
			compra.setId(compraPk);
			compra.setCliente(cliente);
			compra.setMenu(menu);
			compra.setPersonas(personas);
			compra.setImporte(importeFinal);
			
			// Guardamos en la base de datos
			compraDao.persist(compra);
			

			commitTransaction(em);

		} catch (Exception e) {
			rollbackTransaction(em);
			throw e;
		} finally {
			close(em);
		}

	}
	/**
	 * Elimina el descuento de un cliente y actualiza el importe de todas sus compras.
	 * 
	 * @param cif identificador del cliente
	 * @return importe total descontado al cliente
	 * @throws PersistenceException si se produce un error en la persistencia
	 * @throws IncidentException si el cliente no existe
	 */
	@Override
	public float quitarDescuento(String cif) throws PersistenceException {
		EntityManager em = null;
		double total = 0;
		try {
			em = createSession();
			beginTransaction(em);
			
			ClienteDAO clienteDao = new ClienteDAO(em);
			Cliente cliente = clienteDao.findById(cif);
			// validamos que el cliente existe
			if (cliente == null) {
				throw new IncidentException(IncidentError.NOT_EXISTS_CLIENT);
			}
			
			// obtenemos todas las compra del cliente
			Set<Compra> comprasSet = cliente.getCompras();
			for(Compra c : comprasSet) {
				// precio original sin descuento
				double precioOriginal = c.getPersonas() * c.getMenu().getPrecio();
				// precio actual
				double precioActual = c.getImporte();
				// diferencia entre el importe original y el actual
				double diferencia = precioOriginal - precioActual;
				// acumulamos la diferencia total
				total += diferencia;
				// actualizamos la compra
				c.setImporte(precioOriginal);
			}
			// eliminamos el descuento al cliente
			cliente.setBonoCliente(null);			
			
			commitTransaction(em);
			
		}catch(Exception e) {
			rollbackTransaction(em);
			throw e;
		}finally {
			close(em);
		}
		return (float) total;
	}

	/**
	 * Recupera los menús junto con toda la información asociada (compras y clientes)
	 * 
	 * @param idMenu identificador del menú
	 * @return lista de menús con sus compras y clientes asociados
	 * @throws PersistenceException si se produce un error en la persistencia
	 */
	@Override
	public List<Menu> consultarMenu(long idMenu) throws PersistenceException {
		EntityManager em = null;
		try {
			em = createSession();
			beginTransaction(em);
			commitTransaction(em);
		} catch(Exception e) {
			rollbackTransaction(em);
			throw e;
		} finally {
			close(em);
		}
		return null;
	}

}
