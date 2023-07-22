package org.jeinnov.jeitime.api.to.bilan;

import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;

public class RecapProjetMensuelTO {
	private ProjetTO nomProjet;
	private int idTache;
	private int jour;
	private CollaborateurTO collab;
	private double nbheure;

	public RecapProjetMensuelTO() {

	}

	public ProjetTO getNomProjet() {
		return nomProjet;
	}

	public void setNomProjet(ProjetTO nomProjet) {
		this.nomProjet = nomProjet;
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

	public int getJour() {
		return jour;
	}

	public void setJour(int jour) {
		this.jour = jour;
	}

	public int getIdTache() {
		return idTache;
	}

	public void setIdTache(int idTache) {
		this.idTache = idTache;
	}

}
