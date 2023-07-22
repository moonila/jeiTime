package org.jeinnov.jeitime.persistence.dao.collaborateur;

import java.util.List;

import org.jeinnov.jeitime.persistence.bo.collaborateur.AppartientCollegeP;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollegeP;
import org.jeinnov.jeitime.persistence.dao.GenericDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import com.trg.search.Filter;
import com.trg.search.Search;


public class AppartientCollegeDAO extends
		GenericDAO<AppartientCollegeP, Integer> {

	private static AppartientCollegeDAO dao = null;

	public AppartientCollegeDAO() {
	}

	static {
		dao = new AppartientCollegeDAO();
		dao.setSessionFactory(HibernateUtil.getSessionFactory());
	}

	public static AppartientCollegeDAO getInstance() {
		return dao;
	}

	public AppartientCollegeP getByIdCollaborateur(int idC) {
		CollaborateurP collab = new CollaborateurP();
		collab.setIdColl(idC);

		Search search = new Search();
		search.addFilterAnd(Filter.equal("collaborateur", collab));

		AppartientCollegeP aCP = searchUnique(search);

		return aCP;

	}

	public List<AppartientCollegeP> getAllByIdCollege(int idCollege) {
		CollegeP college = new CollegeP();
		college.setIdCollege(idCollege);

		Search search = new Search();
		search.addFilterAnd(Filter.equal("college", college));

		List<AppartientCollegeP> listAC = search(search);

		return listAC;

	}

	public List<AppartientCollegeP> getAllByIdCollaborateur(int idCollaborateur) {
		CollaborateurP collab = new CollaborateurP();
		collab.setIdColl(idCollaborateur);

		Search search = new Search();
		search.addFilterAnd(Filter.equal("collaborateur", collab));

		List<AppartientCollegeP> listAC = search(search);

		return listAC;
	}
}
