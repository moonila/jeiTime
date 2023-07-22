package org.jeinnov.jeitime.api.to.heure;

import org.jeinnov.jeitime.api.to.projet.TacheTO;

public class GereHeure implements java.lang.Comparable<GereHeure> {

	private float nbHeureLundi;
	private float nbHeureMardi;
	private float nbHeureMerc;
	private float nbHeureJeudi;
	private float nbHeureVend;
	private float total;
	private String nomTache;
	private String nomProjet;
	private int idTache;

	private TacheTO tache;

	public GereHeure() {

	}

	public GereHeure(float nbHeureLundi, float nbHeureMardi, float nbHeureMerc,
			float nbHeureJeudi, float nbHeureVend, float total,
			String nomTache, String nomProjet, int idTache) {
		super();
		this.nbHeureLundi = nbHeureLundi;
		this.nbHeureMardi = nbHeureMardi;
		this.nbHeureMerc = nbHeureMerc;
		this.nbHeureJeudi = nbHeureJeudi;
		this.nbHeureVend = nbHeureVend;
		this.nomTache = nomTache;
		this.nomProjet = nomProjet;
		this.total = total;
		this.idTache = idTache;
	}

	public int compareTo(GereHeure heure) {
		String projet = heure.getNomProjet();
		String nomtache = heure.getNomTache();
		if (projet.equals(nomProjet)) {
			return nomtache.compareTo(nomTache);
		}
		if (projet.equals(nomProjet) && nomtache.equals(nomTache)) {
			if (heure.getNbHeureLundi() != nbHeureLundi
					|| heure.getNbHeureMardi() != nbHeureMardi
					|| heure.getNbHeureMerc() != nbHeureMerc
					|| heure.getNbHeureJeudi() != nbHeureJeudi
					|| heure.getNbHeureVend() != nbHeureVend) {
				return 2;
			}
		}
		return projet.compareTo(nomProjet);
	}

	public float getNbHeureLundi() {
		return nbHeureLundi;
	}

	public void setNbHeureLundi(float nbHeureLundi) {
		this.nbHeureLundi = nbHeureLundi;
	}

	public float getNbHeureMardi() {
		return nbHeureMardi;
	}

	public void setNbHeureMardi(float nbHeureMardi) {
		this.nbHeureMardi = nbHeureMardi;
	}

	public float getNbHeureMerc() {
		return nbHeureMerc;
	}

	public void setNbHeureMerc(float nbHeureMerc) {
		this.nbHeureMerc = nbHeureMerc;
	}

	public float getNbHeureJeudi() {
		return nbHeureJeudi;
	}

	public void setNbHeureJeudi(float nbHeureJeudi) {
		this.nbHeureJeudi = nbHeureJeudi;
	}

	public float getNbHeureVend() {
		return nbHeureVend;
	}

	public void setNbHeureVend(float nbHeureVend) {
		this.nbHeureVend = nbHeureVend;
	}

	public String getNomTache() {
		return nomTache;
	}

	public void setNomTache(String nomTache) {
		this.nomTache = nomTache;
	}

	public String getNomProjet() {
		return nomProjet;
	}

	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public int getIdTache() {
		return idTache;
	}

	public void setIdTache(int idTache) {
		this.idTache = idTache;
	}

	public TacheTO getTache() {
		return tache;
	}

	public void setTache(TacheTO tache) {
		this.tache = tache;
	}

}
