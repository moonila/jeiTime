package org.jeinnov.jeitime.api.to.collaborateur;

public class CollegeTO {
	private int idCollege;
	private String nomCollege;
	private float nbHeureLun;
	private float nbHeureMar;
	private float nbHeureMerc;
	private float nbHeureJeu;
	private float nbHeureVen;
	private float nbHeureHeb;

	private float nbHeureMensCollege;
	private float nbHeureAnnCollege;
	private int nbJourCongeAnnCollege;
	private int nbJourRttAnnCollege;
	private boolean alertMois;
	private boolean alertJour;
	private boolean alertAnn;
	private String listSaisie;

	public CollegeTO() {

	}

	public CollegeTO(int idCollege, String nomCollege, float nbHeureLun,
			float nbHeureMar, float nbHeureMerc, float nbHeureJeu,
			float nbHeureVen, float nbHeureHeb, float nbHeureMensCollege,
			float nbHeureAnnCollege, int nbJourCongeAnnCollege,
			int nbJourRttAnnCollege, boolean alertMois, boolean alertJour,
			boolean alertAnn, String listSaisie) {
		super();
		this.idCollege = idCollege;
		this.nomCollege = nomCollege;
		this.nbHeureLun = nbHeureLun;
		this.nbHeureMar = nbHeureMar;
		this.nbHeureMerc = nbHeureMerc;
		this.nbHeureJeu = nbHeureJeu;
		this.nbHeureVen = nbHeureVen;
		this.nbHeureMensCollege = nbHeureMensCollege;
		this.nbHeureAnnCollege = nbHeureAnnCollege;
		this.nbJourCongeAnnCollege = nbJourCongeAnnCollege;
		this.nbJourRttAnnCollege = nbJourRttAnnCollege;
		this.alertMois = alertMois;
		this.alertJour = alertJour;
		this.alertAnn = alertAnn;
		this.listSaisie = listSaisie;
		this.nbHeureHeb = nbHeureHeb;
	}

	public int getIdCollege() {
		return idCollege;
	}

	public void setIdCollege(int idCollege) {
		this.idCollege = idCollege;
	}

	public String getNomCollege() {
		return nomCollege;
	}

	public void setNomCollege(String nomCollege) {
		this.nomCollege = nomCollege;
	}

	public float getNbHeureLun() {
		return nbHeureLun;
	}

	public void setNbHeureHebdoCollege(float nbHeureLun) {
		this.nbHeureLun = nbHeureLun;
	}

	public float getNbHeureMar() {
		return nbHeureMar;
	}

	public void setNbHeureMar(float nbHeureMar) {
		this.nbHeureMar = nbHeureMar;
	}

	public float getNbHeureMerc() {
		return nbHeureMerc;
	}

	public void setNbHeureMerc(float nbHeureMerc) {
		this.nbHeureMerc = nbHeureMerc;
	}

	public float getNbHeureJeu() {
		return nbHeureJeu;
	}

	public void setNbHeureJeu(float nbHeureJeu) {
		this.nbHeureJeu = nbHeureJeu;
	}

	public float getNbHeureVen() {
		return nbHeureVen;
	}

	public void setNbHeureVen(float nbHeureVen) {
		this.nbHeureVen = nbHeureVen;
	}

	public void setNbHeureLun(float nbHeureLun) {
		this.nbHeureLun = nbHeureLun;
	}

	public float getNbHeureMensCollege() {
		return nbHeureMensCollege;
	}

	public void setNbHeureMensCollege(float nbHeureMensCollege) {
		this.nbHeureMensCollege = nbHeureMensCollege;
	}

	public float getNbHeureAnnCollege() {
		return nbHeureAnnCollege;
	}

	public void setNbHeureAnnCollege(float nbHeureAnnCollege) {
		this.nbHeureAnnCollege = nbHeureAnnCollege;
	}

	public int getNbJourCongeAnnCollege() {
		return nbJourCongeAnnCollege;
	}

	public void setNbJourCongeAnnCollege(int nbJourCongeAnnCollege) {
		this.nbJourCongeAnnCollege = nbJourCongeAnnCollege;
	}

	public int getNbJourRttAnnCollege() {
		return nbJourRttAnnCollege;
	}

	public void setNbJourRttAnnCollege(int nbJourRttAnnCollege) {
		this.nbJourRttAnnCollege = nbJourRttAnnCollege;
	}

	public boolean isAlertMois() {
		return alertMois;
	}

	public void setAlertMois(boolean alertMois) {
		this.alertMois = alertMois;
	}

	public boolean isAlertJour() {
		return alertJour;
	}

	public void setAlertJour(boolean alertJour) {
		this.alertJour = alertJour;
	}

	public boolean isAlertAnn() {
		return alertAnn;
	}

	public void setAlertAnn(boolean alertAnn) {
		this.alertAnn = alertAnn;
	}

	public String getListSaisie() {
		return listSaisie;
	}

	public void setListSaisie(String listSaisie) {
		this.listSaisie = listSaisie;
	}

	public float getNbHeureHeb() {
		return nbHeureHeb;
	}

	public void setNbHeureHeb(float nbHeureHeb) {
		this.nbHeureHeb = nbHeureHeb;
	}

}
