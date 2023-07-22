package org.jeinnov.jeitime.api.to.bilan;

public class RecapProjetHibernate {
	private int idProjet;
	private String nomColl;
	private int idColl;
	private String nomTache;
	private int idTache;
	private int idNomTache;
	private String nomProjet;
	private Object total;
	private boolean eligible;

	public RecapProjetHibernate() {
	}

	public RecapProjetHibernate(String nomColl, int idColl, String nomTache,
			int idTache, int idNomTache, String nomProjet, Object total,
			int idProjet) {
		super();
		this.nomColl = nomColl;
		this.idColl = idColl;
		this.nomTache = nomTache;
		this.idTache = idTache;
		this.idNomTache = idNomTache;
		this.nomProjet = nomProjet;
		this.total = total;
		this.idProjet = idProjet;
	}

	public String getNomColl() {
		return nomColl;
	}

	public void setNOMCOLL(String nomColl) {
		this.nomColl = nomColl;
	}
	
	public void setnomcoll(String nomColl) {
		this.nomColl = nomColl;
	}
	

	public int getIdColl() {
		return idColl;
	}

	public void setIDCOLL(int idColl) {
		this.idColl = idColl;
	}
	public void setidcoll(int idColl) {
		this.idColl = idColl;
	}

	public String getNomTache() {
		return nomTache;
	}

	public void setNOMTACHE(String nomTache) {
		this.nomTache = nomTache;
	}
	public void setnomtache(String nomTache) {
		this.nomTache = nomTache;
	}

	public int getIdTache() {
		return idTache;
	}

	public void setIDTACHE(int idTache) {
		this.idTache = idTache;
	}
	public void setidtache(int idTache) {
		this.idTache = idTache;
	}

	public int getIdNomTache() {
		return idNomTache;
	}

	public void setIDNOMTACHE(int idNomTache) {
		this.idNomTache = idNomTache;
	}

	public void setidnomtache(int idNomTache) {
		this.idNomTache = idNomTache;
	}

	public String getNomProjet() {
		return nomProjet;
	}

	public void setNOMPROJET(String nomProjet) {
		this.nomProjet = nomProjet;
	}
	public void setnomprojet(String nomProjet) {
		this.nomProjet = nomProjet;
	}

	public Object getTotal() {
		return total;
	}

	public void setTOTAL(Object total) {
		this.total = total;
	}
	public void settotal(Object total) {
		this.total = total;
	}

	public int getIdProjet() {
		return idProjet;
	}

	public void setIDPROJET(int idProjet) {
		this.idProjet = idProjet;
	}
	public void setidprojet(int idProjet) {
		this.idProjet = idProjet;
	}

	public boolean isEligible() {
		return eligible;
	}

	public void setELIGIBLE(boolean eligible) {
		this.eligible = eligible;
	}

	public void seteligible(boolean eligible) {
		this.eligible = eligible;
	}
}
