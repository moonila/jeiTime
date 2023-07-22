package org.jeinnov.jeitime.api.to.projet;

public class TacheTO implements Comparable<TacheTO> {
	private int idTache;
	private float budgetprevu;
	private float tpsprevu;
	private int priorite;
	private boolean eligible;
	private ProjetTO projet;
	private NomTacheTO nomtache;

	public TacheTO() {

	}

	public int compareTo(TacheTO o) {
		String nomT = o.getNomtache().getNomTache();
		return nomtache.getNomTache().compareTo(nomT);
	}

	public TacheTO(int idTache, NomTacheTO nomtache, float budgetprevu,
			float tpsprevu, int priorite, boolean eligible, ProjetTO projet) {
		super();
		this.idTache = idTache;
		this.nomtache = nomtache;
		this.budgetprevu = budgetprevu;
		this.tpsprevu = tpsprevu;
		this.priorite = priorite;
		this.eligible = eligible;
		this.projet = projet;
	}

	public TacheTO(int idTache, NomTacheTO nomtache, float budgetprevu,
			float tpsprevu, int priorite) {
		super();
		this.idTache = idTache;
		this.nomtache = nomtache;
		this.budgetprevu = budgetprevu;
		this.tpsprevu = tpsprevu;
		this.priorite = priorite;

	}

	public TacheTO(int idTache, NomTacheTO nomtache) {
		super();
		this.idTache = idTache;
		this.nomtache = nomtache;
	}

	public TacheTO(int idTache) {
		super();
		this.idTache = idTache;
	}

	public int getIdTache() {
		return idTache;
	}

	public void setIdTache(int idTache) {
		this.idTache = idTache;
	}

	public NomTacheTO getNomtache() {
		return nomtache;
	}

	public void setNomtache(NomTacheTO nomtache) {
		this.nomtache = nomtache;
	}

	public ProjetTO getProjet() {
		return projet;
	}

	public void setProjet(ProjetTO projet) {
		this.projet = projet;
	}

	public float getBudgetprevu() {
		return budgetprevu;
	}

	public void setBudgetprevu(float budgetprevu) {
		this.budgetprevu = budgetprevu;
	}

	public float getTpsprevu() {
		return tpsprevu;
	}

	public void setTpsprevu(float tpsprevu) {
		this.tpsprevu = tpsprevu;
	}

	public int getPriorite() {
		return priorite;
	}

	public void setPriorite(int priorite) {
		this.priorite = priorite;
	}

	public boolean isEligible() {
		return eligible;
	}

	public void setEligible(boolean eligible) {
		this.eligible = eligible;
	}

}
