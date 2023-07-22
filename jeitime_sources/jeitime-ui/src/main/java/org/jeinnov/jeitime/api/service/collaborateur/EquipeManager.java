package org.jeinnov.jeitime.api.service.collaborateur;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.to.collaborateur.EquipeTO;
import org.jeinnov.jeitime.persistence.bo.collaborateur.EquipeP;
import org.jeinnov.jeitime.persistence.dao.collaborateur.EquipeDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


public class EquipeManager {
	private final Logger logger = Logger.getLogger(this.getClass());
	private EquipeDAO equipeDAO = EquipeDAO.getInstance();

	private static EquipeManager manager;

	public EquipeManager() {
	}

	public static EquipeManager getInstance() {
		if (manager == null) {
			manager = new EquipeManager();
		}
		return manager;
	}

	public EquipeTO get(int id) throws CollaborateurException {
		if (id == 0) {
			throw new CollaborateurException("Aucune équipe n'est spécifiée !");

		}
		EquipeTO equipeTO = new EquipeTO();

		EquipeP equipe = new EquipeP();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		try {
			equipe = equipeDAO.find(id);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		if (equipe != null) {
			equipeTO.setIdEquip(equipe.getIdEquip());
			equipeTO.setNomEquip(equipe.getNomEquip());
			equipeTO.setFonctionEquip(equipe.getFonctionEquip());
		} else {
			throw new CollaborateurException(
					"L'équipe sélectionné n'existe pas dans la base.");
		}

		return equipeTO;
	}

	public List<EquipeTO> getAll() {
		List<EquipeTO> allEquip = new ArrayList<EquipeTO>();

		List<EquipeP> allEquipP = new ArrayList<EquipeP>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {

			allEquipP = equipeDAO.getAll();

			tx.commit();

		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}

		if (allEquipP != null) {
			for (EquipeP eq : allEquipP) {
				EquipeTO eqTO = new EquipeTO();
				eqTO.setIdEquip(eq.getIdEquip());
				eqTO.setFonctionEquip(eq.getFonctionEquip());
				eqTO.setNomEquip(eq.getNomEquip());

				allEquip.add(eqTO);
			}
		}

		return allEquip;
	}

	public int saveOrUpdate(EquipeTO eqTO) throws CollaborateurException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		if (eqTO == null) {
			throw new CollaborateurException(
					"vous devez spécifier une équipe avant sa sauvegarde !");
		}
		if (eqTO.getNomEquip() == null) {
			throw new CollaborateurException(
					"vous devez spécifier un nom à l'équipe avant sa sauvegarde !");
		}
		EquipeP eq = new EquipeP();
		try {
			String nom = eqTO.getNomEquip();
			eq = equipeDAO.getByName(nom);
			verificationAvantEnregistrement(eqTO, eq);

			if (eq == null || eq.getIdEquip() == 0) {
				eq = new EquipeP();
			}
			eq.setIdEquip(eqTO.getIdEquip());
			eq.setNomEquip(eqTO.getNomEquip());
			eq.setFonctionEquip(eqTO.getFonctionEquip());

			equipeDAO.save(eq);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return eq.getIdEquip();
	}

	public void deleteEquipe(int id) throws CollaborateurException {
		if (id == 0) {
			throw new CollaborateurException("Aucune équipe n'est spécifiée !");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		try {
			EquipeP eP = equipeDAO.find(id);

			if (eP == null || eP.getIdEquip() == 0) {
				throw new CollaborateurException(
						"L'équipe sélectionné n'existe pas dans la base.");
			}
			equipeDAO.remove(eP);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public boolean isInCollaborateur(int id) throws CollaborateurException {
		boolean verif = true;

		if (id == 0) {
			throw new CollaborateurException("Aucune équipe n'est spécifiée !");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			verif = equipeDAO.isInCollaborateur(id, session);
			tx.commit();
		} catch (final RuntimeException e) {
			verif = true;
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return verif;
	}

	private void verificationAvantEnregistrement(EquipeTO e, EquipeP eP)
			throws CollaborateurException {

		if (eP != null) {
			if (eP.getIdEquip() != e.getIdEquip()) {
				throw new CollaborateurException(
						"Attention, une équipe avec ce nom existe déjà !");
			}
		}
	}

}
