package org.jeinnov.jeitime.persistence.bo.projet;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "DOMAINE")
public class DomaineP implements java.io.Serializable {

	// --- Variables --- //

	private static final long serialVersionUID = -2631169595946496095L;
	private int idDomaine;
	private String nomDomaine;
	private Set<ProjetP> projets = new HashSet<ProjetP>(0);

	// --- Constructeurs --- //

	public DomaineP() {

	}

	// --- Getters et Setters --- //

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDDOMAINE", unique = true, nullable = false)
	public int getIdDomaine() {
		return idDomaine;
	}

	public void setIdDomaine(int idDomaine) {
		this.idDomaine = idDomaine;
	}

	@Column(name = "NOMDOMAINE", nullable = false)
	public String getNomDomaine() {
		return nomDomaine;
	}

	public void setNomDomaine(String nomDomaine) {
		this.nomDomaine = nomDomaine;
	}

	@OneToMany (fetch = FetchType.LAZY, mappedBy = "domaine")
	public Set<ProjetP> getProjets() {
		return projets;
	}

	public void setProjets(Set<ProjetP> projets) {
		this.projets = projets;
	}

}
