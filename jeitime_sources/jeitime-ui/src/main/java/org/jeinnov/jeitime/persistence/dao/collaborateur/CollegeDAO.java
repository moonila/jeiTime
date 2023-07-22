package org.jeinnov.jeitime.persistence.dao.collaborateur;

import java.util.List;

import org.jeinnov.jeitime.persistence.bo.collaborateur.CollegeP;
import org.jeinnov.jeitime.persistence.dao.GenericDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


import com.trg.search.Search;

public class CollegeDAO extends GenericDAO<CollegeP, Integer> {
	private static CollegeDAO dao;

	public CollegeDAO() {
	}

	static {
		dao = new CollegeDAO();
		dao.setSessionFactory(HibernateUtil.getSessionFactory());
	}

	public static CollegeDAO getInstance() {
		return dao;
	}

	public List<CollegeP> getAll() {

		Search search = new Search();
		search.addSortAsc("nomCollege");

		List<CollegeP> listCollege = search(search);

		return listCollege;
	}

	public CollegeP getByName(String name) {
		return this.searchUnique(new Search()
				.addFilterEqual("nomCollege", name));

	}

}
