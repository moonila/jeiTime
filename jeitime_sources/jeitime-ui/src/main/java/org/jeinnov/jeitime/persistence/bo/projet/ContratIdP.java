package org.jeinnov.jeitime.persistence.bo.projet;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
public class ContratIdP implements java.io.Serializable {

	private static final long serialVersionUID = -2318900178020598532L;
	private int idClientPart;
	private int idProjet;

	public ContratIdP() {

	}

	public ContratIdP(int idClientPart, int idProjet) {
		super();
		this.idClientPart = idClientPart;
		this.idProjet = idProjet;
	}

	@Column(name = "IDCLIENTPART", nullable = false)
	public int getIdClientPart() {
		return idClientPart;
	}

	public void setIdClientPart(int idClientPart) {
		this.idClientPart = idClientPart;
	}

	@Column(name = "IDPROJET", nullable = false)
	public int getIdProjet() {
		return idProjet;
	}

	public void setIdProjet(int idProjet) {
		this.idProjet = idProjet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof ContratIdP))
			return false;
		ContratIdP castOther = (ContratIdP) other;
		return new EqualsBuilder().append(idClientPart, castOther.idClientPart)
				.append(idProjet, castOther.idProjet).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(idClientPart).append(idProjet)
				.toHashCode();
	}
}
