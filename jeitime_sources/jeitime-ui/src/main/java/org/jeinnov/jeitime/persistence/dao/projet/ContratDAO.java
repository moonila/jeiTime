package org.jeinnov.jeitime.persistence.dao.projet;

import java.util.List;

import org.jeinnov.jeitime.persistence.bo.projet.ContratIdP;
import org.jeinnov.jeitime.persistence.bo.projet.ContratP;
import org.jeinnov.jeitime.persistence.bo.projet.ProjetP;
import org.jeinnov.jeitime.persistence.dao.GenericDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import com.trg.search.Filter;
import com.trg.search.Search;


public class ContratDAO extends GenericDAO<ContratP, Integer> {
	private static ContratDAO dao;

	public ContratDAO() {
	}

	static {
		dao = new ContratDAO();
		dao.setSessionFactory(HibernateUtil.getSessionFactory());
	}

	public static ContratDAO getInstance() {
		return dao;
	}

	public List<ContratP> getContratByProjectId(int idP) {
		ProjetP p = new ProjetP();
		p.setIdProjet(idP);
		Search search = new Search();
		search.addFilterAnd(Filter.equal("projet", p));

		List<ContratP> listContrat = search(search);

		return listContrat;
	}

	public ContratP getContratByProjectIdAndCliPartId(int idP, int idCP) {
		ContratIdP contratIdP = new ContratIdP();
		contratIdP.setIdClientPart(idCP);
		contratIdP.setIdProjet(idP);

		Search search = new Search();
		search.addFilterAnd(Filter.equal("id", contratIdP));

		ContratP contrat = searchUnique(search);

		return contrat;
	}

}
