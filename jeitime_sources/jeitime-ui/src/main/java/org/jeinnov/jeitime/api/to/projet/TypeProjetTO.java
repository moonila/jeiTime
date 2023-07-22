package org.jeinnov.jeitime.api.to.projet;

public class TypeProjetTO {

	// --- Variables --- //

	private int idTypeProj;
	private String nomTypePro;

	// --- Constructeurs --- //

	public TypeProjetTO() {

	}

	public TypeProjetTO(int idTypeProj, String nomTypePro) {
		super();
		this.idTypeProj = idTypeProj;
		this.nomTypePro = nomTypePro;
	}

	public TypeProjetTO(int idTypeProj) {
		super();
		this.idTypeProj = idTypeProj;
	}

	// --- Getters et Setters --- //

	public int getIdTypeProj() {
		return idTypeProj;
	}

	public void setIdTypeProj(int idTypeProj) {
		this.idTypeProj = idTypeProj;
	}

	public String getNomTypePro() {
		return nomTypePro;
	}

	public void setNomTypePro(String nomTypePro) {
		this.nomTypePro = nomTypePro;
	}

}
