package org.jeinnov.jeitime.projet;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.projet.NomTacheManager;
import org.jeinnov.jeitime.api.service.projet.ProjetException;
import org.jeinnov.jeitime.api.service.projet.TypeTacheManager;
import org.jeinnov.jeitime.api.to.projet.NomTacheTO;
import org.jeinnov.jeitime.api.to.projet.TypeTacheTO;
import org.jeinnov.jeitime.persistence.bo.projet.NomTacheP;
import org.jeinnov.jeitime.persistence.bo.projet.TypeTacheP;
import org.jeinnov.jeitime.persistence.dao.projet.NomTacheDAO;
import org.jeinnov.jeitime.persistence.dao.projet.TypeTacheDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import junit.framework.Assert;

public class NomTacheManagerTest extends Assert {
	private NomTacheManager nomTacheManager = NomTacheManager.getInstance();
	private NomTacheDAO nomTacheDAO = NomTacheDAO.getInstance();

	private NomTacheP nomTache1;
	private static String NOM_1 = "typeTache1";

	private NomTacheP nomTache2;
	private static String NOM_2 = "typeTache2";

	private TypeTacheP typeTache;

	public void createData() throws ProjetException {
		deleteData();
		createTypeTache();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			nomTache1 = new NomTacheP();
			nomTache1.setNomTache(NOM_1);
			nomTache1.setTypeTache(typeTache);

			nomTacheDAO.save(nomTache1);

			nomTache2 = new NomTacheP();
			nomTache2.setNomTache(NOM_2);
			nomTache2.setTypeTache(typeTache);

			nomTacheDAO.save(nomTache2);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}
	
	public void createTypeTache(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			typeTache = new TypeTacheP();
			typeTache.setNomTypeTache("typeTache1");

			TypeTacheDAO.getInstance().save(typeTache);
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
			List<NomTacheP> getAll = nomTacheDAO.getAll();

			if (getAll != null && !getAll.isEmpty()) {
				nomTacheDAO.removeAll(getAll);
			}

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
		
		deleteTypeTache();
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
	
	@org.junit.Test
	public void testGetAll() throws ProjetException {
		createData();

		List<NomTacheTO> getAll = nomTacheManager.getAll();
		assertEquals(getAll.size(), 2);

		deleteData();
	}

	@org.junit.Test
	public void testCreateNominal() throws ProjetException {

		NomTacheTO eq = new NomTacheTO();
		eq.setNomTache(NOM_1);
		TypeTacheTO typeTacheTO = new TypeTacheTO();
		typeTacheTO.setNomTypTach("typeTache");
		
		int idTypeTache = TypeTacheManager.getInstance().saveOrUpdate(
				typeTacheTO);
		typeTacheTO.setIdTypTach(idTypeTache);
		eq.setTypeTache(typeTacheTO);

		int id = nomTacheManager.saveOrUpdate(eq);

		NomTacheTO e = new NomTacheTO();
		e = nomTacheManager.get(id);

		assertEquals(e.getNomTache(), eq.getNomTache());

		deleteData();
	}

	@org.junit.Test
	public void testCreateTwice() throws ProjetException {
		createData();
		NomTacheTO eq = new NomTacheTO();
		eq.setNomTache(NOM_1);

		try {
			nomTacheManager.saveOrUpdate(eq);
		} catch (ProjetException e) {
			Assert.assertEquals(
					"Attention, une tâche avec ce nom existe déjà !", e
							.getMessage());
		}
		deleteData();
	}

	@org.junit.Test
	public void testCreateNameNull() throws ProjetException {
		NomTacheTO eq = new NomTacheTO();
		eq.setNomTache(null);

		try {
			nomTacheManager.saveOrUpdate(eq);
		} catch (ProjetException e) {
			Assert.assertEquals("Attention aucun nom n'a été donné à la tâche",
					e.getMessage());
		}
	}

	@org.junit.Test
	public void testCreateNomTacheNull() throws ProjetException {

		try {
			nomTacheManager.saveOrUpdate(null);
		} catch (ProjetException e) {
			Assert.assertEquals("Attention aucune tâche n'est créé", e
					.getMessage());
		}
	}

	@org.junit.Test
	public void testCreateTypeTacheNull() throws ProjetException {

		try {
			NomTacheTO eq = new NomTacheTO();
			eq.setNomTache(NOM_1);
			nomTacheManager.saveOrUpdate(eq);
		} catch (ProjetException e) {
			Assert
					.assertEquals(
							"Attention aucun groupe de tâche n'est rattaché à cette tâche",
							e.getMessage());
		}
	}

	@org.junit.Test
	public void testUpdateNominal() throws ProjetException {
		createData();
		NomTacheTO e = new NomTacheTO();
		e.setIdNomTache(nomTache1.getIdNomTache());
		e.setNomTache("test");
		TypeTacheTO typeTacheTO = new TypeTacheTO();
		typeTacheTO.setIdTypTach(typeTache.getIdTypeTache());
		e.setTypeTache(typeTacheTO);

		int id = nomTacheManager.saveOrUpdate(e);

		assertEquals(id, nomTache1.getIdNomTache());

		deleteData();
	}

	@org.junit.Test
	public void testUpdateTwice() throws ProjetException {
		createData();
		NomTacheTO e = new NomTacheTO();
		e.setIdNomTache(nomTache1.getIdNomTache());
		e.setNomTache(NOM_2);

		try {
			nomTacheManager.saveOrUpdate(e);
		} catch (ProjetException cE) {
			Assert.assertEquals(
					"Attention, une tâche avec ce nom existe déjà !", cE
							.getMessage());
		}

		deleteData();
	}

	@org.junit.Test
	public void testDeleteNominal() throws ProjetException {
		createData();

		nomTacheManager.delete(nomTache1.getIdNomTache());

		List<NomTacheTO> getAll = nomTacheManager.getAll();
		assertEquals(getAll.size(), 1);

		deleteData();
	}

	@org.junit.Test
	public void testDeleteIdNull() throws ProjetException {
		try {
			nomTacheManager.delete(0);
		} catch (ProjetException cE) {
			Assert.assertEquals("Attention aucune tâche n'est sélectionné", cE
					.getMessage());
		}
	}

	@org.junit.Test
	public void testDeleteIdUnknown() throws ProjetException {
		try {
			nomTacheManager.delete(1);
		} catch (ProjetException cE) {
			Assert
					.assertEquals(
							"Attention aucun nom de tache avec cet identifiant existe dans la base !",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testGetNominal() throws ProjetException {
		createData();
		NomTacheTO e = nomTacheManager.get(nomTache1.getIdNomTache());

		assertEquals(e.getNomTache(), nomTache1.getNomTache());

		deleteData();
	}

	@org.junit.Test
	public void testGetIdNull() throws ProjetException {
		try {
			nomTacheManager.get(0);
		} catch (ProjetException cE) {
			Assert.assertEquals(
					"Attention aucun nom de tache n'est sélectionné", cE
							.getMessage());
		}
	}

	@org.junit.Test
	public void testGetIdUnknown() throws ProjetException {
		try {
			nomTacheManager.get(1);
		} catch (ProjetException cE) {
			Assert
					.assertEquals(
							"Attention aucun nom de tache avec cet identifiant existe dans la base !",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testIsITacheNominal() throws ProjetException {
		createData();
		boolean verif = nomTacheManager.isInTache(nomTache1.getIdNomTache());

		assertEquals(verif, false);

		deleteData();
	}

	@org.junit.Test
	public void testIsInTacheIdNulll() throws ProjetException {

		try {
			nomTacheManager.isInTache(1);
		} catch (ProjetException cE) {
			Assert.assertEquals(
					"Attention aucun nom de tache n'est sélectionné", cE
							.getMessage());
		}
	}
}
