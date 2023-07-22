package org.jeinnov.jeitime.api.to.projet;

public class ThematiqueTO {

	// --- Variables --- //

	private int idThema;
	private String nomThema;

	// --- Constructeurs --- //

	public ThematiqueTO() {

	}

	public ThematiqueTO(int idThema, String nomThema) {
		super();
		this.idThema = idThema;
		this.nomThema = nomThema;
	}

	public ThematiqueTO(int idThema) {
		super();
		this.idThema = idThema;
	}

	// --- Getters et Setters --- //

	public int getIdThema() {
		return idThema;
	}

	public void setIdThema(int idThema) {
		this.idThema = idThema;
	}

	public String getNomThema() {
		return nomThema;
	}

	public void setNomThema(String nomThema) {
		this.nomThema = nomThema;
	}

}
