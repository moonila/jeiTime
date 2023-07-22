package org.jeinnov.jeitime.api.to.projet;

public class ClientPartTO {
	// --- Variables --- //

	private int idClientPart;
	private String nomClientPart;
	private String nomContact;
	private String nomService;
	private String commentaire;
	private int type;

	// --- Constructeurs ///

	public ClientPartTO() {

	}

	public ClientPartTO(int idClientPart, String nomClientPart,
			String nomService, String nomContact, String commentaire, int type) {
		super();
		this.idClientPart = idClientPart;
		this.nomClientPart = nomClientPart;
		this.nomContact = nomContact;
		this.nomService = nomService;
		this.commentaire = commentaire;
		this.type = type;
	}

	public ClientPartTO(int idClientPart) {
		super();
		this.idClientPart = idClientPart;
	}

	// --- Getters et Setters --- //

	public int getIdClientPart() {
		return idClientPart;
	}

	public void setIdClientPart(int idClientPart) {
		this.idClientPart = idClientPart;
	}

	public String getNomClientPart() {
		return nomClientPart;
	}

	public void setNomClientPart(String nomClientPart) {
		this.nomClientPart = nomClientPart;
	}

	public String getNomContact() {
		return nomContact;
	}

	public void setNomContact(String nomContact) {
		this.nomContact = nomContact;
	}

	public String getNomService() {
		return nomService;
	}

	public void setNomService(String nomService) {
		this.nomService = nomService;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
