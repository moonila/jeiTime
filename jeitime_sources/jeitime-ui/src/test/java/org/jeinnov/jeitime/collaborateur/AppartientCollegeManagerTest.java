package org.jeinnov.jeitime.collaborateur;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.collaborateur.AppartientCollegeManager;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurException;
import org.jeinnov.jeitime.persistence.bo.collaborateur.AppartientCollegeIdP;
import org.jeinnov.jeitime.persistence.bo.collaborateur.AppartientCollegeP;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollegeP;
import org.jeinnov.jeitime.persistence.dao.collaborateur.AppartientCollegeDAO;
import org.jeinnov.jeitime.persistence.dao.collaborateur.CollaborateurDAO;
import org.jeinnov.jeitime.persistence.dao.collaborateur.CollegeDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


import junit.framework.Assert;

public class AppartientCollegeManagerTest extends Assert {
	private CollaborateurP coll1;
	private CollaborateurP coll2;
	private CollegeP college1;
	private CollegeP college2;

	private AppartientCollegeP appartientCollege1;
	private AppartientCollegeP appartientCollege2;

	private CollaborateurDAO collaborateurDAO = CollaborateurDAO.getInstance();
	private CollegeDAO collegeDAO = CollegeDAO.getInstance();

	private AppartientCollegeDAO appartientCollegeDAO = AppartientCollegeDAO
			.getInstance();
	private AppartientCollegeManager appartientCollegeManager = AppartientCollegeManager
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

			coll2 = new CollaborateurP();
			coll2.setNomColl("coll1");
			coll2.setLogin("login2");
			coll2.setPassword("pass");
			coll2.setPrenomColl("prenom");

			collaborateurDAO.save(coll1);
			collaborateurDAO.save(coll2);

			college1 = new CollegeP();
			college1.setNomCollege("College1");

			college2 = new CollegeP();
			college2.setNomCollege("College2");

			collegeDAO.save(college1);
			collegeDAO.save(college2);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void createAppartientCollegeData() {
		createData();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			appartientCollege1 = new AppartientCollegeP();

			AppartientCollegeIdP id = new AppartientCollegeIdP();
			id.setDate(new Date());
			id.setIdColl(coll1.getIdColl());
			id.setIdCollege(college1.getIdCollege());

			appartientCollege1.setCollaborateur(coll1);
			appartientCollege1.setCollege(college1);
			appartientCollege1.setId(id);

			id = new AppartientCollegeIdP();
			id.setDate(new Date());
			id.setIdColl(coll2.getIdColl());
			id.setIdCollege(college1.getIdCollege());
			appartientCollege2 = new AppartientCollegeP();
			appartientCollege2.setCollaborateur(coll2);
			appartientCollege2.setCollege(college1);
			appartientCollege2.setId(id);

			appartientCollegeDAO.save(appartientCollege1);
			appartientCollegeDAO.save(appartientCollege2);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteAllAppartientCollege() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<AppartientCollegeP> list = appartientCollegeDAO.findAll();
			for (int i = 0; i < list.size(); i++) {
				appartientCollegeDAO.remove(list.get(i));
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteAllOtherData() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			collaborateurDAO.remove(coll1);
			collaborateurDAO.remove(coll2);
			collegeDAO.remove(college1);
			collegeDAO.remove(college2);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	@org.junit.Test
	public void testGetForCollaborateur() {
		createAppartientCollegeData();

		int idCollege = appartientCollegeManager.get(coll1.getIdColl());

		assertEquals(idCollege, college1.getIdCollege());

		deleteAllAppartientCollege();
		deleteAllOtherData();
	}

	@org.junit.Test
	public void testSaveNominal() throws CollaborateurException {
		createData();

		appartientCollegeManager.saveOrUpdate(college1.getIdCollege(), coll1
				.getIdColl());

		int idCollege = appartientCollegeManager.get(coll1.getIdColl());

		assertEquals(idCollege, college1.getIdCollege());

		deleteAllAppartientCollege();
		deleteAllOtherData();
	}

	@org.junit.Test
	public void testSaveIdCollegeNull() {
		createData();
		try {
			appartientCollegeManager.saveOrUpdate(0, coll1.getIdColl());
		} catch (CollaborateurException cE) {
			Assert.assertEquals(
					"vous devez spécifier une collège et un collaborateur !",
					cE.getMessage());
		}

		deleteAllOtherData();
	}

	@org.junit.Test
	public void testSaveIdCollaborateurNull() {
		createData();
		try {
			appartientCollegeManager.saveOrUpdate(college1.getIdCollege(), 0);
		} catch (CollaborateurException cE) {
			Assert.assertEquals(
					"vous devez spécifier une collège et un collaborateur !",
					cE.getMessage());
		}
		deleteAllOtherData();
	}

	@org.junit.Test
	public void testUpdateWithSameCollege() throws CollaborateurException {
		createAppartientCollegeData();

		appartientCollegeManager.saveOrUpdate(college1.getIdCollege(), coll1
				.getIdColl());

		int idCollege = appartientCollegeManager.get(coll1.getIdColl());

		assertEquals(idCollege, college1.getIdCollege());

		deleteAllAppartientCollege();
		deleteAllOtherData();
	}

	@org.junit.Test
	public void testUpdateWithOtherCollege() throws CollaborateurException {
		createAppartientCollegeData();

		appartientCollegeManager.saveOrUpdate(college2.getIdCollege(), coll1
				.getIdColl());

		int idCollege = appartientCollegeManager.get(coll1.getIdColl());

		assertEquals(idCollege, college2.getIdCollege());

		deleteAllAppartientCollege();
		deleteAllOtherData();
	}

	@org.junit.Test
	public void testDeleteByCollaborateurNominal()
			throws CollaborateurException {
		createAppartientCollegeData();
		int idCollege = appartientCollegeManager.get(coll1.getIdColl());

		assertEquals(idCollege, college1.getIdCollege());

		appartientCollegeManager.deleteByIdCollaborateur(coll1.getIdColl());

		idCollege = appartientCollegeManager.get(coll1.getIdColl());

		assertEquals(idCollege, 0);

		deleteAllAppartientCollege();
		deleteAllOtherData();
	}

	@org.junit.Test
	public void testDeleteByCollaborateurIdNull() {
		try {
			appartientCollegeManager.deleteByIdCollaborateur(0);
		} catch (CollaborateurException cE) {
			Assert.assertEquals("vous devez spécifier un collaborateur !", cE
					.getMessage());
		}
	}

	@org.junit.Test
	public void testDeleteByCollege() throws CollaborateurException {
		createAppartientCollegeData();
		int idCollege = appartientCollegeManager.get(coll1.getIdColl());

		assertEquals(idCollege, college1.getIdCollege());

		appartientCollegeManager.deleteByIdCollege(college1.getIdCollege());

		idCollege = appartientCollegeManager.get(coll1.getIdColl());

		assertEquals(idCollege, 0);
		deleteAllAppartientCollege();
		deleteAllOtherData();
	}

	@org.junit.Test
	public void testDeleteByCollegeIdNull() {
		try {
			appartientCollegeManager.deleteByIdCollege(0);
		} catch (CollaborateurException cE) {
			Assert.assertEquals("vous devez spécifier un collège !", cE
					.getMessage());
		}
	}
}
