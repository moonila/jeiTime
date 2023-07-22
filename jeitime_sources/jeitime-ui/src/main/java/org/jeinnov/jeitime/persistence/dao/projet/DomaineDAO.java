package org.jeinnov.jeitime.persistence.dao.projet;


import org.hibernate.Session;
import org.jeinnov.jeitime.persistence.bo.projet.DomaineP;
import org.jeinnov.jeitime.persistence.bo.projet.ProjetP;
import org.jeinnov.jeitime.persistence.dao.GenericDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import com.trg.search.Search;

import java.util.List;

public class DomaineDAO extends GenericDAO<DomaineP, Integer> {

	private static DomaineDAO dao = null;

	public DomaineDAO() {
	}

	static {
		dao = new DomaineDAO();
		dao.setSessionFactory(HibernateUtil.getSessionFactory());
	}

	public static DomaineDAO getInstance() {
		return dao;
	}

	public List<DomaineP> getAll() {

		Search search = new Search();
		search.addSortAsc("nomDomaine");

		List<DomaineP> listDomP = search(search);

		return listDomP;
	}

	public DomaineP getByName(String name) {
		return this.searchUnique(new Search()
				.addFilterEqual("nomDomaine", name));
	}

	@SuppressWarnings("unchecked")
	public boolean isInProject(int id, Session session) {

		List<ProjetP> list = null;
		boolean verif = false;

		list = session.getNamedQuery("ProjetP.getListProjetDom").setInteger(
				"iddomaine", id).list();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getIdProjet() == 0) {
				verif = false;
			} else {
				verif = true;
			}
		}
		return verif;
	}

}
