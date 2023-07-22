package org.jeinnov.jeitime.projet;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.projet.ProjetException;
import org.jeinnov.jeitime.api.service.projet.ProjetManager;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.api.to.projet.TypeProjetTO;
import org.jeinnov.jeitime.persistence.bo.projet.ClientPartP;
import org.jeinnov.jeitime.persistence.bo.projet.DomaineP;
import org.jeinnov.jeitime.persistence.bo.projet.ProjetP;
import org.jeinnov.jeitime.persistence.bo.projet.ThematiqueP;
import org.jeinnov.jeitime.persistence.bo.projet.TypeProjetP;
import org.jeinnov.jeitime.persistence.dao.projet.ClientPartDAO;
import org.jeinnov.jeitime.persistence.dao.projet.DomaineDAO;
import org.jeinnov.jeitime.persistence.dao.projet.ProjetDAO;
import org.jeinnov.jeitime.persistence.dao.projet.ThematiqueDAO;
import org.jeinnov.jeitime.persistence.dao.projet.TypeProjetDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;



import junit.framework.Assert;

public class ProjetManagerTest extends Assert {

	private ProjetDAO projetDAO = ProjetDAO.getInstance();
	private ProjetManager projetManager = ProjetManager.getInstance();

	private static String NOM1 = "projet1";
	private static String NOM2 = "projet2";
	private static Date DATEDEBUT = new Date();
	private static Date DATEFIN = new Date();
	private static Date DATECLOTURE = new Date();
	private static Date DATEFERMETURE = new Date();
	private static float BUDGET = 0;
	private static float TPS = 0;

	private TypeProjetP typePro;
	private DomaineP dom;
	private ThematiqueP thema;
	private ClientPartP cliPart;
	private ProjetP projet1;
	private ProjetP projet2;

	public void createDataSingleProject() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			projet1 = new ProjetP();
			projet1.setNomProjet(NOM1);
			projet1.setDateDeb(DATEDEBUT);
			projet1.setDateFin(DATEFIN);
			projet1.setBudgeprevu(BUDGET);
			projet1.setTpsprevu(TPS);
			projet1.setTypeProjet(typePro);

			projetDAO.save(projet1);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void createDataProjects() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			projet1 = new ProjetP();
			projet1.setNomProjet(NOM1);
			projet1.setDateDeb(DATEDEBUT);
			projet1.setDateFin(DATEFIN);
			projet1.setBudgeprevu(BUDGET);
			projet1.setTpsprevu(TPS);
			projet1.setTypeProjet(typePro);

			projetDAO.save(projet1);

			projet2 = new ProjetP();
			projet2.setNomProjet(NOM2);
			projet2.setDateDeb(DATEDEBUT);
			projet2.setDateFin(DATEFIN);
			projet2.setBudgeprevu(BUDGET);
			projet2.setTpsprevu(TPS);
			projet2.setTypeProjet(typePro);

			projetDAO.save(projet2);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void createDataProjectsWithProjectClose() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			projet1 = new ProjetP();
			projet1.setNomProjet(NOM1);
			projet1.setDateDeb(DATEDEBUT);
			projet1.setDateFin(DATEFIN);
			projet1.setBudgeprevu(BUDGET);
			projet1.setTpsprevu(TPS);
			projet1.setTypeProjet(typePro);
			projet1.setDateFermeture(DATEFERMETURE);

			projetDAO.save(projet1);

			projet2 = new ProjetP();
			projet2.setNomProjet(NOM2);
			projet2.setDateDeb(DATEDEBUT);
			projet2.setDateFin(DATEFIN);
			projet2.setBudgeprevu(BUDGET);
			projet2.setTpsprevu(TPS);
			projet2.setTypeProjet(typePro);

			projetDAO.save(projet2);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void createDataProjectsWithProjectLock() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			projet1 = new ProjetP();
			projet1.setNomProjet(NOM1);
			projet1.setDateDeb(DATEDEBUT);
			projet1.setDateFin(DATEFIN);
			projet1.setBudgeprevu(BUDGET);
			projet1.setTpsprevu(TPS);
			projet1.setTypeProjet(typePro);
			projet1.setDateFermeture(DATEFERMETURE);
			projet1.setDateCloture(DATECLOTURE);

			projetDAO.save(projet1);

			projet2 = new ProjetP();
			projet2.setNomProjet(NOM2);
			projet2.setDateDeb(DATEDEBUT);
			projet2.setDateFin(DATEFIN);
			projet2.setBudgeprevu(BUDGET);
			projet2.setTpsprevu(TPS);
			projet2.setTypeProjet(typePro);

			projetDAO.save(projet2);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void createAllData() {
		deleteAllData();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			typePro = new TypeProjetP();
			typePro.setNomTypePro("typeProjet1");
			TypeProjetDAO.getInstance().save(typePro);

			dom = new DomaineP();
			dom.setNomDomaine("dom1");
			DomaineDAO.getInstance().save(dom);

			thema = new ThematiqueP();
			thema.setNomThematique("thema1");
			ThematiqueDAO.getInstance().save(thema);

			cliPart = new ClientPartP();
			cliPart.setNomClientPart("cliPart1");
			ClientPartDAO.getInstance().save(cliPart);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteAllData() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<DomaineP> getAllDom = DomaineDAO.getInstance().findAll();

			if (getAllDom != null && !getAllDom.isEmpty()) {
				DomaineDAO.getInstance().removeAll(getAllDom);
			}
			// session.getTransaction().commit();
			// HibernateUtil.getSessionFactory().close();

			// session = HibernateUtil.getSessionFactory().getCurrentSession();
			// session.beginTransaction();
			List<ThematiqueP> getAllThema = ThematiqueDAO.getInstance()
					.findAll();

			if (getAllThema != null && !getAllThema.isEmpty()) {
				ThematiqueDAO.getInstance().removeAll(getAllThema);
			}
			// session.getTransaction().commit();
			// HibernateUtil.getSessionFactory().close();

			// session = HibernateUtil.getSessionFactory().getCurrentSession();
			// session.beginTransaction();
			List<TypeProjetP> getAllTypeProjet = TypeProjetDAO.getInstance()
					.findAll();

			if (getAllTypeProjet != null && !getAllTypeProjet.isEmpty()) {
				TypeProjetDAO.getInstance().removeAll(getAllTypeProjet);
			}
			// session.getTransaction().commit();
			// HibernateUtil.getSessionFactory().close();

			// session = HibernateUtil.getSessionFactory().getCurrentSession();
			// session.beginTransaction();
			List<ClientPartP> getAllCliPart = ClientPartDAO.getInstance()
					.findAll();

			if (getAllCliPart != null && !getAllCliPart.isEmpty()) {
				ClientPartDAO.getInstance().removeAll(getAllCliPart);
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteAllProjects() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<ProjetP> getAllProjet = ProjetDAO.getInstance().findAll();

			if (getAllProjet != null && !getAllProjet.isEmpty()) {
				ProjetDAO.getInstance().removeAll(getAllProjet);
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
		createAllData();
		createDataProjects();

		List<ProjetTO> getAll = projetManager.getAll();

		assertEquals(getAll.size(), 2);

		deleteAllProjects();
	}

	@org.junit.Test
	public void testCreateNominal() throws ProjetException {
		createAllData();

		ProjetTO p = new ProjetTO();

		p.setNomProjet(NOM1);
		p.setDateDeb(DATEDEBUT);
		p.setDateFin(DATEFIN);
		p.setBudgeprevu(BUDGET);
		p.setTpsprevu(TPS);

		TypeProjetTO tP = new TypeProjetTO(typePro.getIdTypePro());
		p.setTypeProjet(tP);

		int id = projetManager.saveOrUpdate(p);

		ProjetTO pr = new ProjetTO();
		pr = projetManager.get(id);

		assertEquals(pr.getNomProjet(), p.getNomProjet());

		deleteAllProjects();
	}

	@org.junit.Test
	public void testCreateTwice() throws ProjetException {
		createAllData();
		createDataSingleProject();
		ProjetTO p = new ProjetTO();

		p.setNomProjet(NOM1);
		p.setDateDeb(DATEDEBUT);
		p.setDateFin(DATEFIN);
		p.setBudgeprevu(BUDGET);
		p.setTpsprevu(TPS);

		TypeProjetTO tP = new TypeProjetTO(typePro.getIdTypePro());
		p.setTypeProjet(tP);

		try {
			projetManager.saveOrUpdate(p);
		} catch (ProjetException e) {
			Assert.assertEquals(
					"Attention, un projet avec ce nom existe déjà !", e
							.getMessage());
		}
		deleteAllProjects();
	}

	@org.junit.Test
	public void testCreateNameNull() throws ProjetException {
		ProjetTO p = new ProjetTO();
		p.setNomProjet(null);

		try {
			projetManager.saveOrUpdate(p);
		} catch (ProjetException e) {
			Assert.assertEquals("Attention aucun nom n'a été donné au projet ",
					e.getMessage());
		}
	}

	@org.junit.Test
	public void testCreateProjetNull() throws ProjetException {

		try {
			projetManager.saveOrUpdate(null);
		} catch (ProjetException e) {
			Assert.assertEquals("Attention aucun projet n'est sélectionné", e
					.getMessage());
		}
	}

	@org.junit.Test
	public void testCreateTypeProjetNull() throws ProjetException {
		ProjetTO p = new ProjetTO();

		p.setNomProjet(NOM1);
		p.setDateDeb(DATEDEBUT);
		p.setDateFin(DATEFIN);
		p.setBudgeprevu(BUDGET);
		p.setTpsprevu(TPS);
		try {
			projetManager.saveOrUpdate(p);
		} catch (ProjetException e) {
			Assert.assertEquals(
					"Attention aucun type de projet n'est rattaché au projet",
					e.getMessage());
		}
	}

	@org.junit.Test
	public void testUpdateNominal() throws ProjetException {
		createAllData();
		createDataSingleProject();

		ProjetTO p = new ProjetTO();
		p.setIdProjet(projet1.getIdProjet());
		p.setNomProjet("test");
		p.setDateDeb(DATEDEBUT);
		p.setDateFin(DATEFIN);
		p.setBudgeprevu(BUDGET);
		p.setTpsprevu(TPS);

		TypeProjetTO tP = new TypeProjetTO(typePro.getIdTypePro());
		p.setTypeProjet(tP);

		int id = projetManager.saveOrUpdate(p);

		assertEquals(id, projet1.getIdProjet());

		deleteAllProjects();
	}

	@org.junit.Test
	public void testUpdateTwice() throws ProjetException {
		createAllData();
		createDataProjects();

		ProjetTO p = new ProjetTO();
		p.setIdProjet(projet1.getIdProjet());
		p.setNomProjet(NOM2);
		p.setDateDeb(DATEDEBUT);
		p.setDateFin(DATEFIN);
		p.setBudgeprevu(BUDGET);
		p.setTpsprevu(TPS);

		TypeProjetTO tP = new TypeProjetTO(typePro.getIdTypePro());
		p.setTypeProjet(tP);

		try {
			projetManager.saveOrUpdate(p);
		} catch (ProjetException cE) {
			Assert.assertEquals(
					"Attention, un projet avec ce nom existe déjà !", cE
							.getMessage());
		}

		deleteAllProjects();
	}

	@org.junit.Test
	public void testDeleteNominal() throws ProjetException {
		createAllData();
		createDataProjects();

		projetManager.delete(projet1.getIdProjet());

		List<ProjetTO> getAll = projetManager.getAll();
		assertEquals(getAll.size(), 1);

		deleteAllProjects();
	}

	@org.junit.Test
	public void testDeleteIdNull() throws ProjetException {
		try {
			projetManager.delete(0);
		} catch (ProjetException cE) {
			Assert.assertEquals("Attention aucun projet n'est sélectionné", cE
					.getMessage());
		}
	}

	@org.junit.Test
	public void testDeleteIdUnknown() throws ProjetException {
		try {
			projetManager.delete(1);
		} catch (ProjetException cE) {
			Assert
					.assertEquals(
							"Attention, le projet sélectionné n'existe pas dans la base.",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testGetNominal() throws ProjetException {
		createAllData();
		createDataSingleProject();

		ProjetTO p = projetManager.get(projet1.getIdProjet());

		assertEquals(p.getNomProjet(), projet1.getNomProjet());

		deleteAllProjects();
		deleteAllData();
	}

	@org.junit.Test
	public void testGetIdNull() throws ProjetException {
		try {
			projetManager.get(0);
		} catch (ProjetException cE) {
			Assert.assertEquals("Attention aucun projet n'est sélectionné", cE
					.getMessage());
		}
	}

	@org.junit.Test
	public void testGetIdUnknown() throws ProjetException {
		try {
			projetManager.get(1);
		} catch (ProjetException cE) {
			Assert.assertEquals(
					"Attention aucun projet avec cet id existe dans la base",
					cE.getMessage());
		}
	}

	@org.junit.Test
	public void testGetAllProjectClose() {
		createAllData();
		createDataProjectsWithProjectClose();

		List<ProjetTO> listProjet = projetManager.getAllClose();

		assertEquals(listProjet.size(), 1);

		deleteAllProjects();
		deleteAllData();
	}

	@org.junit.Test
	public void testGetAllProjectLock() {
		createAllData();
		createDataProjectsWithProjectLock();

		List<ProjetTO> listProjet = projetManager.getAllLock();

		assertEquals(listProjet.size(), 1);

		deleteAllProjects();
		deleteAllData();
	}

	@org.junit.Test
	public void testGetAllProjectNotLock() {
		createAllData();
		createDataProjectsWithProjectLock();

		List<ProjetTO> listProjet = projetManager.getAllNotLock();

		assertEquals(listProjet.size(), 1);

		deleteAllProjects();
		deleteAllData();
	}

	@org.junit.Test
	public void testGetAllProjectNotClose() {
		createAllData();
		createDataProjectsWithProjectClose();

		List<ProjetTO> listProjet = projetManager.getAllNotClose();

		assertEquals(listProjet.size(), 1);

		deleteAllProjects();
		deleteAllData();
	}

	@org.junit.Test
	public void testCloseProject() throws ProjetException {
		createAllData();
		createDataProjects();

		List<ProjetTO> listProjetTmp = projetManager.getAll();
		assertEquals(listProjetTmp.size(), 2);

		projetManager.closeProject(projet1.getIdProjet());

		List<ProjetTO> listProjet = projetManager.getAllClose();
		assertEquals(listProjet.size(), 1);

		deleteAllProjects();
		deleteAllData();
	}

	@org.junit.Test
	public void testCloseProjectIdNull() throws ProjetException {
		try {
			projetManager.closeProject(0);
		} catch (ProjetException cE) {
			Assert.assertEquals("Attention aucun projet n'est sélectionné", cE
					.getMessage());
		}
	}

	@org.junit.Test
	public void testCloseProjectIdUnknows() throws ProjetException {
		try {
			projetManager.closeProject(5);
		} catch (ProjetException cE) {
			Assert
					.assertEquals(
							"Attention, le projet sélectionné n'existe pas dans la base.",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testLockProject() throws ProjetException {
		createAllData();
		createDataProjects();

		List<ProjetTO> listProjetTmp = projetManager.getAll();
		assertEquals(listProjetTmp.size(), 2);

		projetManager.lockProject(projet1.getIdProjet());

		List<ProjetTO> listProjet = projetManager.getAllLock();
		assertEquals(listProjet.size(), 1);

		deleteAllProjects();
		deleteAllData();
	}

	@org.junit.Test
	public void testLockProjectIdNull() throws ProjetException {
		try {
			projetManager.lockProject(0);
		} catch (ProjetException cE) {
			Assert.assertEquals("Attention aucun projet n'est sélectionné", cE
					.getMessage());
		}
	}

	@org.junit.Test
	public void testLockProjectIdUnknows() throws ProjetException {
		try {
			projetManager.lockProject(5);
		} catch (ProjetException cE) {
			Assert
					.assertEquals(
							"Attention, le projet sélectionné n'existe pas dans la base.",
							cE.getMessage());
		}
	}

	@org.junit.Test
	public void testOpenProject() throws ProjetException {
		createAllData();
		createDataProjectsWithProjectClose();

		List<ProjetTO> listProjetTmp = projetManager.getAllClose();
		assertEquals(listProjetTmp.size(), 1);

		projetManager.openProject(projet1.getIdProjet());

		List<ProjetTO> listProjet = projetManager.getAllNotClose();
		assertEquals(listProjet.size(), 2);

		deleteAllProjects();
		deleteAllData();
	}

	@org.junit.Test
	public void testOpenProjectIdNull() throws ProjetException {
		try {
			projetManager.openProject(0);
		} catch (ProjetException cE) {
			Assert.assertEquals("Attention aucun projet n'est sélectionné", cE
					.getMessage());
		}
	}

	@org.junit.Test
	public void testOpenProjectIdUnknows() throws ProjetException {
		try {
			projetManager.openProject(5);
		} catch (ProjetException cE) {
			Assert
					.assertEquals(
							"Attention, le projet sélectionné n'existe pas dans la base.",
							cE.getMessage());
		}
	}
}
