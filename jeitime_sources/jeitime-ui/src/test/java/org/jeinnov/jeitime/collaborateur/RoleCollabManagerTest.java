package org.jeinnov.jeitime.collaborateur;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurException;
import org.jeinnov.jeitime.api.service.collaborateur.RoleCollabManager;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.jeinnov.jeitime.persistence.bo.collaborateur.Roles;
import org.jeinnov.jeitime.persistence.bo.collaborateur.RolesCollab;
import org.jeinnov.jeitime.persistence.bo.collaborateur.RolesId;
import org.jeinnov.jeitime.persistence.dao.collaborateur.CollaborateurDAO;
import org.jeinnov.jeitime.persistence.dao.collaborateur.RoleCollabDAO;
import org.jeinnov.jeitime.persistence.dao.collaborateur.RolesDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import junit.framework.Assert;

public class RoleCollabManagerTest extends Assert {
	private CollaborateurP coll1;

	private Roles role;
	private RolesCollab rolesCollab;
	private String ROLE_NAME1 = "gestionnaire";

	private CollaborateurDAO collaborateurDAO = CollaborateurDAO.getInstance();
	private RoleCollabDAO rolesCollabDAO = RoleCollabDAO.getInstance();
	private RolesDAO rolesDAO = RolesDAO.getInstance();

	private RoleCollabManager roleCollabManager = RoleCollabManager
			.getInstance();

	public void createData() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			coll1 = new CollaborateurP();
			coll1.setNomColl("coll1");
			coll1.setLogin("login");
			coll1.setPassword("pass");
			coll1.setPrenomColl("prenom");
			coll1.setStatut(1);

			collaborateurDAO.save(coll1);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void createRolesCollab() {
		createData();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			role = new Roles();
			role.setRoles(ROLE_NAME1);

			rolesDAO.save(role);

			rolesCollab = new RolesCollab();
			RolesId id = new RolesId();
			id.setIdColl(coll1.getIdColl());
			id.setRoles(ROLE_NAME1);
			rolesCollab.setRole(role);
			rolesCollab.setCollab(coll1);
			rolesCollab.setId(id);

			rolesCollabDAO.save(rolesCollab);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteAllRolesCollabData() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<RolesCollab> list = rolesCollabDAO.findAll();
			for (int i = 0; i < list.size(); i++) {
				rolesCollabDAO.remove(list.get(i));
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteAllRoles() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<Roles> listRoles = rolesDAO.findAll();
			for (int i = 0; i < listRoles.size(); i++) {
				rolesDAO.remove(listRoles.get(i));
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteAllCollab() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			collaborateurDAO.remove(coll1);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	@org.junit.Test
	public void testInit() {
		roleCollabManager.init();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<Roles> listRoles = rolesDAO.findAll();
			tx.commit();
			assertEquals(listRoles.size(), 4);
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}

		deleteAllRoles();
	}

	@org.junit.Test
	public void testGetByCollab() throws CollaborateurException {
		createRolesCollab();
		List<RolesCollab> list = roleCollabManager.getByCollaborateurId(coll1
				.getIdColl());

		assertEquals(list.size(), 1);
		deleteAllRolesCollabData();
		deleteAllRoles();
		deleteAllCollab();
	}

	@org.junit.Test
	public void testGetByCollabIdNull() {
		try {
			roleCollabManager.getByCollaborateurId(0);
		} catch (CollaborateurException cE) {
			Assert.assertEquals("vous devez spécifier un collaborateur !", cE
					.getMessage());
		}
	}

	@org.junit.Test
	public void testSaveNominal() throws CollaborateurException {
		roleCollabManager.init();
		createData();
		List<RolesCollab> list = roleCollabManager.getByCollaborateurId(coll1
				.getIdColl());

		assertEquals(list.size(), 0);

		roleCollabManager.save(1, coll1.getIdColl());

		list = roleCollabManager.getByCollaborateurId(coll1.getIdColl());

		assertEquals(list.size(), 1);

		deleteAllRolesCollabData();
		deleteAllRoles();
		deleteAllCollab();
	}

	@org.junit.Test
	public void testSaveIdCollNull() {
		try {
			roleCollabManager.save(1, 0);
		} catch (CollaborateurException cE) {
			Assert.assertEquals("vous devez spécifier un collaborateur !", cE
					.getMessage());
		}
	}

	@org.junit.Test
	public void testSaveIdUnknown() {
		try {
			roleCollabManager.save(1, 2);
		} catch (CollaborateurException cE) {
			Assert.assertEquals(
					"Le collaborateur choisi n'existe pas dans la base !", cE
							.getMessage());
		}
	}

	@org.junit.Test
	public void testUpdateNominal() throws CollaborateurException {
		createRolesCollab();
		roleCollabManager.init();

		List<RolesCollab> list = roleCollabManager.getByCollaborateurId(coll1
				.getIdColl());

		assertEquals(list.size(), 1);

		roleCollabManager.update(0, coll1.getIdColl());

		list = roleCollabManager.getByCollaborateurId(coll1.getIdColl());

		assertEquals(list.size(), 1);

		deleteAllRolesCollabData();
		deleteAllRoles();
		deleteAllCollab();
	}

	@org.junit.Test
	public void testUpdateIdCollNull() {
		try {
			roleCollabManager.update(1, 0);
		} catch (CollaborateurException cE) {
			Assert.assertEquals("vous devez spécifier un collaborateur !", cE
					.getMessage());
		}
	}

	@org.junit.Test
	public void testDeleteNominal() throws CollaborateurException {
		createRolesCollab();
		List<RolesCollab> list = roleCollabManager.getByCollaborateurId(coll1
				.getIdColl());

		assertEquals(list.size(), 1);
		roleCollabManager.delete(coll1.getIdColl());

		list = roleCollabManager.getByCollaborateurId(coll1.getIdColl());

		assertEquals(list.size(), 0);

		deleteAllRolesCollabData();
		deleteAllRoles();
		deleteAllCollab();
	}

	@org.junit.Test
	public void testDeleteIdCollNull() {
		try {
			roleCollabManager.delete(0);
		} catch (CollaborateurException cE) {
			Assert.assertEquals("vous devez spécifier un collaborateur !", cE
					.getMessage());
		}
	}

	@org.junit.Test
	public void testDeleteIdCollUnknown() {
		try {
			roleCollabManager.delete(2);
		} catch (CollaborateurException cE) {
			Assert.assertEquals(
					"Le collaborateur choisi n'existe pas dans la base !", cE
							.getMessage());
		}
	}
}
