package org.jeinnov.jeitime.persistence.dao.projet;


import org.hibernate.Session;
import org.jeinnov.jeitime.persistence.bo.projet.LienTachUtilP;
import org.jeinnov.jeitime.persistence.bo.projet.NomTacheP;
import org.jeinnov.jeitime.persistence.bo.projet.ProjetP;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;
import org.jeinnov.jeitime.persistence.dao.GenericDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import com.trg.search.Filter;
import com.trg.search.Search;

import java.util.List;

public class TacheDAO extends GenericDAO<TacheP, Integer> {
	private static TacheDAO dao;

	public TacheDAO() {
	}

	static {
		dao = new TacheDAO();
		dao.setSessionFactory(HibernateUtil.getSessionFactory());
	}

	public static TacheDAO getInstance() {
		return dao;
	}

	public List<TacheP> getAllTacheInProject(int idProjet) {

		ProjetP projet = new ProjetP();
		projet.setIdProjet(idProjet);

		Search search = new Search();
		search.addFilterAnd(Filter.equal("projet", projet));
		// search.addSortAsc("nomTacheP.nomTache");

		List<TacheP> listTacheP = search(search);

		return listTacheP;
	}

	public TacheP getByProjetAndNomTache(ProjetP idProjet, NomTacheP idNomTache) {
		return this.searchUnique(new Search()
				.addFilterEqual("projet", idProjet).addFilterEqual("nomTacheP",
						idNomTache));
	}

	@SuppressWarnings("unchecked")
	public boolean isInLienTachUtil(int id, Session session) {
		boolean verif = false;

		List<LienTachUtilP> list = session.getNamedQuery(
				"LienTachUtilP.getTache").setInteger("idTache", id).list();
		for (int j = 0; j < list.size(); j++) {
			if (list.get(j).getCollaborateur().getIdColl() == 0) {
				verif = false;
			} else {
				verif = true;
			}
		}

		return verif;
	}
}
