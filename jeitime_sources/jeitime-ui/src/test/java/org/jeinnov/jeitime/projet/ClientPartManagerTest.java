package org.jeinnov.jeitime.projet;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.projet.ClientPartManager;
import org.jeinnov.jeitime.api.service.projet.ProjetException;
import org.jeinnov.jeitime.api.to.projet.ClientPartTO;
import org.jeinnov.jeitime.persistence.bo.projet.ClientPartP;
import org.jeinnov.jeitime.persistence.dao.projet.ClientPartDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import junit.framework.Assert;

public class ClientPartManagerTest extends Assert {
	private ClientPartManager clientPartManager = ClientPartManager
			.getInstance();
	private ClientPartDAO clientPartDAO = ClientPartDAO.getInstance();

	private ClientPartP clientPart1;
	private static String NOM_1 = "equipe1";
	private static String COMMENTAIRE_1 = "commentaire 1";
	private static String CONTACT_1 = "contact1";
	private static String SERVICE_1 = "service1";

	private ClientPartP clientPart2;
	private static String NOM_2 = "equipe2";
	private static String COMMENTAIRE_2 = "commentaire 2";
	private static String CONTACT_2 = "contact2";
	private static String SERVICE_2 = "service2";

	public void createData() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			clientPart1 = new ClientPartP();
			clientPart1.setNomClientPart(NOM_1);
			clientPart1.setNomContact(CONTACT_1);
			clientPart1.setCommentaire(COMMENTAIRE_1);
			clientPart1.setNomService(SERVICE_1);

			clientPartDAO.save(clientPart1);

			clientPart2 = new ClientPartP();
			clientPart2.setNomClientPart(NOM_2);
			clientPart2.setNomContact(CONTACT_2);
			clientPart2.setCommentaire(COMMENTAIRE_2);
			clientPart2.setNomService(SERVICE_2);

			clientPartDAO.save(clientPart2);

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
			List<ClientPartP> getAll = clientPartDAO.getAll();

			if (getAll != null && !getAll.isEmpty()) {
				clientPartDAO.removeAll(getAll);
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

		List<ClientPartTO> getAll = clientPartManager.getAll();
		assertEquals(getAll.size(), 2);

		deleteData();
	}

	@org.junit.Test
	public void testCreateNominal() throws ProjetException {
		ClientPartTO eq = new ClientPartTO();
		eq.setNomClientPart(NOM_1);
		eq.setCommentaire(COMMENTAIRE_1);
		eq.setNomContact(CONTACT_1);
		eq.setNomService(SERVICE_1);
		int id = clientPartManager.saveOrUpdate(eq);

		ClientPartTO e = new ClientPartTO();
		e = clientPartManager.get(id);

		assertEquals(e.getNomClientPart(), eq.getNomClientPart());
		deleteData();
	}

	@org.junit.Test
	public void testCreateNameNull() throws ProjetException {
		ClientPartTO eq = new ClientPartTO();
		eq.setNomClientPart(null);

		try {
			clientPartManager.saveOrUpdate(eq);
		} catch (ProjetException e) {
			Assert
					.assertEquals(
							"Attention vous devez donné un nom à votre client/partenaire !",
							e.getMessage());
		}
	}

	@org.junit.Test
	public void testCreateEquipeNull() throws ProjetException {

		try {
			clientPartManager.saveOrUpdate(null);
		} catch (ProjetException e) {
			Assert.assertEquals("Aucun client ou partenaire n'a été créé ", e
					.getMessage());
		}
	}

	@org.junit.Test
	public void testUpdateNominal() throws ProjetException {
		createData();
		ClientPartTO e = new ClientPartTO();
		e.setIdClientPart(clientPart1.getIdClientPart());
		e.setNomClientPart("test");
		e.setCommentaire(COMMENTAIRE_1);

		int id = clientPartManager.saveOrUpdate(e);

		assertEquals(id, clientPart1.getIdClientPart());

		deleteData();
	}

	@org.junit.Test
	public void testDeleteNominal() throws ProjetException {
		createData();

		clientPartManager.delete(clientPart1.getIdClientPart());

		List<ClientPartTO> getAll = clientPartManager.getAll();
		assertEquals(getAll.size(), 1);

		deleteData();
	}

	@org.junit.Test
	public void testDeleteIdNull() throws ProjetException {
		try {
			clientPartManager.delete(0);
		} catch (ProjetException cE) {
			Assert.assertEquals("Aucun client ou partenaire n'a été créé ", cE
					.getMessage());
		}
	}

	@org.junit.Test
	public void testDeleteIdUnknown() throws ProjetException {
		try {
			clientPartManager.delete(1);
		} catch (ProjetException cE) {
			Assert
					.assertEquals(
							"Le client/partenaire sélectionné n'existe pas dans la base de données.",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testGetNominal() throws ProjetException {
		createData();
		ClientPartTO e = clientPartManager.get(clientPart1.getIdClientPart());

		assertEquals(e.getNomClientPart(), clientPart1.getNomClientPart());

		deleteData();
	}

	@org.junit.Test
	public void testGetIdNull() throws ProjetException {
		try {
			clientPartManager.get(0);
		} catch (ProjetException cE) {
			Assert.assertEquals(
					"Attention aucun client ou partenaire n'est spécifié !", cE
							.getMessage());
		}
	}

	@org.junit.Test
	public void testGetIdUnknown() throws ProjetException {
		try {
			clientPartManager.get(1);
		} catch (ProjetException cE) {
			Assert
					.assertEquals(
							"Aucun client ou partenaire avec cet identifiant est enregistré dans la base",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testIsInProjectNominal() throws ProjetException {
		createData();
		boolean verif = clientPartManager.isInProject((clientPart1
				.getIdClientPart()));

		assertEquals(verif, false);

		deleteData();
	}

	@org.junit.Test
	public void testIsInProjectIdNulll() throws ProjetException {

		try {
			clientPartManager.isInProject(1);
		} catch (ProjetException cE) {
			Assert.assertEquals(
					"Attention aucun client ou partenaire n'est spécifié !", cE
							.getMessage());
		}
	}
}
