package org.jeinnov.jeitime.api.service.collaborateur;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.to.collaborateur.CollegeTO;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollegeP;
import org.jeinnov.jeitime.persistence.dao.collaborateur.CollegeDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


public class CollegeManager {
	private final Logger logger = Logger.getLogger(this.getClass());
	private CollegeDAO collegeDAO = CollegeDAO.getInstance();
	private ResultTransformerCollaborateur resultTransformer = new ResultTransformerCollaborateur();

	private static CollegeManager manager;

	public CollegeManager() {
	}

	public static CollegeManager getInstance() {
		if (manager == null) {
			manager = new CollegeManager();
		}
		return manager;
	}

	public CollegeTO get(int id) throws CollaborateurException {
		CollegeTO college = new CollegeTO();

		if (id == 0) {
			throw new CollaborateurException(
					"Attention, aucun collège n'a été sélectionné !");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			CollegeP collegeP = collegeDAO.find(id);

			if (collegeP == null || collegeP.getIdCollege() == 0) {
				throw new CollaborateurException(
						"Le collège sélectionné n'existe pas dans la base.");
			}

			college = resultTransformer.toCollegeTO(collegeP);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return college;
	}

	public List<CollegeTO> getAll() {

		List<CollegeTO> listCollegeTO = new ArrayList<CollegeTO>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<CollegeP> listCollegeP = collegeDAO.getAll();
			if (listCollegeP != null) {
				for (CollegeP cP : listCollegeP) {
					CollegeTO cTO = resultTransformer.toCollegeTO(cP);

					listCollegeTO.add(cTO);
				}
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}

		return listCollegeTO;
	}

	public int saveOrUpdate(CollegeTO collegeTO) throws CollaborateurException {
		if (collegeTO == null) {
			throw new CollaborateurException(
					"Vous devez spécifier un collège avant sa sauvegarde !");
		}
		if (collegeTO.getNomCollege() == null) {
			throw new CollaborateurException(
					"vous devez spécifier un nom au collège avant sa sauvegarde !");
		}

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		CollegeP cP = new CollegeP();
		try {
			cP = collegeDAO.getByName(collegeTO.getNomCollege());
			verificationAvantEnregistrement(collegeTO, cP);

			if (cP == null || cP.getIdCollege() == 0) {
				cP = new CollegeP();
			}

			resultTransformer.toCollegeP(collegeTO, cP);
			collegeDAO.save(cP);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return cP.getIdCollege();
	}

	public void delete(int id) throws CollaborateurException {
		if (id == 0) {
			throw new CollaborateurException(
					"Attention vous devez spécifier un collège avant sa suppression");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		try {
			CollegeP cP = collegeDAO.find(id);
			if (cP == null || cP.getIdCollege() == 0) {
				throw new CollaborateurException(
						"Le collège sélectionné n'existe pas dans la base.");
			}
			collegeDAO.remove(cP);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public String[] createListSelectionHeure(int idColl)
			throws CollaborateurException {

		if (idColl == 0) {
			throw new CollaborateurException(
					"Attention, la valeur de l'identifiant du collège ne peut pas être nulle");
		}
		String[] tablListSaisie = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			CollegeP collegeP = collegeDAO.find(idColl);
			String listS = collegeP.getListSaisie();

			if (listS != null) {
				tablListSaisie = listS.split("/");
			} else {
				tablListSaisie = null;
			}

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(),e);
			throw e;
		}
		return tablListSaisie;
	}

	private void verificationAvantEnregistrement(CollegeTO c, CollegeP cP)
			throws CollaborateurException {

		if (cP != null) {
			if (c.getIdCollege() == 0) {
				throw new CollaborateurException(
						"Attention, un collège avec ce nom existe déjà !");
			}
			if (cP.getIdCollege() != c.getIdCollege()) {
				throw new CollaborateurException(
						"Attention, un collège avec ce nom existe déjà !");
			}
		}
	}
}
