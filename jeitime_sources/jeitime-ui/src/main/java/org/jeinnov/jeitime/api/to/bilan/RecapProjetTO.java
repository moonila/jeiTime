package org.jeinnov.jeitime.api.to.bilan;

import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;

public class RecapProjetTO

{
	private ProjetTO nomProjet;
	private TacheTO tache;
	private CollaborateurTO collab;
	private double nbheure;

	// private double totalnbheure;

	public RecapProjetTO() {

	}

	public RecapProjetTO(ProjetTO nomProjet, TacheTO tache,
			CollaborateurTO collab, double nbheure) {
		super();
		this.nomProjet = nomProjet;
		this.tache = tache;
		this.collab = collab;
		this.nbheure = nbheure;
		// this.totalnbheure = totalnbheure;
	}

	/*
	 * public double getTotalnbheure() { return totalnbheure; }
	 * 
	 * public void setTotalnbheure(double totalnbheure) { this.totalnbheure =
	 * totalnbheure; }
	 */

	public ProjetTO getNomProjet() {
		return nomProjet;
	}

	public void setNomProjet(ProjetTO nomProjet) {
		this.nomProjet = nomProjet;
	}

	public TacheTO getTache() {
		return tache;
	}

	public void setTache(TacheTO tache) {
		this.tache = tache;
	}

	public CollaborateurTO getCollab() {
		return collab;
	}

	public void setCollab(CollaborateurTO collab) {
		this.collab = collab;
	}

	public double getNbheure() {
		return nbheure;
	}

	public void setNbheure(double nbheure) {
		this.nbheure = nbheure;
	}

}
