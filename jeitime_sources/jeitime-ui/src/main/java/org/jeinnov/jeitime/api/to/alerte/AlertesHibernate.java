package org.jeinnov.jeitime.api.to.alerte;

import java.util.Date;

public class AlertesHibernate {
	private String nomColl;
	private int idColl;
	private Object nbHeureAnn;
	private Object nbHeure;
	private Object nbHeurMens;
	private Object nbHeurHebdo;
	private Date date;
	private Date dateC;

	public AlertesHibernate() {

	}

	public AlertesHibernate(String nomColl, int idColl, Float nbHeureAnn,
			Float nbHeure, Float nbHeurMens, Float nbHeurHebdo) {
		super();
		this.nomColl = nomColl;
		this.idColl = idColl;
		this.nbHeureAnn = nbHeureAnn;
		this.nbHeure = nbHeure;
		this.nbHeurMens = nbHeurMens;
		this.nbHeurHebdo = nbHeurHebdo;
	}

	public String getNomColl() {
		return nomColl;
	}

	public void setNOMCOLL(String nomColl) {
		this.nomColl = nomColl;
	}

	public int getIdColl() {
		return idColl;
	}

	public void setIDCOLL(int idColl) {
		this.idColl = idColl;
	}

	public Object getNbHeureAnn() {
		return nbHeureAnn;
	}

	public void setNBHEURANN(Object nbHeureAnn) {
		this.nbHeureAnn = nbHeureAnn;
	}

	public Object getNbHeure() {
		return nbHeure;
	}

	public void setNBHEURE(Object nbHeure) {
		this.nbHeure = nbHeure;
	}

	public Object getNbHeurMens() {
		return nbHeurMens;
	}

	public void setNBHEUREMEN(Object nbHeurMens) {
		this.nbHeurMens = nbHeurMens;
	}

	public Object getNbHeurHebdo() {
		return nbHeurHebdo;
	}

	public void setNBHEUREHEB(Object nbHeurHebdo) {
		this.nbHeurHebdo = nbHeurHebdo;
	}

	public Date getDate() {
		return date;
	}

	public void setDATE(Date date) {
		this.date = date;
	}

	public Date getDateC() {
		return dateC;
	}

	public void setDATEC(Date dateC) {
		this.dateC = dateC;
	}

}
