package org.jeinnov.jeitime.api.to.projet;

public class NomTacheTO {

	private int idNomTache;
	private String nomTache;

	private TypeTacheTO typeTache;

	public NomTacheTO() {

	}

	public NomTacheTO(int idNomTache, String nomTache) {
		this.idNomTache = idNomTache;
		this.nomTache = nomTache;
	}

	public NomTacheTO(int idNomTache, String nomTache, TypeTacheTO typeTache) {
		super();
		this.idNomTache = idNomTache;
		this.nomTache = nomTache;
		this.typeTache = typeTache;

	}

	public NomTacheTO(int idNomTache, TypeTacheTO typeTache) {
		super();
		this.idNomTache = idNomTache;
		this.typeTache = typeTache;

	}

	public NomTacheTO(int idNomTache) {
		super();
		this.idNomTache = idNomTache;
	}

	public int getIdNomTache() {
		return idNomTache;
	}

	public void setIdNomTache(int idNomTache) {
		this.idNomTache = idNomTache;
	}

	public String getNomTache() {
		return nomTache;
	}

	public void setNomTache(String nomTache) {
		this.nomTache = nomTache;
	}

	public TypeTacheTO getTypeTache() {
		return typeTache;
	}

	public void setTypeTache(TypeTacheTO typeTache) {
		this.typeTache = typeTache;
	}

}
