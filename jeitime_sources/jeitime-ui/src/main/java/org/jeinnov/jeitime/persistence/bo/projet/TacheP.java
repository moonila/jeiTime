package org.jeinnov.jeitime.persistence.bo.projet;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "TACHE")
@NamedQueries( {
		@NamedQuery(name = "TacheP.getTache", query = "select t from TacheP t where t.projet = :idProjet"),
		@NamedQuery(name = "TacheP.getNomTache", query = "select t from TacheP t where t.nomTacheP = :idNomTache") })
public class TacheP implements java.io.Serializable,
		java.lang.Comparable<TacheP> {
	// --- Variables --- //

	private static final long serialVersionUID = -343208627608342580L;

	private int idTache;
	private float budgetprevu;
	private float tpsprevu;
	private int priorite;
	private boolean eligible;
	private NomTacheP nomTacheP;
	private ProjetP projet;

	// --- Constructeurs --- //

	public TacheP() {

	}

	public TacheP(int idTache) {
		super();
		this.idTache = idTache;
	}

	// --- Getters and Setters --- //

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDTACHE", unique = true, nullable = false)
	public int getIdTache() {
		return idTache;
	}

	public void setIdTache(int idTache) {
		this.idTache = idTache;
	}

	@Column(name = "BUDGETTACHE")
	public float getBudgetprevu() {
		return budgetprevu;
	}

	public void setBudgetprevu(float budgetprevu) {
		this.budgetprevu = budgetprevu;
	}

	@Column(name = "TPSTACHE")
	public float getTpsprevu() {
		return tpsprevu;
	}

	public void setTpsprevu(float tpsprevu) {
		this.tpsprevu = tpsprevu;
	}

	@Column(name = "PRIORITE")
	public int getPriorite() {
		return priorite;
	}

	public void setPriorite(int priorite) {
		this.priorite = priorite;
	}

	@Column(name = "ELIGIBLE")
	public boolean isEligible() {
		return eligible;
	}

	public void setEligible(boolean eligible) {
		this.eligible = eligible;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDNOMTACHE", nullable = false)
	public NomTacheP getNomTacheP() {
		return nomTacheP;
	}

	public void setNomTacheP(NomTacheP nomTacheP) {
		this.nomTacheP = nomTacheP;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDPROJET")
	public ProjetP getProjet() {
		return projet;
	}

	public void setProjet(ProjetP projet) {
		this.projet = projet;
	}

	@Override
	public int compareTo(TacheP o) {

		String nomtache = o.getNomTacheP().getNomTache();
		String nomProjet = o.getProjet().getNomProjet();

		if (projet.getNomProjet().equals(nomProjet)) {
			return nomTacheP.getNomTache().compareTo(nomtache);
		}

		/*
		 * if(nomProjet.equals(projet.getNomProjet())) { //return
		 * nomtache.compareTo(nomTacheP.getNomTache()); return
		 * nomTacheP.getNomTache().compareTo(nomtache); }
		 */
		if (nomProjet.equals(projet.getNomProjet())
				&& nomtache.equals(nomTacheP.getNomTache())) {
			return 2;
		}

		// return nomProjet.compareTo(projet.getNomProjet());

		return projet.getNomProjet().compareTo(nomProjet);

	}

}
