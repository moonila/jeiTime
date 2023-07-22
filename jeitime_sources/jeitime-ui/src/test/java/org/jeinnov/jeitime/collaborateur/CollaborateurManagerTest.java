package org.jeinnov.jeitime.collaborateur;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurException;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurManager;
import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.jeinnov.jeitime.persistence.bo.collaborateur.EquipeP;
import org.jeinnov.jeitime.persistence.dao.collaborateur.CollaborateurDAO;
import org.jeinnov.jeitime.persistence.dao.collaborateur.EquipeDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import junit.framework.Assert;

public class CollaborateurManagerTest extends Assert {
	private static String NOM1 = "collab1";
	private static String PRENOM1 = "prenom1";
	private static String LOGIN1 = "login1";
	private static String PASSWORD1 = "pass1";
	private static float NBH_ANN1 = 1400;
	private static float NBH_HEB1 = 37;
	private static float NBH_LUN1 = 8;
	private static float NBH_MAR1 = 8;
	private static float NBH_MER1 = 8;
	private static float NBH_JEU1 = 8;
	private static float NBH_VEN1 = 8;
	private static float NBH_MEN1 = 148;
	private static int STATUT1 = 1;

	private CollaborateurP collaborateur1;

	private static String NOM2 = "collab2";
	private static String PRENOM2 = "prenom2";
	private static String LOGIN2 = "login2";
	private static String PASSWORD2 = "pass2";
	private static float NBH_ANN2 = 1400;
	private static float NBH_HEB2 = 37;
	private static float NBH_LUN2 = 8;
	private static float NBH_MAR2 = 8;
	private static float NBH_MER2 = 8;
	private static float NBH_JEU2 = 8;
	private static float NBH_VEN2 = 8;
	private static float NBH_MEN2 = 148;
	private static int STATUT2 = 2;

	private CollaborateurP collaborateur2;

	private CollaborateurP collaborateur3;

	private EquipeP equipe1;
	private static String NOM_EQUIPE = "equipe1";

	private CollaborateurManager collaborateurManager = CollaborateurManager
			.getInstance();
	private CollaborateurDAO collaborateurDAO = CollaborateurDAO.getInstance();

	private EquipeDAO equipDAO = EquipeDAO.getInstance();

	public void createAllData() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			collaborateur1 = new CollaborateurP();
			collaborateur1.setLogin(LOGIN1);
			collaborateur1.setPassword(PASSWORD1);
			collaborateur1.setNomColl(NOM1);
			collaborateur1.setPrenomColl(PRENOM1);
			collaborateur1.setNbHeureAnn(NBH_ANN1);
			collaborateur1.setNbHeureHeb(NBH_HEB1);
			collaborateur1.setNbHeureMens(NBH_MEN1);
			collaborateur1.setNbHeureLundi(NBH_LUN1);
			collaborateur1.setNbHeureMardi(NBH_MAR1);
			collaborateur1.setNbHeureMercredi(NBH_MER1);
			collaborateur1.setNbHeureJeudi(NBH_JEU1);
			collaborateur1.setNbHeureVendredi(NBH_VEN1);
			collaborateur1.setStatut(STATUT1);

			collaborateur2 = new CollaborateurP();
			collaborateur2.setLogin(LOGIN2);
			collaborateur2.setPassword(PASSWORD2);
			collaborateur2.setNomColl(NOM2);
			collaborateur2.setPrenomColl(PRENOM2);
			collaborateur2.setNbHeureAnn(NBH_ANN2);
			collaborateur2.setNbHeureHeb(NBH_HEB2);
			collaborateur2.setNbHeureMens(NBH_MEN2);
			collaborateur2.setNbHeureLundi(NBH_LUN2);
			collaborateur2.setNbHeureMardi(NBH_MAR2);
			collaborateur2.setNbHeureMercredi(NBH_MER2);
			collaborateur2.setNbHeureJeudi(NBH_JEU2);
			collaborateur2.setNbHeureVendredi(NBH_VEN2);
			collaborateur2.setStatut(STATUT2);

			collaborateurDAO.save(collaborateur1);
			collaborateurDAO.save(collaborateur2);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void createAllWithAdminUser() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			collaborateur1 = new CollaborateurP();
			collaborateur1.setLogin(LOGIN1);
			collaborateur1.setPassword(PASSWORD1);
			collaborateur1.setNomColl(NOM1);
			collaborateur1.setPrenomColl(PRENOM1);
			collaborateur1.setNbHeureAnn(NBH_ANN1);
			collaborateur1.setNbHeureHeb(NBH_HEB1);
			collaborateur1.setNbHeureMens(NBH_MEN1);
			collaborateur1.setNbHeureLundi(NBH_LUN1);
			collaborateur1.setNbHeureMardi(NBH_MAR1);
			collaborateur1.setNbHeureMercredi(NBH_MER1);
			collaborateur1.setNbHeureJeudi(NBH_JEU1);
			collaborateur1.setNbHeureVendredi(NBH_VEN1);
			collaborateur1.setStatut(STATUT1);

			collaborateur2 = new CollaborateurP();
			collaborateur2.setLogin(LOGIN2);
			collaborateur2.setPassword(PASSWORD2);
			collaborateur2.setNomColl(NOM2);
			collaborateur2.setPrenomColl(PRENOM2);
			collaborateur2.setNbHeureAnn(NBH_ANN2);
			collaborateur2.setNbHeureHeb(NBH_HEB2);
			collaborateur2.setNbHeureMens(NBH_MEN2);
			collaborateur2.setNbHeureLundi(NBH_LUN2);
			collaborateur2.setNbHeureMardi(NBH_MAR2);
			collaborateur2.setNbHeureMercredi(NBH_MER2);
			collaborateur2.setNbHeureJeudi(NBH_JEU2);
			collaborateur2.setNbHeureVendredi(NBH_VEN2);
			collaborateur2.setStatut(STATUT2);

			collaborateurDAO.save(collaborateur1);
			collaborateurDAO.save(collaborateur2);

			collaborateur3 = new CollaborateurP();
			collaborateur3.setLogin("admin");
			collaborateur3.setNomColl("collab");
			collaborateur3.setPrenomColl("p");
			collaborateur3.setPassword("pass");
			collaborateur3.setStatut(0);

			collaborateurDAO.save(collaborateur3);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void createAllWithAdminUserAndEquip() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			equipe1 = new EquipeP();
			equipe1.setNomEquip(NOM_EQUIPE);
			equipDAO.save(equipe1);

			collaborateur1 = new CollaborateurP();
			collaborateur1.setLogin(LOGIN1);
			collaborateur1.setPassword(PASSWORD1);
			collaborateur1.setNomColl(NOM1);
			collaborateur1.setPrenomColl(PRENOM1);
			collaborateur1.setNbHeureAnn(NBH_ANN1);
			collaborateur1.setNbHeureHeb(NBH_HEB1);
			collaborateur1.setNbHeureMens(NBH_MEN1);
			collaborateur1.setNbHeureLundi(NBH_LUN1);
			collaborateur1.setNbHeureMardi(NBH_MAR1);
			collaborateur1.setNbHeureMercredi(NBH_MER1);
			collaborateur1.setNbHeureJeudi(NBH_JEU1);
			collaborateur1.setNbHeureVendredi(NBH_VEN1);
			collaborateur1.setStatut(STATUT1);
			collaborateur1.setEquipe(equipe1);

			collaborateur2 = new CollaborateurP();
			collaborateur2.setLogin(LOGIN2);
			collaborateur2.setPassword(PASSWORD2);
			collaborateur2.setNomColl(NOM2);
			collaborateur2.setPrenomColl(PRENOM2);
			collaborateur2.setNbHeureAnn(NBH_ANN2);
			collaborateur2.setNbHeureHeb(NBH_HEB2);
			collaborateur2.setNbHeureMens(NBH_MEN2);
			collaborateur2.setNbHeureLundi(NBH_LUN2);
			collaborateur2.setNbHeureMardi(NBH_MAR2);
			collaborateur2.setNbHeureMercredi(NBH_MER2);
			collaborateur2.setNbHeureJeudi(NBH_JEU2);
			collaborateur2.setNbHeureVendredi(NBH_VEN2);
			collaborateur2.setStatut(STATUT2);
			collaborateur2.setEquipe(equipe1);

			collaborateurDAO.save(collaborateur1);
			collaborateurDAO.save(collaborateur2);

			CollaborateurP collaborateur3 = new CollaborateurP();
			collaborateur3.setLogin("admin");
			collaborateur3.setNomColl("collab");
			collaborateur3.setPrenomColl("p");
			collaborateur3.setPassword("pass");
			collaborateur3.setStatut(0);
			collaborateur3.setEquipe(equipe1);

			collaborateurDAO.save(collaborateur3);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void createData() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			collaborateur1 = new CollaborateurP();
			collaborateur1.setLogin(LOGIN1);
			collaborateur1.setPassword(PASSWORD1);
			collaborateur1.setNomColl(NOM1);
			collaborateur1.setPrenomColl(PRENOM1);
			collaborateur1.setNbHeureAnn(NBH_ANN1);
			collaborateur1.setNbHeureHeb(NBH_HEB1);
			collaborateur1.setNbHeureMens(NBH_MEN1);
			collaborateur1.setNbHeureLundi(NBH_LUN1);
			collaborateur1.setNbHeureMardi(NBH_MAR1);
			collaborateur1.setNbHeureMercredi(NBH_MER1);
			collaborateur1.setNbHeureJeudi(NBH_JEU1);
			collaborateur1.setNbHeureVendredi(NBH_VEN1);
			collaborateur1.setStatut(STATUT1);

			collaborateurDAO.save(collaborateur1);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteAllDataCollaborateurs() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<CollaborateurP> listC = collaborateurDAO.getAll();
			for (CollaborateurP c : listC) {
				collaborateurDAO.remove(c);
			}

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteAllDataEquipe() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<EquipeP> listEquip = equipDAO.getAll();
			for (EquipeP e : listEquip) {
				equipDAO.remove(e);
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}

	}

	@org.junit.Test
	public void testGetAll() {

		createAllData();
		List<CollaborateurTO> allCollab = collaborateurManager.getAll();

		assertEquals(allCollab.size(), 2);
		deleteAllDataCollaborateurs();
	}

	@org.junit.Test
	public void testGet() throws CollaborateurException {
		createData();

		CollaborateurTO collab = collaborateurManager.get(collaborateur1
				.getIdColl());

		assertEquals(collab.getLogin(), LOGIN1);
		deleteAllDataCollaborateurs();
	}

	@org.junit.Test
	public void testGetByIdNull() {
		try {
			collaborateurManager.get(0);
		} catch (CollaborateurException cE) {
			Assert.assertEquals(
					"Attention, aucun collaborateur n'a été sélectionné !", cE
							.getMessage());
		}
	}

	@org.junit.Test
	public void testGetByIdUnknown() {
		try {
			collaborateurManager.get(4);
		} catch (CollaborateurException cE) {
			Assert
					.assertEquals(
							"Attention, le collaborateur sélectionné n'existe pas dans la base !",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testGetByLogin() throws CollaborateurException {
		createData();
		CollaborateurP collab = collaborateurManager.getByLogin(LOGIN1);

		assertEquals(collab.getIdColl(), collaborateur1.getIdColl());

		deleteAllDataCollaborateurs();
	}

	@org.junit.Test
	public void testGetByLoginNull() {
		try {
			collaborateurManager.getByLogin(null);
		} catch (CollaborateurException cE) {
			Assert.assertEquals("Vous n'avez pas saisie de login !", cE
					.getMessage());
		}
	}

	@org.junit.Test
	public void testGetByUnknown() {
		try {
			collaborateurManager.getByLogin("test");
		} catch (CollaborateurException cE) {
			Assert.assertEquals(
					"Aucun collaborateur avec ce login existe dans la base !",
					cE.getMessage());
		}
	}

	@org.junit.Test
	public void testGetAllWithoutAdmin() {
		createAllWithAdminUser();
		List<CollaborateurTO> allCollab = collaborateurManager
				.getAllWithoutAdmin();

		assertEquals(allCollab.size(), 2);

		deleteAllDataCollaborateurs();
	}

	@org.junit.Test
	public void testGetAllByEquip() {
		createAllWithAdminUserAndEquip();
		List<CollaborateurTO> allCollab = collaborateurManager
				.getAllByIdEquipe(equipe1.getIdEquip());

		assertEquals(allCollab.size(), 2);

		deleteAllDataCollaborateurs();
		deleteAllDataEquipe();
	}

	@org.junit.Test
	public void testSaveOrUpdateNominal() throws CollaborateurException {
		CollaborateurTO collaborateur = new CollaborateurTO();
		collaborateur.setLogin(LOGIN1);
		collaborateur.setPassword(PASSWORD1);
		collaborateur.setNomColl(NOM1);
		collaborateur.setPrenomColl(PRENOM1);
		collaborateur.setNbHeureAnn(NBH_ANN1);
		collaborateur.setNbHeureHeb(NBH_HEB1);
		collaborateur.setNbHeureMens(NBH_MEN1);
		collaborateur.setNbHeureLundi(NBH_LUN1);
		collaborateur.setNbHeureMardi(NBH_MAR1);
		collaborateur.setNbHeureMercredi(NBH_MER1);
		collaborateur.setNbHeureJeudi(NBH_JEU1);
		collaborateur.setNbHeureVendredi(NBH_VEN1);
		collaborateur.setStatut(STATUT1);

		int id = collaborateurManager.saveOrUpdate(collaborateur);

		collaborateur = collaborateurManager.get(id);

		assertEquals(LOGIN1, collaborateur.getLogin());

		deleteAllDataCollaborateurs();
	}

	@org.junit.Test
	public void testSaveOrUpdateNull() {
		try {
			collaborateurManager.saveOrUpdate(null);
		} catch (CollaborateurException cE) {
			Assert.assertEquals(
					"Attention, aucun collaborateur n'a été créé !", cE
							.getMessage());
		}
	}

	@org.junit.Test
	public void testSaveOrUpdateLoginNull() {
		CollaborateurTO collaborateur = new CollaborateurTO();
		collaborateur.setLogin(null);
		collaborateur.setPassword(PASSWORD1);
		collaborateur.setNomColl(NOM1);
		collaborateur.setPrenomColl(PRENOM1);
		collaborateur.setStatut(STATUT1);
		try {
			collaborateurManager.saveOrUpdate(collaborateur);
		} catch (CollaborateurException cE) {
			Assert
					.assertEquals(
							"Attention, aucun login n'a été spécifié pour ce collaborateur !",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testSaveOrUpdateLoginTwice() {
		createData();
		CollaborateurTO collaborateur = new CollaborateurTO();
		collaborateur.setLogin(LOGIN1);
		collaborateur.setPassword(PASSWORD1);
		collaborateur.setNomColl(NOM1);
		collaborateur.setPrenomColl(PRENOM1);
		collaborateur.setStatut(STATUT1);
		try {
			collaborateurManager.saveOrUpdate(collaborateur);
		} catch (CollaborateurException cE) {
			Assert.assertEquals(
					"Attention, un collaborateur existe déjà avec ce login !",
					cE.getMessage());
		}

		deleteAllDataCollaborateurs();
	}

	@org.junit.Test
	public void testSaveOrUpdateNameNull() {
		CollaborateurTO collaborateur = new CollaborateurTO();
		collaborateur.setLogin(LOGIN1);
		collaborateur.setPassword(PASSWORD1);
		collaborateur.setNomColl(null);
		collaborateur.setPrenomColl(PRENOM1);
		collaborateur.setStatut(STATUT1);
		try {
			collaborateurManager.saveOrUpdate(collaborateur);
		} catch (CollaborateurException cE) {
			Assert
					.assertEquals(
							"Attention, soit le nom du collaborateur, son prénom, ou son mot de passe ne sont pas renseignés !",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testSaveOrUpdatePrenomNull() {
		CollaborateurTO collaborateur = new CollaborateurTO();
		collaborateur.setLogin(LOGIN1);
		collaborateur.setPassword(PASSWORD1);
		collaborateur.setNomColl(NOM1);
		collaborateur.setPrenomColl(null);
		collaborateur.setStatut(STATUT1);
		try {
			collaborateurManager.saveOrUpdate(collaborateur);
		} catch (CollaborateurException cE) {
			Assert
					.assertEquals(
							"Attention, soit le nom du collaborateur, son prénom, ou son mot de passe ne sont pas renseignés !",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testSaveOrUpdatePassNull() {
		CollaborateurTO collaborateur = new CollaborateurTO();
		collaborateur.setLogin(LOGIN1);
		collaborateur.setPassword(null);
		collaborateur.setNomColl(NOM1);
		collaborateur.setPrenomColl(PRENOM1);
		collaborateur.setStatut(STATUT1);
		try {
			collaborateurManager.saveOrUpdate(collaborateur);
		} catch (CollaborateurException cE) {
			Assert
					.assertEquals(
							"Attention, soit le nom du collaborateur, son prénom, ou son mot de passe ne sont pas renseignés !",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testDeleteNominal() throws CollaborateurException {
		createAllWithAdminUser();

		collaborateurManager.delete(collaborateur1.getIdColl());

		List<CollaborateurTO> allCollab = collaborateurManager.getAll();

		assertEquals(allCollab.size(), 2);
		deleteAllDataCollaborateurs();
	}

	@org.junit.Test
	public void testDeleteIdNull() throws CollaborateurException {
		try {
			collaborateurManager.delete(0);
		} catch (CollaborateurException cE) {
			Assert.assertEquals("Aucun collaborateur n'a été sélectionné ! ",
					cE.getMessage());
		}
	}

	@org.junit.Test
	public void testDeleteIdUnknown() throws CollaborateurException {
		try {
			collaborateurManager.delete(4);
		} catch (CollaborateurException cE) {
			Assert.assertEquals(
					"Le collaborateur sélectionné n'existe pas dans la base ",
					cE.getMessage());
		}
	}

	@org.junit.Test
	public void testDeleteAdmin() throws CollaborateurException {
		createAllWithAdminUser();

		try {
			collaborateurManager.delete(collaborateur3.getIdColl());
		} catch (CollaborateurException cE) {
			Assert
					.assertEquals(
							"Vous ne pouvez pas supprimer l'utilisateur avec le login admin ",
							cE.getMessage());
		}
		deleteAllDataCollaborateurs();
	}
}
