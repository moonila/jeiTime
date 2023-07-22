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
import org.jeinnov.jeitime.api.to.projet.ThematiqueTO;
import org.jeinnov.jeitime.api.to.projet.TypeTacheTO;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;
import org.jeinnov.jeitime.persistence.bo.projet.ThematiqueP;
import org.jeinnov.jeitime.persistence.dao.bilan.RecapThemaDAO;
import org.jeinnov.jeitime.persistence.dao.projet.ThematiqueDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


public class ThematiqueBilanManager extends InsertionSousTotaux {
	private final Logger logger = Logger.getLogger(this.getClass());
	private static ThematiqueBilanManager manager;
	private RecapThemaDAO recapThemaDAO = RecapThemaDAO.getInstance();
	private SortList sortList = new SortList();

	public ThematiqueBilanManager() {
		super();
	}

	public static ThematiqueBilanManager getInstance() {
		if (manager == null) {
			manager = new ThematiqueBilanManager();
		}
		return manager;
	}

	/*
	 * Méthodes spécifiques pour les bilans des thématiques
	 */

	public List<RecapProjetTO> creerListRecapProjetThematique(int[] tablIdThem,
			Date dateD, Date dateF) throws BilanException {
		if (tablIdThem == null) {
			throw new BilanException(
					"Attention aucune thématique n'est associée ! ");
		}

		List<RecapProjetTO> listRecap = new ArrayList<RecapProjetTO>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		try {
			for (int m = 0; m < tablIdThem.length; m++) {

				// Récupération de l'id de la thématique
				int id = tablIdThem[m];

				List<RecapProjetHibernate> listRecapHibernate = recapThemaDAO
						.creerListRecapProjetThema(session, id, dateD, dateF);

				List<TacheP> list = recapThemaDAO.listTacheThema(session, id);

				// Création de la liste nécessaire pour faire le récapitulatif
				List<RecapProjetTO> listRecapTmp = new ArrayList<RecapProjetTO>();
				listRecapTmp = createListRecapThem(id, list, listRecapHibernate);
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

	public List<Object> listTacheThema(int[] tablIdThem) throws BilanException {
		if (tablIdThem == null) {
			throw new BilanException(
					"Attention aucune thématique n'est associée ! ");
		}
		List<Object> listItems = new ArrayList<Object>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try{
		for (int i = 0; i < tablIdThem.length; i++) {

			int idT = tablIdThem[i];

			List<TacheP> listTache = recapThemaDAO.listTache(session, idT);

			if (listTache == null || listTache.isEmpty()) {

				ThematiqueP them = ThematiqueDAO.getInstance().find(idT);
				String nomT = them.getNomThematique();
				listItems.add((String) nomT);

				listItems.add((Integer) them.getIdThematique());
			} else {
				List<Object> listItemsTmp = new ArrayList<Object>();
				listItemsTmp = createListTache(listTache);
				listItems.addAll(listItemsTmp);
			}

			listItems.add((Integer) idT);
		}

		tx.commit();
		}catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(),e);
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

			if (listTache.get(j).getProjet().getThematique() != null) {
				proj.setThematique(new ThematiqueTO(listTache.get(j)
						.getProjet().getThematique().getIdThematique()));

				nom = listTache.get(j).getProjet().getThematique()
						.getNomThematique();
			}

			listItems.add((String) nom);
			listItems.add((ProjetTO) proj);
			listItems.add((TacheTO) tache);

			tache.setProjet(proj);
		}
		return listItems;
	}

	private List<RecapProjetTO> createListRecapThem(int id, List<TacheP> list,
			List<RecapProjetHibernate> listRecapHibernate) {

		List<RecapProjetTO> listRecap = new ArrayList<RecapProjetTO>();
		for (int j = 0; j < listRecapHibernate.size(); j++) {
			ProjetTO projet = new ProjetTO();

			projet.setNomProjet(listRecapHibernate.get(j).getNomProjet());
			projet.setIdProjet(listRecapHibernate.get(j).getIdProjet());
			projet.setThematique(new ThematiqueTO(id));

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
