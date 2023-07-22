package org.jeinnov.jeitime.projet;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.projet.ProjetException;
import org.jeinnov.jeitime.api.service.projet.ThematiqueManager;
import org.jeinnov.jeitime.api.to.projet.ThematiqueTO;
import org.jeinnov.jeitime.persistence.bo.projet.ThematiqueP;
import org.jeinnov.jeitime.persistence.dao.projet.ThematiqueDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import junit.framework.Assert;

public class ThematiqueManagerTest extends Assert {
	private ThematiqueManager thematiqueManager = ThematiqueManager
			.getInstance();
	private ThematiqueDAO thematiqueDAO = ThematiqueDAO.getInstance();

	private ThematiqueP thematique1;
	private static String NOM_1 = "thematique1";

	private ThematiqueP thematique2;
	private static String NOM_2 = "thematique2";

	public void createData() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			thematique1 = new ThematiqueP();
			thematique1.setNomThematique(NOM_1);

			thematiqueDAO.save(thematique1);

			thematique2 = new ThematiqueP();
			thematique2.setNomThematique(NOM_2);

			thematiqueDAO.save(thematique2);

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
			List<ThematiqueP> getAll = thematiqueDAO.getAll();

			if (getAll != null && !getAll.isEmpty()) {
				thematiqueDAO.removeAll(getAll);
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

		List<ThematiqueTO> getAll = thematiqueManager.getAll();
		assertEquals(getAll.size(), 2);

		deleteData();
	}

	@org.junit.Test
	public void testCreateNominal() throws ProjetException {
		ThematiqueTO eq = new ThematiqueTO();
		eq.setNomThema(NOM_1);

		int id = thematiqueManager.saveOrUpdate(eq);

		ThematiqueTO e = new ThematiqueTO();
		e = thematiqueManager.get(id);

		assertEquals(e.getNomThema(), eq.getNomThema());

		deleteData();
	}

	@org.junit.Test
	public void testCreateTwice() throws ProjetException {
		createData();
		ThematiqueTO eq = new ThematiqueTO();
		eq.setNomThema(NOM_1);

		try {
			thematiqueManager.saveOrUpdate(eq);
		} catch (ProjetException e) {
			Assert.assertEquals(
					"Attention, une thématique avec ce nom existe déjà !", e
							.getMessage());
		}
		deleteData();
	}

	@org.junit.Test
	public void testCreateNameNull() throws ProjetException {
		ThematiqueTO eq = new ThematiqueTO();
		eq.setNomThema(null);

		try {
			thematiqueManager.saveOrUpdate(eq);
		} catch (ProjetException e) {
			Assert
					.assertEquals(
							"Attention vous devez spécifier un nom à la nouvelle thématique !",
							e.getMessage());
		}
	}

	@org.junit.Test
	public void testCreateThematiqueNull() throws ProjetException {

		try {
			thematiqueManager.saveOrUpdate(null);
		} catch (ProjetException e) {
			Assert.assertEquals(
					"Attention aucune thématique n'est sélectionnée", e
							.getMessage());
		}
	}

	@org.junit.Test
	public void testUpdateNominal() throws ProjetException {
		createData();
		ThematiqueTO e = new ThematiqueTO();
		e.setIdThema(thematique1.getIdThematique());
		e.setNomThema("test");

		int id = thematiqueManager.saveOrUpdate(e);

		assertEquals(id, thematique1.getIdThematique());

		deleteData();
	}

	@org.junit.Test
	public void testUpdateTwice() throws ProjetException {
		createData();
		ThematiqueTO e = new ThematiqueTO();
		e.setIdThema(thematique1.getIdThematique());
		e.setNomThema(NOM_2);

		try {
			thematiqueManager.saveOrUpdate(e);
		} catch (ProjetException cE) {
			Assert.assertEquals(
					"Attention,une thématique avec ce nom existe déjà !", cE
							.getMessage());
		}

		deleteData();
	}

	@org.junit.Test
	public void testDeleteNominal() throws ProjetException {
		createData();

		thematiqueManager.delete(thematique1.getIdThematique());

		List<ThematiqueTO> getAll = thematiqueManager.getAll();
		assertEquals(getAll.size(), 1);

		deleteData();
	}

	@org.junit.Test
	public void testDeleteIdNull() throws ProjetException {
		try {
			thematiqueManager.delete(0);
		} catch (ProjetException cE) {
			Assert.assertEquals(
					"Attention aucune thématique n'est sélectionnée", cE
							.getMessage());
		}
	}

	@org.junit.Test
	public void testDeleteIdUnknown() throws ProjetException {
		try {
			thematiqueManager.delete(1);
		} catch (ProjetException cE) {
			Assert
					.assertEquals(
							"Attention aucune Thématique existe avec cet identifiant dans la base.",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testGetNominal() throws ProjetException {
		createData();
		ThematiqueTO e = thematiqueManager.get(thematique1.getIdThematique());

		assertEquals(e.getNomThema(), thematique1.getNomThematique());

		deleteData();
	}

	@org.junit.Test
	public void testGetIdNull() throws ProjetException {
		try {
			thematiqueManager.get(0);
		} catch (ProjetException cE) {
			Assert.assertEquals(
					"Attention aucune thématique n'est sélectionnée", cE
							.getMessage());
		}
	}

	@org.junit.Test
	public void testGetIdUnknown() throws ProjetException {
		try {
			thematiqueManager.get(1);
		} catch (ProjetException cE) {
			Assert
					.assertEquals(
							"Attention aucune Thématique existe avec cet identifiant dans la base.",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testIsInProjectNominal() throws ProjetException {
		createData();
		boolean verif = thematiqueManager.isInProject(thematique1
				.getIdThematique());

		assertEquals(verif, false);

		deleteData();
	}

	@org.junit.Test
	public void testIsInProjectIdNulll() throws ProjetException {

		try {
			thematiqueManager.isInProject(1);
		} catch (ProjetException cE) {
			Assert.assertEquals(
					"Attention aucune thématique n'est sélectionnée", cE
							.getMessage());
		}
	}
}
