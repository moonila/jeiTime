package org.jeinnov.jeitime.heure;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.heure.GestionHeureManager;
import org.jeinnov.jeitime.api.service.heure.HeureException;
import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.heure.SaisieHeureTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.jeinnov.jeitime.persistence.bo.projet.LienTachUtilIdP;
import org.jeinnov.jeitime.persistence.bo.projet.LienTachUtilP;
import org.jeinnov.jeitime.persistence.bo.projet.NomTacheP;
import org.jeinnov.jeitime.persistence.bo.projet.ProjetP;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;
import org.jeinnov.jeitime.persistence.bo.projet.TypeProjetP;
import org.jeinnov.jeitime.persistence.bo.projet.TypeTacheP;
import org.jeinnov.jeitime.persistence.dao.collaborateur.CollaborateurDAO;
import org.jeinnov.jeitime.persistence.dao.heure.LienTacheUtilDAO;
import org.jeinnov.jeitime.persistence.dao.projet.NomTacheDAO;
import org.jeinnov.jeitime.persistence.dao.projet.ProjetDAO;
import org.jeinnov.jeitime.persistence.dao.projet.TacheDAO;
import org.jeinnov.jeitime.persistence.dao.projet.TypeProjetDAO;
import org.jeinnov.jeitime.persistence.dao.projet.TypeTacheDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import junit.framework.Assert;

public class GestionHeureManagerTest extends Assert {

	private static float NBR_H1 = 8;
	private static float NBR_H2 = 2;

	private TypeProjetP typeProjetP;
	private NomTacheP nomTache1;
	private NomTacheP nomTache2;
	private ProjetP projet1;
	private TacheP tache1;
	private TacheP tache2;
	private TypeTacheP typeTache;
	private CollaborateurP collaborateur1;
	private LienTachUtilP lient1;
	private LienTachUtilP lient2;
	private static Date DATE = new Date();

	private LienTacheUtilDAO lienTachUtilDAO = LienTacheUtilDAO.getInstance();
	private GestionHeureManager gestionManager = GestionHeureManager
			.getInstance();

	public void createAllData() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			typeProjetP = new TypeProjetP();
			typeProjetP.setNomTypePro("typeProjet");
			TypeProjetDAO.getInstance().save(typeProjetP);

			projet1 = new ProjetP();
			projet1.setNomProjet("Projet1");
			projet1.setDateDeb(new Date());
			projet1.setTypeProjet(typeProjetP);
			ProjetDAO.getInstance().save(projet1);

			typeTache = new TypeTacheP();
			typeTache.setNomTypeTache("typeTache1");
			TypeTacheDAO.getInstance().save(typeTache);
			nomTache1 = new NomTacheP();
			nomTache1.setNomTache("tache1");
			nomTache1.setTypeTache(typeTache);

			NomTacheDAO.getInstance().save(nomTache1);

			nomTache2 = new NomTacheP();
			nomTache2.setNomTache("tache2");
			nomTache2.setTypeTache(typeTache);

			NomTacheDAO.getInstance().save(nomTache2);

			tache1 = new TacheP();
			tache1.setEligible(true);
			tache1.setNomTacheP(nomTache1);
			tache1.setProjet(projet1);

			TacheDAO.getInstance().save(tache1);

			tache2 = new TacheP();
			tache2.setEligible(false);
			tache2.setNomTacheP(nomTache2);
			tache2.setProjet(projet1);

			TacheDAO.getInstance().save(tache2);

			collaborateur1 = new CollaborateurP();
			collaborateur1.setLogin("login");
			collaborateur1.setPassword("pass");
			collaborateur1.setPrenomColl("prenom");
			collaborateur1.setNomColl("nom");

			CollaborateurDAO.getInstance().save(collaborateur1);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void createDate() {

		Calendar cal = Calendar.getInstance();
		cal.setTime(DATE);
		int numJ = cal.get(Calendar.DAY_OF_WEEK);

		if (numJ == 7) {
			int intDay = cal.get(Calendar.DAY_OF_MONTH) - 1;
			cal.set(Calendar.DAY_OF_MONTH, intDay);
			DATE = cal.getTime();
		}
		if (numJ == 1) {
			int intDay = cal.get(Calendar.DAY_OF_MONTH) - 2;
			cal.set(Calendar.DAY_OF_MONTH, intDay);
			DATE = cal.getTime();
		}
	}

	public void createDataSaisie() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			LienTachUtilIdP lienId = new LienTachUtilIdP();

			createDate();
			lienId.setDate(DATE);
			lienId.setIdColl(collaborateur1.getIdColl());
			lienId.setIdTache(tache1.getIdTache());

			lient1 = new LienTachUtilP();
			lient1.setCollaborateur(collaborateur1);
			lient1.setTache(tache2);
			lient1.setNbHeure(NBR_H1);
			lient1.setId(lienId);

			lienTachUtilDAO.save(lient1);

			lienId = new LienTachUtilIdP();
			lienId.setDate(DATE);
			lienId.setIdColl(collaborateur1.getIdColl());
			lienId.setIdTache(tache2.getIdTache());

			lient2 = new LienTachUtilP();
			lient2.setCollaborateur(collaborateur1);
			lient2.setTache(tache2);
			lient2.setNbHeure(NBR_H2);
			lient2.setId(lienId);

			lienTachUtilDAO.save(lient2);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteSaisie() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<LienTachUtilP> listLien = lienTachUtilDAO.findAll();

			for (int i = 0; i < listLien.size(); i++) {
				lienTachUtilDAO.remove(listLien.get(i));
			}

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
			TacheDAO.getInstance().remove(tache1);
			TacheDAO.getInstance().remove(tache2);
			NomTacheDAO.getInstance().remove(nomTache1);
			NomTacheDAO.getInstance().remove(nomTache2);
			TypeTacheDAO.getInstance().remove(typeTache);
			ProjetDAO.getInstance().remove(projet1);
			TypeProjetDAO.getInstance().remove(typeProjetP);
			CollaborateurDAO.getInstance().remove(collaborateur1);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	@org.junit.Test
	public void testGetByIdCollaborateurAndIdTacheAndDate()
			throws HeureException {
		createAllData();
		createDataSaisie();

		SaisieHeureTO saisieHeure = gestionManager
				.getByIdCollaborateurAndIdTacheAndDate(tache1.getIdTache(),
						collaborateur1.getIdColl(), DATE);

		assertEquals(saisieHeure.getNbHeure(), NBR_H1);

		deleteSaisie();
		deleteData();
	}

	@org.junit.Test
	public void testGetByIdCollaborateurAndIdTacheAndDateDateNull()
			throws HeureException {
		createAllData();
		try {
			gestionManager.getByIdCollaborateurAndIdTacheAndDate(tache1
					.getIdTache(), collaborateur1.getIdColl(), null);
		} catch (HeureException e) {
			Assert.assertEquals("Attention, une date doit être renseignée", e
					.getMessage());
		}
		deleteData();
	}

	@org.junit.Test
	public void testGetByIdCollaborateurAndIdTacheAndDateCollNull()
			throws HeureException {
		createAllData();
		try {
			gestionManager.getByIdCollaborateurAndIdTacheAndDate(tache1
					.getIdTache(), 0, DATE);
		} catch (HeureException e) {
			Assert
					.assertEquals(
							"Attention, un collaborateur et une tâche doivent être sélectionnés",
							e.getMessage());
		}

		deleteData();
	}

	@org.junit.Test
	public void testGetByIdCollaborateurAndIdTacheAndDateTacheNull()
			throws HeureException {
		createAllData();
		try {
			gestionManager.getByIdCollaborateurAndIdTacheAndDate(0,
					collaborateur1.getIdColl(), DATE);
		} catch (HeureException e) {
			Assert
					.assertEquals(
							"Attention, un collaborateur et une tâche doivent être sélectionnés",
							e.getMessage());
		}
		deleteData();
	}

	@org.junit.Test
	public void testGetAllByIdCollaborateurAndDate() throws HeureException {
		createAllData();
		createDataSaisie();

		List<SaisieHeureTO> listSaisieHeure = gestionManager
				.getAllByIdCollaborateurAndDate(collaborateur1.getIdColl(),
						DATE);

		assertEquals(listSaisieHeure.size(), 2);

		deleteSaisie();
		deleteData();
	}

	@org.junit.Test
	public void testGetAllByIdCollaborateurAndDateDateNull()
			throws HeureException {
		createAllData();
		try {
			gestionManager.getAllByIdCollaborateurAndDate(collaborateur1
					.getIdColl(), null);
		} catch (HeureException e) {
			Assert.assertEquals("Attention, une date doit être renseignée", e
					.getMessage());
		}

		deleteData();
	}

	@org.junit.Test
	public void testGetAllByIdCollaborateurAndDateCollNull()
			throws HeureException {
		createAllData();
		try {
			gestionManager.getAllByIdCollaborateurAndDate(0, DATE);
		} catch (HeureException e) {
			Assert.assertEquals(
					"Attention, un collaborateur doit être sélectionné", e
							.getMessage());
		}

		deleteData();
	}

	@org.junit.Test
	public void testSave() throws HeureException {
		createAllData();

		SaisieHeureTO saisieHeure = new SaisieHeureTO();
		CollaborateurTO collab = new CollaborateurTO(collaborateur1.getIdColl());
		saisieHeure.setCollab(collab);
		TacheTO tache = new TacheTO(tache1.getIdTache());
		saisieHeure.setTache(tache);
		saisieHeure.setSaisiDate(DATE);
		saisieHeure.setNbHeure(NBR_H1);

		gestionManager.saveOrUpdate(saisieHeure);

		SaisieHeureTO saisieHeure2 = gestionManager
				.getByIdCollaborateurAndIdTacheAndDate(tache1.getIdTache(),
						collaborateur1.getIdColl(), DATE);

		assertEquals(saisieHeure2.getNbHeure(), NBR_H1);

		deleteSaisie();
		deleteData();
	}

	@org.junit.Test
	public void testSaveSaisieNull() {
		try {
			gestionManager.saveOrUpdate(null);
		} catch (HeureException e) {
			Assert.assertEquals(
					"Attention aucune saisie d'heure n'est renseignée !", e
							.getMessage());
		}
	}

	@org.junit.Test
	public void testSaveCollabNull() {
		createAllData();
		SaisieHeureTO saisieHeure = new SaisieHeureTO();
		CollaborateurTO collab = new CollaborateurTO(0);
		saisieHeure.setCollab(collab);
		TacheTO tache = new TacheTO(tache1.getIdTache());
		saisieHeure.setTache(tache);
		saisieHeure.setSaisiDate(DATE);
		saisieHeure.setNbHeure(NBR_H1);
		try {
			gestionManager.saveOrUpdate(saisieHeure);
		} catch (HeureException e) {
			Assert
					.assertEquals(
							"Attention aucun collaborateur n'est associé à cette saisie !",
							e.getMessage());
		}

		deleteSaisie();
		deleteData();
	}

	@org.junit.Test
	public void testSaveTacheNull() {
		createAllData();
		SaisieHeureTO saisieHeure = new SaisieHeureTO();
		CollaborateurTO collab = new CollaborateurTO(collaborateur1.getIdColl());
		saisieHeure.setCollab(collab);
		TacheTO tache = new TacheTO(0);
		saisieHeure.setTache(tache);
		saisieHeure.setSaisiDate(DATE);
		saisieHeure.setNbHeure(NBR_H1);
		try {
			gestionManager.saveOrUpdate(saisieHeure);
		} catch (HeureException e) {
			Assert.assertEquals(
					"Attention aucune tâche n'est associée à cette saisie !", e
							.getMessage());
		}

		deleteData();
	}

	@org.junit.Test
	public void testSaveCollabIdUnknown() {
		createAllData();
		SaisieHeureTO saisieHeure = new SaisieHeureTO();
		CollaborateurTO collab = new CollaborateurTO(3);
		saisieHeure.setCollab(collab);
		TacheTO tache = new TacheTO(tache1.getIdTache());
		saisieHeure.setTache(tache);
		saisieHeure.setSaisiDate(DATE);
		saisieHeure.setNbHeure(NBR_H1);
		try {
			gestionManager.saveOrUpdate(saisieHeure);
		} catch (HeureException e) {
			Assert
					.assertEquals(
							"Attention le collaborateur associé à cette saisie n'existe pas dans la base !",
							e.getMessage());
		}

		deleteData();
	}

	@org.junit.Test
	public void testSaveTacheIdUnknown() {
		createAllData();
		SaisieHeureTO saisieHeure = new SaisieHeureTO();
		CollaborateurTO collab = new CollaborateurTO(collaborateur1.getIdColl());
		saisieHeure.setCollab(collab);
		TacheTO tache = new TacheTO(3);
		saisieHeure.setTache(tache);
		saisieHeure.setSaisiDate(DATE);
		saisieHeure.setNbHeure(NBR_H1);
		try {
			gestionManager.saveOrUpdate(saisieHeure);
		} catch (HeureException e) {
			Assert
					.assertEquals(
							"Attention la tâche associée à cette saisie n'existe pas dans la base !",
							e.getMessage());
		}
		deleteData();
	}

	@org.junit.Test
	public void testSaveDateNull() {
		createAllData();
		SaisieHeureTO saisieHeure = new SaisieHeureTO();
		CollaborateurTO collab = new CollaborateurTO(collaborateur1.getIdColl());
		saisieHeure.setCollab(collab);
		TacheTO tache = new TacheTO(tache1.getIdTache());
		saisieHeure.setTache(tache);
		saisieHeure.setSaisiDate(null);
		saisieHeure.setNbHeure(NBR_H1);
		try {
			gestionManager.saveOrUpdate(saisieHeure);
		} catch (HeureException e) {
			Assert.assertEquals(
					"Attention aucune date n'est associée à cette saisie ! ", e
							.getMessage());
		}

		deleteData();
	}

	@org.junit.Test
	public void testUpdate() throws HeureException {
		createAllData();
		createDataSaisie();

		SaisieHeureTO saisieHeure = new SaisieHeureTO();
		CollaborateurTO collab = new CollaborateurTO(collaborateur1.getIdColl());
		saisieHeure.setCollab(collab);
		TacheTO tache = new TacheTO(tache1.getIdTache());
		saisieHeure.setTache(tache);
		saisieHeure.setSaisiDate(DATE);
		saisieHeure.setNbHeure(NBR_H2);

		gestionManager.saveOrUpdate(saisieHeure);

		List<SaisieHeureTO> listSaisieHeure = gestionManager
				.getAllByIdCollaborateurAndDate(collaborateur1.getIdColl(),
						DATE);

		assertEquals(listSaisieHeure.size(), 2);

		deleteSaisie();
		deleteData();
	}

	@org.junit.Test
	public void testDelete() throws HeureException {
		createAllData();
		createDataSaisie();

		SaisieHeureTO saisieHeure = new SaisieHeureTO();
		CollaborateurTO collab = new CollaborateurTO(collaborateur1.getIdColl());
		saisieHeure.setCollab(collab);
		TacheTO tache = new TacheTO(tache1.getIdTache());
		saisieHeure.setTache(tache);
		saisieHeure.setSaisiDate(DATE);
		saisieHeure.setNbHeure(0);

		gestionManager.delete(saisieHeure);

		List<SaisieHeureTO> listSaisieHeure = gestionManager
				.getAllByIdCollaborateurAndDate(collaborateur1.getIdColl(),
						DATE);

		assertEquals(listSaisieHeure.size(), 1);

		deleteSaisie();
		deleteData();
	}

	@org.junit.Test
	public void testTotalResult() throws HeureException {
		createAllData();
		createDataSaisie();

		float total = gestionManager.getResultByIdCollaborateurAndDate(
				collaborateur1.getIdColl(), DATE);
		float f = NBR_H2 + NBR_H1;
		assertEquals(total, f);

		deleteSaisie();
		deleteData();
	}

	@org.junit.Test
	public void testTotalResultCollabNull() throws HeureException {
		try {
			gestionManager.getResultByIdCollaborateurAndDate(0, DATE);
		} catch (HeureException e) {
			Assert
					.assertEquals(
							"Attention, un collaborateur et une tâche doivent être sélectionnés",
							e.getMessage());
		}
	}

	@org.junit.Test
	public void testTotalResultDateNull() throws HeureException {
		try {
			gestionManager.getResultByIdCollaborateurAndDate(2, null);
		} catch (HeureException e) {
			Assert.assertEquals("Attention, une date doit être renseignée", e
					.getMessage());
		}
	}
}