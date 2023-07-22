package org.jeinnov.jeitime.ui.projet;

import javax.servlet.http.HttpServletRequest;

import org.jeinnov.jeitime.api.service.projet.ProjetException;
import org.jeinnov.jeitime.api.service.projet.TypeProjetManager;
import org.jeinnov.jeitime.api.to.projet.TypeProjetTO;
import org.ow2.opensuit.core.error.IError;
import org.ow2.opensuit.core.error.NonLocalizedError;


import java.util.ArrayList;
import java.util.List;

public class TypeProjetUIBean {

	private TypeProjetManager typeProjetManager = TypeProjetManager
			.getInstance();

	// --- Variables --- //

	private int idTypeProjet;
	private String nomTypeProjet;

	private TypeProjetTO selected;
	private List<TypeProjetTO> allTypProj;

	// --- Actions Methods --- //

	public void load() {
		allTypProj = new ArrayList<TypeProjetTO>();
		allTypProj = typeProjetManager.getAll();
	}

	public void create() throws IError {
		TypeProjetTO typProj = new TypeProjetTO(idTypeProjet, nomTypeProjet);
		try {
			typeProjetManager.saveOrUpdate(typProj);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. Le type de projet n'a pas pu être sauvegardé."
							+ " Un type de projet avec ce  nom existe peut-être déjà dans la base.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		refresh();
	}

	public void update() throws IError {
		int id = selected.getIdTypeProj();
		String nom = selected.getNomTypePro();

		TypeProjetTO typProj = new TypeProjetTO(id, nom);
		try {
			typeProjetManager.saveOrUpdate(typProj);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. Le type de projet n'a pas pu être sauvegardé."
							+ " Un type de projet avec ce  nom existe peut-être déjà dans la base.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		refresh();
	}

	public void delete(HttpServletRequest iRequest) throws IError {
		int id = Integer.parseInt(iRequest.getParameter("ID"));
		try {
			typeProjetManager.delete(id);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. Le type de projet selectionné n'a pas pu être supprimé",
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
		idTypeProjet = 0;
		nomTypeProjet = null;

		selected = null;

		allTypProj = new ArrayList<TypeProjetTO>();
		allTypProj = typeProjetManager.getAll();
	}

	public void select(HttpServletRequest iRequest) throws IError {
		selected = null;
		int id = Integer.parseInt(iRequest.getParameter("ID"));
		try {
			selected = typeProjetManager.get(id);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. Le type de projet sélectionné n'a pas pu être chargé.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
	}

	public boolean isInProject(int id) throws ProjetException {
		boolean verif = typeProjetManager.isInProjet(id);

		return verif;
	}

	// --- Getters et Setters --- //

	public List<TypeProjetTO> getAllTypProj() {
		return allTypProj;
	}

	public void setAllTypProj(List<TypeProjetTO> allTypProj) {
		this.allTypProj = allTypProj;
	}

	public TypeProjetTO getSelected() {
		return selected;
	}

	public int getIdTypeProjet() {
		return idTypeProjet;
	}

	public void setIdTypeProjet(int idTypeProjet) {
		this.idTypeProjet = idTypeProjet;
	}

	public String getNomTypeProjet() {
		return nomTypeProjet;
	}

	public void setNomTypeProjet(String nomTypeProjet) {
		this.nomTypeProjet = nomTypeProjet;
	}

}
