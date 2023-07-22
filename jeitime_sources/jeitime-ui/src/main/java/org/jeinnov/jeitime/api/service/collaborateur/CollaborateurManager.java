package org.jeinnov.jeitime.api.service.collaborateur;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.jeinnov.jeitime.persistence.dao.collaborateur.CollaborateurDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;



public class CollaborateurManager {

	private final Logger logger = Logger.getLogger(this.getClass());
	private CollaborateurDAO collaborateurDAO = CollaborateurDAO.getInstance();
	private ResultTransformerCollaborateur resultTransformer = new ResultTransformerCollaborateur();

	private static CollaborateurManager manager;

	public CollaborateurManager() {

	}

	public static CollaborateurManager getInstance() {
		if (manager == null) {
			manager = new CollaborateurManager();
		}
		return manager;
	}

	public void init() throws CollaborateurException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		CollaborateurP collabP = new CollaborateurP();
		try {
			collabP = collaborateurDAO.getByLogin("admin");
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		if (collabP == null) {
			CollaborateurTO collab = new CollaborateurTO();
			collab.setLogin("admin");
			collab.setPrenomColl("admin");
			collab.setNomColl("admin");
			collab.setStatut(0);
			// int id = 0;
			String password = this.passwordCrypting("admin");
			collab.setPassword(password);

			int id = this.saveOrUpdate(collab);

			RoleCollabManager.getInstance().save(0, id);
		}
	}

	public CollaborateurTO get(int id) throws CollaborateurException {

		if (id == 0) {
			throw new CollaborateurException(
					"Attention, aucun collaborateur n'a été sélectionné !");
		}
		CollaborateurTO collaborateur = new CollaborateurTO();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			CollaborateurP collabP = collaborateurDAO.find(id);
			if (collabP == null || collabP.getIdColl() == 0) {
				throw new CollaborateurException(
						"Attention, le collaborateur sélectionné n'existe pas dans la base !");
			}
			collaborateur = resultTransformer.toCollaborateurTO(collabP);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return collaborateur;
	}

	public CollaborateurP getByLogin(String login)
			throws CollaborateurException {
		if (login == null) {
			throw new CollaborateurException(
					"Vous n'avez pas saisie de login !");
		}

		CollaborateurP collabP = new CollaborateurP();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			collabP = collaborateurDAO.getByLogin(login);
			if (collabP == null) {
				throw new CollaborateurException(
						"Aucun collaborateur avec ce login existe dans la base !");
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return collabP;
	}

	public List<CollaborateurTO> getAll() {

		List<CollaborateurTO> listCollabTO = new ArrayList<CollaborateurTO>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {

			List<CollaborateurP> listCollabP = collaborateurDAO.getAll();

			if (listCollabP != null) {
				for (CollaborateurP collabP : listCollabP) {
					CollaborateurTO collaborateur = resultTransformer
							.toCollaborateurTO(collabP);
					listCollabTO.add(collaborateur);
				}
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}

		return listCollabTO;
	}

	public List<CollaborateurTO> getAllWithoutAdmin() {
		List<CollaborateurTO> listCollabTO = this.getAll();
		for (int i = 0; i < listCollabTO.size(); i++) {
			if (listCollabTO.get(i).getLogin().equalsIgnoreCase("admin")) {
				listCollabTO.remove(i);
			}
		}
		return listCollabTO;
	}

	public List<CollaborateurTO> getAllByIdEquipe(int idEquipe) {

		List<CollaborateurTO> listCollabTO = new ArrayList<CollaborateurTO>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {

			if (idEquipe != 0) {
				List<CollaborateurP> listCollabP = collaborateurDAO
						.getAllByIdEquipe(idEquipe);
				if (listCollabP != null) {
					for (CollaborateurP collabP : listCollabP) {
						if (!collabP.getLogin().equalsIgnoreCase("admin")) {
							CollaborateurTO collaborateur = resultTransformer
									.toCollaborateurTO(collabP);

							listCollabTO.add(collaborateur);
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

		return listCollabTO;
	}

	public int saveOrUpdate(CollaborateurTO collaborateur)
			throws CollaborateurException {

		if (collaborateur == null) {
			throw new CollaborateurException(
					"Attention, aucun collaborateur n'a été créé !");
		}
		if (collaborateur.getLogin() == null) {
			throw new CollaborateurException(
					"Attention, aucun login n'a été spécifié pour ce collaborateur !");
		}

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		CollaborateurP collabP = new CollaborateurP();
		int id = 0;
		try {
			collabP = collaborateurDAO.getByLogin(collaborateur.getLogin()
					.toLowerCase());

			verificationAvantEnregistrement(collaborateur, collabP);

			if (collabP == null || collabP.getIdColl() == 0) {
				collabP = new CollaborateurP();
			}
			resultTransformer.toCollaborateurP(collaborateur, collabP);

			collaborateurDAO.save(collabP);
			id = collabP.getIdColl();
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return id;
	}

	public void delete(int id) throws CollaborateurException {

		if (id == 0) {
			throw new CollaborateurException(
					"Aucun collaborateur n'a été sélectionné ! ");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			CollaborateurP c = collaborateurDAO.find(id);
			if (c == null || c.getIdColl() == 0) {
				throw new CollaborateurException(
						"Le collaborateur sélectionné n'existe pas dans la base ");
			}
			if (c.getLogin().equalsIgnoreCase("admin")) {
				throw new CollaborateurException(
						"Vous ne pouvez pas supprimer l'utilisateur avec le login admin ");
			}

			collaborateurDAO.remove(c);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public boolean isInLientTachUtil(int id) {
		boolean verif = true;

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			verif = collaborateurDAO.isInLienTachUtil(id, session);
			tx.commit();
		} catch (final RuntimeException e) {
			verif = true;
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return verif;
	}

	private void verificationAvantEnregistrement(CollaborateurTO c,
			CollaborateurP collabP) throws CollaborateurException {
		if (c.getNomColl() == null || c.getPrenomColl() == null
				|| c.getPassword() == null) {
			throw new CollaborateurException(
					"Attention, soit le nom du collaborateur, son prénom, ou son mot de passe ne sont pas renseignés !");
		}
		if (collabP != null && collabP.getIdColl() != 0) {
			if (c.getIdColl() != collabP.getIdColl()) {
				throw new CollaborateurException(
						"Attention, un collaborateur existe déjà avec ce login !");
			}
		}
	}

	public String passwordCrypting(String password) {
		byte[] uniqueKey = password.getBytes();
		byte[] hash = null;
		try {
			hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
		} catch (NoSuchAlgorithmException e) {
			logger.info("No MD5 support in this VM.");
		}
		StringBuilder hashString = new StringBuilder();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(hash[i]);
			if (hex.length() == 1) {
				hashString.append('0');
				hashString.append(hex.charAt(hex.length() - 1));
			} else {
				hashString.append(hex.substring(hex.length() - 2));
			}
		}
		return hashString.toString();
	}
}
