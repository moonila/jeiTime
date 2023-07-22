package org.jeinnov.jeitime.ui.projet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jeinnov.jeitime.api.service.projet.ClientPartManager;
import org.jeinnov.jeitime.api.service.projet.ProjetException;
import org.jeinnov.jeitime.api.to.projet.ClientPartTO;
import org.ow2.opensuit.core.error.IError;
import org.ow2.opensuit.core.error.NonLocalizedError;


public class ClientPartUIBean {

	private ClientPartManager clientPartManager = ClientPartManager
			.getInstance();

	// --- Variables --- //

	private int idClientPart;
	private String nomClientPart;
	private String nomContact;
	private String nomService;
	private String commentaire;
	private int type;

	private ClientPartTO selected;
	private List<ClientPartTO> allCliPart;

	private static int[] ALL_TYPES = { 0, 1 };

	// --- Actions Methods --- //

	public void loadAll() {
		allCliPart = new ArrayList<ClientPartTO>();
		allCliPart = clientPartManager.getAll();
	}

	public void create() throws IError {
		ClientPartTO clipart = new ClientPartTO(idClientPart, nomClientPart,
				nomService, nomContact, commentaire, type);
		try {
			clientPartManager.saveOrUpdate(clipart);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. Le client/partenaire n'a pas pu être sauvegardé.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		refresh();
	}

	public void update() throws IError {
		int id = selected.getIdClientPart();
		String nom = selected.getNomClientPart();
		String nomContactTmp = selected.getNomContact();
		String nomServicesTmp = nomService;
		String commentaireTmp = selected.getCommentaire();
		int typeTmp = selected.getType();

		ClientPartTO clipart = new ClientPartTO(id, nom, nomServicesTmp,
				nomContactTmp, commentaireTmp, typeTmp);

		try {
			clientPartManager.saveOrUpdate(clipart);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. Le client/partenaire n'a pas pu être sauvegardé.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		refresh();

	}

	public void delete(HttpServletRequest iRequest) throws IError {
		int id = Integer.parseInt(iRequest.getParameter("ID"));

		try {
			clientPartManager.delete(id);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. Le client/partenaire n'a pas pu être supprimé.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		refresh();
	}

	public void cancel() {
		selected = null;
	}

	public void refresh() {
		nomClientPart = null;
		nomContact = null;
		nomService = null;
		commentaire = null;

		allCliPart = new ArrayList<ClientPartTO>();
		allCliPart = clientPartManager.getAll();

		selected = null;
	}

	public void select(HttpServletRequest iRequest) throws IError {
		selected = null;
		int id = Integer.parseInt(iRequest.getParameter("ID"));
		try {
			selected = clientPartManager.get(id);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. Le client/partenaire n'a pas pu être chargé.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		nomService = selected.getNomService();
	}

	public boolean isInProject(int id) throws ProjetException {
		boolean verif = clientPartManager.isInProject(id);

		return verif;
	}

	// --- Getters et Setters --- //

	public ClientPartTO getSelected() {
		return selected;
	}

	public static int[] getAllTypes() {
		return ALL_TYPES;
	}

	public List<ClientPartTO> getAllCliPart() {
		return allCliPart;
	}

	public void setAllCliPart(List<ClientPartTO> allCliPart) {
		this.allCliPart = allCliPart;
	}

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
