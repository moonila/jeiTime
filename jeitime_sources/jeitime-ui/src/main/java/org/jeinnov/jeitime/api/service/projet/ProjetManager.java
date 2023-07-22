package org.jeinnov.jeitime.api.service.projet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.utils.SortList;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.persistence.bo.affecter.AffecterP;
import org.jeinnov.jeitime.persistence.bo.projet.ProjetP;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;
import org.jeinnov.jeitime.persistence.dao.affecter.AffecterDAO;
import org.jeinnov.jeitime.persistence.dao.projet.ProjetDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

public class ProjetManager {
	private final Logger logger = Logger.getLogger(this.getClass());
	private ProjetDAO projetDAO = ProjetDAO.getInstance();
	private ResultTransformersProject resultTransformer = new ResultTransformersProject();
	private SortList sortList = new SortList();

	private static ProjetManager manager;

	public ProjetManager() {

	}

	public static ProjetManager getInstance() {
		if (manager == null) {
			manager = new ProjetManager();
		}
		return manager;
	}

	public ProjetTO get(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucun projet n'est sélectionné");
		}
		ProjetTO projet = new ProjetTO();
		ProjetP p = new ProjetP();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			p = projetDAO.find(id);
			if (p != null) {
				projet = resultTransformer.toProjectTO(p);
			} else {
				throw new ProjetException(
						"Attention aucun projet avec cet id existe dans la base");
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return projet;
	}

	public ProjetTO getProjectNotLock(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucun projet n'est sélectionné");
		}
		ProjetTO projet = new ProjetTO();
		ProjetP p = new ProjetP();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			p = projetDAO.getProjectNotLock(id);
			if (p != null) {
				projet = resultTransformer.toProjectTO(p);
			} else {
				throw new ProjetException(
						"Attention aucun projet avec cet id existe dans la base");
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return projet;
	}

	public ProjetTO getProjectNotClose(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucun projet n'est sélectionné");
		}
		ProjetTO projet = new ProjetTO();
		ProjetP p = new ProjetP();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			p = projetDAO.find(id);
			if (p != null) {
				projet = resultTransformer.toProjectTO(p);
			} else {
				throw new ProjetException(
						"Attention aucun projet avec cet id existe dans la base");
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return projet;
	}

	public List<ProjetTO> getAll() {
		List<ProjetTO> listProjetTO = new ArrayList<ProjetTO>();
		List<ProjetP> listProjetP = new ArrayList<ProjetP>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			listProjetP = projetDAO.findAll();
			if (listProjetP != null) {
				for (ProjetP p : listProjetP) {
					ProjetTO projet = resultTransformer.toProjectTO(p);

					listProjetTO.add(projet);
				}
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		Collections.sort(listProjetTO);

		return listProjetTO;
	}

	public List<ProjetTO> getAllNotLock() {
		List<ProjetTO> listProjetTO = new ArrayList<ProjetTO>();
		List<ProjetP> listProjetP = new ArrayList<ProjetP>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			listProjetP = projetDAO.getAllNotLock();
			if (listProjetP != null) {
				for (ProjetP p : listProjetP) {
					ProjetTO projet = resultTransformer.toProjectTO(p);

					listProjetTO.add(projet);
				}
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return listProjetTO;
	}

	public List<ProjetTO> getAllLock() {
		List<ProjetTO> listProjetTO = new ArrayList<ProjetTO>();
		List<ProjetP> listProjetP = new ArrayList<ProjetP>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			listProjetP = projetDAO.getAllLock();
			if (listProjetP != null) {
				for (ProjetP p : listProjetP) {
					ProjetTO projet = resultTransformer.toProjectTO(p);

					listProjetTO.add(projet);
				}
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}

		return listProjetTO;
	}

	public List<ProjetTO> getAllNotClose() {
		List<ProjetTO> listProjetTO = new ArrayList<ProjetTO>();
		List<ProjetP> listProjetP = new ArrayList<ProjetP>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			listProjetP = projetDAO.getAllNotClose();
			if (listProjetP != null) {
				for (ProjetP p : listProjetP) {
					ProjetTO projet = resultTransformer.toProjectTO(p);

					listProjetTO.add(projet);
				}
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}

		return listProjetTO;
	}

	public List<ProjetTO> getAllClose() {
		List<ProjetTO> listProjetTO = new ArrayList<ProjetTO>();
		List<ProjetP> listProjetP = new ArrayList<ProjetP>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			listProjetP = projetDAO.getAllClose();
			if (listProjetP != null) {
				for (ProjetP p : listProjetP) {
					ProjetTO projet = resultTransformer.toProjectTO(p);
					listProjetTO.add(projet);
				}
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}

		return listProjetTO;
	}

	public List<ProjetTO> getAllForCollabNotLock(int idColl)
			throws ProjetException {
		if (idColl == 0) {
			throw new ProjetException(
					"Attention aucun collaborateur avec cet id existe dans la base");
		}
		List<ProjetTO> tablProjet;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<AffecterP> listA = AffecterDAO.getInstance()
					.getAllByCollabIdAndNotDissociate(idColl);
			List<TacheP> listT = new ArrayList<TacheP>();
			if (listA != null) {
				for (AffecterP a : listA) {
					TacheP tacheP = a.getTache();
					listT.add(tacheP);
				}
			}
			tablProjet = sortList.sortListTachePProjetNotLock(listT);

			Collections.sort(tablProjet);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return tablProjet;
	}

	public List<ProjetTO> getAllForCollabNotClose(int idColl)
			throws ProjetException {
		if (idColl == 0) {
			throw new ProjetException(
					"Attention aucun collaborateur avec cet id existe dans la base");
		}
		List<ProjetTO> tablProjet;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<AffecterP> listA = AffecterDAO.getInstance()
					.getAllByCollabIdAndNotDissociate(idColl);
			List<TacheP> listT = new ArrayList<TacheP>();
			if (listA != null) {
				for (AffecterP a : listA) {
					TacheP tacheP = a.getTache();
					listT.add(tacheP);
				}
			}
			tablProjet = sortList.sortListTachePProjetNotClose(listT);
			Collections.sort(tablProjet);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return tablProjet;
	}

	public int saveOrUpdate(ProjetTO projet) throws ProjetException {
		if (projet == null) {
			throw new ProjetException(
					"Attention aucun projet n'est sélectionné");
		}
		if (projet.getNomProjet() == null) {
			throw new ProjetException(
					"Attention aucun nom n'a été donné au projet ");
		}
		int idProjet;

		ProjetP p = new ProjetP();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			p = projetDAO.getByName(projet.getNomProjet());
			verificationAvantEnregistrement(projet, p);
			if (p == null || p.getIdProjet() == 0) {
				p = new ProjetP();
			}
			resultTransformer.toProjectP(projet, p);
			projetDAO.save(p);
			idProjet = p.getIdProjet();
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return idProjet;
	}

	public void closeProject(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucun projet n'est sélectionné");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			ProjetP p = projetDAO.find(id);
			if (p == null) {
				throw new ProjetException(
						"Attention, le projet sélectionné n'existe pas dans la base.");
			}
			p.setDateFermeture(new Date());
			projetDAO.save(p);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public void openProject(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucun projet n'est sélectionné");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			ProjetP p = projetDAO.find(id);
			if (p == null) {
				throw new ProjetException(
						"Attention, le projet sélectionné n'existe pas dans la base.");
			}
			p.setDateFermeture(null);
			projetDAO.save(p);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public void lockProject(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucun projet n'est sélectionné");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			ProjetP p = projetDAO.find(id);
			if (p == null) {
				throw new ProjetException(
						"Attention, le projet sélectionné n'existe pas dans la base.");
			}
			p.setDateCloture(new Date());
			if (p.getDateFermeture() == null) {
				p.setDateFermeture(new Date());
			}
			projetDAO.save(p);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}

	}

	public void delete(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucun projet n'est sélectionné");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			ProjetP p = projetDAO.find(id);
			if (p == null || p.getIdProjet() == 0) {
				throw new ProjetException(
						"Attention, le projet sélectionné n'existe pas dans la base.");
			}
			verifSiProjetRattache(p);
			projetDAO.remove(p);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	private void verifSiProjetRattache(ProjetP projet) {
		List<ProjetP> listP = projetDAO.getBySousProjet(projet);
		if (listP != null && !listP.isEmpty()) {
			for (int i = 0; i < listP.size(); i++) {
				listP.get(i).setProjet(null);
				projetDAO.save(listP.get(i));
			}
		}
	}

	public boolean isInLienTachUtil(int id) {
		boolean verif = true;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			verif = projetDAO.verifLien(id, session);
			tx.commit();
		} catch (final RuntimeException e) {
			verif = true;
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return verif;
	}

	private void verificationAvantEnregistrement(ProjetTO pTO, ProjetP pP)
			throws ProjetException {
		if (pTO.getTypeProjet() == null) {
			throw new ProjetException(
					"Attention aucun type de projet n'est rattaché au projet");
		}
		if (pP != null) {
			if (pP.getIdProjet() != pTO.getIdProjet()) {
				throw new ProjetException(
						"Attention, un projet avec ce nom existe déjà !");
			}
		}
	}
}
