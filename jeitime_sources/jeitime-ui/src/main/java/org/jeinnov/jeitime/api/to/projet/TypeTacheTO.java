package org.jeinnov.jeitime.api.to.projet;

public class TypeTacheTO {
	// --- Variables --- //

	private int idTypTach;
	private String nomTypTach;

	// --- Constructeurs --- //

	public TypeTacheTO() {

	}

	public TypeTacheTO(int idTypTach, String nomTypTach) {
		super();
		this.idTypTach = idTypTach;
		this.nomTypTach = nomTypTach;
	}

	public TypeTacheTO(String nomTypTach) {
		super();
		this.nomTypTach = nomTypTach;
	}

	public TypeTacheTO(int idTypTach) {
		super();
		this.idTypTach = idTypTach;
	}

	// --- Getters et Settes --- //

	public int getIdTypTach() {
		return idTypTach;
	}

	public void setIdTypTach(int idTypTach) {
		this.idTypTach = idTypTach;
	}

	public String getNomTypTach() {
		return nomTypTach;
	}

	public void setNomTypTach(String nomTypTach) {
		this.nomTypTach = nomTypTach;
	}

}
