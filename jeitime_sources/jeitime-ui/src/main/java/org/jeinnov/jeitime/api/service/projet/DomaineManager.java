package org.jeinnov.jeitime.api.service.projet;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Transaction;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.jeinnov.jeitime.api.to.projet.DomaineTO;
import org.jeinnov.jeitime.persistence.bo.projet.DomaineP;
import org.jeinnov.jeitime.persistence.dao.projet.DomaineDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


public class DomaineManager {
	private final Logger logger = Logger.getLogger(this.getClass());
	private DomaineDAO domaineDAO = DomaineDAO.getInstance();

	private static DomaineManager manager;

	public DomaineManager() {

	}

	public static DomaineManager getInstance() {
		if (manager == null) {
			manager = new DomaineManager();
		}
		return manager;
	}

	public DomaineTO get(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucun domaine n'est sélectionné");
		}
		DomaineTO domTO = new DomaineTO();
		DomaineP domP = new DomaineP();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try{
			domP = domaineDAO.find(id);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(),e);
			throw e;
		}
		
		if (domP != null) {
			domTO.setIdDomaine(domP.getIdDomaine());
			domTO.setNomDomaine(domP.getNomDomaine());
		} else {
			throw new ProjetException(
					"Attention aucun domaine avec cet identifiant existe en base !");
		}
		return domTO;
	}

	public List<DomaineTO> getAll() {
		List<DomaineTO> allDomTO = new ArrayList<DomaineTO>();
		List<DomaineP> allDom = new ArrayList<DomaineP>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			allDom = domaineDAO.getAll();
			tx.commit();
			
		}  catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(),e);
			throw e;
		}

		if (allDom != null) {
			for (DomaineP dom : allDom) {
				DomaineTO domTO = new DomaineTO();
				domTO.setIdDomaine(dom.getIdDomaine());
				domTO.setNomDomaine(dom.getNomDomaine());

				allDomTO.add(domTO);
			}
		}
		return allDomTO;
	}

	public int saveOrUpdate(DomaineTO domTO) throws ProjetException {
		if (domTO == null) {
			throw new ProjetException(
					"Attention aucun domaine n'est sélectionné");
		}
		if (domTO.getNomDomaine() == null) {
			throw new ProjetException(
					"Attention aucun nom n'a été donné au domaine ");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		DomaineP dom = new DomaineP();

		dom = domaineDAO.getByName(domTO.getNomDomaine());

		verificationAvantEnregistrement(domTO, dom);

		if (dom == null || dom.getIdDomaine() == 0) {
			dom = new DomaineP();
		}
		dom.setIdDomaine(domTO.getIdDomaine());
		dom.setNomDomaine(domTO.getNomDomaine());

		try {
			domaineDAO.save(dom);
			tx.commit();
		}  catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(),e);
			throw e;
		}
		return dom.getIdDomaine();
	}

	public void delete(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucun domaine n'est sélectionné");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			DomaineP domP = domaineDAO.find(id);

			if (domP == null || domP.getIdDomaine() == 0) {
				throw new ProjetException(
						"Attention aucun domaine avec cet identifiant existe en base !");
			}
			domaineDAO.remove(domP);
			tx.commit();
		}  catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(),e);
			throw e;
		}
	}

	public boolean isInProject(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucun domaine n'est sélectionné");
		}
		boolean verif = true;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			verif = domaineDAO.isInProject(id, session);
			tx.commit();
		}  catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(),e);
			throw e;
		}
		return verif;
	}

	private void verificationAvantEnregistrement(DomaineTO dTO, DomaineP dP)
			throws ProjetException {

		if (dP != null) {
			if (dP.getIdDomaine() != dTO.getIdDomaine()) {
				throw new ProjetException(
						"Attention, un domaine avec ce nom existe déjà !");
			}
		}
	}

}
