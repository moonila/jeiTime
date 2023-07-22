package org.jeinnov.jeitime.persistence.bo.collaborateur;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
public class AppartientCollegeIdP implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8074957498805456830L;

	private int idCollege;
	private int idColl;
	private Date date;

	public AppartientCollegeIdP() {

	}

	public AppartientCollegeIdP(int idCollege, int idColl, Date date) {
		super();
		this.idCollege = idCollege;
		this.idColl = idColl;
		this.date = date;
	}

	public AppartientCollegeIdP(int idCollege, int idColl) {
		super();
		this.idCollege = idCollege;
		this.idColl = idColl;
	}

	@Column(name = "IDCOLLEGE", nullable = false)
	public int getIdCollege() {
		return idCollege;
	}

	public void setIdCollege(int idCollege) {
		this.idCollege = idCollege;
	}

	@Column(name = "IDCOLL", nullable = false)
	public int getIdColl() {
		return idColl;
	}

	public void setIdColl(int idColl) {
		this.idColl = idColl;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATEC", nullable = false)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof AppartientCollegeIdP))
			return false;
		AppartientCollegeIdP castOther = (AppartientCollegeIdP) other;
		return new EqualsBuilder().append(idCollege, castOther.idCollege)
				.append(idColl, castOther.idColl).append(date, castOther.date)
				.isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(idColl).append(idCollege).append(
				date).toHashCode();
	}
}
