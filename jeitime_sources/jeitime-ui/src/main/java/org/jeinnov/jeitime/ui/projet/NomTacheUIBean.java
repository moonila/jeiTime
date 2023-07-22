package org.jeinnov.jeitime.ui.projet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jeinnov.jeitime.api.service.projet.NomTacheManager;
import org.jeinnov.jeitime.api.service.projet.ProjetException;
import org.jeinnov.jeitime.api.service.projet.TypeTacheManager;
import org.jeinnov.jeitime.api.to.projet.NomTacheTO;
import org.jeinnov.jeitime.api.to.projet.TypeTacheTO;
import org.ow2.opensuit.core.error.IError;
import org.ow2.opensuit.core.error.NonLocalizedError;


public class NomTacheUIBean {
	private NomTacheManager nomTacheManager = NomTacheManager.getInstance();

	// --- Variables --- //
	private int idNomTache;
	private String nomTache;
	private boolean eligible;
	private NomTacheTO selected;
	private List<NomTacheTO> allNomTache;
	private TypeTacheTO typeTache;
	private List<TypeTacheTO> allTypeTache;
	private int idTypeTache;

	// --- Actions Methods --- //
	public void load() {
		allTypeTache = new ArrayList<TypeTacheTO>();
		allTypeTache = TypeTacheManager.getInstance().getAll();
		TypeTacheTO t = new TypeTacheTO();
		t.setIdTypTach(0);
		t.setNomTypTach("Aucun groupe");
		allTypeTache.add(0, t);
		allNomTache = new ArrayList<NomTacheTO>();
		allNomTache = nomTacheManager.getAll();
	}

	public void create() throws IError {
		typeTache = new TypeTacheTO(idTypeTache);
		NomTacheTO nomTacheTO = new NomTacheTO(idNomTache, nomTache, typeTache);
		try {
			nomTacheManager.saveOrUpdate(nomTacheTO);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. Le type de tâche n'a pas pu être sauvegardé."
							+ "Le type de tâche avec ce  nom existe peut-être déjà dans la base.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		refresh();
	}

	public void update() throws IError {
		int id = selected.getIdNomTache();
		String nom = selected.getNomTache();
		idTypeTache = selected.getTypeTache().getIdTypTach();
		TypeTacheTO type = new TypeTacheTO(idTypeTache);

		NomTacheTO nomtacheTo = new NomTacheTO(id, nom, type);

		try {
			nomTacheManager.saveOrUpdate(nomtacheTo);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. Le type de tâche n'a pas pu être sauvegardé."
							+ "Le type de tâche avec ce  nom existe peut-être déjà dans la base.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		refresh();
	}

	public void delete(HttpServletRequest iRequest) throws IError {
		int id = Integer.parseInt(iRequest.getParameter("ID"));
		try {
			nomTacheManager.delete(id);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. Le type de tâche selectionné n'a pas pu être supprimé",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		refresh();
	}

	public String verifGroupe(int idGroupe) {
		if (idGroupe == 0) {
			return "PasGroupeTache";
		} else {
			return "GroupeTache";
		}
	}

	public void cancel() {
		selected = null;
	}

	public void refresh() {
		idNomTache = 0;
		nomTache = null;
		idTypeTache = 0;
		selected = null;

		allNomTache = new ArrayList<NomTacheTO>();
		allNomTache = nomTacheManager.getAll();
	}

	public void select(HttpServletRequest iRequest) throws IError {
		selected = null;
		int id = Integer.parseInt(iRequest.getParameter("ID"));
		try {
			selected = nomTacheManager.get(id);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. Le type de projet selectionné n'a pas pu être chargé",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
	}

	public boolean isInTache(int id) throws ProjetException {
		boolean verif = nomTacheManager.isInTache(id);

		return verif;
	}

	// --- Getters et Setters --- //
	public List<NomTacheTO> getAllNomTache() {
		return allNomTache;
	}

	public void setAllNomTache(List<NomTacheTO> allNomTache) {
		this.allNomTache = allNomTache;
	}

	public NomTacheTO getSelected() {
		return selected;
	}

	public int getIdNomTache() {
		return idNomTache;
	}

	public void setIdNomTache(int idNomTache) {
		this.idNomTache = idNomTache;
	}

	public String getNomTache() {
		return nomTache;
	}

	public void setNomTache(String nomTache) {
		this.nomTache = nomTache;
	}

	public TypeTacheTO getTypeTache() {
		return typeTache;
	}

	public void setTypeTache(TypeTacheTO typeTache) {
		this.typeTache = typeTache;
	}

	public List<TypeTacheTO> getAllTypeTache() {
		return allTypeTache;
	}

	public void setAllTypeTache(List<TypeTacheTO> allTypeTache) {
		this.allTypeTache = allTypeTache;
	}

	public int getIdTypeTache() {
		return idTypeTache;
	}

	public void setIdTypeTache(int idTypeTache) {
		this.idTypeTache = idTypeTache;
	}

	public void setSelected(NomTacheTO selected) {
		this.selected = selected;
	}

	public boolean isEligible() {
		return eligible;
	}

	public void setEligible(boolean eligible) {
		this.eligible = eligible;
	}

}
