package org.jeinnov.jeitime.persistence.dao.projet;


import org.hibernate.Session;
import org.jeinnov.jeitime.persistence.bo.projet.ProjetP;
import org.jeinnov.jeitime.persistence.bo.projet.TypeProjetP;
import org.jeinnov.jeitime.persistence.dao.GenericDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import com.trg.search.Search;

import java.util.List;

public class TypeProjetDAO extends GenericDAO<TypeProjetP, Integer> {
	private static TypeProjetDAO dao;

	public TypeProjetDAO() {
	}

	static {
		dao = new TypeProjetDAO();
		dao.setSessionFactory(HibernateUtil.getSessionFactory());
	}

	public static TypeProjetDAO getInstance() {
		return dao;
	}

	public List<TypeProjetP> getAll() {

		Search search = new Search();
		search.addSortAsc("nomTypePro");

		List<TypeProjetP> listTPP = search(search);

		return listTPP;
	}

	public TypeProjetP getByName(String name) {
		return this.searchUnique(new Search()
				.addFilterEqual("nomTypePro", name));
	}

	@SuppressWarnings("unchecked")
	public boolean isInProject(int id, Session session) {
		List<ProjetP> listTypProj = null;
		boolean verif = false;

		listTypProj = session.getNamedQuery("ProjetP.getListProjetTypeP")
				.setInteger("idTypeP", id).list();

		for (int i = 0; i < listTypProj.size(); i++) {
			if (listTypProj.get(i).getIdProjet() == 0) {
				verif = false;
			} else {
				verif = true;
			}
		}
		return verif;
	}
}
