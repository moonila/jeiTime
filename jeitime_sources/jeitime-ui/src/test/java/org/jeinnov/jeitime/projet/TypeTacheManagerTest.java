package org.jeinnov.jeitime.projet;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.projet.ProjetException;
import org.jeinnov.jeitime.api.service.projet.TypeTacheManager;
import org.jeinnov.jeitime.api.to.projet.TypeTacheTO;
import org.jeinnov.jeitime.persistence.bo.projet.TypeTacheP;
import org.jeinnov.jeitime.persistence.dao.projet.TypeTacheDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import junit.framework.Assert;

public class TypeTacheManagerTest extends Assert {
	private TypeTacheManager typeTacheManager = TypeTacheManager.getInstance();
	private TypeTacheDAO typeTacheDAO = TypeTacheDAO.getInstance();

	private TypeTacheP typeTache1;
	private static String NOM_1 = "typeTache1";

	private TypeTacheP typeTache2;
	private static String NOM_2 = "typeTache2";

	public void createData() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			typeTache1 = new TypeTacheP();
			typeTache1.setNomTypeTache(NOM_1);

			typeTacheDAO.save(typeTache1);

			typeTache2 = new TypeTacheP();
			typeTache2.setNomTypeTache(NOM_2);

			typeTacheDAO.save(typeTache2);

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
			List<TypeTacheP> getAll = typeTacheDAO.getAll();

			if (getAll != null && !getAll.isEmpty()) {
				typeTacheDAO.removeAll(getAll);
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
		createData();

		List<TypeTacheTO> getAll = typeTacheManager.getAll();
		assertEquals(getAll.size(), 2);

		deleteData();
	}

	@org.junit.Test
	public void testCreateNominal() throws ProjetException {
		deleteData();
		TypeTacheTO eq = new TypeTacheTO();
		eq.setNomTypTach(NOM_1);

		int id = typeTacheManager.saveOrUpdate(eq);

		TypeTacheTO e = new TypeTacheTO();
		e = typeTacheManager.get(id);

		assertEquals(e.getNomTypTach(), eq.getNomTypTach());

		deleteData();
	}

	@org.junit.Test
	public void testCreateTwice() throws ProjetException {
		createData();
		TypeTacheTO eq = new TypeTacheTO();
		eq.setNomTypTach(NOM_1);

		try {
			typeTacheManager.saveOrUpdate(eq);
		} catch (ProjetException e) {
			Assert.assertEquals(
					"Attention,un type de Tâche avec ce nom existe déjà !", e
							.getMessage());
		}
		deleteData();
	}

	@org.junit.Test
	public void testCreateNameNull() throws ProjetException {
		TypeTacheTO eq = new TypeTacheTO();
		eq.setNomTypTach(null);

		try {
			typeTacheManager.saveOrUpdate(eq);
		} catch (ProjetException e) {
			Assert.assertEquals(
					"Attention aucun nom n'a été donné au type de Tâche ", e
							.getMessage());
		}
	}

	@org.junit.Test
	public void testCreateTypeTacheNull() throws ProjetException {

		try {
			typeTacheManager.saveOrUpdate(null);
		} catch (ProjetException e) {
			Assert.assertEquals(
					"Attention aucun type de Tâche n'est sélectionné", e
							.getMessage());
		}
	}

	@org.junit.Test
	public void testUpdateNominal() throws ProjetException {
		createData();
		TypeTacheTO e = new TypeTacheTO();
		e.setIdTypTach(typeTache1.getIdTypeTache());
		e.setNomTypTach("test");

		int id = typeTacheManager.saveOrUpdate(e);

		assertEquals(id, typeTache1.getIdTypeTache());

		deleteData();
	}

	@org.junit.Test
	public void testUpdateTwice() throws ProjetException {
		createData();
		TypeTacheTO e = new TypeTacheTO();
		e.setIdTypTach(typeTache1.getIdTypeTache());
		e.setNomTypTach(NOM_2);

		try {
			typeTacheManager.saveOrUpdate(e);
		} catch (ProjetException cE) {
			Assert.assertEquals(
					"Attention,un type de Tâche avec ce nom existe déjà !", cE
							.getMessage());
		}

		deleteData();
	}

	@org.junit.Test
	public void testDeleteNominal() throws ProjetException {
		createData();

		typeTacheManager.delete(typeTache1.getIdTypeTache());

		List<TypeTacheTO> getAll = typeTacheManager.getAll();
		assertEquals(getAll.size(), 1);

		deleteData();
	}

	@org.junit.Test
	public void testDeleteIdNull() throws ProjetException {
		try {
			typeTacheManager.delete(0);
		} catch (ProjetException cE) {
			Assert.assertEquals(
					"Attention aucun type de Tâche n'est sélectionné", cE
							.getMessage());
		}
	}

	@org.junit.Test
	public void testDeleteIdUnknown() throws ProjetException {
		try {
			typeTacheManager.delete(1);
		} catch (ProjetException cE) {
			Assert
					.assertEquals(
							"Attention aucun Type de Tâche existe avec cet identifiant dans la base.",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testGetNominal() throws ProjetException {
		createData();
		TypeTacheTO e = typeTacheManager.get(typeTache1.getIdTypeTache());

		assertEquals(e.getNomTypTach(), typeTache1.getNomTypeTache());

		deleteData();
	}

	@org.junit.Test
	public void testGetIdNull() throws ProjetException {
		try {
			typeTacheManager.get(0);
		} catch (ProjetException cE) {
			Assert.assertEquals(
					"Attention aucun Type de Tâche n'est sélectionné.", cE
							.getMessage());
		}
	}

	@org.junit.Test
	public void testGetIdUnknown() throws ProjetException {
		try {
			typeTacheManager.get(1);
		} catch (ProjetException cE) {
			Assert
					.assertEquals(
							"Attention aucun Type de Tâche existe avec cet identifiant dans la base.",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testIsInNomTacheNominal() throws ProjetException {
		createData();
		boolean verif = typeTacheManager.isInNomTache(typeTache1
				.getIdTypeTache());

		assertEquals(verif, false);

		deleteData();
	}

	@org.junit.Test
	public void testIsInNomTacheIdNulll() throws ProjetException {

		try {
			typeTacheManager.isInNomTache(1);
		} catch (ProjetException cE) {
			Assert.assertEquals(
					"Attention aucun Type de Tâche n'est sélectionné.", cE
							.getMessage());
		}
	}

	// @org.junit.Test
	// public void testDatabase () throws Exception {
	// DatabaseManager.getInstance().updateDatabaseSchema();
	// }
}
