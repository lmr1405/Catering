package es.ubu.lsi.dao.catering;

import java.util.List;

import javax.persistence.EntityManager;

import es.ubu.lsi.dao.JpaDAO;
import es.ubu.lsi.model.catering.BonoCliente;

public class BonoClienteDAO extends JpaDAO<BonoCliente,Long>{
	
	public BonoClienteDAO(EntityManager em) {
		super(em);
	}

	@Override
	public List<BonoCliente> findAll() {
		return getEntityManager().createNamedQuery("BonoCliente.findAll", BonoCliente.class).getResultList();
	}

}
