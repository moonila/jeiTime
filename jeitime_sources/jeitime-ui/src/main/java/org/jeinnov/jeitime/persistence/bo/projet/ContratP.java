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

@Entity
@Table(name = "CONTRAT")
@NamedQueries( { @NamedQuery(name = "ContratP.getClientPart", query = "select c from ContratP c where c.clientPart = :idCliPart") })
public class ContratP implements java.io.Serializable {
	// --- Variables --- //

	private static final long serialVersionUID = 8808171098146384614L;
	private ContratIdP id;
	private ClientPartP clientPart;
	private ProjetP projet;
	private int type;

	// --- Constructeur --- //
	public ContratP() {

	}

	// --- Getters et Setters --- //

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "idClientPart", column = @Column(name = "IDCLIENTPART", nullable = false)),
			@AttributeOverride(name = "idProjet", column = @Column(name = "IDPROJET", nullable = false)) })
	public ContratIdP getId() {
		return id;
	}

	public void setId(ContratIdP id) {
		this.id = id;
	}

	@Column(name = "TYPE", nullable = false)
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idClientPart", nullable = false, insertable = false, updatable = false)
	public ClientPartP getClientPart() {
		return clientPart;
	}

	public void setClientPart(ClientPartP clientPart) {
		this.clientPart = clientPart;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idProjet", nullable = false, insertable = false, updatable = false)
	public ProjetP getProjet() {
		return projet;
	}

	public void setProjet(ProjetP projet) {
		this.projet = projet;
	}

}
