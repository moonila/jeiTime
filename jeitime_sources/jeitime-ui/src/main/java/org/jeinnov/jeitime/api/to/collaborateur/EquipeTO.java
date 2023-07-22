package org.jeinnov.jeitime.api.to.collaborateur;

public class EquipeTO {
	private String nomEquip;
	private int idEquip;
	private String fonctionEquip;

	public EquipeTO() {

	}

	public EquipeTO(int idEquip) {
		this.idEquip = idEquip;
	}

	public EquipeTO(int idEquip, String nomEquip, String fonctionEquip) {
		this.fonctionEquip = fonctionEquip;
		this.idEquip = idEquip;
		this.nomEquip = nomEquip;
	}

	public int getIdEquip() {
		return idEquip;
	}

	public void setIdEquip(int idEquip) {
		this.idEquip = idEquip;
	}

	public String getNomEquip() {
		return nomEquip;
	}

	public void setNomEquip(String nomEquip) {
		this.nomEquip = nomEquip;
	}

	public String getFonctionEquip() {
		return fonctionEquip;
	}

	public void setFonctionEquip(String fonctionEquip) {
		this.fonctionEquip = fonctionEquip;
	}

}
