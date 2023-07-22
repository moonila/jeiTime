package org.jeinnov.jeitime.collaborateur;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurException;
import org.jeinnov.jeitime.api.service.collaborateur.CollegeManager;
import org.jeinnov.jeitime.api.to.collaborateur.CollegeTO;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollegeP;
import org.jeinnov.jeitime.persistence.dao.collaborateur.CollegeDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import junit.framework.Assert;

public class CollegeManagerTest extends Assert {
	private CollegeManager collegeManager = CollegeManager.getInstance();
	private CollegeDAO collegeDAO = CollegeDAO.getInstance();

	private CollegeP college1;
	private static String NOM_1 = "college1";
	private static String LIST_SAISIE = "0/0.5/1/1.5/2/2.5/3";
	private static int NBHEURE_ANN_1 = 3450;
	private static int NBHEURE_HEBDO_1 = 1500;
	private static int NBHEURE_MENS_1 = 750;
	private static int NBHEURE_LUN_1 = 8;
	private static int NBHEURE_MAR_1 = 8;
	private static int NBHEURE_MER_1 = 8;
	private static int NBHEURE_JEU_1 = 8;
	private static int NBHEURE_VEN_1 = 8;
	private static int NBJOUR_CONGE_1 = 8;
	private static int NBJOUR_RTT_1 = 8;

	private CollegeP college2;
	private static String NOM_2 = "college2";
	private static int NBHEURE_ANN_2 = 3450;
	private static int NBHEURE_HEBDO_2 = 1500;
	private static int NBHEURE_MENS_2 = 750;
	private static int NBHEURE_LUN_2 = 8;
	private static int NBHEURE_MAR_2 = 8;
	private static int NBHEURE_MER_2 = 8;
	private static int NBHEURE_JEU_2 = 8;
	private static int NBHEURE_VEN_2 = 8;
	private static int NBJOUR_CONGE_2 = 8;
	private static int NBJOUR_RTT_2 = 8;

	public void createData() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			college1 = new CollegeP();
			college1.setNomCollege(NOM_1);
			college1.setListSaisie(LIST_SAISIE);
			college1.setNbHeureAnnCollege(NBHEURE_ANN_1);
			college1.setNbHeureMensCollege(NBHEURE_MENS_1);
			college1.setNbHeureHebdo(NBHEURE_HEBDO_1);
			college1.setNbHeureLun(NBHEURE_LUN_1);
			college1.setNbHeureMar(NBHEURE_MAR_1);
			college1.setNbHeureMerc(NBHEURE_MER_1);
			college1.setNbHeureJeud(NBHEURE_JEU_1);
			college1.setNbHeureVend(NBHEURE_VEN_1);
			college1.setNbJourCongeAnnCollege(NBJOUR_CONGE_1);
			college1.setNbJourRttAnnCollege(NBJOUR_RTT_1);

			collegeDAO.save(college1);

			college2 = new CollegeP();
			college2.setNomCollege(NOM_2);
			college2.setNbHeureAnnCollege(NBHEURE_ANN_2);
			college2.setNbHeureMensCollege(NBHEURE_MENS_2);
			college2.setNbHeureHebdo(NBHEURE_HEBDO_2);
			college2.setNbHeureLun(NBHEURE_LUN_2);
			college2.setNbHeureMar(NBHEURE_MAR_2);
			college2.setNbHeureMerc(NBHEURE_MER_2);
			college2.setNbHeureJeud(NBHEURE_JEU_2);
			college2.setNbHeureVend(NBHEURE_VEN_2);
			college2.setNbJourCongeAnnCollege(NBJOUR_CONGE_2);
			college2.setNbJourRttAnnCollege(NBJOUR_RTT_2);

			collegeDAO.save(college2);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteData() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<CollegeP> getAll = collegeDAO.getAll();

			if (getAll != null && !getAll.isEmpty()) {
				collegeDAO.removeAll(getAll);
			}

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	@org.junit.Test
	public void testGetAllEquipe() {
		createData();

		List<CollegeTO> getAll = collegeManager.getAll();
		assertEquals(getAll.size(), 2);

		deleteData();
	}

	@org.junit.Test
	public void testCreateNominal() throws CollaborateurException {
		CollegeTO co = new CollegeTO();
		co.setNomCollege(NOM_1);
		co.setListSaisie(LIST_SAISIE);
		co.setNbHeureAnnCollege(NBHEURE_ANN_1);
		co.setNbHeureMensCollege(NBHEURE_MENS_1);
		co.setNbHeureHeb(NBHEURE_HEBDO_1);
		co.setNbHeureLun(NBHEURE_LUN_1);
		co.setNbHeureMar(NBHEURE_MAR_1);
		co.setNbHeureMerc(NBHEURE_MER_1);
		co.setNbHeureJeu(NBHEURE_JEU_1);
		co.setNbHeureVen(NBHEURE_VEN_1);
		co.setNbJourCongeAnnCollege(NBJOUR_CONGE_1);
		co.setNbJourRttAnnCollege(NBJOUR_RTT_1);
		int id = collegeManager.saveOrUpdate(co);

		CollegeTO c = new CollegeTO();
		c = collegeManager.get(id);

		assertEquals(c.getNomCollege(), co.getNomCollege());

		deleteData();
	}

	@org.junit.Test
	public void testCreateTwice() throws CollaborateurException {
		createData();
		CollegeTO co = new CollegeTO();
		co.setNomCollege(NOM_1);
		try {
			collegeManager.saveOrUpdate(co);
		} catch (CollaborateurException e) {
			Assert.assertEquals(
					"Attention, un collège avec ce nom existe déjà !", e
							.getMessage());
		}
		deleteData();
	}

	@org.junit.Test
	public void testCreateNameNull() throws CollaborateurException {
		CollegeTO co = new CollegeTO();
		co.setNomCollege(null);

		try {
			collegeManager.saveOrUpdate(co);
		} catch (CollaborateurException e) {
			Assert
					.assertEquals(
							"vous devez spécifier un nom au collège avant sa sauvegarde !",
							e.getMessage());
		}
	}

	@org.junit.Test
	public void testCreatCollegeNull() throws CollaborateurException {

		try {
			collegeManager.saveOrUpdate(null);
		} catch (CollaborateurException e) {
			Assert.assertEquals(
					"Vous devez spécifier un collège avant sa sauvegarde !", e
							.getMessage());
		}
	}

	@org.junit.Test
	public void testUpdateNominal() throws CollaborateurException {
		createData();
		CollegeTO co = new CollegeTO();
		co.setIdCollege(college1.getIdCollege());
		co.setNomCollege("test");
		co.setListSaisie(LIST_SAISIE);
		co.setNbHeureAnnCollege(NBHEURE_ANN_1);
		co.setNbHeureMensCollege(NBHEURE_MENS_1);
		co.setNbHeureHeb(NBHEURE_HEBDO_1);
		co.setNbHeureLun(NBHEURE_LUN_1);
		co.setNbHeureMar(NBHEURE_MAR_1);
		co.setNbHeureMerc(NBHEURE_MER_1);
		co.setNbHeureJeu(NBHEURE_JEU_1);
		co.setNbHeureVen(NBHEURE_VEN_1);
		co.setNbJourCongeAnnCollege(NBJOUR_CONGE_1);
		co.setNbJourRttAnnCollege(NBJOUR_RTT_1);

		int id = collegeManager.saveOrUpdate(co);

		assertEquals(id, college1.getIdCollege());

		deleteData();
	}

	@org.junit.Test
	public void testUpdateTwice() throws CollaborateurException {
		createData();
		CollegeTO co = new CollegeTO();
		co.setIdCollege(college1.getIdCollege());
		co.setNomCollege(NOM_2);
		co.setListSaisie(LIST_SAISIE);
		co.setNbHeureAnnCollege(NBHEURE_ANN_1);
		co.setNbHeureMensCollege(NBHEURE_MENS_1);
		co.setNbHeureHeb(NBHEURE_HEBDO_1);
		co.setNbHeureLun(NBHEURE_LUN_1);
		co.setNbHeureMar(NBHEURE_MAR_1);
		co.setNbHeureMerc(NBHEURE_MER_1);
		co.setNbHeureJeu(NBHEURE_JEU_1);
		co.setNbHeureVen(NBHEURE_VEN_1);
		co.setNbJourCongeAnnCollege(NBJOUR_CONGE_1);
		co.setNbJourRttAnnCollege(NBJOUR_RTT_1);

		try {
			collegeManager.saveOrUpdate(co);
		} catch (CollaborateurException cE) {
			Assert.assertEquals(
					"Attention, un collège avec ce nom existe déjà !", cE
							.getMessage());
		}

		deleteData();
	}

	@org.junit.Test
	public void testDeleteNominal() throws CollaborateurException {
		createData();

		collegeManager.delete(college1.getIdCollege());

		List<CollegeTO> getAll = collegeManager.getAll();
		assertEquals(getAll.size(), 1);

		deleteData();
	}

	@org.junit.Test
	public void testDeleteIdNull() throws CollaborateurException {
		try {
			collegeManager.delete(0);
		} catch (CollaborateurException cE) {
			Assert
					.assertEquals(
							"Attention vous devez spécifier un collège avant sa suppression",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testDeleteIdUnknown() throws CollaborateurException {
		try {
			collegeManager.delete(1);
		} catch (CollaborateurException cE) {
			Assert.assertEquals(
					"Le collège sélectionné n'existe pas dans la base.", cE
							.getMessage());
		}
	}

	@org.junit.Test
	public void testGetNominal() throws CollaborateurException {
		createData();
		CollegeTO e = collegeManager.get(college1.getIdCollege());

		assertEquals(e.getNomCollege(), college1.getNomCollege());

		deleteData();
	}

	@org.junit.Test
	public void testGetIdNull() throws CollaborateurException {
		try {
			collegeManager.get(0);
		} catch (CollaborateurException cE) {
			Assert.assertEquals(
					"Attention, aucun collège n'a été sélectionné !", cE
							.getMessage());
		}
	}

	@org.junit.Test
	public void testGetIdUnknown() throws CollaborateurException {
		try {
			collegeManager.get(1);
		} catch (CollaborateurException cE) {
			Assert.assertEquals(
					"Le collège sélectionné n'existe pas dans la base.", cE
							.getMessage());
		}
	}
}
