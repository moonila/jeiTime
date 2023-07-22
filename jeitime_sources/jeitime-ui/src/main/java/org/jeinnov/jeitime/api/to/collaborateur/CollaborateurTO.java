package org.jeinnov.jeitime.api.to.collaborateur;

public class CollaborateurTO {
	// --- Variables --- //

	private int idColl;
	private String login;
	private String nomColl;
	private String prenomColl;
	private String password;
	private int contrat;
	private int statut;
	private float salairAnn;
	private float chargeAnn;
	private float nbHeureLundi;
	private float nbHeureMardi;
	private float nbHeureMercredi;
	private float nbHeureJeudi;
	private float nbHeureVendredi;
	private float nbHeureHeb;
	private float nbHeureMens;
	private float nbHeureAnn;
	private EquipeTO equipe;

	// --- Constructeurs --- //

	public CollaborateurTO() {
		super();

	}

	public CollaborateurTO(int idColl) {
		super();
		this.idColl = idColl;
	}

	public CollaborateurTO(int idColl, String nomColl) {
		super();
		this.idColl = idColl;
		this.nomColl = nomColl;
	}

	public CollaborateurTO(int idColl, String prenomColl, String nomColl,
			EquipeTO equipe) {
		super();
		this.idColl = idColl;
		this.prenomColl = prenomColl;
		this.nomColl = nomColl;
		this.equipe = equipe;
	}

	public CollaborateurTO(int idColl, String nomColl, String prenomColl) {
		super();
		this.idColl = idColl;
		this.prenomColl = prenomColl;
		this.nomColl = nomColl;

	}

	public CollaborateurTO(String prenomColl, String nomColl) {
		super();
		this.prenomColl = prenomColl;
		this.nomColl = nomColl;
	}

	public CollaborateurTO(int idColl, String login, String nomColl,
			String prenomColl, String password, int statut, float salairAnn,
			float chargeAnn, float nbHeureLundi, float nbHeureMardi,
			float nbHeureMercredi, float nbHeureJeudi, float nbHeureVendredi,
			float nbHeureHeb, float nbHeureMens, float nbHeureAnn,
			EquipeTO equipe, int contrat) {
		super();
		this.idColl = idColl;
		this.login = login;
		this.nomColl = nomColl;
		this.prenomColl = prenomColl;
		this.password = password;
		this.contrat = contrat;
		this.statut = statut;
		this.salairAnn = salairAnn;
		this.chargeAnn = chargeAnn;
		this.nbHeureLundi = nbHeureLundi;
		this.nbHeureMardi = nbHeureMardi;
		this.nbHeureMercredi = nbHeureMercredi;
		this.nbHeureJeudi = nbHeureJeudi;
		this.nbHeureVendredi = nbHeureVendredi;
		this.equipe = equipe;
		this.nbHeureHeb = nbHeureHeb;
		this.nbHeureMens = nbHeureMens;
		this.nbHeureAnn = nbHeureAnn;
	}

	// --- Getters et Setters --- //

	public int getIdColl() {
		return idColl;
	}

	public void setIdColl(int idColl) {
		this.idColl = idColl;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNomColl() {
		return nomColl;
	}

	public void setNomColl(String nomColl) {
		this.nomColl = nomColl;
	}

	public String getPrenomColl() {
		return prenomColl;
	}

	public void setPrenomColl(String prenomColl) {
		this.prenomColl = prenomColl;
	}

	public int getContrat() {
		return contrat;
	}

	public void setContrat(int contrat) {
		this.contrat = contrat;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatut() {
		return statut;
	}

	public void setStatut(int statut) {
		this.statut = statut;
	}

	public float getSalairAnn() {
		return salairAnn;
	}

	public void setSalairAnn(float salairAnn) {
		this.salairAnn = salairAnn;
	}

	public float getChargeAnn() {
		return chargeAnn;
	}

	public void setChargeAnn(float chargeAnn) {
		this.chargeAnn = chargeAnn;
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

	public float getNbHeureMercredi() {
		return nbHeureMercredi;
	}

	public void setNbHeureMercredi(float nbHeureMercredi) {
		this.nbHeureMercredi = nbHeureMercredi;
	}

	public float getNbHeureJeudi() {
		return nbHeureJeudi;
	}

	public void setNbHeureJeudi(float nbHeureJeudi) {
		this.nbHeureJeudi = nbHeureJeudi;
	}

	public float getNbHeureVendredi() {
		return nbHeureVendredi;
	}

	public void setNbHeureVendredi(float nbHeureVendredi) {
		this.nbHeureVendredi = nbHeureVendredi;
	}

	public EquipeTO getEquipe() {
		return equipe;
	}

	public void setEquipe(EquipeTO equipe) {
		this.equipe = equipe;
	}

	public float getNbHeureHeb() {
		return nbHeureHeb;
	}

	public void setNbHeureHeb(float nbHeureHeb) {
		this.nbHeureHeb = nbHeureHeb;
	}

	public float getNbHeureMens() {
		return nbHeureMens;
	}

	public void setNbHeureMens(float nbHeureMens) {
		this.nbHeureMens = nbHeureMens;
	}

	public float getNbHeureAnn() {
		return nbHeureAnn;
	}

	public void setNbHeureAnn(float nbHeureAnn) {
		this.nbHeureAnn = nbHeureAnn;
	}

}
