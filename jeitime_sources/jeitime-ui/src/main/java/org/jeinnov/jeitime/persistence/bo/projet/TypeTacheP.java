package org.jeinnov.jeitime.persistence.bo.projet;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TYPETACHE")
public class TypeTacheP implements java.io.Serializable {

	// --- Variables --- //

	private static final long serialVersionUID = -5722071278546108463L;
	private int idTypeTache;
	private String nomTypeTache;
	private Set<NomTacheP> nomTaches = new HashSet<NomTacheP>(0);

	// --- Constructeurs --- //
	public TypeTacheP() {

	}

	public TypeTacheP(int idTypeTache) {
		super();
		this.idTypeTache = idTypeTache;
	}

	// --- Getters et Setters --- //

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDTYPETACHE", unique = true, nullable = false)
	public int getIdTypeTache() {
		return idTypeTache;
	}

	public void setIdTypeTache(int idTypeTache) {
		this.idTypeTache = idTypeTache;
	}

	@Column(name = "NOMTYPETACHE", nullable = false)
	public String getNomTypeTache() {
		return nomTypeTache;
	}

	public void setNomTypeTache(String nomTypeTache) {
		this.nomTypeTache = nomTypeTache;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "typeTache")
	public Set<NomTacheP> getNomTaches() {
		return nomTaches;
	}

	public void setNomTaches(Set<NomTacheP> nomTaches) {
		this.nomTaches = nomTaches;
	}
}
