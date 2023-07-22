package org.jeinnov.jeitime.api.service.bilan;

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
import org.jeinnov.jeitime.api.to.projet.TypeProjetTO;
import org.jeinnov.jeitime.api.to.projet.TypeTacheTO;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;
import org.jeinnov.jeitime.persistence.bo.projet.TypeProjetP;
import org.jeinnov.jeitime.persistence.dao.bilan.RecapTypePDAO;
import org.jeinnov.jeitime.persistence.dao.projet.TypeProjetDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


public class TypeProjetBilanManager extends InsertionSousTotaux {
	private final Logger logger = Logger.getLogger(this.getClass());
	private static TypeProjetBilanManager manager;
	private RecapTypePDAO recapTypePDAO = RecapTypePDAO.getInstance();
	private SortList sortList = new SortList();

	public TypeProjetBilanManager() {
		super();
	}

	public static TypeProjetBilanManager getInstance() {
		if (manager == null) {
			manager = new TypeProjetBilanManager();
		}
		return manager;
	}

	/*
	 * Méthodes spécifiques pour les bilans des Types de Projet
	 */

	public List<RecapProjetTO> creerListRecapProjet(int[] tablIdTypeP,
			Date dateD, Date dateF) throws BilanException {
		if (tablIdTypeP == null) {
			throw new BilanException("Attention aucun domaine n'est associé ! ");
		}

		List<RecapProjetTO> listRecap = new ArrayList<RecapProjetTO>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			for (int m = 0; m < tablIdTypeP.length; m++) {

				// Récupération de l'id du type de projet
				int idD = tablIdTypeP[m];

				List<RecapProjetHibernate> listRecapHibernate = recapTypePDAO
						.creerListRecapProjet(session, idD, dateD, dateF);

				List<TacheP> list = recapTypePDAO.listTacheTypeProjet(session,
						idD);

				// Création de la liste nécessaire pour faire le récapitulatif
				List<RecapProjetTO> listRecapTmp = new ArrayList<RecapProjetTO>();
				listRecapTmp = createListRecapTypeProjet(idD, list,
						listRecapHibernate);
				listRecap.addAll(listRecapTmp);

			}

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return listRecap;
	}

	public List<Object> listTacheTypeProjet(int[] tablIdTypeProjet)
			throws BilanException {
		if (tablIdTypeProjet == null) {
			throw new BilanException("Attention aucun domaine n'est associé ! ");
		}

		List<Object> listItems = new ArrayList<Object>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			for (int i = 0; i < tablIdTypeProjet.length; i++) {

				int idT = tablIdTypeProjet[i];

				List<TacheP> listTache = recapTypePDAO.listTache(session, idT);

				if (listTache == null || listTache.isEmpty()) {

					TypeProjetP tp = TypeProjetDAO.getInstance().find(idT);
					String nomD = tp.getNomTypePro();
					listItems.add((String) nomD);

					listItems.add((Integer) tp.getIdTypePro());
				} else {
					List<Object> listItemsTmp = new ArrayList<Object>();
					listItemsTmp = createListTache(listTache);
					listItems.addAll(listItemsTmp);
				}

				listItems.add((Integer) idT);
			}

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		listItems = sortList.trieListItem(listItems);

		listItems = insertionSousTotal(listItems);

		listItems = insertionSousTotalProjet(listItems);

		return listItems;
	}

	public List<Object> createListTache(List<TacheP> listTache) {
		List<Object> listItems = new ArrayList<Object>();

		String nom = null;
		for (int j = 0; j < listTache.size(); j++) {

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
			proj.setNomProjet(listTache.get(j).getProjet().getNomProjet());

			if (listTache.get(j).getProjet().getTypeProjet() != null) {
				proj.setTypeProjet(new TypeProjetTO(listTache.get(j)
						.getProjet().getTypeProjet().getIdTypePro()));
				nom = listTache.get(j).getProjet().getTypeProjet()
						.getNomTypePro();
			}

			listItems.add((String) nom);
			listItems.add((ProjetTO) proj);
			listItems.add((TacheTO) tache);

			tache.setProjet(proj);
		}
		return listItems;
	}

	private List<RecapProjetTO> createListRecapTypeProjet(int idTP,
			List<TacheP> list, List<RecapProjetHibernate> listRecapHibernate) {

		List<RecapProjetTO> listRecap = new ArrayList<RecapProjetTO>();
		for (int j = 0; j < listRecapHibernate.size(); j++) {
			ProjetTO projet = new ProjetTO();

			projet.setNomProjet(listRecapHibernate.get(j).getNomProjet());
			projet.setIdProjet(listRecapHibernate.get(j).getIdProjet());
			projet.setTypeProjet(new TypeProjetTO(idTP));

			TypeTacheTO typeTache = null;

			NomTacheTO nomTache = new NomTacheTO(listRecapHibernate.get(j)
					.getIdNomTache(), listRecapHibernate.get(j).getNomTache(),
					typeTache);

			TacheTO tache = new TacheTO(listRecapHibernate.get(j).getIdTache(),
					nomTache);
			CollaborateurTO collab = new CollaborateurTO(listRecapHibernate
					.get(j).getIdColl(), listRecapHibernate.get(j).getNomColl());
			double nbheure = 0;
			if(listRecapHibernate.get(j).getTotal() instanceof Double){
				nbheure = (Double) listRecapHibernate.get(j).getTotal();
			}
			if(listRecapHibernate.get(j).getTotal() instanceof Float){
				nbheure = (Float) listRecapHibernate.get(j).getTotal();
			}

			for (TacheP t : list) {
				if (listRecapHibernate.get(j).getIdTache() == t.getIdTache()) {
					tache.setEligible(t.isEligible());
				}
			}
			listRecap.add(new RecapProjetTO(projet, tache, collab, nbheure));
		}
		return listRecap;
	}
}
