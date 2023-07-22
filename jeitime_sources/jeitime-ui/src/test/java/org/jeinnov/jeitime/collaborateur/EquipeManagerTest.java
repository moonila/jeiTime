package org.jeinnov.jeitime.collaborateur;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurException;
import org.jeinnov.jeitime.api.service.collaborateur.EquipeManager;
import org.jeinnov.jeitime.api.to.collaborateur.EquipeTO;
import org.jeinnov.jeitime.persistence.bo.collaborateur.EquipeP;
import org.jeinnov.jeitime.persistence.dao.collaborateur.EquipeDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


import junit.framework.Assert;

public class EquipeManagerTest extends Assert {
	EquipeManager equipeManager = EquipeManager.getInstance();
	EquipeDAO equipeDAO = EquipeDAO.getInstance();

	private EquipeP equipe1;
	private static String NOM_EQ1 = "equipe1";
	private static String FCT_EQ = "fonction1";

	private EquipeP equipe2;
	private static String NOM_EQ2 = "equipe2";

	public void createData() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			equipe1 = new EquipeP();
			equipe1.setNomEquip(NOM_EQ1);
			equipe1.setFonctionEquip(FCT_EQ);

			equipeDAO.save(equipe1);

			equipe2 = new EquipeP();
			equipe2.setNomEquip(NOM_EQ2);
			equipe2.setFonctionEquip(FCT_EQ);

			equipeDAO.save(equipe2);

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
			List<EquipeP> getAll = equipeDAO.getAll();

			if (getAll != null && !getAll.isEmpty()) {
				equipeDAO.removeAll(getAll);
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

		List<EquipeTO> getAll = equipeManager.getAll();
		assertEquals(getAll.size(), 2);

		deleteData();
	}

	@org.junit.Test
	public void testCreateNominal() throws CollaborateurException {
		EquipeTO eq = new EquipeTO();
		eq.setNomEquip(NOM_EQ1);
		eq.setFonctionEquip(FCT_EQ);
		int id = equipeManager.saveOrUpdate(eq);

		EquipeTO e = new EquipeTO();
		e = equipeManager.get(id);

		assertEquals(e.getNomEquip(), eq.getNomEquip());

		deleteData();
	}

	@org.junit.Test
	public void testCreateTwice() throws CollaborateurException {
		createData();
		EquipeTO eq = new EquipeTO();
		eq.setNomEquip(NOM_EQ1);
		eq.setFonctionEquip(FCT_EQ);
		try {
			equipeManager.saveOrUpdate(eq);
		} catch (CollaborateurException e) {
			Assert.assertEquals(
					"Attention, une équipe avec ce nom existe déjà !", e
							.getMessage());
		}
		deleteData();
	}

	@org.junit.Test
	public void testCreateNameNull() throws CollaborateurException {
		EquipeTO eq = new EquipeTO();
		eq.setNomEquip(null);

		try {
			equipeManager.saveOrUpdate(eq);
		} catch (CollaborateurException e) {
			Assert
					.assertEquals(
							"vous devez spécifier un nom à l'équipe avant sa sauvegarde !",
							e.getMessage());
		}
	}

	@org.junit.Test
	public void testCreateEquipeNull() throws CollaborateurException {

		try {
			equipeManager.saveOrUpdate(null);
		} catch (CollaborateurException e) {
			Assert.assertEquals(
					"vous devez spécifier une équipe avant sa sauvegarde !", e
							.getMessage());
		}
	}

	@org.junit.Test
	public void testUpdateNominal() throws CollaborateurException {
		createData();
		EquipeTO e = new EquipeTO();
		e.setIdEquip(equipe1.getIdEquip());
		e.setNomEquip("equipe");
		e.setFonctionEquip(FCT_EQ);

		int id = equipeManager.saveOrUpdate(e);

		assertEquals(id, equipe1.getIdEquip());

		deleteData();
	}

	@org.junit.Test
	public void testUpdateTwice() throws CollaborateurException {
		createData();
		EquipeTO e = new EquipeTO();
		e.setIdEquip(equipe1.getIdEquip());
		e.setNomEquip(NOM_EQ2);
		e.setFonctionEquip(FCT_EQ);

		try {
			equipeManager.saveOrUpdate(e);
		} catch (CollaborateurException cE) {
			Assert.assertEquals(
					"Attention, une équipe avec ce nom existe déjà !", cE
							.getMessage());
		}

		deleteData();
	}

	@org.junit.Test
	public void testUpdateNominalFct() throws CollaborateurException {
		createData();
		EquipeTO e = new EquipeTO();
		e.setIdEquip(equipe1.getIdEquip());
		e.setNomEquip(NOM_EQ1);
		e.setFonctionEquip("une nouvelle fonction");

		int id = equipeManager.saveOrUpdate(e);

		assertEquals(id, equipe1.getIdEquip());

		deleteData();
	}

	@org.junit.Test
	public void testDeleteNominal() throws CollaborateurException {
		createData();

		equipeManager.deleteEquipe(equipe1.getIdEquip());

		List<EquipeTO> getAll = equipeManager.getAll();
		assertEquals(getAll.size(), 1);

		deleteData();
	}

	@org.junit.Test
	public void testDeleteIdNull() throws CollaborateurException {
		try {
			equipeManager.deleteEquipe(0);
		} catch (CollaborateurException cE) {
			Assert.assertEquals("Aucune équipe n'est spécifiée !", cE
					.getMessage());
		}
	}

	@org.junit.Test
	public void testDeleteIdUnknown() throws CollaborateurException {
		try {
			equipeManager.deleteEquipe(1);
		} catch (CollaborateurException cE) {
			Assert.assertEquals(
					"L'équipe sélectionné n'existe pas dans la base.", cE
							.getMessage());
		}
	}

	@org.junit.Test
	public void testGetNominal() throws CollaborateurException {
		createData();
		EquipeTO e = equipeManager.get(equipe1.getIdEquip());

		assertEquals(e.getNomEquip(), equipe1.getNomEquip());

		deleteData();
	}

	@org.junit.Test
	public void testGetIdNull() throws CollaborateurException {
		try {
			equipeManager.get(0);
		} catch (CollaborateurException cE) {
			Assert.assertEquals("Aucune équipe n'est spécifiée !", cE
					.getMessage());
		}
	}

	@org.junit.Test
	public void testGetIdUnknown() throws CollaborateurException {
		try {
			equipeManager.get(1);
		} catch (CollaborateurException cE) {
			Assert.assertEquals(
					"L'équipe sélectionné n'existe pas dans la base.", cE
							.getMessage());
		}
	}

	@org.junit.Test
	public void testIsInCollabNominal() throws CollaborateurException {
		createData();
		boolean verif = equipeManager.isInCollaborateur(equipe1.getIdEquip());

		assertEquals(verif, false);

		deleteData();
	}

	@org.junit.Test
	public void testIsInCollabIdNulll() throws CollaborateurException {

		try {
			equipeManager.isInCollaborateur(1);
		} catch (CollaborateurException cE) {
			Assert.assertEquals("Aucune équipe n'est spécifiée !", cE
					.getMessage());
		}
	}

}
