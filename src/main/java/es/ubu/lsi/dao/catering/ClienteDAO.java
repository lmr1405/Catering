package es.ubu.lsi.dao.catering;

import java.util.List;

import javax.persistence.EntityManager;

import es.ubu.lsi.dao.JpaDAO;
import es.ubu.lsi.model.catering.Cliente;

public class ClienteDAO extends JpaDAO<Cliente, String> {

	public ClienteDAO(EntityManager em) {
		super(em);
	}

	@Override
	public List<Cliente> findAll() {
		return getEntityManager().createNamedQuery("Cliente.findAll", Cliente.class).getResultList();
	}

}
