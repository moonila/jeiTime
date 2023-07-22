package org.jeinnov.jeitime.persistence.dao.collaborateur;

import java.util.List;

import org.hibernate.Session;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.jeinnov.jeitime.persistence.bo.collaborateur.EquipeP;
import org.jeinnov.jeitime.persistence.dao.GenericDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


import com.trg.search.Search;

public class EquipeDAO extends GenericDAO<EquipeP, Integer> {
	private static EquipeDAO dao = null;

	public EquipeDAO() {
	}

	static {
		dao = new EquipeDAO();
		dao.setSessionFactory(HibernateUtil.getSessionFactory());
	}

	public static EquipeDAO getInstance() {
		return dao;
	}

	public List<EquipeP> getAll() {

		Search search = new Search();
		search.addSortAsc("nomEquip");

		List<EquipeP> listEq = search(search);

		return listEq;
	}

	public EquipeP getByName(String name) {
		return this.searchUnique(new Search().addFilterEqual("nomEquip", name));
	}

	@SuppressWarnings("unchecked")
	public boolean isInCollaborateur(int id, Session session) {

		List<CollaborateurP> list = null;
		boolean verif = false;

		list = session
				.getNamedQuery("CollaborateurP.getCollaborateur.getEquip")
				.setInteger("idequip", id).list();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getIdColl() == 0) {
				verif = false;
			} else {
				verif = true;
			}
		}
		return verif;
	}

}
