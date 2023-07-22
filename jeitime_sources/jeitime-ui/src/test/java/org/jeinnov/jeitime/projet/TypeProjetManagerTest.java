package org.jeinnov.jeitime.projet;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.projet.ProjetException;
import org.jeinnov.jeitime.api.service.projet.ProjetManager;
import org.jeinnov.jeitime.api.service.projet.TypeProjetManager;
import org.jeinnov.jeitime.api.to.projet.TypeProjetTO;
import org.jeinnov.jeitime.persistence.bo.projet.ProjetP;
import org.jeinnov.jeitime.persistence.bo.projet.TypeProjetP;
import org.jeinnov.jeitime.persistence.dao.projet.ProjetDAO;
import org.jeinnov.jeitime.persistence.dao.projet.TypeProjetDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import junit.framework.Assert;

public class TypeProjetManagerTest extends Assert {
	private TypeProjetManager typeProjetManager = TypeProjetManager
			.getInstance();
	private TypeProjetDAO typeProjetDAO = TypeProjetDAO.getInstance();

	private TypeProjetP typeProjet1;
	private static String NOM_1 = "typeProjet1";

	private TypeProjetP typeProjet2;
	private static String NOM_2 = "typeProjet2";

	private ProjetP p;

	private void createData() {
		// deleteData();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			typeProjet1 = new TypeProjetP();
			typeProjet1.setNomTypePro(NOM_1);

			typeProjetDAO.save(typeProjet1);

			typeProjet2 = new TypeProjetP();
			typeProjet2.setNomTypePro(NOM_2);

			typeProjetDAO.save(typeProjet2);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	private void deleteData() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<TypeProjetP> getAll = typeProjetDAO.getAll();

			if (getAll != null && !getAll.isEmpty()) {
				typeProjetDAO.removeAll(getAll);
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	private void createProjectData() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			p = new ProjetP();
			p.setNomProjet("projet1");
			p.setDateDeb(new Date());
			p.setTypeProjet(typeProjet1);

			ProjetDAO.getInstance().save(p);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	

	@org.junit.Test
	public void testCreateNominal() throws ProjetException {
		deleteData();

		TypeProjetTO eq = new TypeProjetTO();
		eq.setNomTypePro(NOM_1);

		int id = typeProjetManager.saveOrUpdate(eq);

		TypeProjetTO e = new TypeProjetTO();
		e = typeProjetManager.get(id);

		assertEquals(e.getNomTypePro(), eq.getNomTypePro());

		deleteData();
	}

	@org.junit.Test
	public void testCreateTwice() throws ProjetException {
		createData();
		TypeProjetTO eq = new TypeProjetTO();
		eq.setNomTypePro(NOM_1);

		try {
			typeProjetManager.saveOrUpdate(eq);
		} catch (ProjetException e) {
			Assert.assertEquals(
					"Attention,un type de projet avec ce nom existe déjà !", e
							.getMessage());
		}
		deleteData();
	}

	@org.junit.Test
	public void testCreateNameNull() throws ProjetException {
		TypeProjetTO eq = new TypeProjetTO();
		eq.setNomTypePro(null);

		try {
			typeProjetManager.saveOrUpdate(eq);
		} catch (ProjetException e) {
			Assert.assertEquals(
					"Attention aucun nom n'a été donné au type de projet ", e
							.getMessage());
		}
	}

	@org.junit.Test
	public void testCreateTypeProjetNull() throws ProjetException {

		try {
			typeProjetManager.saveOrUpdate(null);
		} catch (ProjetException e) {
			Assert.assertEquals(
					"Attention aucun type de projet n'est sélectionné", e
							.getMessage());
		}
	}

	@org.junit.Test
	public void testUpdateNominal() throws ProjetException {
		createData();
		TypeProjetTO e = new TypeProjetTO();
		e.setIdTypeProj(typeProjet1.getIdTypePro());
		e.setNomTypePro("test");

		int id = typeProjetManager.saveOrUpdate(e);

		assertEquals(id, typeProjet1.getIdTypePro());

		deleteData();
	}

	@org.junit.Test
	public void testUpdateTwice() throws ProjetException {
		createData();
		TypeProjetTO e = new TypeProjetTO();
		e.setIdTypeProj(typeProjet1.getIdTypePro());
		e.setNomTypePro(NOM_2);

		try {
			typeProjetManager.saveOrUpdate(e);
		} catch (ProjetException cE) {
			Assert.assertEquals(
					"Attention,un type de projet avec ce nom existe déjà !", cE
							.getMessage());
		}

		deleteData();
	}

	@org.junit.Test
	public void testDeleteNominal() throws ProjetException {
		createData();

		typeProjetManager.delete(typeProjet1.getIdTypePro());

		List<TypeProjetTO> getAll = typeProjetManager.getAll();
		assertEquals(getAll.size(), 1);

		deleteData();
	}

	@org.junit.Test
	public void testDeleteBeforeProjectDeletion() throws ProjetException {
		createData();
		createProjectData();

		try {
			typeProjetManager.delete(typeProjet1.getIdTypePro());
		} catch (ProjetException cE) {
			Assert
					.assertEquals(
							"Attention ce Type de projet ne pourra être supprimé, car des projets lui sont rattachés.",
							cE.getMessage());
		}
		ProjetManager.getInstance().delete(p.getIdProjet());
		deleteData();
	}

	@org.junit.Test
	public void testDeleteIdNull() throws ProjetException {
		try {
			typeProjetManager.delete(0);
		} catch (ProjetException cE) {
			Assert.assertEquals(
					"Attention aucun type de projet n'est sélectionné", cE
							.getMessage());
		}
	}

	@org.junit.Test
	public void testDeleteIdUnknown() throws ProjetException {
		try {
			typeProjetManager.delete(1);
		} catch (ProjetException cE) {
			Assert
					.assertEquals(
							"Attention aucun Type de Projet existe avec cet identifiant dans la base.",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testGetNominal() throws ProjetException {
		createData();
		TypeProjetTO e = typeProjetManager.get(typeProjet1.getIdTypePro());

		assertEquals(e.getNomTypePro(), typeProjet1.getNomTypePro());

		deleteData();
	}

	@org.junit.Test
	public void testGetIdNull() throws ProjetException {
		try {
			typeProjetManager.get(0);
		} catch (ProjetException cE) {
			Assert.assertEquals(
					"Attention aucun Type de Projet n'est sélectionné.", cE
							.getMessage());
		}
	}

	@org.junit.Test
	public void testGetIdUnknown() throws ProjetException {
		try {
			typeProjetManager.get(1);
		} catch (ProjetException cE) {
			Assert
					.assertEquals(
							"Attention aucun Type de Projet existe avec cet identifiant dans la base.",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testIsInProjectNominal() throws ProjetException {
		createData();
		boolean verif = typeProjetManager
				.isInProjet(typeProjet1.getIdTypePro());

		assertEquals(verif, false);

		deleteData();
	}

	@org.junit.Test
	public void testIsInProjectIdNulll() throws ProjetException {

		try {
			typeProjetManager.isInProjet(1);
		} catch (ProjetException cE) {
			Assert.assertEquals(
					"Attention aucun Type de Projet n'est sélectionné.", cE
							.getMessage());
		}
	}
	
	@org.junit.Test
	public void testGetAll() {
		deleteData();
		createData();

		List<TypeProjetTO> getAll = typeProjetManager.getAll();
		assertEquals(getAll.size(), 2);

		deleteData();
	}
}
