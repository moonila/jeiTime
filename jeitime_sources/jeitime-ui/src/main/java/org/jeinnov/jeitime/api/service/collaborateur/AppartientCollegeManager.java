package org.jeinnov.jeitime.api.service.collaborateur;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.persistence.bo.collaborateur.AppartientCollegeIdP;
import org.jeinnov.jeitime.persistence.bo.collaborateur.AppartientCollegeP;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollegeP;
import org.jeinnov.jeitime.persistence.dao.collaborateur.AppartientCollegeDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


public class AppartientCollegeManager {
	private final Logger logger = Logger.getLogger(this.getClass());
	private AppartientCollegeDAO appartientCollegeDAO = AppartientCollegeDAO
			.getInstance();

	private static AppartientCollegeManager manager;

	public AppartientCollegeManager() {
	}

	public static AppartientCollegeManager getInstance() {
		if (manager == null) {
			manager = new AppartientCollegeManager();
		}
		return manager;
	}

	public int get(int idCollaborateur) {
		AppartientCollegeP aCP = new AppartientCollegeP();
		int idCollege = 0;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			aCP = appartientCollegeDAO.getByIdCollaborateur(idCollaborateur);
			if (aCP != null) {
				idCollege = aCP.getCollege().getIdCollege();
			} else {
				idCollege = 0;
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return idCollege;
	}

	public void saveOrUpdate(int idCollege, int idCollaborateur)
			throws CollaborateurException {
		if (idCollaborateur == 0 || idCollege == 0) {
			throw new CollaborateurException(
					"vous devez spécifier une collège et un collaborateur !");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<AppartientCollegeP> listACP = appartientCollegeDAO
					.getAllByIdCollaborateur(idCollaborateur);

			if (listACP != null) {
				for (AppartientCollegeP aCP : listACP) {
					appartientCollegeDAO.remove(aCP);
				}
			}

			Date date;
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MILLISECOND, 00);
			date = cal.getTime();

			AppartientCollegeIdP idLiaison = new AppartientCollegeIdP(
					idCollege, idCollaborateur, date);
			CollaborateurP collaborateur = new CollaborateurP(idCollaborateur);
			CollegeP college = new CollegeP(idCollege);

			AppartientCollegeP liaisonCollegeColla = new AppartientCollegeP();

			liaisonCollegeColla.setCollaborateur(collaborateur);
			liaisonCollegeColla.setCollege(college);
			liaisonCollegeColla.setId(idLiaison);

			appartientCollegeDAO.save(liaisonCollegeColla);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public void deleteByIdCollaborateur(int idCollaborateur)
			throws CollaborateurException {

		if (idCollaborateur == 0) {
			throw new CollaborateurException(
					"vous devez spécifier un collaborateur !");
		}

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<AppartientCollegeP> listACP = appartientCollegeDAO
					.getAllByIdCollaborateur(idCollaborateur);

			if (listACP != null) {
				for (AppartientCollegeP aCP : listACP) {
					appartientCollegeDAO.remove(aCP);
				}
			}

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public void deleteByIdCollege(int idCollege) throws CollaborateurException {
		if (idCollege == 0) {
			throw new CollaborateurException(
					"vous devez spécifier un collège !");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<AppartientCollegeP> listACP = appartientCollegeDAO
					.getAllByIdCollege(idCollege);

			if (listACP != null) {
				for (AppartientCollegeP aCP : listACP) {
					appartientCollegeDAO.remove(aCP);
				}
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
}
