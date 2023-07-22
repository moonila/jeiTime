package org.jeinnov.jeitime.persistence.bo.projet;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "NOMTACHE")
public class NomTacheP  implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7978822075656517836L;
	private int idNomTache;
	private String nomTache;

	private TypeTacheP typeTache;

	private Set<TacheP> tache = new HashSet<TacheP>(0);

	public NomTacheP() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDNOMTACHE", unique = true, nullable = false)
	public int getIdNomTache() {
		return idNomTache;
	}

	public void setIdNomTache(int idNomTache) {
		this.idNomTache = idNomTache;
	}

	@Column(name = "NOMTACHE", nullable = false)
	public String getNomTache() {
		return nomTache;
	}

	public void setNomTache(String nomTache) {
		this.nomTache = nomTache;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDTYPETACHE", nullable = false)
	public TypeTacheP getTypeTache() {
		return typeTache;
	}

	public void setTypeTache(TypeTacheP typeTache) {
		this.typeTache = typeTache;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "nomTacheP")
	public Set<TacheP> getTache() {
		return tache;
	}

	public void setTache(Set<TacheP> tache) {
		this.tache = tache;
	}

}
