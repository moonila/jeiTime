package org.jeinnov.jeitime.persistence.dao.affecter;

import java.util.List;

import org.jeinnov.jeitime.persistence.bo.affecter.AffecterP;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;
import org.jeinnov.jeitime.persistence.dao.GenericDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


import com.trg.search.Filter;
import com.trg.search.Search;

public class AffecterDAO extends GenericDAO<AffecterP, Integer> {

	private static AffecterDAO dao;

	public AffecterDAO() {
	}

	static {
		dao = new AffecterDAO();
		dao.setSessionFactory(HibernateUtil.getSessionFactory());
	}

	public static AffecterDAO getInstance() {
		return dao;
	}

	public List<AffecterP> getAllByCollabIdAndNotDissociate(int idColl) {

		CollaborateurP collab = new CollaborateurP();
		collab.setIdColl(idColl);

		Search search = new Search();
		search.addFilterAnd(Filter.equal("collaborateur", collab));
		search.addFilterEmpty("dateFin");

		List<AffecterP> listAffect = search(search);

		return listAffect;
	}

	public AffecterP getAllByIdCollaborateurAndIdTacheAndNotDissociate(
			int idColl, int idTache) {

		CollaborateurP collab = new CollaborateurP();
		collab.setIdColl(idColl);

		TacheP tache = new TacheP();
		tache.setIdTache(idTache);

		Search search = new Search();
		search.addFilterAnd(Filter.equal("collaborateur", collab));
		search.addFilterAnd(Filter.equal("tache", tache));
		search.addFilterEmpty("dateFin");

		AffecterP affect = searchUnique(search);

		return affect;

	}

	public List<AffecterP> getAllByIdTache(int idTache) {

		TacheP tache = new TacheP();
		tache.setIdTache(idTache);

		Search search = new Search();
		search.addFilterAnd(Filter.equal("tache", tache));
		search.addFilterEmpty("dateFin");

		List<AffecterP> listAffect = search(search);

		return listAffect;
	}

	public List<AffecterP> getAllByIdCollaborateur(int idCollaborateur) {

		CollaborateurP collab = new CollaborateurP();
		collab.setIdColl(idCollaborateur);

		Search search = new Search();
		search.addFilterAnd(Filter.equal("collaborateur", collab));

		List<AffecterP> listAffect = search(search);

		return listAffect;
	}

}
