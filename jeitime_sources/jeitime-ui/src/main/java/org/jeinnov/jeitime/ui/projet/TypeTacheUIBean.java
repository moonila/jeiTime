package org.jeinnov.jeitime.ui.projet;

import javax.servlet.http.HttpServletRequest;

import org.jeinnov.jeitime.api.service.projet.NomTacheManager;
import org.jeinnov.jeitime.api.service.projet.ProjetException;
import org.jeinnov.jeitime.api.service.projet.TypeTacheManager;
import org.jeinnov.jeitime.api.to.projet.NomTacheTO;
import org.jeinnov.jeitime.api.to.projet.TypeTacheTO;
import org.ow2.opensuit.core.error.IError;
import org.ow2.opensuit.core.error.NonLocalizedError;


import java.util.ArrayList;
import java.util.List;

public class TypeTacheUIBean {
	private TypeTacheManager typeTacheManager = TypeTacheManager.getInstance();
	private NomTacheManager nomTacheManager = NomTacheManager.getInstance();

	// --- Variables --- //

	private int idTypTach;
	private String nomTypTach;

	private TypeTacheTO selected;
	private List<TypeTacheTO> allTypTach;
	private TypeTacheTO typTache;
	private List<NomTacheTO> alltache;

	private String nomTache;

	// --- Actions Methods --- //

	public void load() {
		allTypTach = new ArrayList<TypeTacheTO>();
		allTypTach = typeTacheManager.getAll();
	}

	public void create() throws IError {
		TypeTacheTO typTach = new TypeTacheTO(idTypTach, nomTypTach);
		try {
			typeTacheManager.saveOrUpdate(typTach);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. Le groupe de tache n'a pas pu être sauvegardé."
							+ " Un groupe de tâche avec ce  nom existe peut-être déjà dans la base.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		refresh();
	}

	public void update() throws IError {
		int id = idTypTach;
		String nom = nomTypTach;

		TypeTacheTO typTach = new TypeTacheTO(id, nom);
		try {
			typeTacheManager.saveOrUpdate(typTach);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. Le groupe de tache n'a pas pu être sauvegardé."
							+ " Un groupe de tâche avec ce  nom existe peut-être déjà dans la base.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		refresh();
	}

	public void delete(HttpServletRequest iRequest) throws IError {
		int id = Integer.parseInt(iRequest.getParameter("ID"));
		try {
			typeTacheManager.delete(id);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. Le groupe de tâche selectionné n'a pas pu être supprimé",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		refresh();
	}

	public void search(HttpServletRequest iRequest) throws ProjetException {
		int id = Integer.parseInt(iRequest.getParameter("id"));
		typTache = typeTacheManager.get(id);
		idTypTach = typTache.getIdTypTach();
		nomTypTach = typTache.getNomTypTach();

		alltache = nomTacheManager.getAllByIdTypeTache(idTypTach);
	}

	public void ajoutTache(int idTypT) throws ProjetException {

		typTache = typeTacheManager.get(idTypT);
		idTypTach = typTache.getIdTypTach();
	}

	public void ajouter() throws IError, ProjetException {

		NomTacheTO nt = new NomTacheTO();
		nt.setTypeTache(typTache);
		nt.setNomTache(nomTache);
		try {
			nomTacheManager.saveOrUpdate(nt);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError("Attention : ",
					"Une erreur est survenue", e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		alltache = nomTacheManager.getAllByIdTypeTache(typTache.getIdTypTach());
		nomTache = null;
	}

	public void cancel() {
		selected = null;
	}

	public void refresh() {
		idTypTach = 0;
		nomTypTach = null;

		selected = null;

		allTypTach = new ArrayList<TypeTacheTO>();
		allTypTach = typeTacheManager.getAll();
	}

	public void select(HttpServletRequest iRequest) throws IError {
		selected = null;
		int id = Integer.parseInt(iRequest.getParameter("ID"));
		try {
			selected = typeTacheManager.get(id);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. Le groupe de tâche sélectionné n'a pas pu être chargé.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
	}

	public boolean isInTache(int id) throws IError {
		boolean verif;
		try {
			verif = typeTacheManager.isInNomTache(id);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError("Attention : ",
					"Une erreur est survenue", e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		return verif;
	}

	public boolean isInTacheNT(int id) throws ProjetException {
		boolean verif = nomTacheManager.isInTache(id);

		return verif;
	}

	public void deleteNT(HttpServletRequest iRequest) throws IError,
			ProjetException {
		int id = Integer.parseInt(iRequest.getParameter("ID"));

		try {
			nomTacheManager.delete(id);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError("Attention : ",
					"Une erreur est survenue", e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		alltache = nomTacheManager.getAllByIdTypeTache(typTache.getIdTypTach());
	}

	// --- Getters et Setters --- //

	public List<TypeTacheTO> getAllTypTach() {
		return allTypTach;
	}

	public void setAllTypTach(List<TypeTacheTO> allTypTach) {
		this.allTypTach = allTypTach;
	}

	public TypeTacheTO getSelected() {
		return selected;
	}

	public int getIdTypTach() {
		return idTypTach;
	}

	public void setIdTypTach(int idTypTach) {
		this.idTypTach = idTypTach;
	}

	public String getNomTypTach() {
		return nomTypTach;
	}

	public void setNomTypTach(String nomTypTach) {
		this.nomTypTach = nomTypTach;
	}

	public List<NomTacheTO> getAlltache() {
		return alltache;
	}

	public void setAlltache(List<NomTacheTO> alltache) {
		this.alltache = alltache;
	}

	public String getNomTache() {
		return nomTache;
	}

	public void setNomTache(String nomTache) {
		this.nomTache = nomTache;
	}

}
