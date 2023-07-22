package org.jeinnov.jeitime.ui.projet;

import javax.servlet.http.HttpServletRequest;

import org.jeinnov.jeitime.api.service.projet.ProjetException;
import org.jeinnov.jeitime.api.service.projet.ThematiqueManager;
import org.jeinnov.jeitime.api.to.projet.ThematiqueTO;
import org.ow2.opensuit.core.error.IError;
import org.ow2.opensuit.core.error.NonLocalizedError;


import java.util.ArrayList;
import java.util.List;

public class ThematiqueUIBean {
	private ThematiqueManager thematiqueManager = ThematiqueManager
			.getInstance();

	// --- Variables --- //

	private int idThema;
	private String nomThema;

	private ThematiqueTO selected;
	private List<ThematiqueTO> allThema;
	private List<String> themat;

	// --- Actions Methods --- //

	public void load() {
		allThema = new ArrayList<ThematiqueTO>();
		allThema = thematiqueManager.getAll();
	}

	public List<String> getThema() {
		List<String> recup = new ArrayList<String>();
		recup.add("Thematique 1");
		recup.add("Thematique 2");
		recup.add("Thematique 3");
		recup.add("Thematique 4");
		recup.add("Thematique 5");
		return recup;
	}

	public void create() throws IError {
		ThematiqueTO thema = new ThematiqueTO(idThema, nomThema);
		try {
			thematiqueManager.saveOrUpdate(thema);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. La thématique n'a pas pu être sauvegardée."
							+ "Une thématique avec ce  nom existe peut-être déjà dans la base.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		refresh();
	}

	public void update() throws IError {
		int id = selected.getIdThema();
		String nom = selected.getNomThema();

		ThematiqueTO thema = new ThematiqueTO(id, nom);
		try {
			thematiqueManager.saveOrUpdate(thema);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. La thématique n'a pas pu être sauvegardée."
							+ "Une thématique avec ce  nom existe peut-être déjà dans la base.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		refresh();
	}

	public void delete(HttpServletRequest iRequest) throws IError {
		int id = Integer.parseInt(iRequest.getParameter("ID"));
		try {
			thematiqueManager.delete(id);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. La thématique sélectionnée n'a pas pu être supprimée",
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
		idThema = 0;
		nomThema = null;
		selected = null;

		allThema = new ArrayList<ThematiqueTO>();
		allThema = thematiqueManager.getAll();
	}

	public void select(HttpServletRequest iRequest) throws IError {
		selected = null;
		int id = Integer.parseInt(iRequest.getParameter("ID"));
		try {
			selected = thematiqueManager.get(id);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. La thématique sélectionnée n'a pas pu être chargée.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
	}

	public boolean isInProject(int id) throws ProjetException {
		boolean verif = thematiqueManager.isInProject(id);

		return verif;
	}

	// --- Getters et Setters --- //

	public List<ThematiqueTO> getAllThema() {
		return allThema;
	}

	public void setAllThema(List<ThematiqueTO> allThema) {
		this.allThema = allThema;
	}

	public ThematiqueTO getSelected() {
		return selected;
	}

	public int getIdThema() {
		return idThema;
	}

	public void setIdThema(int idThema) {
		this.idThema = idThema;
	}

	public String getNomThema() {
		return nomThema;
	}

	public void setNomThema(String nomThema) {
		this.nomThema = nomThema;
	}

	public List<String> getThemat() {
		return themat;
	}

}
