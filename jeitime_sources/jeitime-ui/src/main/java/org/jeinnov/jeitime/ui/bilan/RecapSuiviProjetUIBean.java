package org.jeinnov.jeitime.ui.bilan;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.jeinnov.jeitime.api.service.bilan.BilanException;
import org.jeinnov.jeitime.api.service.bilan.RecapProjetManager;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurManager;
import org.jeinnov.jeitime.api.service.projet.ProjetManager;
import org.jeinnov.jeitime.api.to.bilan.RecapProjetTO;
import org.jeinnov.jeitime.api.to.bilan.SousTotal;
import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.ow2.opensuit.core.error.IError;
import org.ow2.opensuit.core.error.NonLocalizedError;


public class RecapSuiviProjetUIBean {

	private int[] selectedProjet;
	private List<ProjetTO> allprojet = new ArrayList<ProjetTO>();

	private List<RecapProjetTO> listRecap = new ArrayList<RecapProjetTO>();

	private List<Object> items = new ArrayList<Object>();

	private List<CollaborateurTO> collaborateurs = new ArrayList<CollaborateurTO>();

	public void load() {
		allprojet = ProjetManager.getInstance().getAll();
	}

	public String nomDomaine(ProjetTO p) {

		if (p.getDomaine() == null || p.getDomaine().getNomDomaine() == null) {
			return "";
		} else {
			return p.getDomaine().getNomDomaine();
		}
	}

	public String nomThema(ProjetTO p) {

		if (p.getThematique() == null || p.getThematique().getNomThema() == null) {
			return "";
		} else {
			return p.getThematique().getNomThema();
		}
	}

	/************************************************************************
	 * This method allows to fill a project table with R&D tasks,NonR&D tasks or
	 * both at the same time.
	 * 
	 * @throws IError
	 ************************************************************************/
	public void voirTableau() throws IError

	{
		collaborateurs = CollaborateurManager.getInstance().getAll();

		try {
			items = RecapProjetManager.getInstance().listTache(selectedProjet);
		} catch (BilanException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention, aucun projet n'est associé", e.getMessage(), e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		try {
			listRecap = RecapProjetManager.getInstance()
					.creerListRecapSuiviProjet(selectedProjet);
		} catch (BilanException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention, aucun projet n'est associé", e.getMessage(), e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
	}

	/****************************************
	 * Méthodes permettant le remplissage du tableau récapitulatif par projet
	 ****************************************/

	public String getHtmlName(Object item) {
		if (item instanceof String) {
			// this is the project name
			return "\\html\\<span class=sousTitleTable>" + item + "</span>";
		} else if (item instanceof Integer) {
			return "Total";
		}

		else if (item instanceof SousTotal) {
			return ((SousTotal) item).getNomSousTotal();
		}
		// else: this is a task
		else {
			return ((TacheTO) item).getNomtache().getNomTache();

		}
	}

	/***************************************************************************
	 * Méthode qui remplit le tableau de tâches R&D et Non R&D sans distinction*
	 ***************************************************************************/
	public String getValue(Object item, CollaborateurTO collab) {
		if (item instanceof String) {
			// this is the project name
			return null;
		} else if (item instanceof Integer) {
			return itemsInstanceOfInteger(item, collab);
		} else if (item instanceof SousTotal) {
			return itemsInstanceOfSousTotal(item, collab);
		} else {
			return itemsInstanceOfTacheTO(item, collab);
		}
	}

	private String itemsInstanceOfInteger(Object item, CollaborateurTO collab) {
		String nb = "0";
		int idP = (Integer) item;
		double nbht = 0;

		for (int i = 0; i < listRecap.size(); i++) {
			if (listRecap.get(i).getCollab().getIdColl() == collab.getIdColl()
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

	private String itemsInstanceOfSousTotal(Object item, CollaborateurTO collab) {
		String nb = "0";
		int idP = ((SousTotal) item).getIdProjet();
		boolean rd = ((SousTotal) item).isRd();
		double nbht = 0;
		for (int i = 0; i < listRecap.size(); i++) {

			if (listRecap.get(i).getCollab().getIdColl() == collab.getIdColl()
					&& listRecap.get(i).getNomProjet().getIdProjet() == idP
					&& listRecap.get(i).getTache().isEligible() == rd) {

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

	private String itemsInstanceOfTacheTO(Object item, CollaborateurTO collab) {
		double nbh;
		int idT = ((TacheTO) item).getIdTache();
		int idCo = collab.getIdColl();
		String nb = "";
		for (int i = 0; i < listRecap.size(); i++) {
			if ((idT == listRecap.get(i).getTache().getIdTache())
					&& (idCo == listRecap.get(i).getCollab().getIdColl())) {

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
			return total;
		} else if (item instanceof SousTotal) {
			// Insertion du total de la ligne sous total
			total = totalInstanceOfSousTotal(item);
			return total;
		} else {
			total = totalInstanceOfTacheTO(item, nbh);
		}
		return total;
	}

	private String totalInstanceOfInteger(Object item, double nbh) {
		String total;
		for (int i = 0; i < listRecap.size(); i++) {
			int idP = (Integer) item;
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

	private String totalInstanceOfSousTotal(Object item) {

		int idP = ((SousTotal) item).getIdProjet();
		String totalTmp = null;
		boolean rd = ((SousTotal) item).isRd();
		double nbht = 0;
		for (int i = 0; i < listRecap.size(); i++) {
			if (listRecap.get(i).getNomProjet().getIdProjet() == idP
					&& listRecap.get(i).getTache().isEligible() == rd) {

				nbht = listRecap.get(i).getNbheure() + nbht;

				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				df.setMinimumFractionDigits(2);
				df.setDecimalSeparatorAlwaysShown(true);

				totalTmp = df.format(nbht);
			}
		}
		return totalTmp;
	}

	private String totalInstanceOfTacheTO(Object item, double nbh) {
		String total;
		int idT = ((TacheTO) item).getIdTache();

		for (int i = 0; i < listRecap.size(); i++) {
			if ((idT == (listRecap.get(i).getTache().getIdTache()))) {
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
		selectedProjet = null;
	}

	/***********************
	 * 
	 * Getters et Setters
	 * 
	 **********************/
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

	public List<RecapProjetTO> getListRecap() {
		return listRecap;
	}

	public void setListRecap(List<RecapProjetTO> listRecap) {
		this.listRecap = listRecap;
	}

}
