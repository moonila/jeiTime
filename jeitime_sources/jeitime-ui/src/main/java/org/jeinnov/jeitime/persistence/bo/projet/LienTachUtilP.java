package org.jeinnov.jeitime.persistence.bo.projet;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;


@Entity
@Table(name = "LIENTACHUTIL")
@NamedQueries( {
		@NamedQuery(name = "LienTachUtilP.getCollab", query = "select ltu from LienTachUtilP ltu where ltu.collaborateur = :idcoll"),
		@NamedQuery(name = "LienTachUtilP.getTache", query = "select ltu from LienTachUtilP ltu where ltu.tache = :idTache") })
public class LienTachUtilP implements java.io.Serializable {

	private static final long serialVersionUID = -605685286233976036L;
	private LienTachUtilIdP id;
	private TacheP tache;
	private CollaborateurP collaborateur;
	private float nbHeure;
	private String commentaire;

	public LienTachUtilP() {

	}

	public LienTachUtilP(LienTachUtilIdP id, TacheP tache,
			CollaborateurP collaborateur, float nbHeure, String commentaire) {
		super();
		this.id = id;
		this.tache = tache;
		this.collaborateur = collaborateur;
		this.nbHeure = nbHeure;
		this.commentaire = commentaire;
	}

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "idColl", column = @Column(name = "IDCOLL", nullable = false)),
			@AttributeOverride(name = "idTache", column = @Column(name = "IDTACHE", nullable = false)) })
	public LienTachUtilIdP getId() {
		return id;
	}

	public void setId(LienTachUtilIdP id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTache", nullable = false, insertable = false, updatable = false)
	public TacheP getTache() {
		return tache;
	}

	public void setTache(TacheP tache) {
		this.tache = tache;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idColl", nullable = false, insertable = false, updatable = false)
	public CollaborateurP getCollaborateur() {
		return collaborateur;
	}

	public void setCollaborateur(CollaborateurP collaborateur) {
		this.collaborateur = collaborateur;
	}

	@Column(name = "NBHEURE", nullable = false)
	public float getNbHeure() {
		return nbHeure;
	}

	public void setNbHeure(float nbHeure) {
		this.nbHeure = nbHeure;
	}

	@Column(name = "COMMENTAIRE")
	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

}
