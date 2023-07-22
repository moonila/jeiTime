package org.jeinnov.jeitime.api.service.projet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.jeinnov.jeitime.persistence.bo.affecter.AffecterP;
import org.jeinnov.jeitime.persistence.bo.projet.NomTacheP;
import org.jeinnov.jeitime.persistence.bo.projet.ProjetP;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;
import org.jeinnov.jeitime.persistence.dao.affecter.AffecterDAO;
import org.jeinnov.jeitime.persistence.dao.projet.TacheDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


public class TacheManager {
	private final Logger logger = Logger.getLogger(this.getClass());
	private TacheDAO tacheDAO = TacheDAO.getInstance();
	private ResultTransformersProject resultTransformer = new ResultTransformersProject();

	private static TacheManager manager;

	public TacheManager() {

	}

	public static TacheManager getInstance() {
		if (manager == null) {
			manager = new TacheManager();
		}
		return manager;
	}

	public TacheTO get(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucune tâche n'est sélectionné");
		}
		TacheTO tacheTO = new TacheTO();
		TacheP tacheP = new TacheP();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			tacheP = tacheDAO.find(id);
			if (tacheP != null) {
				tacheTO = resultTransformer.toTacheTO(tacheP);
			} else {
				throw new ProjetException(
						"Attention aucune tâche avec cet identifiant existe dans la base !");
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return tacheTO;
	}

	public List<TacheTO> getAllTacheInProject(int idProjet)
			throws ProjetException {
		if (idProjet == 0) {
			throw new ProjetException(
					"Attention aucun projet n'est sélectionné");
		}
		List<TacheTO> listTacheTO = new ArrayList<TacheTO>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<TacheP> listTacheP = tacheDAO.getAllTacheInProject(idProjet);

			if (listTacheP != null) {
				for (TacheP tacheP : listTacheP) {
					TacheTO tacheTO = resultTransformer.toTacheTO(tacheP);
					listTacheTO.add(tacheTO);
				}
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}

		Collections.sort(listTacheTO);
		return listTacheTO;

	}

	public List<TacheTO> getAllInProjectAndForACollaborateur(int idProjet,
			int idCollaborateur) throws ProjetException {
		if (idProjet == 0) {
			throw new ProjetException(
					"Attention aucun projet n'est sélectionné");
		}
		if (idCollaborateur == 0) {
			throw new ProjetException(
					"Attention aucun collaborateur n'est sélectionné");
		}
		List<TacheTO> listTacheTO = new ArrayList<TacheTO>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<TacheP> listTacheP = tacheDAO.getAllTacheInProject(idProjet);

			if (listTacheP != null) {
				for (TacheP tacheP : listTacheP) {
					AffecterP affecter = AffecterDAO.getInstance()
							.getAllByIdCollaborateurAndIdTacheAndNotDissociate(
									idCollaborateur, tacheP.getIdTache());
					if (affecter != null) {
						TacheTO tacheTO = resultTransformer.toTacheTO(tacheP);
						listTacheTO.add(tacheTO);
					}
				}
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		Collections.sort(listTacheTO);

		return listTacheTO;
	}

	public int saveOrUpdate(TacheTO tacheTO) throws ProjetException {
		TacheP tacheP = new TacheP();
		if (tacheTO == null) {
			throw new ProjetException("Attention aucune tâche n'a été créée");
		}
		if (tacheTO.getProjet() == null) {
			throw new ProjetException("Vous devez spécifier un projet");
		}
		if (tacheTO.getNomtache() == null) {
			throw new ProjetException("Vous devez spécifier une tâche");
		}

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		ProjetP projetP = new ProjetP(tacheTO.getProjet().getIdProjet());
		NomTacheP nomTacheP = new NomTacheP();
		nomTacheP.setIdNomTache(tacheTO.getNomtache().getIdNomTache());
		try {
			tacheP = tacheDAO.getByProjetAndNomTache(projetP, nomTacheP);

			verificationAvantEnregistrement(tacheTO, tacheP);

			if (tacheP == null || tacheP.getIdTache() == 0) {
				tacheP = new TacheP();
			}
			resultTransformer.toTacheP(tacheTO, tacheP);
			tacheDAO.save(tacheP);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return tacheP.getIdTache();
	}

	/*
	 * public void update(TacheTO tacheTO) { save(tacheTO); }
	 */
	public void delete(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucune tâche n'est sélectionnée");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			TacheP tacheP = tacheDAO.find(id);

			if (tacheP == null || tacheP.getIdTache() == 0) {
				throw new ProjetException(
						"Attention, la tâche sélectionnée n'existe pas dans la base.");
			}
			tacheDAO.remove(tacheP);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public void deleteAllTacheInProject(int idProjet) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<TacheP> listTacheP = tacheDAO.getAllTacheInProject(idProjet);
			if (listTacheP != null) {
				for (TacheP tacheP : listTacheP) {

					List<AffecterP> listAffecter = AffecterDAO.getInstance()
							.getAllByIdTache(tacheP.getIdTache());

					for (AffecterP af : listAffecter) {
						AffecterDAO.getInstance().remove(af);
					}
					tacheDAO.remove(tacheP);
				}
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public boolean isInLienTachUtil(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucune tâche n'est sélectionné");
		}
		boolean verif = true;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			verif = tacheDAO.isInLienTachUtil(id, session);
			tx.commit();
		} catch (final RuntimeException e) {
			verif = true;
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return verif;
	}

	private void verificationAvantEnregistrement(TacheTO tTO, TacheP tP)
			throws ProjetException {
		if (tP != null) {
			if (tP.getIdTache() != tTO.getIdTache()) {
				throw new ProjetException(
						"Attention, une même tâche existe déjà pour ce projet !");
			}
		}
	}

}
