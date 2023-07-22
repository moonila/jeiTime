package org.jeinnov.jeitime.persistence.dao.collaborateur;

import java.util.List;



import org.hibernate.Session;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.jeinnov.jeitime.persistence.bo.collaborateur.EquipeP;
import org.jeinnov.jeitime.persistence.bo.projet.LienTachUtilP;
import org.jeinnov.jeitime.persistence.dao.GenericDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import com.trg.search.Filter;
import com.trg.search.Search;

public class CollaborateurDAO extends GenericDAO<CollaborateurP, Integer>

{

	private static CollaborateurDAO dao;

	public CollaborateurDAO() {

	}

	static {
		dao = new CollaborateurDAO();
		dao.setSessionFactory(HibernateUtil.getSessionFactory());
	}

	public static CollaborateurDAO getInstance() {
		return dao;
	}

	public CollaborateurP getByLogin(String login) {
		Search search = new Search();
		search.addFilterAnd(Filter.equal("login", login));

		// CollaborateurP collabP = searchUnique(search);

		return searchUnique(search);
	}

	public List<CollaborateurP> getAll() {
		Search search = new Search();
		search.addSortAsc("nomColl");

		List<CollaborateurP> listCollabP = search(search);

		return listCollabP;
	}

	public List<CollaborateurP> getAllByIdEquipe(int idEquipe) {
		EquipeP eq = new EquipeP();
		eq.setIdEquip(idEquipe);

		Search search = new Search();
		search.addFilterAnd(Filter.equal("equipe", eq));
		search.addSortAsc("nomColl");

		List<CollaborateurP> listCollabP = search(search);

		return listCollabP;

	}

	@SuppressWarnings("unchecked")
	public boolean isInLienTachUtil(int id, Session session) {

		List<LienTachUtilP> list = null;
		boolean verif = false;

		list = session.getNamedQuery("LienTachUtilP.getCollab").setInteger(
				"idcoll", id).list();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getTache().getIdTache() == 0) {
				verif = false;
			} else {
				verif = true;
			}
		}
		return verif;
	}
}