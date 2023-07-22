package org.jeinnov.jeitime.projet;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.projet.ContratManager;
import org.jeinnov.jeitime.api.service.projet.ProjetException;
import org.jeinnov.jeitime.api.to.projet.ClientPartTO;
import org.jeinnov.jeitime.persistence.bo.projet.ClientPartP;
import org.jeinnov.jeitime.persistence.bo.projet.ContratIdP;
import org.jeinnov.jeitime.persistence.bo.projet.ContratP;
import org.jeinnov.jeitime.persistence.bo.projet.ProjetP;
import org.jeinnov.jeitime.persistence.bo.projet.TypeProjetP;
import org.jeinnov.jeitime.persistence.dao.projet.ClientPartDAO;
import org.jeinnov.jeitime.persistence.dao.projet.ContratDAO;
import org.jeinnov.jeitime.persistence.dao.projet.ProjetDAO;
import org.jeinnov.jeitime.persistence.dao.projet.TypeProjetDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


import junit.framework.Assert;

public class ContratManagerTest extends Assert {
	private ProjetDAO projetDAO = ProjetDAO.getInstance();
	private ClientPartDAO clientPartDAO = ClientPartDAO.getInstance();
	private ContratDAO contratDAO = ContratDAO.getInstance();
	private ContratManager contratManager = ContratManager.getInstance();

	private ContratP contrat1;
	private int TYPE_1 = 0;
	private ContratP contrat2;
	private int TYPE_2 = 0;
	ContratIdP contratIdP;

	private ClientPartP clientPart1;
	private static String NOM_1 = "equipe1";
	private static String COMMENTAIRE_1 = "commentaire 1";
	private static String CONTACT_1 = "contact1";
	private static String SERVICE_1 = "service1";

	private ClientPartP clientPart2;
	private static String NOM_2 = "equipe2";

	private static String NOM_PROJET = "projet1";
	private static Date DATEDEBUT = new Date();
	private TypeProjetP typePro;
	private ProjetP projet1;

	public void createAllData() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			clientPart1 = new ClientPartP();
			clientPart1.setNomClientPart(NOM_1);
			clientPart1.setNomContact(CONTACT_1);
			clientPart1.setNomService(SERVICE_1);
			clientPart1.setCommentaire(COMMENTAIRE_1);

			clientPart2 = new ClientPartP();
			clientPart2.setNomClientPart(NOM_2);

			clientPartDAO.save(clientPart1);
			clientPartDAO.save(clientPart2);

			typePro = new TypeProjetP();
			typePro.setNomTypePro("NomTypeProjet");
			TypeProjetDAO.getInstance().save(typePro);
			projet1 = new ProjetP();
			projet1.setTypeProjet(typePro);
			projet1.setNomProjet(NOM_PROJET);
			projet1.setDateDeb(DATEDEBUT);

			projetDAO.save(projet1);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void createDataContact() {
		createAllData();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			contrat1 = new ContratP();
			contrat1.setClientPart(clientPart1);
			contrat1.setProjet(projet1);
			contrat1.setType(TYPE_1);

			contratIdP = new ContratIdP();
			contratIdP.setIdClientPart(clientPart1.getIdClientPart());
			contratIdP.setIdProjet(projet1.getIdProjet());
			contrat1.setId(contratIdP);

			contrat2 = new ContratP();
			contrat2.setClientPart(clientPart2);
			contrat2.setProjet(projet1);
			contrat2.setType(TYPE_2);

			contratIdP = new ContratIdP();
			contratIdP.setIdClientPart(clientPart2.getIdClientPart());
			contratIdP.setIdProjet(projet1.getIdProjet());
			contrat2.setId(contratIdP);

			contratDAO.save(contrat1);
			contratDAO.save(contrat2);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteDataContrat() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<ContratP> listC = contratDAO.findAll();

			for (ContratP contrat : listC) {
				contratDAO.remove(contrat);
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteDataProjet() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			projetDAO.remove(projet1);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteDataCliPart() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			clientPartDAO.remove(clientPart1);
			clientPartDAO.remove(clientPart2);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	@org.junit.Test
	public void testGetClientPartByProjectIdInContrat() throws ProjetException {
		createDataContact();
		List<ClientPartTO> tablCliPart = contratManager
				.getClientPartByProjectIdInContrat(projet1.getIdProjet());

		assertEquals(tablCliPart.size(), 2);

		deleteDataContrat();
		deleteDataCliPart();
		deleteDataProjet();
	}

	@org.junit.Test
	public void testGetClientPartByProjectIdInContratIdNull()
			throws ProjetException {
		try {
			contratManager.getClientPartByProjectIdInContrat(0);
		} catch (ProjetException e) {
			Assert.assertEquals("Aucun projet n'est associé", e.getMessage());
		}
	}

	@org.junit.Test
	public void testSaveNominal() throws ProjetException {
		createAllData();

		contratManager.save(projet1.getIdProjet(), clientPart1
				.getIdClientPart(), TYPE_1);

		List<ClientPartTO> tablCliPart = contratManager
				.getClientPartByProjectIdInContrat(projet1.getIdProjet());

		assertEquals(tablCliPart.size(), 1);

		deleteDataContrat();
		deleteDataCliPart();
		deleteDataProjet();
	}

	@org.junit.Test
	public void testSaveIdProjetNull() throws ProjetException {
		createAllData();
		try {
			contratManager.save(0, clientPart1.getIdClientPart(), TYPE_1);
		} catch (ProjetException e) {
			Assert.assertEquals(
					"Aucun projet ou client/partenaire sélectionnés", e
							.getMessage());
		}
		deleteDataContrat();
		deleteDataCliPart();
		deleteDataProjet();
	}

	@org.junit.Test
	public void testSaveIdCliPartNull() throws ProjetException {
		createAllData();
		try {
			contratManager.save(projet1.getIdProjet(), 0, TYPE_1);
		} catch (ProjetException e) {
			Assert.assertEquals(
					"Aucun projet ou client/partenaire sélectionnés", e
							.getMessage());
		}
		deleteDataContrat();
		deleteDataCliPart();
		deleteDataProjet();
	}

	@org.junit.Test
	public void testDeleteNominalByProject() throws ProjetException {
		createDataContact();
		List<ClientPartTO> tablCliPart = contratManager
				.getClientPartByProjectIdInContrat(projet1.getIdProjet());

		assertEquals(tablCliPart.size(), 2);

		contratManager.delete(projet1.getIdProjet());

		tablCliPart = contratManager.getClientPartByProjectIdInContrat(projet1
				.getIdProjet());

		assertEquals(tablCliPart.size(), 0);

		deleteDataContrat();
		deleteDataCliPart();
		deleteDataProjet();
	}

	@org.junit.Test
	public void testDeleteNominalByProjectNull() throws ProjetException {
		try {
			contratManager.delete(0);
		} catch (ProjetException e) {
			Assert.assertEquals(
					"Aucun projet ou client/partenaire sélectionnés", e
							.getMessage());
		}

	}

	@org.junit.Test
	public void testDeleteNominalByProjectAndCliPart() throws ProjetException {
		createDataContact();
		List<ClientPartTO> tablCliPart = contratManager
				.getClientPartByProjectIdInContrat(projet1.getIdProjet());

		assertEquals(tablCliPart.size(), 2);

		contratManager.delete(projet1.getIdProjet(), clientPart1
				.getIdClientPart());

		tablCliPart = contratManager.getClientPartByProjectIdInContrat(projet1
				.getIdProjet());

		assertEquals(tablCliPart.size(), 1);
		deleteDataContrat();
		deleteDataCliPart();
		deleteDataProjet();
	}

	@org.junit.Test
	public void testDeleteNominalByProjectAndCliPartIdProjectNull()
			throws ProjetException {
		createDataContact();
		try {
			contratManager.delete(0, clientPart1.getIdClientPart());
		} catch (ProjetException e) {
			Assert.assertEquals(
					"Aucun projet ou client/partenaire sélectionnés", e
							.getMessage());
		}
		deleteDataContrat();
		deleteDataCliPart();
		deleteDataProjet();
	}

	@org.junit.Test
	public void testDeleteNominalByProjectAndCliPartIdCliPartNull()
			throws ProjetException {
		createDataContact();
		try {
			contratManager.delete(projet1.getIdProjet(), 0);
		} catch (ProjetException e) {
			Assert.assertEquals(
					"Aucun projet ou client/partenaire sélectionnés", e
							.getMessage());
		}
		deleteDataContrat();
		deleteDataCliPart();
		deleteDataProjet();
	}
}
