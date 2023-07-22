package org.jeinnov.jeitime.persistence.dao.collaborateur;

import org.jeinnov.jeitime.persistence.bo.collaborateur.Roles;
import org.jeinnov.jeitime.persistence.dao.GenericDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import com.trg.search.Search;


public class RolesDAO extends GenericDAO<Roles, Integer> {
	private static RolesDAO dao = null;

	public RolesDAO() {
	}

	static {
		dao = new RolesDAO();
		dao.setSessionFactory(HibernateUtil.getSessionFactory());
	}

	public static RolesDAO getInstance() {
		return dao;
	}

	public Roles getByName(String name) {
		return this.searchUnique(new Search().addFilterEqual("roles", name));

	}
}
