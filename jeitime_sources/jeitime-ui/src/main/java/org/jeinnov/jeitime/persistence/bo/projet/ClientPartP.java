package org.jeinnov.jeitime.persistence.bo.projet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENTPART")
public class ClientPartP implements java.io.Serializable {

	private static final long serialVersionUID = -3357419465491387375L;

	// --- Variables --- //

	private int idClientPart;
	private String nomClientPart;
	private String nomService;
	private String nomContact;
	private String commentaire;

	// --- Constructeurs --- //

	public ClientPartP() {

	}

	// --- Getters et Setters ---//

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDCLIENTPART", unique = true, nullable = false)
	public int getIdClientPart() {
		return idClientPart;
	}

	public void setIdClientPart(int idClientPart) {
		this.idClientPart = idClientPart;
	}

	@Column(name = "NOMETP", nullable = false)
	public String getNomClientPart() {
		return nomClientPart;
	}

	public void setNomClientPart(String nomClientPart) {
		this.nomClientPart = nomClientPart;
	}

	@Column(name = "NOMSERVICE")
	public String getNomService() {
		return nomService;
	}

	public void setNomService(String nomService) {
		this.nomService = nomService;
	}

	@Column(name = "NOMCONTACT")
	public String getNomContact() {
		return nomContact;
	}

	public void setNomContact(String nomContact) {
		this.nomContact = nomContact;
	}

	@Column(name = "COMMENTAIRE")
	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

}
