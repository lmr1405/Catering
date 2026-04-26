package es.ubu.lsi.service.catering;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ubu.lsi.model.catering.Menu;
import es.ubu.lsi.service.PersistenceException;
import es.ubu.lsi.service.PersistenceService;

public class ServiceImpl extends PersistenceService implements Service{
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceImpl.class);
	
	public ServiceImpl() {
		
	}

	@Override
	public void insertarCompra(Date fecha, String cif, long idMenu, long personas) throws PersistenceException {
		EntityManager em = null;
		try {
			em = createSession();
			beginTransaction(em);
			// cuerpo
			commitTransaction(em);
			
		}catch(Exception e) {
			rollbackTransaction(em);
			throw e;
		}finally {
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
