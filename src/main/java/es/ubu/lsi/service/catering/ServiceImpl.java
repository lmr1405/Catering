package es.ubu.lsi.service.catering;

import java.util.Date;
import java.util.List;

import es.ubu.lsi.model.catering.Menu;
import es.ubu.lsi.service.PersistenceException;

public class ServiceImpl implements Service{

	@Override
	public void insertarCompra(Date fecha, String cif, long idMenu, long personas) throws PersistenceException {
		// TODO Auto-generated method stub
		
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
