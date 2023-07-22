package org.jeinnov.jeitime.persistence.bo.collaborateur;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "ROLES")
public class Roles implements java.io.Serializable {

	private static final long serialVersionUID = 8238089675288931674L;

	private String roles;

	public Roles() {
		super();
	}

	public Roles(String roles) {
		super();
		this.roles = roles;
	}

	@Id
	@Column(name = "ROLES", nullable = true)
	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
