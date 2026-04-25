package es.ubu.lsi.dao.catering;

import java.util.List;

import javax.persistence.EntityManager;

import es.ubu.lsi.dao.JpaDAO;
import es.ubu.lsi.model.catering.Compra;
import es.ubu.lsi.model.catering.CompraPK;

public class CompraDAO extends JpaDAO<Compra ,CompraPK>{

	public CompraDAO(EntityManager em) {
		super(em);
	}

	@Override
	public List<Compra> findAll() {
		return getEntityManager().createNamedQuery("Compra.findAll", Compra.class).getResultList();
	}

}
