package org.jeinnov.jeitime.api.to.affecter;

import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;

public class AffecterTO {
	// Variables

	private int affecterIdP;

	private ProjetTO projet;
	private int idProjet;
	private String nomProjet;

	private CollaborateurTO collaborateur;
	private int idCollab;

	private TacheTO tache;
	private int idTache;

	// Constructors

	public AffecterTO() {
	}

	// Getters and Setters

	public int getAffecterIdP() {
		return affecterIdP;
	}

	public void setAffecterIdP(int affecterIdP) {
		this.affecterIdP = affecterIdP;
	}

	public int getIdCollab() {
		return idCollab;
	}

	public void setIdCollab(int idCollab) {
		this.idCollab = idCollab;
	}

	public TacheTO getTache() {
		return tache;
	}

	public void setTache(TacheTO tache) {
		this.tache = tache;
	}

	public int getIdProjet() {
		return idProjet;
	}

	public void setIdProjet(int idProjet) {
		this.idProjet = idProjet;
	}

	public String getNomProjet() {
		return nomProjet;
	}

	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}

	public CollaborateurTO getCollaborateur() {
		return collaborateur;
	}

	public void setCollaborateur(CollaborateurTO collaborateur) {
		this.collaborateur = collaborateur;
	}

	public int getIdTache() {
		return idTache;
	}

	public void setIdTache(int idTache) {
		this.idTache = idTache;
	}

	public ProjetTO getProjet() {
		return projet;
	}

	public void setProjet(ProjetTO projet) {
		this.projet = projet;
	}

}
