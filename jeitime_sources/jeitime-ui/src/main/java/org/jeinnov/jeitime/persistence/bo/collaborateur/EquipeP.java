package org.jeinnov.jeitime.persistence.bo.collaborateur;


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
@Table(name = "EQUIPE")
public class EquipeP implements java.io.Serializable {

	private static final long serialVersionUID = 2336618158738639752L;
	private String nomEquip;
	private int idEquip;
	private String fonctionEquip;
	private Set<CollaborateurP> collaborateurs = new HashSet<CollaborateurP>(0);

	public EquipeP() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDEQ", unique = true, nullable = false)
	public int getIdEquip() {
		return idEquip;
	}

	public void setIdEquip(int idEquip) {
		this.idEquip = idEquip;
	}

	@Column(name = "NOMEQ", nullable = false)
	public String getNomEquip() {
		return nomEquip;
	}

	public void setNomEquip(String nomEquip) {
		this.nomEquip = nomEquip;
	}

	@Column(name = "FCTEQ")
	public String getFonctionEquip() {
		return fonctionEquip;
	}

	public void setFonctionEquip(String fonctionEquip) {
		this.fonctionEquip = fonctionEquip;
	}

	@OneToMany (fetch = FetchType.LAZY, mappedBy = "equipe")
	public Set<CollaborateurP> getCollaborateurs() {
		return collaborateurs;
	}

	public void setCollaborateurs(Set<CollaborateurP> collaborateurs) {
		this.collaborateurs = collaborateurs;
	}

}
