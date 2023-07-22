package org.jeinnov.jeitime.persistence.bo.collaborateur;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "APPARTIENTCOLLEGE")
public class AppartientCollegeP implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1042508416432745365L;

	private AppartientCollegeIdP id;
	private CollaborateurP collaborateur;
	private CollegeP college;

	public AppartientCollegeP() {
		super();
	}

	public AppartientCollegeP(AppartientCollegeIdP id,
			CollaborateurP collaborateur, CollegeP college) {
		super();
		this.id = id;
		this.collaborateur = collaborateur;
		this.college = college;
	}

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "idCollege", column = @Column(name = "IDCOLLEGE", nullable = false)),
			@AttributeOverride(name = "idColl", column = @Column(name = "IDCOLL", nullable = false)) })
	public AppartientCollegeIdP getId() {
		return id;
	}

	public void setId(AppartientCollegeIdP id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idColl", nullable = false, insertable = false, updatable = false)
	public CollaborateurP getCollaborateur() {
		return collaborateur;
	}

	public void setCollaborateur(CollaborateurP collaborateur) {
		this.collaborateur = collaborateur;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCollege", nullable = false, insertable = false, updatable = false)
	public CollegeP getCollege() {
		return college;
	}

	public void setCollege(CollegeP college) {
		this.college = college;
	}

}
