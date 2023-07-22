package org.jeinnov.jeitime.persistence.bo.collaborateur;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COLLEGE")
public class CollegeP implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7054394670907997911L;
	private int idCollege;
	private String nomCollege;
	private float nbHeureLun;
	private float nbHeureMar;
	private float nbHeureMerc;
	private float nbHeureJeud;
	private float nbHeureVend;
	private float nbHeureHebdo;

	private float nbHeureMensCollege;
	private float nbHeureAnnCollege;
	private int nbJourCongeAnnCollege;
	private int nbJourRttAnnCollege;
	private boolean alertMois;
	private boolean alertJour;
	private boolean alertAnn;

	private String listSaisie;

	public CollegeP() {

	}

	public CollegeP(int idCollege) {

		this.idCollege = idCollege;

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDCOLLEGE", unique = true, nullable = false)
	public int getIdCollege() {
		return idCollege;
	}

	public void setIdCollege(int idCollege) {
		this.idCollege = idCollege;
	}

	@Column(name = "NOMCOLLEGE", nullable = false)
	public String getNomCollege() {
		return nomCollege;
	}

	public void setNomCollege(String nomCollege) {
		this.nomCollege = nomCollege;
	}

	@Column(name = "NBHEURLUN", nullable = false)
	public float getNbHeureLun() {
		return nbHeureLun;
	}

	public void setNbHeureLun(float nbHeureLun) {
		this.nbHeureLun = nbHeureLun;
	}

	@Column(name = "NBHEURMAR", nullable = false)
	public float getNbHeureMar() {
		return nbHeureMar;
	}

	public void setNbHeureMar(float nbHeureMar) {
		this.nbHeureMar = nbHeureMar;
	}

	@Column(name = "NBHEURMERC", nullable = false)
	public float getNbHeureMerc() {
		return nbHeureMerc;
	}

	public void setNbHeureMerc(float nbHeureMerc) {
		this.nbHeureMerc = nbHeureMerc;
	}

	@Column(name = "NBHEURJEU", nullable = false)
	public float getNbHeureJeud() {
		return nbHeureJeud;
	}

	public void setNbHeureJeud(float nbHeureJeud) {
		this.nbHeureJeud = nbHeureJeud;
	}

	@Column(name = "NBHEURVEN", nullable = false)
	public float getNbHeureVend() {
		return nbHeureVend;
	}

	public void setNbHeureVend(float nbHeureVend) {
		this.nbHeureVend = nbHeureVend;
	}

	@Column(name = "NBHEURHEBDO", nullable = false)
	public float getNbHeureHebdo() {
		return nbHeureHebdo;
	}

	public void setNbHeureHebdo(float nbHeureHebdo) {
		this.nbHeureHebdo = nbHeureHebdo;
	}

	@Column(name = "NBHEURMENS", nullable = false)
	public float getNbHeureMensCollege() {
		return nbHeureMensCollege;
	}

	public void setNbHeureMensCollege(float nbHeureMensCollege) {
		this.nbHeureMensCollege = nbHeureMensCollege;
	}

	@Column(name = "NBHEUREANN", nullable = false)
	public float getNbHeureAnnCollege() {
		return nbHeureAnnCollege;
	}

	public void setNbHeureAnnCollege(float nbHeureAnnCollege) {
		this.nbHeureAnnCollege = nbHeureAnnCollege;
	}

	@Column(name = "NBJOURCONGE", nullable = false)
	public int getNbJourCongeAnnCollege() {
		return nbJourCongeAnnCollege;
	}

	public void setNbJourCongeAnnCollege(int nbJourCongeAnnCollege) {
		this.nbJourCongeAnnCollege = nbJourCongeAnnCollege;
	}

	@Column(name = "NBJOURRTT", nullable = false)
	public int getNbJourRttAnnCollege() {
		return nbJourRttAnnCollege;
	}

	public void setNbJourRttAnnCollege(int nbJourRttAnnCollege) {
		this.nbJourRttAnnCollege = nbJourRttAnnCollege;
	}

	@Column(name = "ALERTMOI", nullable = false)
	public boolean isAlertMois() {
		return alertMois;
	}

	public void setAlertMois(boolean alertMois) {
		this.alertMois = alertMois;
	}

	@Column(name = "ALERTHEB", nullable = false)
	public boolean isAlertJour() {
		return alertJour;
	}

	public void setAlertJour(boolean alertJour) {
		this.alertJour = alertJour;
	}

	@Column(name = "ALERTANN", nullable = false)
	public boolean isAlertAnn() {
		return alertAnn;
	}

	public void setAlertAnn(boolean alertAnn) {
		this.alertAnn = alertAnn;
	}

	@Column(name = "LISTSAISIE")
	public String getListSaisie() {
		return listSaisie;
	}

	public void setListSaisie(String listSaisie) {
		this.listSaisie = listSaisie;
	}

}
