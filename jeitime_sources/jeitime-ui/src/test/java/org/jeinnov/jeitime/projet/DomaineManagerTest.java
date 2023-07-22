package org.jeinnov.jeitime.projet;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.projet.DomaineManager;
import org.jeinnov.jeitime.api.service.projet.ProjetException;
import org.jeinnov.jeitime.api.to.projet.DomaineTO;
import org.jeinnov.jeitime.persistence.bo.projet.DomaineP;
import org.jeinnov.jeitime.persistence.dao.projet.DomaineDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import junit.framework.Assert;

public class DomaineManagerTest extends Assert {
	private DomaineManager domaineManager = DomaineManager.getInstance();
	private DomaineDAO domaineDAO = DomaineDAO.getInstance();

	private DomaineP domaine1;
	private static String NOM_1 = "domaine1";

	private DomaineP domaine2;
	private static String NOM_2 = "domaine2";

	public void createData() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			domaine1 = new DomaineP();
			domaine1.setNomDomaine(NOM_1);

			domaineDAO.save(domaine1);

			domaine2 = new DomaineP();
			domaine2.setNomDomaine(NOM_2);

			domaineDAO.save(domaine2);

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
			List<DomaineP> getAll = domaineDAO.getAll();

			if (getAll != null && !getAll.isEmpty()) {
				domaineDAO.removeAll(getAll);
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

		List<DomaineTO> getAll = domaineManager.getAll();
		assertEquals(getAll.size(), 2);

		deleteData();
	}

	@org.junit.Test
	public void testCreateNominal() throws ProjetException {
		DomaineTO eq = new DomaineTO();
		eq.setNomDomaine(NOM_1);

		int id = domaineManager.saveOrUpdate(eq);

		DomaineTO e = new DomaineTO();
		e = domaineManager.get(id);

		assertEquals(e.getNomDomaine(), eq.getNomDomaine());

		deleteData();
	}

	@org.junit.Test
	public void testCreateTwice() throws ProjetException {
		createData();
		DomaineTO eq = new DomaineTO();
		eq.setNomDomaine(NOM_1);

		try {
			domaineManager.saveOrUpdate(eq);
		} catch (ProjetException e) {
			Assert.assertEquals(
					"Attention, un domaine avec ce nom existe déjà !", e
							.getMessage());
		}
		deleteData();
	}

	@org.junit.Test
	public void testCreateNameNull() throws ProjetException {
		DomaineTO eq = new DomaineTO();
		eq.setNomDomaine(null);

		try {
			domaineManager.saveOrUpdate(eq);
		} catch (ProjetException e) {
			Assert.assertEquals(
					"Attention aucun nom n'a été donné au domaine ", e
							.getMessage());
		}
	}

	@org.junit.Test
	public void testCreateDomaineNull() throws ProjetException {

		try {
			domaineManager.saveOrUpdate(null);
		} catch (ProjetException e) {
			Assert.assertEquals("Attention aucun domaine n'est sélectionné", e
					.getMessage());
		}
	}

	@org.junit.Test
	public void testUpdateNominal() throws ProjetException {
		createData();
		DomaineTO e = new DomaineTO();
		e.setIdDomaine(domaine1.getIdDomaine());
		e.setNomDomaine("test");

		int id = domaineManager.saveOrUpdate(e);

		assertEquals(id, domaine1.getIdDomaine());

		deleteData();
	}

	@org.junit.Test
	public void testUpdateTwice() throws ProjetException {
		createData();
		DomaineTO e = new DomaineTO();
		e.setIdDomaine(domaine1.getIdDomaine());
		e.setNomDomaine(NOM_2);

		try {
			domaineManager.saveOrUpdate(e);
		} catch (ProjetException cE) {
			Assert.assertEquals(
					"Attention, un domaine avec ce nom existe déjà !", cE
							.getMessage());
		}

		deleteData();
	}

	@org.junit.Test
	public void testDeleteNominal() throws ProjetException {
		createData();

		domaineManager.delete(domaine1.getIdDomaine());

		List<DomaineTO> getAll = domaineManager.getAll();
		assertEquals(getAll.size(), 1);

		deleteData();
	}

	@org.junit.Test
	public void testDeleteIdNull() throws ProjetException {
		try {
			domaineManager.delete(0);
		} catch (ProjetException cE) {
			Assert.assertEquals("Attention aucun domaine n'est sélectionné", cE
					.getMessage());
		}
	}

	@org.junit.Test
	public void testDeleteIdUnknown() throws ProjetException {
		try {
			domaineManager.delete(1);
		} catch (ProjetException cE) {
			Assert
					.assertEquals(
							"Attention aucun domaine avec cet identifiant existe en base !",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testGetNominal() throws ProjetException {
		createData();
		DomaineTO e = domaineManager.get(domaine1.getIdDomaine());

		assertEquals(e.getNomDomaine(), domaine1.getNomDomaine());

		deleteData();
	}

	@org.junit.Test
	public void testGetIdNull() throws ProjetException {
		try {
			domaineManager.get(0);
		} catch (ProjetException cE) {
			Assert.assertEquals("Attention aucun domaine n'est sélectionné", cE
					.getMessage());
		}
	}

	@org.junit.Test
	public void testGetIdUnknown() throws ProjetException {
		try {
			domaineManager.get(1);
		} catch (ProjetException cE) {
			Assert
					.assertEquals(
							"Attention aucun domaine avec cet identifiant existe en base !",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testIsInProjectNominal() throws ProjetException {
		createData();
		boolean verif = domaineManager.isInProject(domaine1.getIdDomaine());

		assertEquals(verif, false);

		deleteData();
	}

	@org.junit.Test
	public void testIsInProjectIdNulll() throws ProjetException {

		try {
			domaineManager.isInProject(1);
		} catch (ProjetException cE) {
			Assert.assertEquals("Attention aucun domaine n'est sélectionné", cE
					.getMessage());
		}
	}
}
