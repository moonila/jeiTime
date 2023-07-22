package org.jeinnov.jeitime.api.to.alerte;

import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;

public class AlertesTO {
	private String indicDate;
	private float nbheureCollege;
	private float nbheureSaisie;
	private float difference;

	private CollaborateurTO collab;

	public AlertesTO() {

	}

	public AlertesTO(String indicDate, float nbheureCollege,
			float nbheureSaisie, float difference, CollaborateurTO collab) {
		super();
		this.indicDate = indicDate;
		this.nbheureCollege = nbheureCollege;
		this.nbheureSaisie = nbheureSaisie;
		this.difference = difference;
		this.collab = collab;
	}

	public String getIndicDate() {
		return indicDate;
	}

	public void setIndicDate(String indicDate) {
		this.indicDate = indicDate;
	}

	public float getNbheureCollege() {
		return nbheureCollege;
	}

	public void setNbheureCollege(float nbheureCollege) {
		this.nbheureCollege = nbheureCollege;
	}

	public float getNbheureSaisie() {
		return nbheureSaisie;
	}

	public void setNbheureSaisie(float nbheureSaisie) {
		this.nbheureSaisie = nbheureSaisie;
	}

	public float getDifference() {
		return difference;
	}

	public void setDifference(float difference) {
		this.difference = difference;
	}

	public CollaborateurTO getCollab() {
		return collab;
	}

	public void setCollab(CollaborateurTO collab) {
		this.collab = collab;
	}

}
