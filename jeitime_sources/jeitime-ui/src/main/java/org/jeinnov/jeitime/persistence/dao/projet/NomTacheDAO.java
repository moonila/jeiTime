package org.jeinnov.jeitime.persistence.dao.projet;


import org.hibernate.Session;
import org.jeinnov.jeitime.persistence.bo.projet.NomTacheP;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;
import org.jeinnov.jeitime.persistence.bo.projet.TypeTacheP;
import org.jeinnov.jeitime.persistence.dao.GenericDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import com.trg.search.Filter;
import com.trg.search.Search;

import java.util.List;

public class NomTacheDAO extends GenericDAO<NomTacheP, Integer> {

	private static NomTacheDAO dao;

	public NomTacheDAO() {
	}

	static {
		dao = new NomTacheDAO();
		dao.setSessionFactory(HibernateUtil.getSessionFactory());
	}

	public static NomTacheDAO getInstance() {
		return dao;
	}

	public List<NomTacheP> getAll() {

		Search search = new Search();
		search.addSortAsc("nomTache");

		List<NomTacheP> listNomTP = search(search);

		return listNomTP;
	}

	public List<NomTacheP> getAllByIdTypeTache(int idTypeTache) {

		TypeTacheP typeTP = new TypeTacheP();
		typeTP.setIdTypeTache(idTypeTache);

		Search search = new Search();
		search.addFilterAnd(Filter.equal("typeTache", typeTP));
		search.addSortAsc("nomTache");

		List<NomTacheP> listNomTP = search(search);

		return listNomTP;
	}

	@SuppressWarnings("unchecked")
	public boolean isInTache(int id, Session session) {

		List<TacheP> list = null;
		boolean verif = false;

		list = session.getNamedQuery("TacheP.getNomTache").setInteger(
				"idNomTache", id).list();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getIdTache() == 0) {
				verif = false;
			} else {
				verif = true;
			}
		}
		return verif;
	}

	public NomTacheP getByName(String name) {
		return this.searchUnique(new Search().addFilterEqual("nomTache", name));
	}
}
