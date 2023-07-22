package org.jeinnov.jeitime.api.service.heure;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.utils.InsertionSousTotaux;
import org.jeinnov.jeitime.api.service.utils.SortList;
import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.heure.SaisieHeureTO;
import org.jeinnov.jeitime.api.to.projet.NomTacheTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.jeinnov.jeitime.api.to.projet.TypeTacheTO;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.jeinnov.jeitime.persistence.bo.projet.LienTachUtilP;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;
import org.jeinnov.jeitime.persistence.dao.heure.LienTacheUtilDAO;
import org.jeinnov.jeitime.persistence.dao.projet.TacheDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;



public class ConsultationMensuelleManager extends InsertionSousTotaux {

	private SortList sortList = new SortList();
	private final Logger logger = Logger.getLogger(this.getClass());
	private static ConsultationMensuelleManager manager;

	public ConsultationMensuelleManager() {

	}

	public static ConsultationMensuelleManager getInstance() {
		if (manager == null) {
			manager = new ConsultationMensuelleManager();
		}
		return manager;
	}

	public List<SaisieHeureTO> afficheListSaisieAllC(int idColl, Date dateD,
			Date dateF) throws HeureException {
		if (idColl == 0) {
			throw new HeureException(
					"Attention aucun collaborateur n'est rattaché !");
		}
		if (dateD == null || dateF == null) {
			throw new HeureException(
					"Attention la date de début ou la date de fin ne sont pas renseignées");
		}
		Timestamp timeDeb = new Timestamp(dateD.getTime());
		Timestamp timeFin = new Timestamp(dateF.getTime());

		List<SaisieHeureTO> tablSaisie = new ArrayList<SaisieHeureTO>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		try {
			List<LienTachUtilP> listLien = LienTacheUtilDAO.getInstance()
					.getAllByIdCollaborateurAndStartDateAndEndDate(idColl,
							timeDeb, timeFin);

			if (listLien == null || listLien.isEmpty()) {
				createSaisieHeure(idColl, tablSaisie, session);
			} else {

				for (int i = 0; i < listLien.size(); i++) {
					int idTach = listLien.get(i).getTache().getIdTache();

					TacheP tacheP = TacheDAO.getInstance().find(idTach);
					ProjetTO projet = new ProjetTO(tacheP.getProjet()
							.getIdProjet(), tacheP.getProjet().getNomProjet());

					CollaborateurTO collab = new CollaborateurTO();
					collab.setIdColl(listLien.get(i).getCollaborateur()
							.getIdColl());
					collab.setNomColl(listLien.get(i).getCollaborateur()
							.getNomColl());
					collab.setPrenomColl(listLien.get(i).getCollaborateur()
							.getPrenomColl());

					TypeTacheTO typeTache = new TypeTacheTO(listLien.get(i)
							.getTache().getNomTacheP().getTypeTache()
							.getIdTypeTache());
					NomTacheTO nomTache = new NomTacheTO(listLien.get(i)
							.getTache().getNomTacheP().getIdNomTache(),
							listLien.get(i).getTache().getNomTacheP()
									.getNomTache(), typeTache);

					TacheTO tache = new TacheTO(listLien.get(i).getTache()
							.getIdTache(), nomTache);

					Date dateS = listLien.get(i).getId().getDate();

					float nbheure = listLien.get(i).getNbHeure();
					String commentaire = listLien.get(i).getCommentaire();

					SaisieHeureTO saisieH = new SaisieHeureTO(tache, projet,
							collab, dateS, nbheure, commentaire);

					tablSaisie.add(saisieH);
				}
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		Collections.sort(tablSaisie);

		return tablSaisie;
	}

	private void createSaisieHeure(int idColl, List<SaisieHeureTO> tablSaisie,
			Session session) {
		SaisieHeureTO saisieH = new SaisieHeureTO();
		CollaborateurP c = (CollaborateurP) session.load(CollaborateurP.class,
				idColl);
		CollaborateurTO cTO = new CollaborateurTO();
		cTO.setIdColl(c.getIdColl());
		cTO.setNomColl(c.getNomColl());
		cTO.setPrenomColl(c.getPrenomColl());
		Date d = null;

		TacheTO t = new TacheTO();
		t.setIdTache(0);
		ProjetTO p = new ProjetTO();
		p.setIdProjet(0);

		saisieH.setCollab(cTO);
		saisieH.setNbHeure(0);
		saisieH.setSaisiDate(d);
		saisieH.setCommentaire(null);
		saisieH.setProjet(p);
		saisieH.setTache(t);

		tablSaisie.add(saisieH);
	}

	public List<Object> listSaisieAllCollab(List<SaisieHeureTO> tablSaisie)
			throws HeureException {
		if (tablSaisie == null) {
			throw new HeureException(
					"Attention aucune saisie n'est sélectionnée");
		}
		int id = 0;
//		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//		Transaction tx = session.beginTransaction();
		List<Object> listItems = new ArrayList<Object>();

		for (int i = 0; i < tablSaisie.size(); i++) {

			creerListItems(tablSaisie, listItems, i);

			id = tablSaisie.get(i).getCollab().getIdColl();
		}

//		session.getTransaction().commit();
//		HibernateUtil.getSessionFactory().close();

		sortList.sortListItems(listItems);

		sortList.sortListByTacheTO(listItems);

		sortList.sortListByCollaborateurTO(listItems);

		if (id != 0) {
			listItems.add((Integer) id);
		}

		return listItems;
	}

	private void creerListItems(List<SaisieHeureTO> tablSaisie,
			List<Object> listItems, int i) {
		if (tablSaisie.get(i).getTache().getIdTache() == 0) {
			CollaborateurTO c = new CollaborateurTO();
			c.setIdColl(tablSaisie.get(i).getCollab().getIdColl());
			c.setNomColl(tablSaisie.get(i).getCollab().getNomColl());
			c.setPrenomColl(tablSaisie.get(i).getCollab().getPrenomColl());
			c.setEquipe(tablSaisie.get(i).getCollab().getEquipe());
			listItems.add((CollaborateurTO) c);

		} else {

			CollaborateurTO c = new CollaborateurTO();
			c.setIdColl(tablSaisie.get(i).getCollab().getIdColl());
			c.setNomColl(tablSaisie.get(i).getCollab().getNomColl());
			c.setPrenomColl(tablSaisie.get(i).getCollab().getPrenomColl());
			c.setEquipe(tablSaisie.get(i).getCollab().getEquipe());
			listItems.add((CollaborateurTO) c);

			String nomProjet = tablSaisie.get(i).getProjet().getNomProjet();
			listItems.add((String) nomProjet);

			TacheTO t = new TacheTO();
			t.setIdTache(tablSaisie.get(i).getTache().getIdTache());
			t.setNomtache(tablSaisie.get(i).getTache().getNomtache());
			t.setPriorite(tablSaisie.get(i).getCollab().getIdColl());
			t.setEligible(tablSaisie.get(i).getTache().isEligible());
			t.setProjet(tablSaisie.get(i).getProjet());
			listItems.add((TacheTO) t);

		}
	}

}
