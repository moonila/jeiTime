package org.jeinnov.jeitime.persistence.bo.affecter;

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;


@Entity
@Table(name = "AFFECTER")
public class AffecterP implements java.io.Serializable {

	// Variables

	private static final long serialVersionUID = -269480950702183747L;
	private AffecterIdP id;

	private Date dateDeb;
	private Date dateFin;

	private TacheP tache;
	private CollaborateurP collaborateur;

	// Constructors

	public AffecterP() {

	}

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "idColl", column = @Column(name = "IDCOLL", nullable = false)),
			@AttributeOverride(name = "idTache", column = @Column(name = "IDTACHE", nullable = false)) })
	public AffecterIdP getId() {
		return id;
	}

	public void setId(AffecterIdP id) {
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

	public void setCollaborateur(CollaborateurP collaborateur) {
		this.collaborateur = collaborateur;
	}

}