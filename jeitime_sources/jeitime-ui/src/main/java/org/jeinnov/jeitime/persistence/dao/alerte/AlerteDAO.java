package org.jeinnov.jeitime.persistence.dao.alerte;

import java.sql.Timestamp;
import java.util.List;

import org.jeinnov.jeitime.persistence.bo.projet.LienTachUtilP;
import org.jeinnov.jeitime.persistence.dao.GenericDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


import com.trg.search.Search;

public class AlerteDAO extends GenericDAO<LienTachUtilP, Integer> {

	private static AlerteDAO dao;

	public AlerteDAO() {

	}

	static {
		dao = new AlerteDAO();
		dao.setSessionFactory(HibernateUtil.getSessionFactory());
	}

	public static AlerteDAO getInstance() {
		return dao;
	}

	public List<LienTachUtilP> getAllByStartDateAndEndDate(Timestamp start,
			Timestamp end) {

		Search search = new Search();
		search.addFilterGreaterOrEqual("id.date", start);
		search.addFilterLessOrEqual("id.date", end);

		List<LienTachUtilP> listLienTachUtil = search(search);

		return listLienTachUtil;
	}

}
