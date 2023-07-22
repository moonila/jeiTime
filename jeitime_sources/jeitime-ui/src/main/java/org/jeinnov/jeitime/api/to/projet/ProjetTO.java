package org.jeinnov.jeitime.api.to.projet;

import java.util.Date;

public class ProjetTO implements Comparable<ProjetTO> {

	// --- Variables --- //

	private int idProjet;
	private String nomProjet;
	private Date dateDeb;
	private Date dateFin;
	private Date dateCloture;
	private Date dateFermeture;

	private float budgeprevu;
	private float tpsprevu;

	private TypeProjetTO typeProjet;
	private DomaineTO domaine;
	private ThematiqueTO thematique;

	private ProjetTO projet;

	// --- Constructeurs --- //

	public ProjetTO() {

	}

	public ProjetTO(int idProjet) {
		super();
		this.idProjet = idProjet;
	}

	public ProjetTO(int idProjet, String nomProjet) {
		super();
		this.idProjet = idProjet;
		this.nomProjet = nomProjet;
	}

	public ProjetTO(int idProjet, String nomProjet, TypeProjetTO typeProjet) {
		super();
		this.idProjet = idProjet;
		this.nomProjet = nomProjet;
		this.typeProjet = typeProjet;
	}

	public ProjetTO(int idProjet, String nomProjet, Date dateDeb, Date dateFin,
			Date dateCloture, Date dateFermeture, float budgeprevu,
			float tpsprevu, TypeProjetTO typeProjet, DomaineTO domaine,
			ThematiqueTO thematique, ProjetTO projet) {
		super();
		this.idProjet = idProjet;
		this.nomProjet = nomProjet;
		this.dateDeb = dateDeb;
		this.dateFin = dateFin;
		this.dateCloture = dateCloture;
		this.dateFermeture = dateFermeture;
		this.budgeprevu = budgeprevu;
		this.tpsprevu = tpsprevu;
		this.typeProjet = typeProjet;
		this.domaine = domaine;
		this.thematique = thematique;
		this.projet = projet;
	}

	public ProjetTO(int idProjet, String nomProjet, float budgeprevu,
			float tpsprevu) {
		super();
		this.idProjet = idProjet;
		this.nomProjet = nomProjet;
		this.budgeprevu = budgeprevu;
		this.tpsprevu = tpsprevu;
	}

	// --- Getters et Setters --- //

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

	public Date getDateDeb() {
		return dateDeb;
	}

	public void setDateDeb(Date dateDeb) {
		this.dateDeb = dateDeb;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public float getBudgeprevu() {
		return budgeprevu;
	}

	public void setBudgeprevu(float budgeprevu) {
		this.budgeprevu = budgeprevu;
	}

	public float getTpsprevu() {
		return tpsprevu;
	}

	public void setTpsprevu(float tpsprevu) {
		this.tpsprevu = tpsprevu;
	}

	public TypeProjetTO getTypeProjet() {
		return typeProjet;
	}

	public void setTypeProjet(TypeProjetTO typeProjet) {
		this.typeProjet = typeProjet;
	}

	public DomaineTO getDomaine() {
		return domaine;
	}

	public void setDomaine(DomaineTO domaine) {
		this.domaine = domaine;
	}

	public ThematiqueTO getThematique() {
		return thematique;
	}

	public void setThematique(ThematiqueTO thematique) {
		this.thematique = thematique;
	}

	public Date getDateCloture() {
		return dateCloture;
	}

	public void setDateCloture(Date dateCloture) {
		this.dateCloture = dateCloture;
	}

	public Date getDateFermeture() {
		return dateFermeture;
	}

	public void setDateFermeture(Date dateFermeture) {
		this.dateFermeture = dateFermeture;
	}

	public ProjetTO getProjet() {
		return projet;
	}

	public void setProjet(ProjetTO projet) {
		this.projet = projet;
	}

	@Override
	public int compareTo(ProjetTO o) {

		return this.nomProjet.compareTo(o.nomProjet);
	}

}
