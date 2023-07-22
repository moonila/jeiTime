package org.jeinnov.jeitime.persistence.bo.collaborateur;

import java.security.Principal;

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
import javax.persistence.Transient;

@Entity
@Table(name = "COLLABORATEUR")
@NamedQueries( { @NamedQuery(name = "CollaborateurP.getCollaborateur.getEquip", query = "select c from CollaborateurP c where c.equipe = :idequip") })
public class CollaborateurP implements Principal, java.io.Serializable {
	// --- Variables --- //

	/**
	 * 
	 */
	private static final long serialVersionUID = 7754188934030545714L;
	private int idColl;
	private String login;
	private String password;
	private String nomColl;
	private String prenomColl;
	private int statut;
	private float salairAnn;
	private float chargeAnn;
	private int contrat;
	private EquipeP equipe;

	private float nbHeureLundi;
	private float nbHeureMardi;
	private float nbHeureMercredi;
	private float nbHeureJeudi;
	private float nbHeureVendredi;

	private float nbHeureHeb;
	private float nbHeureAnn;
	private float nbHeureMens;

	private String name;

	// --- Constructeurs --- //

	public CollaborateurP() {
		super();

	}

	public CollaborateurP(int idColl) {
		super();
		this.idColl = idColl;
	}

	public CollaborateurP(String login) {
		super();
		this.login = login;
		this.name = login;
	}

	// --- Getters and Setters --- //

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDCOLL", unique = true, nullable = false)
	public int getIdColl() {
		return idColl;
	}

	public void setIdColl(int idColl) {
		this.idColl = idColl;
	}

	@Column(name = "LOGIN", unique = true, nullable = false)
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "PASSWORD", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "CONTRAT", nullable = false)
	public int getContrat() {
		return contrat;
	}

	public void setContrat(int contrat) {
		this.contrat = contrat;
	}

	@Column(name = "NOMCOLL", length = 20, nullable = false)
	public String getNomColl() {
		return nomColl;
	}

	public void setNomColl(String nomColl) {
		this.nomColl = nomColl;
	}

	@Column(name = "PRENOMCOLL", length = 20, nullable = false)
	public String getPrenomColl() {
		return prenomColl;
	}

	public void setPrenomColl(String prenomColl) {
		this.prenomColl = prenomColl;
	}

	@Column(name = "STATUT", nullable = false)
	public int getStatut() {
		return statut;
	}

	public void setStatut(int statut) {
		this.statut = statut;
	}

	@Column(name = "SALAIRANN", nullable = false)
	public float getSalairAnn() {
		return salairAnn;
	}

	public void setSalairAnn(float salairAnn) {
		this.salairAnn = salairAnn;
	}

	@Column(name = "CHARGEANN", nullable = false)
	public float getChargeAnn() {
		return chargeAnn;
	}

	public void setChargeAnn(float chargeAnn) {
		this.chargeAnn = chargeAnn;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDEQ")
	public EquipeP getEquipe() {
		return equipe;
	}

	public void setEquipe(EquipeP equipe) {
		this.equipe = equipe;
	}

	@Column(name = "NBHEURLUNDI", nullable = false)
	public float getNbHeureLundi() {
		return nbHeureLundi;
	}

	public void setNbHeureLundi(float nbHeureLundi) {
		this.nbHeureLundi = nbHeureLundi;
	}

	@Column(name = "NBHEURMARDI", nullable = false)
	public float getNbHeureMardi() {
		return nbHeureMardi;
	}

	public void setNbHeureMardi(float nbHeureMardi) {
		this.nbHeureMardi = nbHeureMardi;
	}

	@Column(name = "NBHEURMERCREDI", nullable = false)
	public float getNbHeureMercredi() {
		return nbHeureMercredi;
	}

	public void setNbHeureMercredi(float nbHeureMercredi) {
		this.nbHeureMercredi = nbHeureMercredi;
	}

	@Column(name = "NBHEURJEUDI", nullable = false)
	public float getNbHeureJeudi() {
		return nbHeureJeudi;
	}

	public void setNbHeureJeudi(float nbHeureJeudi) {
		this.nbHeureJeudi = nbHeureJeudi;
	}

	@Column(name = "NBHEURVENDREDI", nullable = false)
	public float getNbHeureVendredi() {
		return nbHeureVendredi;
	}

	public void setNbHeureVendredi(float nbHeureVendredi) {
		this.nbHeureVendredi = nbHeureVendredi;
	}

	@Column(name = "NBHEUREHEB", nullable = false)
	public float getNbHeureHeb() {
		return nbHeureHeb;
	}

	public void setNbHeureHeb(float nbHeureHeb) {
		this.nbHeureHeb = nbHeureHeb;
	}

	@Column(name = "NBHEURANN", nullable = false)
	public float getNbHeureAnn() {
		return nbHeureAnn;
	}

	public void setNbHeureAnn(float nbHeureAnn) {
		this.nbHeureAnn = nbHeureAnn;
	}

	@Column(name = "NBHEUREMEN", nullable = false)
	public float getNbHeureMens() {
		return nbHeureMens;
	}

	public void setNbHeureMens(float nbHeureMens) {
		this.nbHeureMens = nbHeureMens;
	}

	@Transient
	public String getName() {
		return name;
	}

	public boolean equals(Object another) {
		try {
			CollaborateurP cp = (CollaborateurP) another;
			return cp.name.equalsIgnoreCase(name);
		} catch (Exception e) {
			return false;
		}
	}

	public int hashCode() {
		return name.hashCode();
	}

	public String toString() {
		return name;
	}

	@Transient
	public String getNameAppli() {
		if (nomColl == null) {
			return nomColl;
		}
		if (prenomColl == null) {
			return nomColl;
		}
		return nomColl + " " + prenomColl;
	}
}
