package org.jeinnov.jeitime.api.to.bilan;

public class SousTotal {

	private int idProjet;
	private String nomSousTotal;
	private int idCollab;
	private boolean rd;

	public SousTotal() {
		super();
	}

	public int getIdProjet() {
		return idProjet;
	}

	public void setIdProjet(int idProjet) {
		this.idProjet = idProjet;
	}

	public boolean isRd() {
		return rd;
	}

	public void setRd(boolean rd) {
		this.rd = rd;
	}

	public String getNomSousTotal() {
		return nomSousTotal;
	}

	public void setNomSousTotal(String nomSousTotal) {
		this.nomSousTotal = nomSousTotal;
	}

	public int getIdCollab() {
		return idCollab;
	}

	public void setIdCollab(int idCollab) {
		this.idCollab = idCollab;
	}

}
