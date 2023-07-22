package org.jeinnov.jeitime.api.service.heure;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.collaborateur.AppartientCollegeManager;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurException;
import org.jeinnov.jeitime.api.service.collaborateur.CollegeManager;
import org.jeinnov.jeitime.api.service.projet.ResultTransformersProject;
import org.jeinnov.jeitime.api.service.utils.CalculDate;
import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.heure.GereHeure;
import org.jeinnov.jeitime.api.to.heure.SaisieHeureTO;
import org.jeinnov.jeitime.api.to.projet.NomTacheTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.jeinnov.jeitime.persistence.bo.projet.LienTachUtilIdP;
import org.jeinnov.jeitime.persistence.bo.projet.LienTachUtilP;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;
import org.jeinnov.jeitime.persistence.dao.collaborateur.CollaborateurDAO;
import org.jeinnov.jeitime.persistence.dao.heure.LienTacheUtilDAO;
import org.jeinnov.jeitime.persistence.dao.projet.TacheDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;
import org.ow2.opensuit.core.error.IError;
import org.ow2.opensuit.core.error.NonLocalizedError;


public class GestionHeureManager {
	private final Logger logger = Logger.getLogger(this.getClass());
	private LienTacheUtilDAO lienTachUtilDAO = LienTacheUtilDAO.getInstance();
	private CalculDate calculDate = new CalculDate();
	private ResultTransformersProject resultTransformer = new ResultTransformersProject();

	private static GestionHeureManager manager;

	public GestionHeureManager() {

	}

	public static GestionHeureManager getInstance() {
		if (manager == null) {
			manager = new GestionHeureManager();
		}
		return manager;
	}

	public SaisieHeureTO getByIdCollaborateurAndIdTacheAndDate(int idTache,
			int idColl, Date date) throws HeureException {
		if (date == null) {
			throw new HeureException("Attention, une date doit être renseignée");
		}
		if (idColl == 0 || idTache == 0) {
			throw new HeureException(
					"Attention, un collaborateur et une tâche doivent être sélectionnés");
		}

		SaisieHeureTO saisieHeure = new SaisieHeureTO();
		Timestamp timeDeb = new Timestamp(date.getTime());

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			LienTachUtilP lienTach = lienTachUtilDAO
					.getByIdCollaborateurAndIdTacheAndDate(idColl, idTache,
							timeDeb);

			if (lienTach != null) {
				TacheTO tache = resultTransformer
						.toTacheTO(lienTach.getTache());

				CollaborateurTO collaborateur = new CollaborateurTO();
				collaborateur.setIdColl(idColl);

				float totalHeures = lienTach.getNbHeure();
				String commentaire = lienTach.getCommentaire();

				saisieHeure.setCollab(collaborateur);
				saisieHeure.setProjet(tache.getProjet());
				saisieHeure.setTache(tache);
				saisieHeure.setNbHeure(totalHeures);
				saisieHeure.setCommentaire(commentaire);
				saisieHeure.setSaisiDate(date);
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}

		return saisieHeure;
	}

	public List<SaisieHeureTO> getAllByIdCollaborateurAndDate(int idColl,
			Date date) throws HeureException {
		if (date == null) {
			throw new HeureException("Attention, une date doit être renseignée");
		}
		if (idColl == 0) {
			throw new HeureException(
					"Attention, un collaborateur doit être sélectionné");
		}
		Timestamp startDate = calculDate.getDateStartWeek(date);
		Timestamp endDate = calculDate.getDateEndWeek(date);
		ArrayList<SaisieHeureTO> tablSaisie = new ArrayList<SaisieHeureTO>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<LienTachUtilP> listLTU = lienTachUtilDAO
					.getAllByIdCollaborateurAndStartDateAndEndDate(idColl,
							startDate, endDate);

			if (listLTU != null) {
				for (LienTachUtilP lien : listLTU) {
					SaisieHeureTO saisieHeure = new SaisieHeureTO();

					TacheTO tache = resultTransformer
							.toTacheTO(lien.getTache());

					CollaborateurTO collaborateur = new CollaborateurTO();
					collaborateur.setIdColl(idColl);

					float totalHeures = lien.getNbHeure();
					String commentaire = lien.getCommentaire();

					saisieHeure.setCollab(collaborateur);
					saisieHeure.setProjet(tache.getProjet());
					saisieHeure.setTache(tache);
					saisieHeure.setNbHeure(totalHeures);
					saisieHeure.setCommentaire(commentaire);
					saisieHeure.setSaisiDate(lien.getId().getDate());

					tablSaisie.add(saisieHeure);
				}
			}
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return tablSaisie;
	}

	public void saveOrUpdate(SaisieHeureTO saisieHeure) throws HeureException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		verificationAvantEnregistrement(saisieHeure);

		int idCollaborateur = saisieHeure.getCollab().getIdColl();
		Date date = saisieHeure.getSaisiDate();
		int idTache = saisieHeure.getTache().getIdTache();
		Timestamp timeDate = new Timestamp(date.getTime());

		try {
			LienTachUtilP lienTach = lienTachUtilDAO
					.getByIdCollaborateurAndIdTacheAndDate(idCollaborateur,
							idTache, timeDate);

			if (lienTach != null) {

				lienTachUtilDAO.remove(lienTach);

				lienTach.setCollaborateur(new CollaborateurP(idCollaborateur));
				lienTach.setTache(new TacheP(idTache));
				lienTach.setId(new LienTachUtilIdP(idTache, idCollaborateur,
						timeDate));
				lienTach.setCommentaire(saisieHeure.getCommentaire());
				lienTach.setNbHeure(saisieHeure.getNbHeure());

				lienTachUtilDAO.save(lienTach);
			} else {
				LienTachUtilIdP id = new LienTachUtilIdP();
				id.setDate(date);
				id.setIdColl(idCollaborateur);
				id.setIdTache(idTache);

				lienTach = new LienTachUtilP();
				lienTach.setId(id);
				lienTach.setCommentaire(saisieHeure.getCommentaire());
				lienTach.setNbHeure(saisieHeure.getNbHeure());

				lienTachUtilDAO.save(lienTach);
			}
			tx.commit();
		}  catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(),e);
			throw e;
		}
	}

	public void delete(SaisieHeureTO saisieHeure) throws HeureException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		verificationAvantEnregistrement(saisieHeure);
		int idCollaborateur = saisieHeure.getCollab().getIdColl();
		Date date = saisieHeure.getSaisiDate();
		int idTache = saisieHeure.getTache().getIdTache();

		LienTachUtilIdP id = new LienTachUtilIdP();
		id.setDate(date);
		id.setIdColl(idCollaborateur);
		id.setIdTache(idTache);

		try {
			lienTachUtilDAO.removeById(id);
			tx.commit();
		}  catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(),e);
			throw e;
		}
	}

	public float getResultByIdCollaborateurAndDate(int idColl, Date date)
			throws HeureException {
		if (date == null) {
			throw new HeureException("Attention, une date doit être renseignée");
		}
		if (idColl == 0) {
			throw new HeureException(
					"Attention, un collaborateur et une tâche doivent être sélectionnés");
		}

		Timestamp timeDeb = new Timestamp(date.getTime());
		float totalHeures = 0;

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			List<LienTachUtilP> listLTU = lienTachUtilDAO
					.getAllByIdCollaborateurAndDate(idColl, timeDeb);
			if (listLTU != null) {
				for (LienTachUtilP lien : listLTU) {
					totalHeures = lien.getNbHeure() + totalHeures;
				}
			}
			tx.commit();
		}  catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(),e);
			throw e;
		}
		return totalHeures;
	}

	public String[] listSaisieVerifCollege(int idC, String[] listSaisie)
			throws NonLocalizedError {
		int idCollege = AppartientCollegeManager.getInstance().get(idC);

		if (idCollege != 0) {
			try {
				listSaisie = CollegeManager.getInstance()
						.createListSelectionHeure(idCollege);
			} catch (CollaborateurException e) {
				NonLocalizedError error = new NonLocalizedError("Attention : ",
						"Aucun college n'est sélectionné", e);
				error.setType(IError.FUNCTIONAL_ERROR);
				throw error;
			}
		}
		return listSaisie;
	}

	public List<GereHeure> fusionElemTablHeure(List<GereHeure> tablHeure) {
		float nbLun = 0, nbMar = 0, nbMerc = 0, nbJeud = 0, nbVend = 0, totaux = 0;
		List<GereHeure> tablTem = new ArrayList<GereHeure>();

		for (int i = 0; i < tablHeure.size(); i++) {
			nbLun = tablHeure.get(i).getNbHeureLundi();
			nbMar = tablHeure.get(i).getNbHeureMardi();
			nbMerc = tablHeure.get(i).getNbHeureMerc();
			nbJeud = tablHeure.get(i).getNbHeureJeudi();
			nbVend = tablHeure.get(i).getNbHeureVend();
			int j = i + 1;
			while (j < tablHeure.size()) {
				if (tablHeure.get(i).compareTo(tablHeure.get(j)) == 0) {
					if (nbLun == 0) {
						nbLun = tablHeure.get(i).getNbHeureLundi()
								+ tablHeure.get(j).getNbHeureLundi();
					}
					if (nbMar == 0) {
						nbMar = tablHeure.get(i).getNbHeureMardi()
								+ tablHeure.get(j).getNbHeureMardi();
					}
					if (nbMerc == 0) {
						nbMerc = tablHeure.get(i).getNbHeureMerc()
								+ tablHeure.get(j).getNbHeureMerc();
					}
					if (nbJeud == 0) {
						nbJeud = tablHeure.get(i).getNbHeureJeudi()
								+ tablHeure.get(j).getNbHeureJeudi();
					}
					if (nbVend == 0) {
						nbVend = tablHeure.get(i).getNbHeureVend()
								+ tablHeure.get(j).getNbHeureVend();
					}
					tablHeure.remove(j);
				} else {
					++j;
				}
			}
			GereHeure gereHeure = new GereHeure(nbLun, nbMar, nbMerc, nbJeud,
					nbVend, totaux, tablHeure.get(i).getNomTache(), tablHeure
							.get(i).getNomProjet(), tablHeure.get(i)
							.getIdTache());
			gereHeure.setTache(tablHeure.get(i).getTache());

			tablTem.add(gereHeure);
		}

		insertTotal(tablTem);

		tablHeure = getTotalSaisieHeure(tablTem);

		return tablHeure;
	}

	private void insertTotal(List<GereHeure> tablTem) {
		float totaux;
		for (int i = 0; i < tablTem.size(); i++) {
			totaux = tablTem.get(i).getNbHeureLundi()
					+ tablTem.get(i).getNbHeureMardi()
					+ tablTem.get(i).getNbHeureMerc()
					+ tablTem.get(i).getNbHeureJeudi()
					+ tablTem.get(i).getNbHeureVend();
			tablTem.get(i).setTotal(totaux);
		}
	}

	private List<GereHeure> getTotalSaisieHeure(List<GereHeure> tablHeure) {
		float nblun = 0;
		float nbMar = 0;
		float nbMerc = 0;
		float nbJeu = 0;
		float nbVen = 0;
		float totaux = 0;
		for (int i = 0; i < tablHeure.size(); i++) {
			nblun = tablHeure.get(i).getNbHeureLundi() + nblun;
			nbMar = tablHeure.get(i).getNbHeureMardi() + nbMar;
			nbMerc = tablHeure.get(i).getNbHeureMerc() + nbMerc;
			nbJeu = tablHeure.get(i).getNbHeureJeudi() + nbJeu;
			nbVen = tablHeure.get(i).getNbHeureVend() + nbVen;

			totaux = nblun + nbMar + nbMerc + nbJeu + nbVen;
		}

		GereHeure totalTmp = new GereHeure();
		totalTmp.setNbHeureJeudi(nbJeu);
		totalTmp.setNbHeureLundi(nblun);
		totalTmp.setNbHeureMardi(nbMar);
		totalTmp.setNbHeureMerc(nbMerc);
		totalTmp.setNbHeureVend(nbVen);
		totalTmp.setNomProjet("");
		totalTmp.setNomTache("Total");
		TacheTO tacheTO = new TacheTO();

		NomTacheTO nomTacheTO = new NomTacheTO();
		nomTacheTO.setNomTache("Total");

		tacheTO.setNomtache(nomTacheTO);
		totalTmp.setTache(tacheTO);
		totalTmp.setTotal(totaux);

		tablHeure.add(totalTmp);

		return tablHeure;
	}

	private void verificationAvantEnregistrement(SaisieHeureTO saisieHeure)
			throws HeureException {
		if (saisieHeure == null) {
			throw new HeureException(
					"Attention aucune saisie d'heure n'est renseignée !");
		}
		if (saisieHeure.getCollab() == null
				|| saisieHeure.getCollab().getIdColl() == 0) {
			throw new HeureException(
					"Attention aucun collaborateur n'est associé à cette saisie !");
		}
		if (saisieHeure.getTache() == null
				|| saisieHeure.getTache().getIdTache() == 0) {
			throw new HeureException(
					"Attention aucune tâche n'est associée à cette saisie !");
		}
		if (saisieHeure.getSaisiDate() == null) {
			throw new HeureException(
					"Attention aucune date n'est associée à cette saisie ! ");
		}

		CollaborateurP collab = CollaborateurDAO.getInstance().find(
				saisieHeure.getCollab().getIdColl());
		if (collab == null) {
			throw new HeureException(
					"Attention le collaborateur associé à cette saisie n'existe pas dans la base !");
		}

		TacheP tache = TacheDAO.getInstance().find(
				saisieHeure.getTache().getIdTache());
		if (tache == null) {
			throw new HeureException(
					"Attention la tâche associée à cette saisie n'existe pas dans la base !");
		}
	}
}
