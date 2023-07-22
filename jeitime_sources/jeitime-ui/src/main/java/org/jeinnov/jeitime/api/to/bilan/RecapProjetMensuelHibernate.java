package org.jeinnov.jeitime.api.to.bilan;

import java.util.Date;

public class RecapProjetMensuelHibernate {
	private int idProjet;
	private String nomColl;
	private int idColl;
	private String nomTache;
	private int idTache;
	private int idNomTache;
	private String nomProjet;
	private float nbheure;
	private Date date;

	public RecapProjetMensuelHibernate() {
	}

	public String getNomColl() {
		return nomColl;
	}

	public void setNOMCOLL(String nomColl) {
		this.nomColl = nomColl;
	}
	public void setnomcoll(String nomColl) {
		this.nomColl = nomColl;
	}

	public int getIdColl() {
		return idColl;
	}

	public void setIDCOLL(int idColl) {
		this.idColl = idColl;
	}
	public void setidcoll(int idColl) {
		this.idColl = idColl;
	}

	public String getNomTache() {
		return nomTache;
	}

	public void setNOMTACHE(String nomTache) {
		this.nomTache = nomTache;
	}
	public void setnomtache(String nomTache) {
		this.nomTache = nomTache;
	}

	public int getIdTache() {
		return idTache;
	}

	public void setIDTACHE(int idTache) {
		this.idTache = idTache;
	}
	public void setidtache(int idTache) {
		this.idTache = idTache;
	}

	public int getIdNomTache() {
		return idNomTache;
	}

	public void setIDNOMTACHE(int idNomTache) {
		this.idNomTache = idNomTache;
	}

	public void setidnomtache(int idNomTache) {
		this.idNomTache = idNomTache;
	}
	
	public String getNomProjet() {
		return nomProjet;
	}

	public void setNOMPROJET(String nomProjet) {
		this.nomProjet = nomProjet;
	}
	public void setnomprojet(String nomProjet) {
		this.nomProjet = nomProjet;
	}

	public int getIdProjet() {
		return idProjet;
	}

	public void setIDPROJET(int idProjet) {
		this.idProjet = idProjet;
	}
	public void setidprojet(int idProjet) {
		this.idProjet = idProjet;
	}

	public float getNbheure() {
		return nbheure;
	}

	public void setNBHEURE(Object nbheure) {
		if(nbheure instanceof Float){
			this.nbheure = (Float) nbheure;
		}else {
			this.nbheure = new Float((Double) nbheure);
		}
	}
	public void setnbheure(float nbheure) {
		this.nbheure = nbheure;
	}

	public Date getDate() {
		return date;
	}

	public void setDATE(Date date) {
		this.date = date;
	}
	public void setdate(Date date) {
		this.date = date;
	}
}
