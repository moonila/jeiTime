package org.jeinnov.jeitime.persistence.dao.projet;

import java.util.List;



import org.hibernate.Session;
import org.jeinnov.jeitime.persistence.bo.projet.LienTachUtilP;
import org.jeinnov.jeitime.persistence.bo.projet.ProjetP;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;
import org.jeinnov.jeitime.persistence.dao.GenericDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import com.trg.search.Filter;
import com.trg.search.Search;

public class ProjetDAO extends GenericDAO<ProjetP, Integer> {

	private static ProjetDAO dao;

	public ProjetDAO() {
	}

	static {
		dao = new ProjetDAO();
		dao.setSessionFactory(HibernateUtil.getSessionFactory());
	}

	public static ProjetDAO getInstance() {
		return dao;
	}

	public ProjetP getProjectNotLock(int id) {
		Search search = new Search();
		search.addFilterAnd(Filter.equal("idProjet", id));
		search.addFilterEmpty("dateCloture");

		ProjetP projet = searchUnique(search);

		return projet;
	}

	public ProjetP getProjectNotLockNotClose(int id) {
		Search search = new Search();
		search.addFilterAnd(Filter.equal("idProjet", id));
		search.addFilterEmpty("dateCloture");
		search.addFilterEmpty("dateFermeture");

		ProjetP projet = searchUnique(search);

		return projet;
	}

	public List<ProjetP> getAll() {

		Search search = new Search();
		search.addSortAsc("nomProjet");

		List<ProjetP> listProjetP = search(search);

		return listProjetP;
	}

	public List<ProjetP> getAllNotLock() {
		Search search = new Search();
		search.addFilterEmpty("dateCloture");
		search.addSortAsc("nomProjet");

		List<ProjetP> listProjetP = search(search);

		return listProjetP;
	}

	public List<ProjetP> getAllLock() {
		Search search = new Search();
		search.addFilterNotEmpty("dateCloture");
		search.addSortAsc("nomProjet");

		List<ProjetP> listProjetP = search(search);

		return listProjetP;
	}

	public List<ProjetP> getAllNotClose() {
		Search search = new Search();
		search.addFilterEmpty("dateCloture");
		search.addFilterEmpty("dateFermeture");
		search.addSortAsc("nomProjet");

		List<ProjetP> listProjetP = search(search);

		return listProjetP;
	}

	public List<ProjetP> getAllClose() {
		Search search = new Search();
		search.addFilterEmpty("dateCloture");
		search.addFilterNotEmpty("dateFermeture");
		search.addSortAsc("nomProjet");

		List<ProjetP> listProjetP = search(search);

		return listProjetP;
	}

	public ProjetP getByName(String name) {
		return this
				.searchUnique(new Search().addFilterEqual("nomProjet", name));
	}
	
	public  List<ProjetP> getBySousProjet(ProjetP projet) {
		List<ProjetP> listProjetP = search(new Search().addFilterEqual("projet", projet));
		return listProjetP;
	}

	@SuppressWarnings("unchecked")
	public boolean verifLien(int id, Session session) {

		List<TacheP> listT = session.getNamedQuery("TacheP.getTache")
				.setInteger("idProjet", id).list();
		boolean verif = false;

		for (int i = 0; i < listT.size(); i++) {
			int idTache = listT.get(i).getIdTache();
			List<LienTachUtilP> list = null;

			list = session.getNamedQuery("LienTachUtilP.getTache").setInteger(
					"idTache", idTache).list();
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j).getCollaborateur().getIdColl() == 0) {
					verif = false;
				} else {
					verif = true;
				}
			}
		}

		return verif;
	}

}
