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
import org.jeinnov.jeitime.api.to.projet.DomaineTO;
import org.jeinnov.jeitime.api.to.projet.NomTacheTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.jeinnov.jeitime.api.to.projet.TypeTacheTO;
import org.jeinnov.jeitime.persistence.bo.projet.DomaineP;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;
import org.jeinnov.jeitime.persistence.dao.bilan.RecapDomDAO;
import org.jeinnov.jeitime.persistence.dao.projet.DomaineDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

public class DomaineBilanManager extends InsertionSousTotaux {
	private final Logger logger = Logger.getLogger(this.getClass());
	private static DomaineBilanManager manager;
	private RecapDomDAO recapDomDAO = RecapDomDAO.getInstance();
	private SortList sortList = new SortList();

	public DomaineBilanManager() {
		super();
	}

	public static DomaineBilanManager getInstance() {
		if (manager == null) {
			manager = new DomaineBilanManager();
		}
		return manager;
	}

	/*
	 * Méthodes spécifiques pour les bilans de Domaines
	 */

	public List<RecapProjetTO> creerListRecapProjetDomaine(int[] tablIdDom,
			Date dateD, Date dateF) throws BilanException {

		if (tablIdDom == null) {
			throw new BilanException("Attention aucun domaine n'est associé ! ");
		}

		List<RecapProjetTO> listRecap = new ArrayList<RecapProjetTO>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		try {

			for (int m = 0; m < tablIdDom.length; m++) {

				// Récupération de l'id du domaine
				int idD = tablIdDom[m];

				List<RecapProjetHibernate> listRecapHibernate = recapDomDAO
						.creerListRecapProjet(session, idD, dateD, dateF);

				List<TacheP> list = recapDomDAO.listTacheDomaine(session, idD);

				// Création de la liste nécessaire pour faire le récapitulatif
				List<RecapProjetTO> listRecapTmp = new ArrayList<RecapProjetTO>();
				listRecapTmp = createListRecapDomaine(idD, list,
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

	public List<Object> listTacheDomaine(int[] tablIdDom) throws BilanException {
		if (tablIdDom == null) {
			throw new BilanException("Attention aucun domaine n'est associé ! ");
		}

		List<Object> listItems = new ArrayList<Object>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		try {

			for (int i = 0; i < tablIdDom.length; i++) {

				int idT = tablIdDom[i];

				List<TacheP> listTache = recapDomDAO.listTache(session, idT);

				if (listTache == null || listTache.isEmpty()) {

					DomaineP dp = DomaineDAO.getInstance().find(idT);
					String nomD = dp.getNomDomaine();
					listItems.add((String) nomD);

					listItems.add((Integer) dp.getIdDomaine());
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

			if (listTache.get(j).getProjet().getDomaine() != null) {
				proj.setDomaine(new DomaineTO(listTache.get(j).getProjet()
						.getDomaine().getIdDomaine()));
				nom = listTache.get(j).getProjet().getDomaine().getNomDomaine();
			}

			listItems.add((String) nom);
			listItems.add((ProjetTO) proj);
			listItems.add((TacheTO) tache);

			tache.setProjet(proj);
		}
		return listItems;
	}

	private List<RecapProjetTO> createListRecapDomaine(int idD,
			List<TacheP> list, List<RecapProjetHibernate> listRecapHibernate) {

		List<RecapProjetTO> listRecap = new ArrayList<RecapProjetTO>();
		for (int j = 0; j < listRecapHibernate.size(); j++) {
			ProjetTO projet = new ProjetTO();

			projet.setNomProjet(listRecapHibernate.get(j).getNomProjet());
			projet.setIdProjet(listRecapHibernate.get(j).getIdProjet());
			projet.setDomaine(new DomaineTO(idD));

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
