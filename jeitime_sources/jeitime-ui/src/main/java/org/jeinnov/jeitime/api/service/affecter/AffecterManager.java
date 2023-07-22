package org.jeinnov.jeitime.api.service.affecter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.projet.ResultTransformersProject;
import org.jeinnov.jeitime.api.to.affecter.RecapAffectTO;
import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.jeinnov.jeitime.persistence.bo.affecter.AffecterIdP;
import org.jeinnov.jeitime.persistence.bo.affecter.AffecterP;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;
import org.jeinnov.jeitime.persistence.dao.affecter.AffecterDAO;
import org.jeinnov.jeitime.persistence.dao.projet.TacheDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


public class AffecterManager {
	private final Logger logger = Logger.getLogger(this.getClass());
	private AffecterDAO affecterDAO = AffecterDAO.getInstance();
	private ResultTransformersProject resultTransformer = new ResultTransformersProject();

	private static AffecterManager manager;

	public AffecterManager() {

	}

	public static AffecterManager getInstance() {
		if (manager == null) {
			manager = new AffecterManager();
		}
		return manager;
	}

	public List<RecapAffectTO> getAllByIdProjet(int idProjet)
			throws AffecterException {

		if (idProjet == 0) {
			throw new AffecterException(
					"Attention, aucun projet n'est sélectionné ! ");
		}
		List<RecapAffectTO> listRecap = new ArrayList<RecapAffectTO>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		List<TacheP> listTache = TacheDAO.getInstance().getAllTacheInProject(
				idProjet);
		try {

			if (listTache != null) {
				for (TacheP t : listTache) {

					List<AffecterP> listAP = affecterDAO.getAllByIdTache(t
							.getIdTache());

					if (listAP != null) {
						for (AffecterP aP : listAP) {
							CollaborateurTO collaborateur = new CollaborateurTO();

							collaborateur.setIdColl(aP.getCollaborateur()
									.getIdColl());
							collaborateur.setNomColl(aP.getCollaborateur()
									.getNomColl());
							collaborateur.setPrenomColl(aP.getCollaborateur()
									.getPrenomColl());

							TacheTO tache = resultTransformer.toTacheTO(t);

							RecapAffectTO recap = new RecapAffectTO();
							recap.setCollaborateur(collaborateur);
							recap.setTache(tache);

							listRecap.add(recap);
						}
					}
				}
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		Collections.sort(listRecap);

		return listRecap;
	}

	public void save(int idTache, int idCollaborateur) throws AffecterException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		if (idTache == 0 || idCollaborateur == 0) {
			throw new AffecterException(
					"Aucune tache ou aucun collaborateur n'est rattaché, l'affectation ne peut pas faire ");
		}

		CollaborateurP collaborateur = new CollaborateurP();
		collaborateur.setIdColl(idCollaborateur);

		TacheP tache = new TacheP();
		tache.setIdTache(idTache);

		AffecterIdP affecterID = new AffecterIdP();
		affecterID.setIdColl(idCollaborateur);
		affecterID.setIdTache(idTache);

		Date date;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND, 00);
		date = cal.getTime();

		AffecterP affecter = new AffecterP();
		affecter.setCollaborateur(collaborateur);
		affecter.setTache(tache);
		affecter.setId(affecterID);
		affecter.setDateDeb(date);

		try {
			affecterDAO.save(affecter);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}

	}

	public void saveAll(List<RecapAffectTO> listRecap) throws AffecterException {

		if (listRecap == null || listRecap.isEmpty()) {
			throw new AffecterException(
					"Attention la liste des saisies est vide, "
							+ "veuillez sélectionner au moins un projet, une tache et un collaborteur");
		}

		for (RecapAffectTO recapAffect : listRecap) {
			int idTache = recapAffect.getTache().getIdTache();
			int idCollaborateur = recapAffect.getCollaborateur().getIdColl();

			save(idTache, idCollaborateur);
		}
	}

	public void delete(int idTache, int idCollaborateur)
			throws AffecterException {

		if (idTache == 0 || idCollaborateur == 0) {
			throw new AffecterException(
					"Aucune tache ou aucun collaborateur n'est rattaché, la suppression de l'affectation ne peut être effectuée ! ");
		}

		AffecterIdP affecterID = new AffecterIdP();
		affecterID.setIdColl(idCollaborateur);
		affecterID.setIdTache(idTache);

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			affecterDAO.removeById(affecterID);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public void deleteAllForCollaborateur(int idCollaborateur)
			throws AffecterException {

		if (idCollaborateur == 0) {
			throw new AffecterException(
					"Aucun collaborateur n'est rattaché, la suppression ne peut pas être effectuée");

		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		try {

			List<AffecterP> listAffecter = affecterDAO
					.getAllByIdCollaborateur(idCollaborateur);

			for (AffecterP af : listAffecter) {
				affecterDAO.remove(af);
			}

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
}
