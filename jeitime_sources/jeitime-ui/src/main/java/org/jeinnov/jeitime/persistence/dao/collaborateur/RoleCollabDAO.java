package org.jeinnov.jeitime.persistence.dao.collaborateur;

import java.util.List;

import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.jeinnov.jeitime.persistence.bo.collaborateur.RolesCollab;
import org.jeinnov.jeitime.persistence.dao.GenericDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import com.trg.search.Filter;
import com.trg.search.Search;


public class RoleCollabDAO extends GenericDAO<RolesCollab, Integer> {
	private static RoleCollabDAO dao = null;

	public RoleCollabDAO() {
	}

	static {
		dao = new RoleCollabDAO();
		dao.setSessionFactory(HibernateUtil.getSessionFactory());
	}

	public static RoleCollabDAO getInstance() {
		return dao;
	}

	public List<RolesCollab> getByIdCollab(int idCollab) {
		CollaborateurP collabP = new CollaborateurP();
		collabP.setIdColl(idCollab);

		Search search = new Search();
		search.addFilterAnd(Filter.equal("collab", collabP));

		List<RolesCollab> listRolesCollab = search(search);

		return listRolesCollab;

	}

}
