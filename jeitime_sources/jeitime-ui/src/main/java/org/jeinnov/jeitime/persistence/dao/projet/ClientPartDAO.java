package org.jeinnov.jeitime.persistence.dao.projet;

import java.util.List;



import org.hibernate.Session;
import org.jeinnov.jeitime.persistence.bo.projet.ClientPartP;
import org.jeinnov.jeitime.persistence.bo.projet.ContratP;
import org.jeinnov.jeitime.persistence.dao.GenericDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import com.trg.search.Search;

public class ClientPartDAO extends GenericDAO<ClientPartP, Integer> {
	private static ClientPartDAO dao;

	public ClientPartDAO() {
	}

	static {
		dao = new ClientPartDAO();
		dao.setSessionFactory(HibernateUtil.getSessionFactory());
	}

	public static ClientPartDAO getInstance() {
		return dao;
	}

	public List<ClientPartP> getAll() {

		Search search = new Search();
		search.addSortAsc("nomClientPart");

		List<ClientPartP> listCPP = search(search);

		return listCPP;
	}

	@SuppressWarnings("unchecked")
	public boolean isInProject(int id, Session session) {

		boolean verif = false;
		List<ContratP> listContrat = session.getNamedQuery(
				"ContratP.getClientPart").setInteger("idCliPart", id).list();

		for (int i = 0; i < listContrat.size(); i++) {
			if (listContrat.get(i).getId().getIdClientPart() == 0) {
				verif = false;
			} else {
				verif = true;
			}
		}
		return verif;
	}
}
