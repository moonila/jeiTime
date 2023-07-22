package org.jeinnov.jeitime.persistence.bo.collaborateur;

import java.security.Principal;

import javax.persistence.AttributeOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Column;


@Entity
@Table(name = "ROLECOLLAB")
public class RolesCollab implements Principal, java.io.Serializable {

	private static final long serialVersionUID = 5781745226419019386L;

	private RolesId id;
	private Roles roles;
	private CollaborateurP collab;
	private String name;

	public RolesCollab() {

	}

	public RolesCollab(String name) {
		this.name = name;
	}

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "roles", column = @Column(name = "ROLES", nullable = false)),
			@AttributeOverride(name = "idColl", column = @Column(name = "IDCOLL", nullable = false)) })
	public RolesId getId() {
		return id;
	}

	public void setId(RolesId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLES", nullable = false, insertable = false, updatable = false)
	public Roles getRole() {
		return roles;
	}

	public void setRole(Roles roles) {
		this.roles = roles;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDCOLL", nullable = false, insertable = false, updatable = false)
	public CollaborateurP getCollab() {
		return collab;
	}

	public void setCollab(CollaborateurP collab) {
		this.collab = collab;
	}

	@Transient
	public String getName() {
		return name;
	}

	@Transient
	public String toString() {
		String className = getClass().getName();
		return className.substring(className.lastIndexOf('.') + 1) + ":" + name;
	}

	@Transient
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (!(o instanceof RolesCollab)) {
			return false;
		}
		RolesCollab that = (RolesCollab) o;
		if (this.getName().equals((that.getName()))) {
			return true;
		}
		return false;
	}

	@Transient
	public int hashCode() {
		return name.hashCode();
	}

}
