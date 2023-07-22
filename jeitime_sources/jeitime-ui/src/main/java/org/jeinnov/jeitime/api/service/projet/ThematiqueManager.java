package org.jeinnov.jeitime.api.service.projet;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.to.projet.ThematiqueTO;
import org.jeinnov.jeitime.persistence.bo.projet.ThematiqueP;
import org.jeinnov.jeitime.persistence.dao.projet.ThematiqueDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


public class ThematiqueManager {
	private final Logger logger = Logger.getLogger(this.getClass());
	private ThematiqueDAO thematiqueDAO = ThematiqueDAO.getInstance();

	private static ThematiqueManager manager;

	public ThematiqueManager() {

	}

	public static ThematiqueManager getInstance() {
		if (manager == null) {
			manager = new ThematiqueManager();
		}
		return manager;
	}

	public ThematiqueTO get(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucune thématique n'est sélectionnée");
		}
		ThematiqueTO themTO = new ThematiqueTO();
		ThematiqueP themP = new ThematiqueP();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			themP = thematiqueDAO.find(id);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		if (themP != null) {
			themTO.setIdThema(themP.getIdThematique());
			themTO.setNomThema(themP.getNomThematique());
		} else {
			throw new ProjetException(
					"Attention aucune Thématique existe avec cet identifiant dans la base.");
		}
		return themTO;
	}

	public List<ThematiqueTO> getAll() {

		List<ThematiqueTO> listThemTO = new ArrayList<ThematiqueTO>();
		List<ThematiqueP> listThemP = new ArrayList<ThematiqueP>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			listThemP = thematiqueDAO.getAll();
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		if (listThemP != null) {
			for (ThematiqueP themP : listThemP) {
				ThematiqueTO them = new ThematiqueTO();
				them.setIdThema(themP.getIdThematique());
				them.setNomThema(themP.getNomThematique());
				listThemTO.add(them);
			}
		}
		return listThemTO;
	}

	public int saveOrUpdate(ThematiqueTO themTO) throws ProjetException {
		if (themTO == null) {
			throw new ProjetException(
					"Attention aucune thématique n'est sélectionnée");
		}
		if (themTO.getNomThema() == null) {
			throw new ProjetException(
					"Attention vous devez spécifier un nom à la nouvelle thématique !");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		ThematiqueP themP = new ThematiqueP();
		try {
			themP = thematiqueDAO.getByName(themTO.getNomThema());
			verificationAvantEnregistrement(themTO, themP);
			if (themP == null || themP.getIdThematique() == 0) {
				themP = new ThematiqueP();
			}
			themP.setIdThematique(themTO.getIdThema());
			themP.setNomThematique(themTO.getNomThema());
			thematiqueDAO.save(themP);
			tx.commit();

		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		int id = themP.getIdThematique();
		return id;
	}

	public void delete(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucune thématique n'est sélectionnée");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			ThematiqueP themP = thematiqueDAO.find(id);

			if (themP == null || themP.getIdThematique() == 0) {
				throw new ProjetException(
						"Attention aucune Thématique existe avec cet identifiant dans la base.");
			}
			thematiqueDAO.remove(themP);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public boolean isInProject(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucune thématique n'est sélectionnée");
		}
		boolean verif = true;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			verif = thematiqueDAO.isInProject(id, session);
			tx.commit();
		} catch (final RuntimeException e) {
			verif = true;
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return verif;
	}

	private void verificationAvantEnregistrement(ThematiqueTO themT,
			ThematiqueP themP) throws ProjetException {
		if (themP != null) {
			if (themT.getIdThema() == 0) {
				throw new ProjetException(
						"Attention, une thématique avec ce nom existe déjà !");
			}
			if (themP.getIdThematique() != themT.getIdThema()) {
				throw new ProjetException(
						"Attention,une thématique avec ce nom existe déjà !");
			}
		}
	}
}
