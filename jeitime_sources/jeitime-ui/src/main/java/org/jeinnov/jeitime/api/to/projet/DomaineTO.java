package org.jeinnov.jeitime.api.to.projet;

public class DomaineTO {
	// --- Variables --- //

	private int idDomaine;
	private String nomDomaine;

	// --- Constructeur --- //

	public DomaineTO() {

	}

	public DomaineTO(int idDomaine, String nomDomaine) {
		super();
		this.idDomaine = idDomaine;
		this.nomDomaine = nomDomaine;
	}

	public DomaineTO(int idDomaine) {
		super();
		this.idDomaine = idDomaine;
	}

	// --- Getters et Setters --- //

	public int getIdDomaine() {
		return idDomaine;
	}

	public void setIdDomaine(int idDomaine) {
		this.idDomaine = idDomaine;
	}

	public String getNomDomaine() {
		return nomDomaine;
	}

	public void setNomDomaine(String nomDomaine) {
		this.nomDomaine = nomDomaine;
	}

}
