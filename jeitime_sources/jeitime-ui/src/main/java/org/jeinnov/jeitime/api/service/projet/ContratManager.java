package org.jeinnov.jeitime.api.service.projet;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.to.projet.ClientPartTO;
import org.jeinnov.jeitime.persistence.bo.projet.ClientPartP;
import org.jeinnov.jeitime.persistence.bo.projet.ContratIdP;
import org.jeinnov.jeitime.persistence.bo.projet.ContratP;
import org.jeinnov.jeitime.persistence.bo.projet.ProjetP;
import org.jeinnov.jeitime.persistence.dao.projet.ContratDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


public class ContratManager {
	private final Logger logger = Logger.getLogger(this.getClass());
	private ContratDAO contratDAO = ContratDAO.getInstance();

	private static ContratManager manager;

	public ContratManager() {

	}

	public static ContratManager getInstance() {
		if (manager == null) {
			manager = new ContratManager();
		}
		return manager;
	}

	public List<ClientPartTO> getClientPartByProjectIdInContrat(int idProjet)
			throws ProjetException {

		if (idProjet == 0) {
			throw new ProjetException("Aucun projet n'est associé");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<ClientPartTO> tablCliPart = new ArrayList<ClientPartTO>();
		try {
			List<ContratP> listContrat = contratDAO
					.getContratByProjectId(idProjet);

			if (listContrat != null) {
				for (int i = 0; i < listContrat.size(); i++) {
					int id = listContrat.get(i).getClientPart()
							.getIdClientPart();
					String nom = listContrat.get(i).getClientPart()
							.getNomClientPart();
					String nomContact = listContrat.get(i).getClientPart()
							.getNomContact();
					String nomService = listContrat.get(i).getClientPart()
							.getNomService();
					String commentaire = listContrat.get(i).getClientPart()
							.getCommentaire();
					int type = listContrat.get(i).getType();

					ClientPartTO cliPart = new ClientPartTO(id, nom,
							nomService, nomContact, commentaire, type);
					tablCliPart.add(cliPart);
				}
			}
			tx.commit();			
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(),e);
			throw e;
		}
		return tablCliPart;
	}

	public void save(int idProjet, int idCliPart, int typeCli)
			throws ProjetException {
		if (idProjet == 0 || idCliPart == 0) {
			throw new ProjetException(
					"Aucun projet ou client/partenaire sélectionnés");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			ContratP contrat = contratDAO.getContratByProjectIdAndCliPartId(
					idProjet, idCliPart);
			if (contrat == null) {
				ContratIdP idContrat = new ContratIdP(idCliPart, idProjet);
				ClientPartP clientPart = new ClientPartP();
				clientPart.setIdClientPart(idCliPart);
				ProjetP projet = new ProjetP(idProjet);

				ContratP contratP = new ContratP();

				contratP.setId(idContrat);
				contratP.setClientPart(clientPart);
				contratP.setProjet(projet);
				contratP.setType(typeCli);

				session.save(contratP);
			}

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(),e);
			throw e;
		}
	}

	public void delete(int idP, int idCP) throws ProjetException {
		if (idP == 0 || idCP == 0) {
			throw new ProjetException(
					"Aucun projet ou client/partenaire sélectionnés");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			ContratP contrat = contratDAO.getContratByProjectIdAndCliPartId(
					idP, idCP);

			contratDAO.remove(contrat);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(),e);
			throw e;
		}
	}

	public void delete(int idProjet) throws ProjetException {
		if (idProjet == 0) {
			throw new ProjetException(
					"Aucun projet ou client/partenaire sélectionnés");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<ContratP> listContrat = contratDAO
					.getContratByProjectId(idProjet);
			if (listContrat != null) {
				for (ContratP contrat : listContrat) {
					contratDAO.remove(contrat);
				}
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(),e);
			throw e;
		}
	}
}
