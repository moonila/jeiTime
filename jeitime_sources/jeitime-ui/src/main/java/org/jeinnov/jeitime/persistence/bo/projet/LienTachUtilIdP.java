package org.jeinnov.jeitime.persistence.bo.projet;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
public class LienTachUtilIdP implements java.io.Serializable {

	private static final long serialVersionUID = 8521539942883196506L;
	private int idTache;
	private int idColl;
	private Date date;

	public LienTachUtilIdP() {

	}

	public LienTachUtilIdP(int idTache, int idColl, Date date) {
		super();

		this.idTache = idTache;
		this.idColl = idColl;
		this.date = date;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE", nullable = false)
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
		if (!(other instanceof LienTachUtilIdP))
			return false;
		LienTachUtilIdP castOther = (LienTachUtilIdP) other;
		return new EqualsBuilder().append(idTache, castOther.idTache).append(
				idColl, castOther.idColl).append(date, castOther.date)
				.isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(idColl).append(idTache)
				.append(date).toHashCode();
	}
}
