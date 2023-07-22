package org.jeinnov.jeitime.ui.heure;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurManager;
import org.jeinnov.jeitime.api.service.heure.ConsultationMensuelleManager;
import org.jeinnov.jeitime.api.service.heure.HeureException;
import org.jeinnov.jeitime.api.to.bilan.JourMois;
import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.heure.SaisieHeureTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.jeinnov.jeitime.ui.utils.CalculJourDate;
import org.ow2.opensuit.core.error.IError;
import org.ow2.opensuit.core.error.NonLocalizedError;



public class ConsultMensCollabUIBean {

	private CalculJourDate calCulJourDate = new CalculJourDate();

	private static int[] ALL_MOIS = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
	private int mois;
	private int annee;

	private Date dateDeb;
	private Date dateFin;

	private List<JourMois> jour = new ArrayList<JourMois>();

	private List<SaisieHeureTO> listSaisieMens = new ArrayList<SaisieHeureTO>();
	private List<Object> items = new ArrayList<Object>();
	private List<CollaborateurTO> allCollaborateur = new ArrayList<CollaborateurTO>();
	private int[] selectedC;

	private boolean voirTabl = false;
	private boolean voirCollab = false;
	private boolean voirDate = true;

	public void load() {
		allCollaborateur = CollaborateurManager.getInstance().getAll();

		voirTabl = false;
		voirCollab = false;
		voirDate = true;
		Calendar cal = GregorianCalendar.getInstance();
		cal.getTime();
		annee = cal.get(Calendar.YEAR);
	}

	public void voirSaisieMens() throws IError {
		jour = new ArrayList<JourMois>();
		calculDate();
		List<Object> itemTmp = new ArrayList<Object>();
		List<SaisieHeureTO> listSaisieMensTmp = new ArrayList<SaisieHeureTO>();
		listSaisieMens = new ArrayList<SaisieHeureTO>();
		items = new ArrayList<Object>();
		for (int i = 0; i < selectedC.length; i++) {
			int idColl = selectedC[i];

			try {
				listSaisieMensTmp = ConsultationMensuelleManager.getInstance()
						.afficheListSaisieAllC(idColl, dateDeb, dateFin);
			} catch (HeureException e) {
				NonLocalizedError error = new NonLocalizedError("Attention : ",
						"le collaborateur avec l'id : " + idColl
								+ " n'existe pas dans la base.", e);
				error.setType(IError.FUNCTIONAL_ERROR);
				throw error;
			}
			try {
				itemTmp = ConsultationMensuelleManager.getInstance()
						.listSaisieAllCollab(listSaisieMensTmp);
			} catch (HeureException e) {
				NonLocalizedError error = new NonLocalizedError(
						"Attention : ",
						"Une erreur est survenue, la liste des saisie du collaborateur n'a pas pu être chargée",
						e);
				error.setType(IError.FUNCTIONAL_ERROR);
				throw error;
			}

			for (Object o : itemTmp) {
				items.add(o);
			}
			for (SaisieHeureTO sh : listSaisieMensTmp) {
				listSaisieMens.add(sh);
			}
		}

		calCulJourDate.nbrJour(annee, mois, getJour());

		voirCollab = false;
		voirDate = false;
		voirTabl = true;
	}

	public String getHtmlName(Object item) {
		if (item instanceof String) {
			// this is the project name
			return "\\html\\<span class=Title>" + item + "</span>";
		} else if (item instanceof CollaborateurTO) {
			return "\\html\\<span class=sousTitre>"
					+ ((CollaborateurTO) item).getNomColl() + "</span>";
		} else if (item instanceof Integer) {
			return "\\html\\<span class=sousTitre> Total</span>";
		}
		// else: this is a task
		else {
			return ((TacheTO) item).getNomtache().getNomTache();
		}
	}

	public String getValue(Object item, int j) {
		String nb = "";
		if (item instanceof String) {
			// this is the project name
			return null;
		} else if (item instanceof CollaborateurTO) {
			return null;
		} else if (item instanceof Integer) {
			nb = itemsInstanceOfInteger(item, j);
			return nb;
		} else {
			nb = itemsInstanceOfTacheTO(item, j);
			return nb;
		}
	}

	private String itemsInstanceOfInteger(Object item, int j) {
		String nb;
		nb = "0";
		double nbht = 0;
		int idC = ((Integer) item);
		int jo = 0;

		for (int i = 0; i < listSaisieMens.size(); i++) {
			if (listSaisieMens.get(i).getSaisiDate() != null) {

				jo = definirLeJour(listSaisieMens.get(i).getSaisiDate());

				if (jo == j
						&& idC == listSaisieMens.get(i).getCollab().getIdColl()) {
					nbht = listSaisieMens.get(i).getNbHeure() + nbht;

					DecimalFormat df = new DecimalFormat();
					df.setMaximumFractionDigits(2);
					df.setMinimumFractionDigits(2);
					df.setDecimalSeparatorAlwaysShown(true);
					nb = df.format(nbht);
				}
			}
		}
		return nb;
	}

	private String itemsInstanceOfTacheTO(Object item, int j) {
		String nb = "";
		double nbh;
		int idT = ((TacheTO) item).getIdTache();
		int idC = ((TacheTO) item).getPriorite();

		for (int i = 0; i < listSaisieMens.size(); i++) {

			if (listSaisieMens.get(i).getSaisiDate() != null) {

				int jo = definirLeJour(listSaisieMens.get(i).getSaisiDate());
				if ((j == jo)
						&& (idT == listSaisieMens.get(i).getTache()
								.getIdTache())
						&& (idC == listSaisieMens.get(i).getCollab()
								.getIdColl())) {
					nbh = listSaisieMens.get(i).getNbHeure();
					DecimalFormat df = new DecimalFormat();
					df.setMaximumFractionDigits(2);
					df.setMinimumFractionDigits(2);
					df.setDecimalSeparatorAlwaysShown(true);

					nb = df.format(nbh);
				}
			}
		}
		return nb;
	}

	public String getTotal(Object item) {
		double nbh = 0;
		// String total="0";
		if (item instanceof String) {
			// this is the project name
			return null;
		} else if (item instanceof CollaborateurTO) {
			return null;
		} else if (item instanceof Integer) {
			return totalInstanceOfInteger(item, nbh);
		} else {
			return totalInstanceOfTacheTO(item, nbh);
		}
	}

	private String totalInstanceOfInteger(Object item, double nbh) {
		int idC = (Integer) item;
		for (int i = 0; i < listSaisieMens.size(); i++) {
			if (idC == listSaisieMens.get(i).getCollab().getIdColl()) {
				nbh = listSaisieMens.get(i).getNbHeure() + nbh;
			}
		}
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		df.setDecimalSeparatorAlwaysShown(true);

		return df.format(nbh);
	}

	private String totalInstanceOfTacheTO(Object item, double nbh) {
		int idT = ((TacheTO) item).getIdTache();
		int idC = ((TacheTO) item).getPriorite();

		for (int i = 0; i < listSaisieMens.size(); i++) {
			if (idT != 0 && listSaisieMens.get(i).getTache().getIdTache() != 0) {

				if (idT == listSaisieMens.get(i).getTache().getIdTache()
						&& idC == listSaisieMens.get(i).getCollab().getIdColl()) {
					nbh = listSaisieMens.get(i).getNbHeure() + nbh;
				}
			}
		}
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		df.setDecimalSeparatorAlwaysShown(true);

		return df.format(nbh);
	}

	public void calculDate() {
		Calendar cal = GregorianCalendar.getInstance();
		if (annee == 0) {
			cal.getTime();
			annee = cal.get(Calendar.YEAR);
		}
		if (mois == 1) {
			if (annee == 2012 || annee == 2016 || annee == 2020) {
				dateDeb = calCulJourDate.calculDateFevrierDebut(cal, annee,
						mois);
				dateFin = calCulJourDate.calculDateFevrierFin29(cal, annee,
						mois);
			} else {
				dateDeb = calCulJourDate.calculDateFevrierDebut(cal, annee,
						mois);
				dateFin = calCulJourDate.calculDateFevrierFin28(cal, annee,
						mois);
			}
		} else if ((mois == 0 || mois == 2 || mois == 4 || mois == 6
				|| mois == 7 || mois == 9 || mois == 11)) {
			dateDeb = calCulJourDate.calculDateMoisDebut(cal, annee, mois);
			dateFin = calCulJourDate.calculDateMois31Fin(cal, annee, mois);
		} else {
			dateDeb = calCulJourDate.calculDateMoisDebut(cal, annee, mois);
			dateFin = calCulJourDate.calculDateMois30Fin(cal, annee, mois);
		}
	}

	public int definirLeJour(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);

		return cal.get((Calendar.DAY_OF_MONTH));
	}

	public void voirMoisSuiv() throws IError {
		if (mois == 11) {
			annee = annee + 1;
			mois = 0;
		} else {
			mois = mois + 1;
		}
		jour = new ArrayList<JourMois>();
		voirSaisieMens();

	}

	public void voirMoisPrec() throws IError {
		if (mois == 0) {
			annee = annee - 1;
			mois = 11;
		} else {
			mois = mois - 1;
		}
		jour = new ArrayList<JourMois>();
		voirSaisieMens();
	}

	public void envoyer() {
		voirCollab = true;
	}

	public void reset() {
		voirTabl = false;
		voirCollab = false;
		voirDate = true;
		selectedC = null;
		listSaisieMens = new ArrayList<SaisieHeureTO>();
		items = new ArrayList<Object>();
		allCollaborateur = new ArrayList<CollaborateurTO>();

		allCollaborateur = CollaborateurManager.getInstance().getAll();
	}

	public void selectAll() {
		selectedC = new int[allCollaborateur.size()];
		for (int i = 0; i < allCollaborateur.size(); i++) {

			selectedC[i] = allCollaborateur.get(i).getIdColl();
		}
	}

	public void deSelectAll() {
		selectedC = null;
	}

	/***
	 * 
	 * Getters and Setters
	 */
	public int getMois() {
		return mois;
	}

	public void setMois(int mois) {
		this.mois = mois;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public Date getDateDeb() {
		return dateDeb;
	}

	public void setDateDeb(Date dateDeb) {
		this.dateDeb = dateDeb;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public List<JourMois> getJour() {
		return jour;
	}

	public void setJour(List<JourMois> jour) {
		this.jour = jour;
	}

	public List<SaisieHeureTO> getListSaisieMens() {
		return listSaisieMens;
	}

	public void setListSaisieMens(List<SaisieHeureTO> listSaisieMens) {
		this.listSaisieMens = listSaisieMens;
	}

	public List<Object> getItems() {
		return items;
	}

	public void setItems(List<Object> items) {
		this.items = items;
	}

	public List<CollaborateurTO> getAllCollaborateur() {
		return allCollaborateur;
	}

	public void setAllCollaborateur(List<CollaborateurTO> allCollaborateur) {
		this.allCollaborateur = allCollaborateur;
	}

	public int[] getSelectedC() {
		return selectedC;
	}

	public void setSelectedC(int[] selectedC) {
		this.selectedC = selectedC;
	}

	public boolean isVoirTabl() {
		return voirTabl;
	}

	public void setVoirTabl(boolean voirTabl) {
		this.voirTabl = voirTabl;
	}

	public boolean isVoirCollab() {
		return voirCollab;
	}

	public void setVoirCollab(boolean voirCollab) {
		this.voirCollab = voirCollab;
	}

	public boolean isVoirDate() {
		return voirDate;
	}

	public void setVoirDate(boolean voirDate) {
		this.voirDate = voirDate;
	}

	public static int[] getAllMois() {
		return ALL_MOIS;
	}

}
