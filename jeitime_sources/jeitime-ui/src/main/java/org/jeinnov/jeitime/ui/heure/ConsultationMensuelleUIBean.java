package org.jeinnov.jeitime.ui.heure;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.jeinnov.jeitime.api.service.heure.ConsultationMensuelleManager;
import org.jeinnov.jeitime.api.service.heure.HeureException;
import org.jeinnov.jeitime.api.to.bilan.JourMois;
import org.jeinnov.jeitime.api.to.bilan.SousTotal;
import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.heure.SaisieHeureTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.jeinnov.jeitime.ui.utils.CalculJourDate;
import org.ow2.opensuit.core.error.IError;
import org.ow2.opensuit.core.error.NonLocalizedError;



public class ConsultationMensuelleUIBean {

	private CalculJourDate calCulJourDate = new CalculJourDate();

	private static int[] ALL_MOIS = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
	private int mois;
	private int annee;

	private Date dateDeb;
	private Date dateFin;

	private List<JourMois> jour = new ArrayList<JourMois>();

	private List<SaisieHeureTO> listSaisieMens = new ArrayList<SaisieHeureTO>();
	private List<Object> items = new ArrayList<Object>();

	private boolean voirTabl = false;
	private boolean voirDate = true;

	public void load() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.getTime();
		annee = cal.get(Calendar.YEAR);
	}

	public void voirSaisieMens(int idCollab) throws IError {
		jour = new ArrayList<JourMois>();
		calCulJourDate.nbrJour(annee, mois, getJour());
		calculDate();
		try {
			listSaisieMens = ConsultationMensuelleManager.getInstance()
					.afficheListSaisieAllC(idCollab, dateDeb, dateFin);
		} catch (HeureException e) {
			NonLocalizedError error = new NonLocalizedError("Attention : ",
					"Une erreur est survenue, "
							+ "le collaborateur avec l'id : " + idCollab
							+ " n'existe pas dans la base.", e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		try {
			items = ConsultationMensuelleManager.getInstance()
					.listSaisieAllCollab(listSaisieMens);
		} catch (HeureException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue, la liste des saisie du collaborateur n'a pas pu être chargée",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		voirTabl = true;
		voirDate = false;
	}

	public void cancel() {
		listSaisieMens = null;
		jour = null;
		items = null;
		voirDate = true;
		voirTabl = false;
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

		else if (item instanceof SousTotal) {
			return "\\html\\<span class=sousTitre>"
					+ ((SousTotal) item).getNomSousTotal() + "</span>";
		}
		// else: this is a task
		else {
			return ((TacheTO) item).getNomtache().getNomTache();
		}
	}

	public String getValue(Object item, int j) {
		if (item instanceof String) {
			// this is the project name
			return null;
		} else if (item instanceof CollaborateurTO) {
			return null;
		} else if (item instanceof Integer) {
			// String nb = itemsInstanceOfInteger(j);
			return "\\html\\<span class=sousTitre>" + itemsInstanceOfInteger(j)
					+ "</span>";
		} else if (item instanceof SousTotal) {
			// String nb = itemsInstanceOfSousTotal(item, j);
			return itemsInstanceOfSousTotal(item, j);
		} else {
			// String nb = itemsInstanceOfTacheTO(item, j);
			return itemsInstanceOfTacheTO(item, j);
		}
	}

	public String getTotal(Object item) {
		double nbh = 0;
		String total = "0";
		if (item instanceof String) {
			// this is the project name
			return null;
		} else if (item instanceof CollaborateurTO) {
			return null;
		} else if (item instanceof Integer) {
			total = totalInstanceOfInteger(nbh);
		} else if (item instanceof SousTotal) {
			// Insertion du total de la ligne sous total
			totalInstanceOfSousTotal(item, nbh);
		} else {

			total = totalInstanceOfTacheTO(item, nbh);
		}
		return total;
	}

	public void voirMoisSuiv(int idCollab) throws IError {
		if (mois == 11) {
			annee = annee + 1;
			mois = 0;
		} else {
			mois = mois + 1;
		}
		jour = new ArrayList<JourMois>();
		voirSaisieMens(idCollab);
	}

	public void voirMoisPrec(int idCollab) throws IError {
		if (mois == 0) {
			annee = annee - 1;
			mois = 11;
		} else {
			mois = mois - 1;
		}
		jour = new ArrayList<JourMois>();
		voirSaisieMens(idCollab);
	}

	/*********
	 *Private methods
	 */

	private void calculDate() {
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

	private int definirLeJour(Date d) {
		int j = 0;

		if (d != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			j = cal.get((Calendar.DAY_OF_MONTH));
		}
		return j;
	}

	private String itemsInstanceOfInteger(int j) {
		String nb = "0";
		double nbht = 0;

		for (int i = 0; i < listSaisieMens.size(); i++) {
			int jo = definirLeJour(listSaisieMens.get(i).getSaisiDate());
			if (jo == j) {
				nbht = listSaisieMens.get(i).getNbHeure() + nbht;

				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				df.setMinimumFractionDigits(2);
				df.setDecimalSeparatorAlwaysShown(true);
				nb = df.format(nbht);
			}
		}
		return nb;
	}

	private String itemsInstanceOfSousTotal(Object item, int j) {
		String nb = "0";
		double nbht = 0;
		int idP = ((SousTotal) item).getIdProjet();
		for (int i = 0; i < listSaisieMens.size(); i++) {
			int jo = definirLeJour(listSaisieMens.get(i).getSaisiDate());

			if (jo == j
					&& idP == listSaisieMens.get(i).getProjet().getIdProjet()) {
				nbht = listSaisieMens.get(i).getNbHeure() + nbht;

				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				df.setMinimumFractionDigits(2);
				df.setDecimalSeparatorAlwaysShown(true);
				nb = df.format(nbht);
			}
		}
		return nb;
	}

	private String itemsInstanceOfTacheTO(Object item, int j) {
		double nbh;
		int idT = ((TacheTO) item).getIdTache();
		String nb = "";
		for (int i = 0; i < listSaisieMens.size(); i++) {
			int jo = definirLeJour(listSaisieMens.get(i).getSaisiDate());
			if ((j == jo)
					&& (idT == listSaisieMens.get(i).getTache().getIdTache())) {
				nbh = listSaisieMens.get(i).getNbHeure();
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				df.setMinimumFractionDigits(2);
				df.setDecimalSeparatorAlwaysShown(true);

				nb = df.format(nbh);
			}
		}
		return nb;
	}

	private String totalInstanceOfInteger(double nbh) {
		String total;
		for (int i = 0; i < listSaisieMens.size(); i++) {
			nbh = listSaisieMens.get(i).getNbHeure() + nbh;
		}
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		df.setDecimalSeparatorAlwaysShown(true);

		total = df.format(nbh);
		return total;
	}

	private void totalInstanceOfSousTotal(Object item, double nbh) {
		int idP = ((SousTotal) item).getIdProjet();

		for (int i = 0; i < listSaisieMens.size(); i++) {
			if (idP == listSaisieMens.get(i).getProjet().getIdProjet()) {
				nbh = listSaisieMens.get(i).getNbHeure() + nbh;
			}
		}
	}

	private String totalInstanceOfTacheTO(Object item, double nbh) {
		String total;
		int idT = ((TacheTO) item).getIdTache();

		for (int i = 0; i < listSaisieMens.size(); i++) {
			if (idT == listSaisieMens.get(i).getTache().getIdTache()) {
				nbh = listSaisieMens.get(i).getNbHeure() + nbh;
			}
		}
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		df.setDecimalSeparatorAlwaysShown(true);
		total = df.format(nbh);
		return total;
	}

	/**
	 * 
	 * Getters and Setters
	 * 
	 */

	public int getMois() {
		return mois;
	}

	public void setMois(int mois) {
		this.mois = mois;
	}

	public static int[] getAllMois() {
		return ALL_MOIS;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public boolean isVoirTabl() {
		return voirTabl;
	}

	public void setVoirTabl(boolean voirTabl) {
		this.voirTabl = voirTabl;
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

	public boolean isVoirDate() {
		return voirDate;
	}

	public void setVoirDate(boolean voirDate) {
		this.voirDate = voirDate;
	}

}
