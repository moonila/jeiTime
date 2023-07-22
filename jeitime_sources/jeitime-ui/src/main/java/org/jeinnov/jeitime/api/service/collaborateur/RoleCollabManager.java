package org.jeinnov.jeitime.api.service.collaborateur;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.jeinnov.jeitime.persistence.bo.collaborateur.Roles;
import org.jeinnov.jeitime.persistence.bo.collaborateur.RolesCollab;
import org.jeinnov.jeitime.persistence.bo.collaborateur.RolesId;
import org.jeinnov.jeitime.persistence.dao.collaborateur.CollaborateurDAO;
import org.jeinnov.jeitime.persistence.dao.collaborateur.RoleCollabDAO;
import org.jeinnov.jeitime.persistence.dao.collaborateur.RolesDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


public class RoleCollabManager {
	private final Logger logger = Logger.getLogger(this.getClass());
	private RoleCollabDAO rolesCollabDAO = RoleCollabDAO.getInstance();
	private RolesDAO rolesDAO = RolesDAO.getInstance();

	private static RoleCollabManager manager;

	public RoleCollabManager() {
	}

	public static RoleCollabManager getInstance() {
		if (manager == null) {
			manager = new RoleCollabManager();
		}
		return manager;
	}

	public void init() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			Roles role = rolesDAO.getByName("administrateur");
			if (role == null) {
				role = new Roles();
				role.setRoles("administrateur");
				rolesDAO.save(role);
			}
			role = rolesDAO.getByName("chef_de_projet");
			if (role == null) {
				role = new Roles();
				role.setRoles("chef_de_projet");
				rolesDAO.save(role);
			}
			role = rolesDAO.getByName("collaborateur");
			if (role == null) {
				role = new Roles();
				role.setRoles("collaborateur");
				rolesDAO.save(role);
			}
			role = rolesDAO.getByName("gestionnaire");
			if (role == null) {
				role = new Roles();
				role.setRoles("gestionnaire");
				rolesDAO.save(role);
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public List<RolesCollab> getByCollaborateurId(int idCollab)
			throws CollaborateurException {
		if (idCollab == 0) {
			throw new CollaborateurException(
					"vous devez spécifier un collaborateur !");
		}

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<RolesCollab> listRoles = new ArrayList<RolesCollab>();
		try {
			listRoles = rolesCollabDAO.getByIdCollab(idCollab);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}

		return listRoles;
	}

	public void save(int statut, int idCollab) throws CollaborateurException {
		if (idCollab == 0) {
			throw new CollaborateurException(
					"vous devez spécifier un collaborateur !");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		CollaborateurP collabP = CollaborateurDAO.getInstance().find(idCollab);
		if (collabP == null) {
			throw new CollaborateurException(
					"Le collaborateur choisi n'existe pas dans la base !");
		} else {
			collabP = new CollaborateurP();
			collabP.setIdColl(idCollab);
		}

		String roleName = getRole(statut);
		Roles role = new Roles();
		role.setRoles(roleName);

		RolesId roleId = new RolesId();
		roleId.setIdColl(idCollab);
		roleId.setRoles(roleName);

		RolesCollab rC = new RolesCollab();
		rC.setId(roleId);
		rC.setCollab(collabP);
		rC.setRole(role);
		try {
			rolesCollabDAO.save(rC);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public void update(int statut, int idCollab) throws CollaborateurException {
		if (idCollab == 0) {
			throw new CollaborateurException(
					"vous devez spécifier un collaborateur !");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<RolesCollab> listRoles = new ArrayList<RolesCollab>();
		try {
			listRoles = rolesCollabDAO.getByIdCollab(idCollab);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
			if (listRoles == null || listRoles.isEmpty()) {
				save(statut, idCollab);
			} else {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				tx = session.beginTransaction();
				try{
					for (RolesCollab rolesC : listRoles) {
						rolesCollabDAO.remove(rolesC);
					}
					tx.commit();
				} catch (final RuntimeException e) {
					tx.rollback();
					logger.error(e.getMessage(), e);
					throw e;
				}
				
				save(statut, idCollab);
			}
			

	}

	public void delete(int idCollab) throws CollaborateurException {
		if (idCollab == 0) {
			throw new CollaborateurException(
					"vous devez spécifier un collaborateur !");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			CollaborateurP collabP = CollaborateurDAO.getInstance().find(
					idCollab);
			if (collabP == null) {
				throw new CollaborateurException(
						"Le collaborateur choisi n'existe pas dans la base !");
			} else {
				collabP = new CollaborateurP();
				collabP.setIdColl(idCollab);
			}

			List<RolesCollab> listRoles = rolesCollabDAO
					.getByIdCollab(idCollab);

			if (listRoles != null) {
				for (RolesCollab rolesC : listRoles) {
					rolesCollabDAO.remove(rolesC);
				}
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}

	}

	public String getRole(int statut) {
		String role = null;
		if (statut == 0) {
			role = "administrateur";
		}
		if (statut == 1) {
			role = "gestionnaire";
		}
		if (statut == 2) {
			role = "chef_de_projet";
		}
		if (statut == 3) {
			role = "collaborateur";
		}

		// if (statut == 4) {
		// role = "chef d_equipe";
		// }

		return role;
	}
}
