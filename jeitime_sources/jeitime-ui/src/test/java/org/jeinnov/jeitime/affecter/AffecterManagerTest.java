package org.jeinnov.jeitime.affecter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.affecter.AffecterException;
import org.jeinnov.jeitime.api.service.affecter.AffecterManager;
import org.jeinnov.jeitime.api.to.affecter.RecapAffectTO;
import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.jeinnov.jeitime.persistence.bo.affecter.AffecterIdP;
import org.jeinnov.jeitime.persistence.bo.affecter.AffecterP;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.jeinnov.jeitime.persistence.bo.projet.NomTacheP;
import org.jeinnov.jeitime.persistence.bo.projet.ProjetP;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;
import org.jeinnov.jeitime.persistence.bo.projet.TypeProjetP;
import org.jeinnov.jeitime.persistence.bo.projet.TypeTacheP;
import org.jeinnov.jeitime.persistence.dao.affecter.AffecterDAO;
import org.jeinnov.jeitime.persistence.dao.collaborateur.CollaborateurDAO;
import org.jeinnov.jeitime.persistence.dao.projet.NomTacheDAO;
import org.jeinnov.jeitime.persistence.dao.projet.ProjetDAO;
import org.jeinnov.jeitime.persistence.dao.projet.TacheDAO;
import org.jeinnov.jeitime.persistence.dao.projet.TypeProjetDAO;
import org.jeinnov.jeitime.persistence.dao.projet.TypeTacheDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import junit.framework.Assert;

public class AffecterManagerTest extends Assert {
	private CollaborateurP coll1;
	private CollaborateurP coll2;

	private TypeTacheP typeTache;
	private NomTacheP nomTache;
	private TacheP tache1;

	private TypeProjetP typeProjet;
	private ProjetP projet1;

	private AffecterP affect1;
	private AffecterP affect2;
	private List<RecapAffectTO> listRecap;

	private CollaborateurDAO collaborateurDAO = CollaborateurDAO.getInstance();
	private ProjetDAO projetDAO = ProjetDAO.getInstance();
	private TacheDAO tacheDAO = TacheDAO.getInstance();
	private AffecterManager affecterManager = AffecterManager.getInstance();
	private AffecterDAO affecterDAO = AffecterDAO.getInstance();

	public void createInfo() {
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

			typeProjet = new TypeProjetP();
			typeProjet.setNomTypePro("typeProjet1");

			TypeProjetDAO.getInstance().save(typeProjet);
			projet1 = new ProjetP();
			projet1.setNomProjet("projet1");
			projet1.setDateDeb(new Date());
			projet1.setTypeProjet(typeProjet);

			projetDAO.save(projet1);

			typeTache = new TypeTacheP();
			typeTache.setNomTypeTache("typeTache1");
			TypeTacheDAO.getInstance().save(typeTache);

			nomTache = new NomTacheP();
			nomTache.setNomTache("nomTache1");
			nomTache.setTypeTache(typeTache);
			NomTacheDAO.getInstance().save(nomTache);

			tache1 = new TacheP();
			tache1.setEligible(true);
			tache1.setNomTacheP(nomTache);
			tache1.setProjet(projet1);

			tacheDAO.save(tache1);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void createData() {
		createInfo();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			AffecterIdP id = new AffecterIdP();
			id.setIdColl(coll1.getIdColl());
			id.setIdTache(tache1.getIdTache());

			affect1 = new AffecterP();
			affect1.setId(id);
			affect1.setCollaborateur(coll1);
			affect1.setDateDeb(new Date());
			affect1.setTache(tache1);

			id = new AffecterIdP();
			id.setIdColl(coll2.getIdColl());
			id.setIdTache(tache1.getIdTache());

			affect2 = new AffecterP();
			affect2.setId(id);
			affect2.setCollaborateur(coll2);
			affect2.setDateDeb(new Date());
			affect2.setTache(tache1);

			affecterDAO.save(affect2);
			affecterDAO.save(affect1);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteAffectation() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<AffecterP> affecterPs = affecterDAO.findAll();

			for (int i = 0; i < affecterPs.size(); i++) {
				affecterDAO.remove(affecterPs.get(i));
			}

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteAllProjet() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			projetDAO.remove(projet1);

			TypeProjetDAO.getInstance().remove(typeProjet);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteAllTache() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			tacheDAO.remove(tache1);
			NomTacheDAO.getInstance().remove(nomTache);
			TypeTacheDAO.getInstance().remove(typeTache);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteAllCollaborateur() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			collaborateurDAO.remove(coll1);
			collaborateurDAO.remove(coll2);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteAllData() {
		deleteAllCollaborateur();
		deleteAllTache();
		deleteAllProjet();
	}

	@org.junit.Test
	public void testGetAllByIdProjet() throws AffecterException {
		createData();

		listRecap = affecterManager.getAllByIdProjet(projet1.getIdProjet());

		assertEquals(listRecap.size(), 2);

		deleteAffectation();
		deleteAllData();

	}

	@org.junit.Test
	public void testGetAllByIdProjetNull() throws AffecterException {
		try {
			affecterManager.getAllByIdProjet(0);
		} catch (AffecterException cE) {
			Assert.assertEquals("Attention, aucun projet n'est sélectionné ! ",
					cE.getMessage());
		}
	}

	@org.junit.Test
	public void testSaveNominal() throws AffecterException {
		createInfo();

		affecterManager.save(tache1.getIdTache(), coll1.getIdColl());

		listRecap = affecterManager.getAllByIdProjet(projet1.getIdProjet());

		assertEquals(listRecap.size(), 1);
		deleteAffectation();
		deleteAllData();
	}

	@org.junit.Test
	public void testSaveIdTacheNull() throws AffecterException {
		createInfo();
		try {
			affecterManager.save(tache1.getIdTache(), 0);
		} catch (AffecterException cE) {
			Assert
					.assertEquals(
							"Aucune tache ou aucun collaborateur n'est rattaché, l'affectation ne peut pas faire ",
							cE.getMessage());
		}
		deleteAllData();
	}

	@org.junit.Test
	public void testSaveIdCollaborateurNull() throws AffecterException {
		createInfo();
		try {
			affecterManager.save(0, coll1.getIdColl());
		} catch (AffecterException cE) {
			Assert
					.assertEquals(
							"Aucune tache ou aucun collaborateur n'est rattaché, l'affectation ne peut pas faire ",
							cE.getMessage());
		}
		deleteAllData();
	}

	@org.junit.Test
	public void testSaveAllNominal() throws AffecterException {
		createInfo();
		List<RecapAffectTO> listRecap = new ArrayList<RecapAffectTO>();
		RecapAffectTO recapAffect = new RecapAffectTO();
		CollaborateurTO c1 = new CollaborateurTO(coll1.getIdColl());
		TacheTO t1 = new TacheTO(tache1.getIdTache());

		recapAffect.setCollaborateur(c1);
		recapAffect.setTache(t1);
		listRecap.add(recapAffect);

		RecapAffectTO recapAffect2 = new RecapAffectTO();
		CollaborateurTO c2 = new CollaborateurTO(coll2.getIdColl());
		recapAffect2.setCollaborateur(c2);
		recapAffect2.setTache(t1);
		listRecap.add(recapAffect2);

		affecterManager.saveAll(listRecap);

		listRecap = affecterManager.getAllByIdProjet(projet1.getIdProjet());

		assertEquals(listRecap.size(), 2);

		deleteAffectation();
		deleteAllData();
	}

	@org.junit.Test
	public void testSaveAllListNull() {
		try {
			affecterManager.saveAll(null);
		} catch (AffecterException cE) {
			Assert
					.assertEquals(
							"Attention la liste des saisies est vide, "
									+ "veuillez sélectionner au moins un projet, une tache et un collaborteur",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testDeleteNominal() throws AffecterException {
		createData();
		listRecap = affecterManager.getAllByIdProjet(projet1.getIdProjet());

		assertEquals(listRecap.size(), 2);

		affecterManager.delete(tache1.getIdTache(), coll1.getIdColl());

		listRecap = affecterManager.getAllByIdProjet(projet1.getIdProjet());

		assertEquals(listRecap.size(), 1);
		deleteAffectation();
		deleteAllData();
	}

	@org.junit.Test
	public void testDeleteIdTacheNull() throws AffecterException {
		createInfo();
		try {
			affecterManager.delete(0, coll1.getIdColl());
		} catch (AffecterException cE) {
			Assert
					.assertEquals(
							"Aucune tache ou aucun collaborateur n'est rattaché, la suppression de l'affectation ne peut être effectuée ! ",
							cE.getMessage());
		}
		deleteAffectation();
		deleteAllData();
	}

	@org.junit.Test
	public void testDeleteIdCollaborateurNull() throws AffecterException {
		createInfo();
		try {
			affecterManager.delete(tache1.getIdTache(), 0);
		} catch (AffecterException cE) {
			Assert
					.assertEquals(
							"Aucune tache ou aucun collaborateur n'est rattaché, la suppression de l'affectation ne peut être effectuée ! ",
							cE.getMessage());
		}
		deleteAffectation();
		deleteAllData();
	}

	@org.junit.Test
	public void testDeleteAllForCollaborateur() throws AffecterException {
		createData();
		listRecap = affecterManager.getAllByIdProjet(projet1.getIdProjet());

		assertEquals(listRecap.size(), 2);

		affecterManager.deleteAllForCollaborateur(coll1.getIdColl());

		listRecap = affecterManager.getAllByIdProjet(projet1.getIdProjet());

		assertEquals(listRecap.size(), 1);
		deleteAffectation();
		deleteAllData();
	}

	@org.junit.Test
	public void testDeleteAllForCollaborateurIdNull() throws AffecterException {
		try {
			affecterManager.deleteAllForCollaborateur(0);
		} catch (AffecterException cE) {
			Assert
					.assertEquals(
							"Aucun collaborateur n'est rattaché, la suppression ne peut pas être effectuée",
							cE.getMessage());
		}
	}
}
