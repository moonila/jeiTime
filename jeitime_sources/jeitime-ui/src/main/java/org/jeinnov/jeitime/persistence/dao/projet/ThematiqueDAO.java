package org.jeinnov.jeitime.persistence.dao.projet;


import org.hibernate.Session;
import org.jeinnov.jeitime.persistence.bo.projet.ProjetP;
import org.jeinnov.jeitime.persistence.bo.projet.ThematiqueP;
import org.jeinnov.jeitime.persistence.dao.GenericDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import com.trg.search.Search;

import java.util.List;

public class ThematiqueDAO extends GenericDAO<ThematiqueP, Integer> {
	private static ThematiqueDAO dao;

	public ThematiqueDAO() {
	}

	static {
		dao = new ThematiqueDAO();
		dao.setSessionFactory(HibernateUtil.getSessionFactory());
	}

	public static ThematiqueDAO getInstance() {
		return dao;
	}

	public List<ThematiqueP> getAll() {

		Search search = new Search();
		search.addSortAsc("nomThematique");

		List<ThematiqueP> listAllThema = search(search);

		return listAllThema;
	}

	@SuppressWarnings("unchecked")
	public boolean isInProject(int id, Session session) {

		List<ProjetP> list = null;
		boolean verif = false;

		list = session.getNamedQuery("ProjetP.getListProjetThema").setInteger(
				"idthematique", id).list();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getIdProjet() == 0) {
				verif = false;
			} else {
				verif = true;
			}
		}
		return verif;
	}

	public ThematiqueP getByName(String name) {
		return this.searchUnique(new Search().addFilterEqual("nomThematique",
				name));
	}

}
