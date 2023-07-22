package org.jeinnov.jeitime.persistence.bo.affecter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
public class AffecterIdP implements java.io.Serializable

{
	// Variables
	private static final long serialVersionUID = -8229643616942427498L;

	private int idColl;
	private int idTache;

	public AffecterIdP() {
		// TODO Auto-generated constructor stub
	}

	public AffecterIdP(int idColl, int idTache) {
		super();
		this.idTache = idTache;
		this.idColl = idColl;

	}

	@Column(name = "IDTACHE", nullable = false)
	public int getIdTache() {
		return idTache;
	}

	public void setIdTache(int idTache) {
		this.idTache = idTache;
	}

	@Column(name = "IDCOLL", nullable = false)
	public int getIdColl() {
		return idColl;
	}

	public void setIdColl(int idColl) {
		this.idColl = idColl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof AffecterIdP))
			return false;
		AffecterIdP castOther = (AffecterIdP) other;
		return new EqualsBuilder().append(idTache, castOther.idTache).append(
				idColl, castOther.idColl).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(idColl).append(idTache)
				.toHashCode();
	}

}
