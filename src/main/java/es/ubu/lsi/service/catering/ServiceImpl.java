package es.ubu.lsi.service.catering;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ubu.lsi.dao.catering.ClienteDAO;
import es.ubu.lsi.dao.catering.CompraDAO;
import es.ubu.lsi.dao.catering.MenuDAO;
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
			
			// Cramos los DAO
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
			
			
			
			

			commitTransaction(em);

		} catch (Exception e) {
			rollbackTransaction(em);
			throw e;
		} finally {
			close(em);
		}

	}

	@Override
	public float quitarDescuento(String cif) throws PersistenceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Menu> consultarMenu(long idMenu) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

}
