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
@Table(name = "THEMATIQUE")
public class ThematiqueP implements java.io.Serializable {
	// --- Variables --- //

	private static final long serialVersionUID = 5868931630138107000L;
	private int idThematique;
	private String nomThematique;
	private Set<ProjetP> projets = new HashSet<ProjetP>(0);

	// --- Constructeurs --- //

	public ThematiqueP() {

	}

	// --- Getters et Setters --- //

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDTHEMATIQUE", unique = true, nullable = false)
	public int getIdThematique() {
		return idThematique;
	}

	public void setIdThematique(int idThematique) {
		this.idThematique = idThematique;
	}

	@Column(name = "NOMTHEMATIQUE", nullable = false)
	public String getNomThematique() {
		return nomThematique;
	}

	public void setNomThematique(String nomThematique) {
		this.nomThematique = nomThematique;
	}

	@OneToMany (fetch = FetchType.LAZY, mappedBy = "thematique")
	public Set<ProjetP> getProjets() {
		return projets;
	}

	public void setProjets(Set<ProjetP> projets) {
		this.projets = projets;
	}

}
