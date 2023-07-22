package org.jeinnov.jeitime.api.service.projet;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.to.projet.ClientPartTO;
import org.jeinnov.jeitime.persistence.bo.projet.ClientPartP;
import org.jeinnov.jeitime.persistence.dao.projet.ClientPartDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;


public class ClientPartManager {
	private final Logger logger = Logger.getLogger(this.getClass());
	private ClientPartDAO clientPartDAO = ClientPartDAO.getInstance();

	private static ClientPartManager manager;

	public static ClientPartManager getInstance() {
		if (manager == null) {
			manager = new ClientPartManager();
		}
		return manager;
	}

	public ClientPartTO get(int id) throws ProjetException {

		if (id == 0) {
			throw new ProjetException(
					"Attention aucun client ou partenaire n'est spécifié !");
		}
		ClientPartTO cPTO = new ClientPartTO();
		ClientPartP cPP = new ClientPartP();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try{
			cPP = clientPartDAO.find(id);
			tx.commit();
		}catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(),e);
			throw e;
		}
	

		if (cPP != null) {
			cPTO.setIdClientPart(cPP.getIdClientPart());
			cPTO.setNomClientPart(cPP.getNomClientPart());
			cPTO.setCommentaire(cPP.getCommentaire());
			cPTO.setNomContact(cPP.getNomContact());
			cPTO.setNomService(cPP.getNomService());
		} else {
			throw new ProjetException(
					"Aucun client ou partenaire avec cet identifiant est enregistré dans la base");
		}
		return cPTO;
	}

	public List<ClientPartTO> getAll() {

		List<ClientPartTO> listCPTO = new ArrayList<ClientPartTO>();
		List<ClientPartP> listCPP = new ArrayList<ClientPartP>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			listCPP = clientPartDAO.getAll();
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(),e);
			throw e;
		}

		if (listCPP != null) {
			for (ClientPartP cPP : listCPP) {
				ClientPartTO cPTO = new ClientPartTO();
				cPTO.setIdClientPart(cPP.getIdClientPart());
				cPTO.setNomClientPart(cPP.getNomClientPart());
				cPTO.setCommentaire(cPP.getCommentaire());
				cPTO.setNomContact(cPP.getNomContact());
				cPTO.setNomService(cPP.getNomService());

				listCPTO.add(cPTO);
			}
		}

		return listCPTO;
	}

	public int saveOrUpdate(ClientPartTO cPTO) throws ProjetException {
		if (cPTO == null) {
			throw new ProjetException(
					"Aucun client ou partenaire n'a été créé ");
		}
		if (cPTO.getNomClientPart() == null) {
			throw new ProjetException(
					"Attention vous devez donné un nom à votre client/partenaire !");
		}

		ClientPartP cPP = new ClientPartP();
		cPP.setIdClientPart(cPTO.getIdClientPart());
		cPP.setNomClientPart(cPTO.getNomClientPart());
		cPP.setCommentaire(cPTO.getCommentaire());
		cPP.setNomContact(cPTO.getNomContact());
		cPP.setNomService(cPTO.getNomService());

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			clientPartDAO.save(cPP);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(),e);
			throw e;
		}

		int id = cPP.getIdClientPart();

		return id;
	}

	public void delete(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Aucun client ou partenaire n'a été créé ");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {

			ClientPartP cPP = clientPartDAO.find(id);
			if (cPP == null || cPP.getIdClientPart() == 0) {
				throw new ProjetException(
						"Le client/partenaire sélectionné n'existe pas dans la base de données.");
			}

			clientPartDAO.remove(cPP);
			tx.commit();

		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(),e);
			throw e;
		}
	}

	public boolean isInProject(int id) throws ProjetException {
		boolean verif = true;
		if (id == 0) {
			throw new ProjetException(
					"Attention aucun client ou partenaire n'est spécifié !");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			verif = clientPartDAO.isInProject(id, session);
			tx.commit();			
		} catch (final RuntimeException e) {
			verif = true;
			tx.rollback();
			logger.error(e.getMessage(),e);
			throw e;
		}
		return verif;
	}

}
