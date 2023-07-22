package org.jeinnov.jeitime.api.to.bilan;

import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;

public class RecapTO {

	private String intitule;
	private ProjetTO nomP;
	private CollaborateurTO collab;
	private double nbheure;

	public RecapTO() {
		super();

	}

	public RecapTO(String intitule, ProjetTO nomP, CollaborateurTO collab,
			double nbheure) {
		super();
		this.intitule = intitule;
		this.nomP = nomP;
		this.collab = collab;
		this.nbheure = nbheure;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public ProjetTO getNomP() {
		return nomP;
	}

	public void setNomP(ProjetTO nomP) {
		this.nomP = nomP;
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