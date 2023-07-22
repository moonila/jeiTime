package org.jeinnov.jeitime.api.to.bilan;

public class CollaborateurRecapMensTO {

	private int idCollab;
	private String nomCollab;
	private String prenomCollab;
	private int idProjet;

	public CollaborateurRecapMensTO() {
		super();
	}

	public int getIdCollab() {
		return idCollab;
	}

	public void setIdCollab(int idCollab) {
		this.idCollab = idCollab;
	}

	public String getNomCollab() {
		return nomCollab;
	}

	public void setNomCollab(String nomCollab) {
		this.nomCollab = nomCollab;
	}

	public String getPrenomCollab() {
		return prenomCollab;
	}

	public void setPrenomCollab(String prenomCollab) {
		this.prenomCollab = prenomCollab;
	}

	public int getIdProjet() {
		return idProjet;
	}

	public void setIdProjet(int idProjet) {
		this.idProjet = idProjet;
	}

}
