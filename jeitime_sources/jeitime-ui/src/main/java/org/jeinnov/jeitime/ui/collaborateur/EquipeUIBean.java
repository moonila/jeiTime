package org.jeinnov.jeitime.ui.collaborateur;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurException;
import org.jeinnov.jeitime.api.service.collaborateur.EquipeManager;
import org.jeinnov.jeitime.api.to.collaborateur.EquipeTO;
import org.ow2.opensuit.core.error.IError;
import org.ow2.opensuit.core.error.NonLocalizedError;


public class EquipeUIBean {
	private EquipeManager equipeManager = EquipeManager.getInstance();

	private int idEquipe;
	private String nomEquipe;
	private String fonctionEquipe;

	private EquipeTO selected;
	private List<EquipeTO> allEquip;

	// ===========================================================
	// === Actions Methods
	// ===========================================================
	public void loadAll() {
		idEquipe = 0;
		nomEquipe = null;
		fonctionEquipe = null;
		allEquip = new ArrayList<EquipeTO>();
		allEquip = equipeManager.getAll();

	}

	public void create() throws IError {
		EquipeTO equipColl = new EquipeTO(idEquipe, nomEquipe, fonctionEquipe);
		try {
			equipeManager.saveOrUpdate(equipColl);
		} catch (CollaborateurException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Aucune équipe n'est spécifiée ou l'équipe sélectionnée n'existe plus dans la base.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		clear();
	}

	public void refresh() {
		// do nothing
	}

	public void clear() {
		idEquipe = 0;
		nomEquipe = null;
		fonctionEquipe = null;
		allEquip = new ArrayList<EquipeTO>();
		allEquip = equipeManager.getAll();
	}

	public void select(HttpServletRequest iRequest) throws IError {
		selected = null;
		int id = Integer.parseInt(iRequest.getParameter("ID"));

		try {
			selected = equipeManager.get(id);
		} catch (CollaborateurException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Aucune équipe n'est sélectionnée ou l'équipe sélectionnée n'existe plus dans la base.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

	}

	public void update() throws IError {
		int id = selected.getIdEquip();
		String nom = selected.getNomEquip();
		String fct = selected.getFonctionEquip();

		EquipeTO equipe = new EquipeTO(id, nom, fct);
		try {
			equipeManager.saveOrUpdate(equipe);
		} catch (CollaborateurException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Aucune équipe n'est spécifiée ou l'équipe sélectionnée n'existe plus dans la base.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		selected = null;
		clear();
	}

	public void delete(HttpServletRequest iRequest) throws IError {
		int id = Integer.parseInt(iRequest.getParameter("ID"));

		try {
			equipeManager.deleteEquipe(id);
		} catch (CollaborateurException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Aucune équipe n'est sélectionnée ou l'équipe sélectionnée n'existe plus dans la base.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		clear();
	}

	public void cancel() {
		// unselect
		selected = null;
	}

	public boolean isInCollab(int id) throws CollaborateurException {
		boolean verif = equipeManager.isInCollaborateur(id);

		return verif;
	}

	// ===========================================================
	// === Getter / Setters
	// ===========================================================

	public EquipeTO getSelected() {
		return selected;
	}

	public List<EquipeTO> getAllEquip() {
		return allEquip;
	}

	public void setAllEquip(List<EquipeTO> allEquip) {
		this.allEquip = allEquip;
	}

	public int getIdEquipe() {
		return idEquipe;
	}

	public void setIdEquipe(int idEquipe) {
		this.idEquipe = idEquipe;
	}

	public String getNomEquipe() {
		return nomEquipe;
	}

	public void setNomEquipe(String nomEquipe) {
		this.nomEquipe = nomEquipe;
	}

	public String getFonctionEquipe() {
		return fonctionEquipe;
	}

	public void setFonctionEquipe(String fonctionEquipe) {
		this.fonctionEquipe = fonctionEquipe;
	}

	public void setEquipeManager(EquipeManager equipeManager) {
		this.equipeManager = equipeManager;
	}

}
