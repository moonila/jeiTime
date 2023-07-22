package org.jeinnov.jeitime.api.to.heure;

import java.util.Date;

import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;


public class SaisieHeureTO implements Comparable<SaisieHeureTO> {

	private TacheTO tache;
	private ProjetTO projet;
	private CollaborateurTO collab;
	private float nbHeure;
	private String commentaire;
	private Date saisiDate;

	public SaisieHeureTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int compareTo(SaisieHeureTO saisieTO) {
		String nomProjet = saisieTO.getProjet().getNomProjet();
		String nomTache = saisieTO.getTache().getNomtache().getNomTache();

		if (nomProjet.equals(projet.getNomProjet())) {
			return tache.getNomtache().getNomTache().compareTo(nomTache);
		}
		return projet.getNomProjet().compareTo(nomProjet);
	}

	public SaisieHeureTO(TacheTO tache, ProjetTO projet,
			CollaborateurTO collab, Date saisiDate, float nbHeure,
			String commentaire) {
		super();
		this.tache = tache;
		this.projet = projet;
		this.collab = collab;
		this.nbHeure = nbHeure;
		this.commentaire = commentaire;
		this.saisiDate = saisiDate;
	}

	public TacheTO getTache() {
		return tache;
	}

	public void setTache(TacheTO tache) {
		this.tache = tache;
	}

	public ProjetTO getProjet() {
		return projet;
	}

	public void setProjet(ProjetTO projet) {
		this.projet = projet;
	}

	public float getNbHeure() {
		return nbHeure;
	}

	public void setNbHeure(float nbHeure) {
		this.nbHeure = nbHeure;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public CollaborateurTO getCollab() {
		return collab;
	}

	public void setCollab(CollaborateurTO collab) {
		this.collab = collab;
	}

	public Date getSaisiDate() {
		return saisiDate;
	}

	public void setSaisiDate(Date saisiDate) {
		this.saisiDate = saisiDate;
	}

}
