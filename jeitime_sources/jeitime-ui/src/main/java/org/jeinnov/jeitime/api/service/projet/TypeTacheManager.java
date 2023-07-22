package org.jeinnov.jeitime.api.service.projet;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.to.projet.NomTacheTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.jeinnov.jeitime.api.to.projet.TypeTacheTO;
import org.jeinnov.jeitime.persistence.bo.projet.TypeTacheP;
import org.jeinnov.jeitime.persistence.dao.projet.TypeTacheDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


public class TypeTacheManager {
	private final Logger logger = Logger.getLogger(this.getClass());
	private TypeTacheDAO typeTacheDAO = TypeTacheDAO.getInstance();

	private static TypeTacheManager manager;

	public TypeTacheManager() {

	}

	public static TypeTacheManager getInstance() {
		if (manager == null) {
			manager = new TypeTacheManager();
		}
		return manager;
	}

	public TypeTacheTO get(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucun Type de Tâche n'est sélectionné.");
		}
		TypeTacheTO typeTTO = new TypeTacheTO();
		TypeTacheP typeTP = new TypeTacheP();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			typeTP = typeTacheDAO.find(id);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		if (typeTP != null) {
			typeTTO.setIdTypTach(typeTP.getIdTypeTache());
			typeTTO.setNomTypTach(typeTP.getNomTypeTache());
		} else {
			throw new ProjetException(
					"Attention aucun Type de Tâche existe avec cet identifiant dans la base.");
		}
		return typeTTO;
	}

	public List<TypeTacheTO> getAll() {
		List<TypeTacheTO> listTypeTTO = new ArrayList<TypeTacheTO>();
		List<TypeTacheP> listTypeTP = new ArrayList<TypeTacheP>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			listTypeTP = typeTacheDAO.getAll();
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		if (listTypeTP != null) {
			for (TypeTacheP typeTP : listTypeTP) {
				TypeTacheTO typeTTO = new TypeTacheTO();
				typeTTO.setIdTypTach(typeTP.getIdTypeTache());
				typeTTO.setNomTypTach(typeTP.getNomTypeTache());
				listTypeTTO.add(typeTTO);
			}
		}
		return listTypeTTO;
	}

	public List<TypeTacheTO> getAllTypeTacheInProject(List<TacheTO> alltaches) {
		List<TypeTacheTO> listTypeTTO = new ArrayList<TypeTacheTO>();
		for (TacheTO t : alltaches) {
			TypeTacheTO tTTO = new TypeTacheTO();
			tTTO.setIdTypTach(t.getNomtache().getTypeTache().getIdTypTach());
			tTTO.setNomTypTach(t.getNomtache().getTypeTache().getNomTypTach());
			listTypeTTO.add(tTTO);
		}

		return listTypeTTO;
	}

	public int saveOrUpdate(TypeTacheTO typeTTO) throws ProjetException {
		if (typeTTO == null) {
			throw new ProjetException(
					"Attention aucun type de Tâche n'est sélectionné");
		}
		if (typeTTO.getNomTypTach() == null) {
			throw new ProjetException(
					"Attention aucun nom n'a été donné au type de Tâche ");
		}
		TypeTacheP typeTP = new TypeTacheP();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			typeTP = typeTacheDAO.getByName(typeTTO.getNomTypTach());
//			System.out.println("j'affiche l'id du type de tache : " +typeTTO.getIdTypTach()
//					+" j'affiche l'autre id dy type de tache : " +typeTP.getIdTypeTache());
			verificationAvantEnregistrement(typeTTO, typeTP);
			if (typeTP == null || typeTP.getIdTypeTache() == 0) {
				typeTP = new TypeTacheP();
			}
			typeTP.setIdTypeTache(typeTTO.getIdTypTach());
			typeTP.setNomTypeTache(typeTTO.getNomTypTach());
			typeTacheDAO.save(typeTP);
			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		int id = typeTP.getIdTypeTache();
		return id;
	}

	public void delete(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucun type de Tâche n'est sélectionné");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			TypeTacheP typeTP = typeTacheDAO.find(id);
			typeTacheDAO.remove(typeTP);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}

	}

	public boolean isInNomTache(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucun Type de Tâche n'est sélectionné.");
		}
		boolean verif = false;
		List<NomTacheTO> list = new ArrayList<NomTacheTO>();
		try {
			list = NomTacheManager.getInstance().getAllByIdTypeTache(id);
			if (list == null || list.isEmpty()) {
				verif = false;
			} else {
				for (NomTacheTO nomT : list) {
					verif = NomTacheManager.getInstance().isInTache(
							nomT.getIdNomTache());
				}
			}
		} catch (final RuntimeException e) {
			verif = true;
			throw e;
		}
		return verif;
	}

	private void verificationAvantEnregistrement(TypeTacheTO typePT,
			TypeTacheP typePP) throws ProjetException {
		if (typePP != null) {
			if (typePP.getIdTypeTache() != typePT.getIdTypTach()) {
//				System.out.println("j'affiche l'id du type de tache : " +typePP.getIdTypeTache()
//						+" j'affiche l'autre id dy type de tache : " +typePT.getIdTypTach());
				throw new ProjetException(
						"Attention,un type de Tâche avec ce nom existe déjà !");
			}
		}
	}
}
