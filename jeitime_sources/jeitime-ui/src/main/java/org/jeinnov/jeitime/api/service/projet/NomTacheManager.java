package org.jeinnov.jeitime.api.service.projet;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.to.projet.NomTacheTO;
import org.jeinnov.jeitime.api.to.projet.TypeTacheTO;
import org.jeinnov.jeitime.persistence.bo.projet.NomTacheP;
import org.jeinnov.jeitime.persistence.bo.projet.TypeTacheP;
import org.jeinnov.jeitime.persistence.dao.projet.NomTacheDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


public class NomTacheManager {
	private final Logger logger = Logger.getLogger(this.getClass());
	private NomTacheDAO nomTacheDAO = NomTacheDAO.getInstance();

	private static NomTacheManager manager;

	public NomTacheManager() {

	}

	public static NomTacheManager getInstance() {
		if (manager == null) {
			manager = new NomTacheManager();
		}
		return manager;
	}

	public NomTacheTO get(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucun nom de tache n'est sélectionné");
		}
		NomTacheTO nomTTO = new NomTacheTO();
		NomTacheP nomTP = new NomTacheP();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			nomTP = nomTacheDAO.find(id);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		if (nomTP != null) {
			nomTTO.setIdNomTache(nomTP.getIdNomTache());
			nomTTO.setNomTache(nomTP.getNomTache());
			TypeTacheTO typeTacheTO = new TypeTacheTO();
			TypeTacheP typeTacheP = nomTP.getTypeTache();
			if (typeTacheP != null) {
				typeTacheTO.setIdTypTach(typeTacheP.getIdTypeTache());
			}
			nomTTO.setTypeTache(typeTacheTO);
		} else {
			throw new ProjetException(
					"Attention aucun nom de tache avec cet identifiant existe dans la base !");
		}
		return nomTTO;
	}

	public List<NomTacheTO> getAll() {
		List<NomTacheTO> listNomTTO = new ArrayList<NomTacheTO>();
		List<NomTacheP> listNomTP = new ArrayList<NomTacheP>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			listNomTP = nomTacheDAO.getAll();
			if (listNomTP != null) {
				for (NomTacheP nomTP : listNomTP) {
					NomTacheTO nomTTO = new NomTacheTO();
					nomTTO.setIdNomTache(nomTP.getIdNomTache());
					nomTTO.setNomTache(nomTP.getNomTache());
					TypeTacheTO typeTacheTO = new TypeTacheTO();
					TypeTacheP typeTacheP = nomTP.getTypeTache();
					if (typeTacheP != null) {
						typeTacheTO.setIdTypTach(typeTacheP.getIdTypeTache());
						typeTacheTO.setNomTypTach(typeTacheP.getNomTypeTache());
					}
					nomTTO.setTypeTache(typeTacheTO);
					listNomTTO.add(nomTTO);
				}
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}

		return listNomTTO;
	}

	public List<NomTacheTO> getAllByIdTypeTache(int idTypeTache)
			throws ProjetException {
		if (idTypeTache == 0) {
			throw new ProjetException(
					"Attention aucun groupe de tache n'est sélectionné");
		}
		List<NomTacheTO> listNomTTO = new ArrayList<NomTacheTO>();
		List<NomTacheP> listNomTP = new ArrayList<NomTacheP>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			listNomTP = nomTacheDAO.getAllByIdTypeTache(idTypeTache);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		if (listNomTP != null) {
			for (NomTacheP nomTP : listNomTP) {
				NomTacheTO nomTTO = new NomTacheTO();
				nomTTO.setIdNomTache(nomTP.getIdNomTache());
				nomTTO.setNomTache(nomTP.getNomTache());

				TypeTacheTO typeTacheTO = new TypeTacheTO();
				TypeTacheP typeTacheP = nomTP.getTypeTache();
				if (typeTacheP != null) {
					typeTacheTO.setIdTypTach(typeTacheP.getIdTypeTache());
				}

				nomTTO.setTypeTache(typeTacheTO);

				listNomTTO.add(nomTTO);
			}
		}
		return listNomTTO;
	}

	public int saveOrUpdate(NomTacheTO nomTTO) throws ProjetException {
		if (nomTTO == null) {
			throw new ProjetException("Attention aucune tâche n'est créé");
		}
		if (nomTTO.getNomTache() == null) {
			throw new ProjetException(
					"Attention aucun nom n'a été donné à la tâche");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		NomTacheP nomTP = new NomTacheP();
		nomTP = nomTacheDAO.getByName(nomTTO.getNomTache());
		verificationAvantEnregistrement(nomTTO, nomTP);

		if (nomTP == null || nomTP.getIdNomTache() == 0) {
			nomTP = new NomTacheP();
		}
		nomTP.setIdNomTache(nomTTO.getIdNomTache());
		nomTP.setNomTache(nomTTO.getNomTache());

		TypeTacheP typeTP = new TypeTacheP();
		TypeTacheTO typeTTO = nomTTO.getTypeTache();
		if (typeTTO != null) {
			typeTP.setIdTypeTache(typeTTO.getIdTypTach());
		}
		nomTP.setTypeTache(typeTP);
		try {
			nomTacheDAO.save(nomTP);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		int id = nomTP.getIdNomTache();
		return id;
	}

	public void delete(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucune tâche n'est sélectionné");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			NomTacheP nomTP = nomTacheDAO.find(id);
			if (nomTP == null || nomTP.getIdNomTache() == 0) {
				throw new ProjetException(
						"Attention aucun nom de tache avec cet identifiant existe dans la base !");
			}
			nomTacheDAO.remove(nomTP);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public boolean isInTache(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucun nom de tache n'est sélectionné");
		}
		boolean verif = true;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			verif = nomTacheDAO.isInTache(id, session);
			tx.commit();
		} catch (final RuntimeException e) {
			verif = true;
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return verif;
	}

	private void verificationAvantEnregistrement(NomTacheTO nomT, NomTacheP nomP)
			throws ProjetException {
		if (nomP != null) {
			if (nomP.getIdNomTache() != nomT.getIdNomTache()) {
				throw new ProjetException(
						"Attention, une tâche avec ce nom existe déjà !");
			}
		}
		if (nomT.getTypeTache() == null) {
			throw new ProjetException(
					"Attention aucun groupe de tâche n'est rattaché à cette tâche");
		}
	}
}
