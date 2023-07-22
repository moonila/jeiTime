package org.jeinnov.jeitime.persistence.bo.collaborateur;


import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
public class RolesId implements java.io.Serializable {

	private static final long serialVersionUID = 4750371522369521624L;

	private int idColl;
	private String roles;

	public RolesId() {

	}

	public RolesId(int idColl, String roles) {
		this.idColl = idColl;
		this.roles = roles;
	}

	@Column(name = "IDCOLL", nullable = false)
	public int getIdColl() {
		return idColl;
	}

	public void setIdColl(int idColl) {
		this.idColl = idColl;
	}

	@Column(name = "ROLES", nullable = false)
	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof RolesId))
			return false;
		RolesId castOther = (RolesId) other;
		return new EqualsBuilder().append(roles, castOther.roles).append(
				idColl, castOther.idColl).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(idColl).append(roles).toHashCode();
	}
}
