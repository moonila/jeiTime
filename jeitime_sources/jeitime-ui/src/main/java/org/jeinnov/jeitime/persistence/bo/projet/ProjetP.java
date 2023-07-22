package org.jeinnov.jeitime.persistence.bo.projet;

import java.util.*;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

import javax.persistence.*;

@Entity
@Table(name = "PROJET")
@NamedQueries( {
		@NamedQuery(name = "ProjetP.getListProjetDom", query = "select p from ProjetP p where p.domaine = :iddomaine"),
		@NamedQuery(name = "ProjetP.getListProjetThema", query = "select p from ProjetP p where p.thematique = :idthematique"),
		@NamedQuery(name = "ProjetP.getListProjetTypeP", query = "select p from ProjetP p where p.typeProjet = :idTypeP") })
public class ProjetP implements java.io.Serializable {
	// --- Variables --- //

	private static final long serialVersionUID = -1188347660195664024L;
	private int idProjet;
	private String nomProjet;
	private Date dateDeb;
	private Date dateFin;
	private Date dateCloture;
	private Date dateFermeture;

	private float budgeprevu;
	private float tpsprevu;

	private TypeProjetP typeProjet;
	private DomaineP domaine;
	private ThematiqueP thematique;

	private Set<TacheP> tache;

	private Set<ProjetP> projets;
	private ProjetP projet;

	// --- Constructeurs --- //
	public ProjetP() {
		tache = new HashSet<TacheP>(0);
		projets = new HashSet<ProjetP>();
	}

	public ProjetP(int idProjet) {
		super();
		this.idProjet = idProjet;
	}

	// --- Getters and Setter --- //

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDPROJET", unique = true, nullable = false)
	public int getIdProjet() {
		return idProjet;
	}

	public void setIdProjet(int idProjet) {
		this.idProjet = idProjet;
	}

	@Column(name = "NOMPROJET", nullable = false)
	public String getNomProjet() {
		return nomProjet;
	}

	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATEDEBUT", length = 6, nullable = false)
	public Date getDateDeb() {
		return dateDeb;
	}

	public void setDateDeb(Date dateDeb) {
		this.dateDeb = dateDeb;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATEFIN", length = 6)
	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	@Column(name = "BUDGET")
	public float getBudgeprevu() {
		return budgeprevu;
	}

	public void setBudgeprevu(float budgeprevu) {
		this.budgeprevu = budgeprevu;
	}

	@Column(name = "TPSPREVU")
	public float getTpsprevu() {
		return tpsprevu;
	}

	public void setTpsprevu(float tpsprevu) {
		this.tpsprevu = tpsprevu;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATECLOTURE", length = 6)
	public Date getDateCloture() {
		return dateCloture;
	}

	public void setDateCloture(Date dateCloture) {
		this.dateCloture = dateCloture;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATEFERMETURE", length = 6)
	public Date getDateFermeture() {
		return dateFermeture;
	}

	public void setDateFermeture(Date dateFermeture) {
		this.dateFermeture = dateFermeture;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDTYPEPROJET", nullable = false)
	public TypeProjetP getTypeProjet() {
		return typeProjet;
	}

	public void setTypeProjet(TypeProjetP typeProjet) {
		this.typeProjet = typeProjet;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDDOMAINE")
	public DomaineP getDomaine() {
		return domaine;
	}

	public void setDomaine(DomaineP domaine) {
		this.domaine = domaine;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDTHEMATIQUE")
	public ThematiqueP getThematique() {
		return thematique;
	}

	public void setThematique(ThematiqueP thematique) {
		this.thematique = thematique;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "projet")
	public Set<TacheP> getTache() {
		return tache;
	}

	public void setTache(Set<TacheP> tache) {
		this.tache = tache;
	}

	@OneToMany(fetch = FetchType.LAZY)
	public Set<ProjetP> getProjets() {
		return projets;
	}

	public void setProjets(Set<ProjetP> projets) {
		this.projets = projets;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SOUSPROJET")
	public ProjetP getProjet() {
		return projet;
	}

	public void setProjet(ProjetP projet) {
		this.projet = projet;
	}

}
