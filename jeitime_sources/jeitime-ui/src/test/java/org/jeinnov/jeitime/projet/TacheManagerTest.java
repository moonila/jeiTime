package org.jeinnov.jeitime.projet;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.projet.ProjetException;
import org.jeinnov.jeitime.api.service.projet.TacheManager;
import org.jeinnov.jeitime.api.to.projet.NomTacheTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.jeinnov.jeitime.api.to.projet.TypeProjetTO;
import org.jeinnov.jeitime.api.to.projet.TypeTacheTO;
import org.jeinnov.jeitime.persistence.bo.projet.NomTacheP;
import org.jeinnov.jeitime.persistence.bo.projet.ProjetP;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;
import org.jeinnov.jeitime.persistence.bo.projet.TypeProjetP;
import org.jeinnov.jeitime.persistence.bo.projet.TypeTacheP;
import org.jeinnov.jeitime.persistence.dao.projet.NomTacheDAO;
import org.jeinnov.jeitime.persistence.dao.projet.ProjetDAO;
import org.jeinnov.jeitime.persistence.dao.projet.TacheDAO;
import org.jeinnov.jeitime.persistence.dao.projet.TypeProjetDAO;
import org.jeinnov.jeitime.persistence.dao.projet.TypeTacheDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import junit.framework.Assert;

public class TacheManagerTest extends Assert {
	private TacheManager tacheManager = TacheManager.getInstance();
	private TacheDAO tacheDAO = TacheDAO.getInstance();

	private TacheP tache1;
	private static boolean ELIGIBLE1 = true;
	private static float TPS_PREVU1 = 456;
	private static float BUDGET_PREVU1 = 456;
	private static int PRIORITE1 = 1;
	private NomTacheP nomTache1;
	private ProjetP projet1;
	private TacheP tache2;
	private static boolean ELIGIBLE2 = true;
	private static float TPS_PREVU2 = 456;
	private static float BUDGET_PREVU2 = 456;
	private static int PRIORITE2 = 1;
	private NomTacheP nomTache2;

	// private

	public void createData() {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			tache1 = new TacheP();
			tache1.setBudgetprevu(BUDGET_PREVU1);
			tache1.setEligible(ELIGIBLE1);
			tache1.setPriorite(PRIORITE1);
			tache1.setTpsprevu(TPS_PREVU1);

			tache1.setNomTacheP(nomTache1);
			tache1.setProjet(projet1);

			tacheDAO.save(tache1);

			tache2 = new TacheP();
			tache2.setBudgetprevu(BUDGET_PREVU2);
			tache2.setEligible(ELIGIBLE2);
			tache2.setPriorite(PRIORITE2);
			tache2.setTpsprevu(TPS_PREVU2);

			tache2.setNomTacheP(nomTache2);
			tache2.setProjet(projet1);

			tacheDAO.save(tache2);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void createDataForCreateTache() {

		// deleteData2();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			TypeProjetP typeProjetP = new TypeProjetP();
			typeProjetP.setNomTypePro("typeProjet");
			TypeProjetDAO.getInstance().save(typeProjetP);

			projet1 = new ProjetP();
			projet1.setNomProjet("Projet1");
			projet1.setDateDeb(new Date());
			projet1.setTypeProjet(typeProjetP);
			ProjetDAO.getInstance().save(projet1);

			TypeTacheP typeTache = new TypeTacheP();
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

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}
	
	

	public void deleteDataTache() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<TacheP> getAll = tacheDAO.findAll();

			if (getAll != null && !getAll.isEmpty()) {
				tacheDAO.removeAll(getAll);
			}

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}
	
	public void deleteData2(){
		deleteNomTache();
		deleteTypeTache();
		deleteProjet();
		deleteTypeProjet();
	}

	public void deleteNomTache() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<NomTacheP> getAllNomTache = NomTacheDAO.getInstance()
					.findAll();

			if (getAllNomTache != null && !getAllNomTache.isEmpty()) {
				NomTacheDAO.getInstance().removeAll(getAllNomTache);
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteTypeTache() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<TypeTacheP> getAllTypeTache = TypeTacheDAO.getInstance()
					.findAll();

			if (getAllTypeTache != null && !getAllTypeTache.isEmpty()) {
				TypeTacheDAO.getInstance().removeAll(getAllTypeTache);
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}
	public void deleteProjet(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<ProjetP> getAll = ProjetDAO.getInstance().findAll();

			if (getAll != null && !getAll.isEmpty()) {
				ProjetDAO.getInstance().removeAll(getAll);
			}

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}
	public void deleteTypeProjet() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<TypeProjetP> getAllTypeProjet = TypeProjetDAO.getInstance()
					.findAll();

			if (getAllTypeProjet != null && !getAllTypeProjet.isEmpty()) {
				TypeProjetDAO.getInstance().removeAll(getAllTypeProjet);
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}
	
	


	@org.junit.Test
	public void testGetAllNominal() throws ProjetException {
		createDataForCreateTache();
		createData();

		List<TacheTO> getAll = tacheManager.getAllTacheInProject(projet1
				.getIdProjet());
		assertEquals(getAll.size(), 2);

		deleteDataTache();
		deleteData2();
	}

	@org.junit.Test
	public void testGetAllIdProjetNull() throws ProjetException {
		try {
			tacheManager.getAllTacheInProject(0);
		} catch (ProjetException e) {
			Assert.assertEquals("Attention aucun projet n'est sélectionné", e
					.getMessage());
		}
	}

	@org.junit.Test
	public void testCreateNominal() throws ProjetException {
		createDataForCreateTache();
		TacheTO t = new TacheTO();

		t.setBudgetprevu(BUDGET_PREVU1);
		t.setEligible(ELIGIBLE1);
		t.setPriorite(PRIORITE1);
		t.setTpsprevu(TPS_PREVU1);

		NomTacheTO nT = new NomTacheTO();
		nT.setIdNomTache(nomTache1.getIdNomTache());
		nT.setTypeTache(new TypeTacheTO(nomTache1.getTypeTache()
				.getIdTypeTache()));
		t.setNomtache(nT);

		ProjetTO p = new ProjetTO();
		p.setIdProjet(projet1.getIdProjet());
		p
				.setTypeProjet(new TypeProjetTO(projet1.getTypeProjet()
						.getIdTypePro()));
		t.setProjet(p);

		int id = tacheManager.saveOrUpdate(t);

		TacheTO ta = tacheManager.get(id);
		assertEquals(ta.getNomtache().getNomTache(), "tache1");
		// deleteData2();
		deleteDataTache();
		deleteData2();
	}

	@org.junit.Test
	public void testCreateTwice() throws ProjetException {
		createDataForCreateTache();
		createData();
		TacheTO t = new TacheTO();
		t.setBudgetprevu(BUDGET_PREVU1);
		t.setEligible(ELIGIBLE1);
		t.setPriorite(PRIORITE1);
		t.setTpsprevu(TPS_PREVU1);

		NomTacheTO nT = new NomTacheTO();
		nT.setIdNomTache(nomTache1.getIdNomTache());
		nT.setTypeTache(new TypeTacheTO(nomTache1.getTypeTache()
				.getIdTypeTache()));
		t.setNomtache(nT);

		ProjetTO p = new ProjetTO();
		p.setIdProjet(projet1.getIdProjet());
		p
				.setTypeProjet(new TypeProjetTO(projet1.getTypeProjet()
						.getIdTypePro()));
		t.setProjet(p);

		try {
			tacheManager.saveOrUpdate(t);
		} catch (ProjetException e) {
			Assert.assertEquals(
					"Attention, une même tâche existe déjà pour ce projet !", e
							.getMessage());
		}

		deleteDataTache();
		deleteData2();
	}

	@org.junit.Test
	public void testCreateNameNull() throws ProjetException {
		TacheTO t = new TacheTO();
		t.setBudgetprevu(BUDGET_PREVU1);
		t.setEligible(ELIGIBLE1);
		t.setPriorite(PRIORITE1);
		t.setTpsprevu(TPS_PREVU1);

		ProjetTO p = new ProjetTO();
		p.setIdProjet(1);
		p.setTypeProjet(new TypeProjetTO(1));
		t.setProjet(p);

		t.setNomtache(null);

		try {
			tacheManager.saveOrUpdate(t);
		} catch (ProjetException e) {
			Assert.assertEquals("Vous devez spécifier une tâche", e
					.getMessage());
		}
	}

	@org.junit.Test
	public void testCreateProjetNull() throws ProjetException {
		TacheTO t = new TacheTO();
		t.setBudgetprevu(BUDGET_PREVU1);
		t.setEligible(ELIGIBLE1);
		t.setPriorite(PRIORITE1);
		t.setTpsprevu(TPS_PREVU1);
		t.setProjet(null);

		try {
			tacheManager.saveOrUpdate(t);
		} catch (ProjetException e) {
			Assert.assertEquals("Vous devez spécifier un projet", e
					.getMessage());
		}
	}

	@org.junit.Test
	public void testCreateTacheNull() throws ProjetException {
		try {
			tacheManager.saveOrUpdate(null);
		} catch (ProjetException e) {
			Assert.assertEquals("Attention aucune tâche n'a été créée", e
					.getMessage());
		}
	}

	@org.junit.Test
	public void testUpdateNominal() throws ProjetException {
		createDataForCreateTache();
		createData();

		TacheTO t = tacheManager.get(tache1.getIdTache());
		t.setTpsprevu(0);

		int id = tacheManager.saveOrUpdate(t);

		assertEquals(id, t.getIdTache());

		deleteDataTache();
		deleteData2();
	}

	@org.junit.Test
	public void testUpdateTwice() throws ProjetException {
		createDataForCreateTache();
		createData();

		TacheTO t = new TacheTO();

		t.setBudgetprevu(BUDGET_PREVU1);
		t.setEligible(ELIGIBLE1);
		t.setPriorite(PRIORITE1);
		t.setTpsprevu(TPS_PREVU1);

		NomTacheTO nT = new NomTacheTO();
		nT.setIdNomTache(nomTache1.getIdNomTache());
		nT.setTypeTache(new TypeTacheTO(nomTache1.getTypeTache()
				.getIdTypeTache()));
		t.setNomtache(nT);

		ProjetTO p = new ProjetTO();
		p.setIdProjet(projet1.getIdProjet());
		p
				.setTypeProjet(new TypeProjetTO(projet1.getTypeProjet()
						.getIdTypePro()));
		t.setProjet(p);

		try {
			tacheManager.saveOrUpdate(t);
		} catch (ProjetException cE) {
			Assert.assertEquals(
					"Attention, une même tâche existe déjà pour ce projet !",
					cE.getMessage());
		}

		deleteDataTache();
		deleteData2();
	}

	@org.junit.Test
	public void testDeleteNominal() throws ProjetException {
		createDataForCreateTache();
		createData();

		tacheManager.delete(tache1.getIdTache());

		List<TacheTO> getAll = tacheManager.getAllTacheInProject(projet1
				.getIdProjet());
		assertEquals(getAll.size(), 1);

		deleteDataTache();
		deleteData2();
	}

	@org.junit.Test
	public void testDeleteIdNull() throws ProjetException {
		try {
			tacheManager.delete(0);
		} catch (ProjetException cE) {
			Assert.assertEquals("Attention aucune tâche n'est sélectionnée", cE
					.getMessage());
		}
	}

	@org.junit.Test
	public void testDeleteIdUnknown() throws ProjetException {
		try {
			tacheManager.delete(1);
		} catch (ProjetException cE) {
			Assert
					.assertEquals(
							"Attention, la tâche sélectionnée n'existe pas dans la base.",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testGetNominal() throws ProjetException {
		createDataForCreateTache();
		createData();
		TacheTO e = tacheManager.get(tache1.getIdTache());

		assertEquals(e.getNomtache().getNomTache(), "tache1");

		deleteDataTache();
		deleteData2();
	}

	@org.junit.Test
	public void testGetIdNull() throws ProjetException {
		try {
			tacheManager.get(0);
		} catch (ProjetException cE) {
			Assert.assertEquals("Attention aucune tâche n'est sélectionné", cE
					.getMessage());
		}
	}

	@org.junit.Test
	public void testGetIdUnknown() throws ProjetException {
		try {
			tacheManager.get(1);
		} catch (ProjetException cE) {
			Assert
					.assertEquals(
							"Attention aucune tâche avec cet identifiant existe dans la base !",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testIsInNomTacheNominal() throws ProjetException {
		createDataForCreateTache();
		createData();
		boolean verif = tacheManager.isInLienTachUtil(tache1.getIdTache());

		assertEquals(verif, false);

		deleteDataTache();
		deleteData2();
	}

	@org.junit.Test
	public void testIsInNomTacheIdNulll() throws ProjetException {

		try {
			tacheManager.isInLienTachUtil(0);
		} catch (ProjetException cE) {
			Assert.assertEquals("Attention aucune tâche n'est sélectionné", cE
					.getMessage());
		}
	}
}
