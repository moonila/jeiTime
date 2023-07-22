package org.jeinnov.jeitime.api.service.bilan;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.to.bilan.CollaborateurRecapMensTO;
import org.jeinnov.jeitime.api.to.bilan.RecapProjetMensuelHibernate;
import org.jeinnov.jeitime.api.to.bilan.RecapProjetMensuelTO;
import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.jeinnov.jeitime.persistence.bo.projet.ProjetP;
import org.jeinnov.jeitime.persistence.dao.bilan.RecapProjetMensuelDAO;
import org.jeinnov.jeitime.persistence.dao.collaborateur.CollaborateurDAO;
import org.jeinnov.jeitime.persistence.dao.projet.ProjetDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;



public class RecapProjetMensuelManager {
	private final Logger logger = Logger.getLogger(this.getClass());
	private RecapProjetMensuelDAO recapProjetMensuelDAO = RecapProjetMensuelDAO
			.getInstance();

	private static RecapProjetMensuelManager manager;

	public RecapProjetMensuelManager() {

	}

	public static RecapProjetMensuelManager getInstance() {
		if (manager == null) {
			manager = new RecapProjetMensuelManager();
		}
		return manager;
	}

	public List<RecapProjetMensuelTO> creerListRecapProjet(int[] idProjet,
			Date dateD, Date dateF) throws BilanException {
		if (idProjet == null) {
			throw new BilanException("Attention aucun projet n'est associé ! ");
		}
		List<RecapProjetMensuelTO> listRecap = new ArrayList<RecapProjetMensuelTO>();
		Timestamp timeDeb = new Timestamp(dateD.getTime());
		Timestamp timeFin = new Timestamp(dateF.getTime());

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			for (int i = 0; i < idProjet.length; i++) {
				List<RecapProjetMensuelHibernate> listTache = recapProjetMensuelDAO
						.creerListRecapProjet(session, idProjet[i], timeDeb,
								timeFin);

				remplissageListRecap(listRecap, listTache);

				calculTotalProjet(listRecap, i);
			}

			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		calculTotalPourCollab(listRecap);

		return listRecap;
	}

	public List<Object> listCollab(int[] idProjet) throws BilanException {

		if (idProjet == null) {
			throw new BilanException("Attention aucun projet n'est associé ! ");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		int idP = 0;
		List<Object> listItems = new ArrayList<Object>();
		try {
			for (int i = 0; i < idProjet.length; i++) {

				ProjetP p = ProjetDAO.getInstance().find(idProjet[i]);

				String nomProjet = p.getNomProjet();
				listItems.add((String) nomProjet);

				List<CollaborateurP> listColl = CollaborateurDAO.getInstance()
						.findAll();

				idP = p.getIdProjet();
				for (int j = 0; j < listColl.size(); j++) {

					CollaborateurRecapMensTO c = new CollaborateurRecapMensTO();

					c.setIdCollab(listColl.get(j).getIdColl());
					c.setNomCollab(listColl.get(j).getNomColl());
					c.setPrenomCollab(listColl.get(j).getPrenomColl());
					c.setIdProjet(idP);

					listItems.add((CollaborateurRecapMensTO) c);
				}

				listItems.add((Integer) idP);
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return listItems;
	}

	private void remplissageListRecap(List<RecapProjetMensuelTO> listRecap,
			List<RecapProjetMensuelHibernate> listTache) {
		for (int j = 0; j < listTache.size(); j++) {
			ProjetTO projet = new ProjetTO();

			projet.setNomProjet(listTache.get(j).getNomProjet());
			projet.setIdProjet(listTache.get(j).getIdProjet());

			CollaborateurTO collab = new CollaborateurTO(listTache.get(j)
					.getIdColl(), listTache.get(j).getNomColl());
			double nbheure = listTache.get(j).getNbheure();
			Date d = listTache.get(j).getDate();

			int jour = definirLeJour(d);

			RecapProjetMensuelTO r = new RecapProjetMensuelTO();
			r.setCollab(collab);
			r.setNomProjet(projet);
			r.setJour(jour);
			r.setNbheure(nbheure);
			r.setIdTache(listTache.get(j).getIdTache());
			listRecap.add(r);
		}
	}

	private void calculTotalProjet(List<RecapProjetMensuelTO> listRecap, int i) {
		double nh = 0;
		for (int l = 0; l < listRecap.size(); l++) {
			int j = l + 1;
			while (j < listRecap.size()) {
				if (listRecap.get(l).getNomProjet() == listRecap.get(j)
						.getNomProjet()
						&& listRecap.get(l).getCollab() == listRecap.get(i)
								.getCollab()
						&& listRecap.get(l).getJour() == listRecap.get(i)
								.getJour()) {
					nh = listRecap.get(l).getNbheure() + nh;
					listRecap.remove(j);
				} else {
					j++;
				}
			}
		}
	}

	private void calculTotalPourCollab(List<RecapProjetMensuelTO> listRecap) {
		double nbh = 0;
		for (int i = 0; i < listRecap.size(); i++) {

			int j = i + 1;
			while (j < listRecap.size()) {
				if (listRecap.get(i).getCollab().getIdColl() == listRecap
						.get(j).getCollab().getIdColl()
						&& listRecap.get(i).getNomProjet().getIdProjet() == listRecap
								.get(j).getNomProjet().getIdProjet()
						&& listRecap.get(i).getJour() == listRecap.get(j)
								.getJour()) {
					nbh = listRecap.get(i).getNbheure()
							+ listRecap.get(j).getNbheure();
					listRecap.get(i).setNbheure(nbh);
					listRecap.remove(j);
				} else {
					j++;
				}
			}

		}
	}

	public int definirLeJour(Date d) {
		int j;
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		j = cal.get((Calendar.DAY_OF_MONTH));

		return j;
	}

}
