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
@Table(name = "TYPEPROJET")
public class TypeProjetP implements java.io.Serializable {

	// --- Variables --- //

	private static final long serialVersionUID = -7082116004373850944L;
	private int idTypePro;
	private String nomTypePro;
	private Set<ProjetP> projets = new HashSet<ProjetP>(0);

	// --- Constructeurs --- //

	public TypeProjetP() {

	}

	// --- Getters and Setters --- //

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDTYPEPROJET", unique = true, nullable = false)
	public int getIdTypePro() {
		return idTypePro;
	}

	public void setIdTypePro(int idTypePro) {
		this.idTypePro = idTypePro;
	}

	@Column(name = "NOMTYPEPROJET", nullable = false)
	public String getNomTypePro() {
		return nomTypePro;
	}

	public void setNomTypePro(String nomTypePro) {
		this.nomTypePro = nomTypePro;
	}

	@OneToMany( fetch = FetchType.LAZY, mappedBy = "typeProjet")
	public Set<ProjetP> getProjets() {
		return projets;
	}

	public void setProjets(Set<ProjetP> projets) {
		this.projets = projets;
	}

}
