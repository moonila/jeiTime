package org.jeinnov.jeitime.ui.bilan;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.jeinnov.jeitime.api.service.bilan.BilanException;
import org.jeinnov.jeitime.api.service.bilan.RecapProjetMensuelManager;
import org.jeinnov.jeitime.api.service.projet.ProjetManager;
import org.jeinnov.jeitime.api.to.bilan.CollaborateurRecapMensTO;
import org.jeinnov.jeitime.api.to.bilan.JourMois;
import org.jeinnov.jeitime.api.to.bilan.RecapProjetMensuelTO;
import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.ui.utils.CalculJourDate;
import org.ow2.opensuit.core.error.IError;
import org.ow2.opensuit.core.error.NonLocalizedError;



public class RecapProjetMensuelUIBean {

	private RecapProjetMensuelManager recapProjetMensuelManager = RecapProjetMensuelManager
			.getInstance();
	private ProjetManager projetManager = ProjetManager.getInstance();
	private CalculJourDate calCulJourDate = new CalculJourDate();

	private boolean voirTabl = false;
	private boolean voirProj = false;
	private boolean voirDate = true;

	private static int[] ALL_MOIS = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
	private int mois;
	private int annee;

	private int[] selectedProjet;
	private List<ProjetTO> allprojet = new ArrayList<ProjetTO>();

	private List<JourMois> jour = new ArrayList<JourMois>();

	private List<RecapProjetMensuelTO> listRecap = new ArrayList<RecapProjetMensuelTO>();

	private List<Object> items = new ArrayList<Object>();

	private List<CollaborateurTO> collaborateurs = new ArrayList<CollaborateurTO>();

	public void load() {
		items = new ArrayList<Object>();
		jour = new ArrayList<JourMois>();
		listRecap = new ArrayList<RecapProjetMensuelTO>();
		allprojet = projetManager.getAll();

		voirDate = true;
		voirProj = false;
		voirTabl = false;
		Calendar cal = GregorianCalendar.getInstance();
		cal.getTime();
		annee = cal.get(Calendar.YEAR);
	}

	/************************************************************************
	 * This method allows to fill a project table with R&D tasks,NonR&D tasks or
	 * both at the same time.
	 ************************************************************************/
	public void voirProjet() {
		voirProj = true;
	}

	public void reset() {

		items = new ArrayList<Object>();
		listRecap = new ArrayList<RecapProjetMensuelTO>();
		allprojet = new ArrayList<ProjetTO>();
		allprojet = projetManager.getAllNotLock();
		voirDate = true;
		voirProj = false;
		voirTabl = false;
		jour = new ArrayList<JourMois>();
		selectedProjet = null;
	}

	public void voirTableau() throws IError

	{
		Calendar cal = GregorianCalendar.getInstance();
		if (annee == 0) {
			cal.getTime();
			annee = cal.get(Calendar.YEAR);
		}

		Date dateDeb = calCulJourDate.calculDateDebut(cal, annee, mois);
		Date dateFin = calCulJourDate.calculDateFin(cal, annee, mois);

		items = new ArrayList<Object>();
		jour = new ArrayList<JourMois>();
		listRecap = new ArrayList<RecapProjetMensuelTO>();

		try {
			items = recapProjetMensuelManager.listCollab(selectedProjet);
		} catch (BilanException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention, aucun projet n'est associé", e.getMessage(), e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		try {
			listRecap = recapProjetMensuelManager.creerListRecapProjet(
					selectedProjet, dateDeb, dateFin);
		} catch (BilanException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention, aucun projet n'est associé", e.getMessage(), e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		calCulJourDate.nbrJour(annee, mois, getJour());

		voirTabl = true;
		voirProj = false;
		voirDate = false;
	}

	/****************************************
	 * Méthodes permettant le remplissage du tableau récapitulatif par projet
	 ****************************************/

	public String getHtmlName(Object item) {
		if (item instanceof String) {
			// this is the project name
			return "\\html\\<span class=sousTitleTable>" + item + "</span>";
		} else if (item instanceof Integer) {
			return " Total";
		} else {
			// else: this is a task
			return ((CollaborateurRecapMensTO) item).getNomCollab() + " "
					+ ((CollaborateurRecapMensTO) item).getPrenomCollab();
		}
	}

	/***************************************************************************
	 * Méthode qui remplit le tableau de tâches R&D et Non R&D sans distinction*
	 ***************************************************************************/
	public String getValue(Object item, int j) {
		if (item instanceof String) {
			// this is the project name
			return null;
		} else if (item instanceof Integer) {
			return itemsInstanceOfInteger(item, j);
		} else {
			return itemsInstanceOfCollaborateurRecapMensTO(item, j);
		}
	}

	private String itemsInstanceOfInteger(Object item, int j) {
		String nb = "0";
		int idP = (Integer) item;
		double nbht = 0;

		for (int i = 0; i < listRecap.size(); i++) {
			if (listRecap.get(i).getJour() == j
					&& listRecap.get(i).getNomProjet().getIdProjet() == idP) {
				nbht = listRecap.get(i).getNbheure() + nbht;

				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				df.setMinimumFractionDigits(2);
				df.setDecimalSeparatorAlwaysShown(true);
				nb = df.format(nbht);
			}
		}
		return nb;
	}

	private String itemsInstanceOfCollaborateurRecapMensTO(Object item, int j) {
		double nbh;
		int idCo = ((CollaborateurRecapMensTO) item).getIdCollab();
		int idP = ((CollaborateurRecapMensTO) item).getIdProjet();
		String nb = "";
		for (int i = 0; i < listRecap.size(); i++) {
			if ((j == listRecap.get(i).getJour())
					&& (idCo == listRecap.get(i).getCollab().getIdColl())
					&& (idP == listRecap.get(i).getNomProjet().getIdProjet())) {

				nbh = listRecap.get(i).getNbheure();
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				df.setMinimumFractionDigits(2);
				df.setDecimalSeparatorAlwaysShown(true);

				nb = df.format(nbh);
			}
		}
		return nb;
	}

	/***********************************************************************************************************
	 * Cette méthode permet de caculer le total des heures saisies par les
	 * collaborateurs sur un projet elle permet aussi d'afficher la valeur des
	 * sous totaux d'un projet qu'ils soient R&D, non R&D
	 ************************************************************************************************************/
	public String getTotal(Object item) {
		double nbh = 0;
		String total = "0";
		if (item instanceof String) {
			// this is the project name
			return null;
		} else if (item instanceof Integer) {
			total = totalInstanceOfInteger(item, nbh);
		} else {
			// Insertion du total de la ligne sous total
			total = totalInstanceOfCollaborateurRecapMensTO(item, nbh);
		}
		return total;
	}

	private String totalInstanceOfInteger(Object item, double nbh) {
		String total;
		int idP = (Integer) item;
		for (int i = 0; i < listRecap.size(); i++) {
			if (idP == listRecap.get(i).getNomProjet().getIdProjet()) {
				nbh = listRecap.get(i).getNbheure() + nbh;
			}
		}
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		df.setDecimalSeparatorAlwaysShown(true);

		total = df.format(nbh);
		return total;
	}

	private String totalInstanceOfCollaborateurRecapMensTO(Object item,
			double nbh) {
		String total;
		int idCo = ((CollaborateurRecapMensTO) item).getIdCollab();
		int idP = ((CollaborateurRecapMensTO) item).getIdProjet();
		for (int i = 0; i < listRecap.size(); i++) {
			if (idCo == listRecap.get(i).getCollab().getIdColl()
					&& idP == listRecap.get(i).getNomProjet().getIdProjet()) {
				nbh = listRecap.get(i).getNbheure() + nbh;
			}
		}
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		df.setDecimalSeparatorAlwaysShown(true);
		total = df.format(nbh);
		return total;
	}

	/*******************************************************************************
	 * Méthodes permettant de sélectionner tous les projets ou de les
	 * déselectionner
	 ********************************************************************************/
	public void selectAll() {
		selectedProjet = new int[allprojet.size()];
		for (int i = 0; i < allprojet.size(); i++) {
			selectedProjet[i] = allprojet.get(i).getIdProjet();
		}
	}

	public void deSelectAll() {
		// selectedProjet = new int [allprojet.size()];
		selectedProjet = null;
	}

	public void voirMoisSuiv() throws IError {
		if (mois == 11) {
			annee = annee + 1;
			mois = 0;
		} else {
			mois = mois + 1;

		}
		jour = new ArrayList<JourMois>();
		voirTableau();

	}

	public void voirMoisPrec() throws IError {
		if (mois == 0) {
			annee = annee - 1;
			mois = 11;
		} else {
			mois = mois - 1;

		}
		jour = new ArrayList<JourMois>();
		voirTableau();
	}

	/***************************
	 * 
	 * Getters and Setters
	 * 
	 ************************/

	public int[] getSelectedProjet() {
		return selectedProjet;
	}

	public void setSelectedProjet(int[] selectedProjet) {
		this.selectedProjet = selectedProjet;
	}

	public List<ProjetTO> getAllprojet() {
		return allprojet;
	}

	public void setAllprojet(List<ProjetTO> allprojet) {
		this.allprojet = allprojet;
	}

	public List<Object> getItems() {
		return items;
	}

	public void setItems(List<Object> items) {
		this.items = items;
	}

	public List<CollaborateurTO> getCollaborateurs() {
		return collaborateurs;
	}

	public void setCollaborateurs(List<CollaborateurTO> collaborateurs) {
		this.collaborateurs = collaborateurs;
	}

	public boolean isVoirTabl() {
		return voirTabl;
	}

	public void setVoirTabl(boolean voirTabl) {
		this.voirTabl = voirTabl;
	}

	public boolean isVoirProj() {
		return voirProj;
	}

	public void setVoirProj(boolean voirProj) {
		this.voirProj = voirProj;
	}

	public List<RecapProjetMensuelTO> getListRecap() {
		return listRecap;
	}

	public void setListRecap(List<RecapProjetMensuelTO> listRecap) {
		this.listRecap = listRecap;
	}

	public boolean isVoirDate() {
		return voirDate;
	}

	public void setVoirDate(boolean voirDate) {
		this.voirDate = voirDate;
	}

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

	public List<JourMois> getJour() {
		return jour;
	}

	public void setJour(List<JourMois> jour) {
		this.jour = jour;
	}

}
