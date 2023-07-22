package org.jeinnov.jeitime.api.to.affecter;

import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.jeinnov.jeitime.api.to.projet.TypeTacheTO;

public class RecapAffectTO implements Comparable<RecapAffectTO> {

	private ProjetTO projet;

	private TacheTO tache;

	private CollaborateurTO collaborateur;

	private TypeTacheTO typetache;

	private RecapAffectTO listRecapAffect;

	public RecapAffectTO()

	{
		// TODO Auto-generated constructor stub
	}

	public RecapAffectTO(ProjetTO projet, TacheTO tache,
			CollaborateurTO collaborateur)

	{
		super();
		this.projet = projet;
		this.tache = tache;
		this.collaborateur = collaborateur;

	}

	public RecapAffectTO(RecapAffectTO listRecapAffect) {
		super();
		this.listRecapAffect = listRecapAffect;
	}

	public ProjetTO getProjet() {
		return projet;
	}

	public void setProjet(ProjetTO projet) {
		this.projet = projet;
	}

	public TacheTO getTache() {
		return tache;
	}

	public void setTache(TacheTO tache) {
		this.tache = tache;
	}

	public CollaborateurTO getCollaborateur() {
		return collaborateur;
	}

	public void setCollaborateur(CollaborateurTO collaborateur) {
		this.collaborateur = collaborateur;
	}

	public RecapAffectTO getListRecapAffect() {
		return listRecapAffect;
	}

	public void setListRecapAffect(RecapAffectTO listRecapAffect) {
		this.listRecapAffect = listRecapAffect;
	}

	public TypeTacheTO getTypetache() {
		return typetache;
	}

	public void setTypetache(TypeTacheTO typetache) {
		this.typetache = typetache;
	}

	@Override
	public int compareTo(RecapAffectTO o) {

		return this.collaborateur.getNomColl().compareTo(
				o.getCollaborateur().getNomColl());
	}

}
