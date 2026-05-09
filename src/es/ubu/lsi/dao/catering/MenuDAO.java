package es.ubu.lsi.dao.catering;

import java.util.List;

import javax.persistence.EntityManager;

import es.ubu.lsi.dao.JpaDAO;
import es.ubu.lsi.model.catering.Menu;

public class MenuDAO extends JpaDAO<Menu, Long> {

	public MenuDAO(EntityManager em) {
		super(em);
	}

	@Override
	public List<Menu> findAll() {
		return getEntityManager().createNamedQuery("Menu.findAll", Menu.class).getResultList();
	}

}
