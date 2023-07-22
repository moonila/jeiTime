package org.jeinnov.jeitime.api.service.bilan;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.utils.InsertionSousTotaux;
import org.jeinnov.jeitime.api.service.utils.SortList;
import org.jeinnov.jeitime.api.to.bilan.RecapProjetHibernate;
import org.jeinnov.jeitime.api.to.bilan.RecapProjetTO;
import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.projet.NomTacheTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.jeinnov.jeitime.api.to.projet.TypeTacheTO;
import org.jeinnov.jeitime.persistence.bo.projet.ProjetP;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;
import org.jeinnov.jeitime.persistence.dao.bilan.RecapProjetDAO;
import org.jeinnov.jeitime.persistence.dao.projet.TacheDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;




public class RecapProjetManager extends InsertionSousTotaux {
	private final Logger logger = Logger.getLogger(this.getClass());
	private static RecapProjetManager manager;
	private SortList sortList = new SortList();
	private RecapProjetDAO recapProjetDAO = RecapProjetDAO.getInstance();

	public RecapProjetManager() {

	}

	public static RecapProjetManager getInstance() {
		if (manager == null) {
			manager = new RecapProjetManager();
		}
		return manager;
	}

	public List<RecapProjetTO> creerListRecapProjet(int[] idProjet, Date dateD,
			Date dateF) throws BilanException {

		if (idProjet == null) {
			throw new BilanException("Attention aucun projet n'est associé ! ");
		}

		List<RecapProjetTO> listRecap = new ArrayList<RecapProjetTO>();
		Timestamp timeDeb = new Timestamp(dateD.getTime());
		Timestamp timeFin = new Timestamp(dateF.getTime());

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		try {

			for (int i = 0; i < idProjet.length; i++) {
				int idP = idProjet[i];

				List<TacheP> list = TacheDAO.getInstance()
						.getAllTacheInProject(idP);

				List<RecapProjetHibernate> listTache = recapProjetDAO
						.creerListRecapProjet(session, idP, timeDeb, timeFin);

				creerListRecap(listRecap, list, listTache);
			}

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return listRecap;
	}

	public List<Object> listTache(int[] tablProjet) throws BilanException {
		if (tablProjet == null) {
			throw new BilanException("Attention aucun projet n'est associé ! ");
		}
		List<Object> listItems = new ArrayList<Object>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		try {

			for (int k = 0; k < tablProjet.length; k++) {
				int idProjet = tablProjet[k];

				List<TacheP> listTache = recapProjetDAO.listTache(session,
						idProjet);

				if (listTache == null || listTache.isEmpty()) {
					ProjetP p = (ProjetP) session.load(ProjetP.class, idProjet);

					String nomProjet = p.getNomProjet();
					listItems.add((String) nomProjet);
					listItems.add((Integer) idProjet);
				} else {
					creerListItems(listItems, listTache);
					listItems.add((Integer) idProjet);
				}
			}

			sortList.sortListItems(listItems);

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		listItems = insertionSousTotalProjetMensuel(listItems);
		return listItems;
	}

	public List<RecapProjetTO> creerListRecapSuiviProjet(int[] idProjet)
			throws BilanException {
		if (idProjet == null) {
			throw new BilanException("Attention aucun projet n'est associé ! ");
		}
		List<RecapProjetTO> listRecap = new ArrayList<RecapProjetTO>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			for (int i = 0; i < idProjet.length; i++) {
				int idP = idProjet[i];

				List<TacheP> list = TacheDAO.getInstance()
						.getAllTacheInProject(idP);

				List<RecapProjetHibernate> listTache = recapProjetDAO
						.creerListRecapSuiviProjet(session, idP);

				for (int j = 0; j < listTache.size(); j++) {
					ProjetTO projet = new ProjetTO();

					projet.setNomProjet(listTache.get(j).getNomProjet());
					projet.setIdProjet(listTache.get(j).getIdProjet());
					TypeTacheTO typeTache = null;

					NomTacheTO nomTache = new NomTacheTO(listTache.get(j)
							.getIdNomTache(), listTache.get(j).getNomTache(),
							typeTache);

					TacheTO tache = new TacheTO(listTache.get(j).getIdTache(),
							nomTache);
					CollaborateurTO collab = new CollaborateurTO(listTache.get(
							j).getIdColl(), listTache.get(j).getNomColl());
					double nbheure = (Double) listTache.get(j).getTotal();

					for (TacheP t : list) {
						if (listTache.get(j).getIdTache() == t.getIdTache()) {
							tache.setEligible(t.isEligible());
						}
					}
					listRecap.add(new RecapProjetTO(projet, tache, collab,
							nbheure));
				}
			}

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return listRecap;
	}

	private void creerListItems(List<Object> listItems, List<TacheP> listTache) {
		for (int j = 0; j < listTache.size(); j++) {
			String nomProjet = listTache.get(j).getProjet().getNomProjet();

			TypeTacheTO typeTache = null;

			NomTacheTO nomtache = new NomTacheTO(listTache.get(j)
					.getNomTacheP().getIdNomTache(), listTache.get(j)
					.getNomTacheP().getNomTache(), typeTache);

			TacheTO tache = new TacheTO(listTache.get(j).getIdTache(),
					nomtache, listTache.get(j).getBudgetprevu(), listTache.get(
							j).getTpsprevu(), listTache.get(j).getPriorite(),
					listTache.get(j).isEligible(), null);
			tache.setEligible(listTache.get(j).isEligible());
			ProjetTO proj = new ProjetTO();
			proj.setIdProjet(listTache.get(j).getProjet().getIdProjet());

			listItems.add((String) nomProjet);
			listItems.add((TacheTO) tache);

			tache.setProjet(proj);

		}
	}

	private void creerListRecap(List<RecapProjetTO> listRecap,
			List<TacheP> list, List<RecapProjetHibernate> listTache) {
		for (int j = 0; j < listTache.size(); j++) {
			ProjetTO projet = new ProjetTO();

			projet.setNomProjet(listTache.get(j).getNomProjet());
			projet.setIdProjet(listTache.get(j).getIdProjet());
			TypeTacheTO typeTache = null;

			NomTacheTO nomTache = new NomTacheTO(listTache.get(j)
					.getIdNomTache(), listTache.get(j).getNomTache(), typeTache);

			TacheTO tache = new TacheTO(listTache.get(j).getIdTache(), nomTache);
			CollaborateurTO collab = new CollaborateurTO(listTache.get(j)
					.getIdColl(), listTache.get(j).getNomColl());
			double nbheure = 0;
			if(listTache.get(j).getTotal() instanceof Double){
				nbheure = (Double) listTache.get(j).getTotal();
			}
			if(listTache.get(j).getTotal() instanceof Float){
				nbheure = (Float) listTache.get(j).getTotal();
			}

			for (TacheP t : list) {
				if (listTache.get(j).getIdTache() == t.getIdTache()) {
					tache.setEligible(t.isEligible());
				}
			}
			listRecap.add(new RecapProjetTO(projet, tache, collab, nbheure));
		}
	}
}
