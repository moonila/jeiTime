package org.jeinnov.jeitime.persistence.dao.projet;


import com.trg.search.Search;

import java.util.List;

import org.jeinnov.jeitime.persistence.bo.projet.TypeTacheP;
import org.jeinnov.jeitime.persistence.dao.GenericDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

public class TypeTacheDAO extends GenericDAO<TypeTacheP, Integer> {

	private static TypeTacheDAO dao;

	public TypeTacheDAO() {
	}

	static {
		dao = new TypeTacheDAO();
		dao.setSessionFactory(HibernateUtil.getSessionFactory());
	}

	public static TypeTacheDAO getInstance() {
		return dao;
	}

	public List<TypeTacheP> getAll() {

		Search search = new Search();
		search.addSortAsc("nomTypeTache");
		List<TypeTacheP> listTypeTP = search(search);

		return listTypeTP;
	}

	public TypeTacheP getByName(String name) {
		return this.searchUnique(new Search().addFilterEqual("nomTypeTache",
				name));
	}
}
