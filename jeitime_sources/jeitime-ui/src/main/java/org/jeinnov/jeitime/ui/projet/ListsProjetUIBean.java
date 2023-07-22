package org.jeinnov.jeitime.ui.projet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jeinnov.jeitime.api.service.projet.ContratManager;
import org.jeinnov.jeitime.api.service.projet.ProjetException;
import org.jeinnov.jeitime.api.service.projet.ProjetManager;
import org.jeinnov.jeitime.api.service.projet.TacheManager;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.ow2.opensuit.core.error.IError;
import org.ow2.opensuit.core.error.NonLocalizedError;


public class ListsProjetUIBean {

	private ProjetManager projetManager = ProjetManager.getInstance();
	private TacheManager tacheManager = TacheManager.getInstance();

	private List<TacheTO> alltache;
	private List<ProjetTO> allprojet;
	private List<ProjetTO> listProjetFerme;
	private List<ProjetTO> listProjetNonFerme;
	private List<ProjetTO> listProjetCloture;
	private List<ProjetTO> listProjetNonCloture;

	private int[] selected;

	public void load() {

		allprojet = new ArrayList<ProjetTO>();
		allprojet = projetManager.getAllNotLock();
	}

	public void listerProjetFerme() {
		listProjetFerme = new ArrayList<ProjetTO>();
		listProjetFerme = projetManager.getAllClose();// .listProjetFerme();
	}

	public void listerProjetNonFerme() {
		listProjetNonFerme = new ArrayList<ProjetTO>();
		listProjetNonFerme = projetManager.getAllNotClose();// .listProjetNonFerme();
	}

	public void listerProjetClos() {
		listProjetCloture = new ArrayList<ProjetTO>();
		listProjetCloture = projetManager.getAllLock();// .listProjetClos();
	}

	public void listerProjetNonClos() {
		listProjetNonCloture = new ArrayList<ProjetTO>();
		listProjetNonCloture = projetManager.getAllNotLock();// .listProjetNonClos();
	}

	public boolean isInLienTach(int id) {
		boolean verif = projetManager.isInLienTachUtil(id);// .verifLien(id);

		return verif;
	}

	public void delete(HttpServletRequest iRequest) throws IError,
			ProjetException {
		int id = Integer.parseInt(iRequest.getParameter("ID"));

		TacheManager.getInstance().deleteAllTacheInProject(id);
		ContratManager.getInstance().delete(id);
		try {
			projetManager.delete(id);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue, le projet n'a pas pu être supprimé.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
	}

	public void searchCopy(HttpServletRequest iRequest) throws IError,
			ProjetException {
		int id = Integer.parseInt(iRequest.getParameter("id"));
		ProjetTO projet = projetManager.get(id);
		final StringBuilder builder = new StringBuilder(projet.getNomProjet());
		builder.append(" Copie");

		alltache = TacheManager.getInstance().getAllTacheInProject(
				projet.getIdProjet());

		projet.setNomProjet(builder.toString());
		projet.setIdProjet(0);
		projet.setDateCloture(null);
		projet.setDateFermeture(null);
		int idP;
		try {
			idP = projetManager.saveOrUpdate(projet);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue, "
							+ "le projet n'a pas pu etre sauvegardé. "
							+ "Un autre projet existe peut-être déjà avec ce nom. ",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		saveAllTaches(idP);

		load();

	}

	private void saveAllTaches(int idP) throws IError {
		for (TacheTO tache : alltache) {
			tache.setProjet(new ProjetTO(idP));
			tache.setIdTache(0);
			try {
				tacheManager.saveOrUpdate(tache);
			} catch (ProjetException e) {
				NonLocalizedError error = new NonLocalizedError(
						"Attention : ",
						"Une erreur est survenue. La tâche n'a pas pu être sauvegardée.",
						e);
				error.setType(IError.FUNCTIONAL_ERROR);
				throw error;
			}
		}
	}

	public void deselect() throws ProjetException {
		for (int i = 0; i < selected.length; i++) {
			int id = selected[i];
			projetManager.openProject(id);
		}
		listerProjetFerme();
	}

	public void selectClo() throws ProjetException {
		for (int i = 0; i < selected.length; i++) {
			int id = selected[i];
			projetManager.lockProject(id);
		}

		listerProjetNonClos();
	}

	public void select() throws ProjetException {
		for (int i = 0; i < selected.length; i++) {
			int id = selected[i];
			projetManager.closeProject(id);
		}

		listerProjetNonFerme();
	}

	public List<TacheTO> getAlltache() {
		return alltache;
	}

	public void setAlltache(List<TacheTO> alltache) {
		this.alltache = alltache;
	}

	public List<ProjetTO> getAllprojet() {
		return allprojet;
	}

	public void setAllprojet(List<ProjetTO> allprojet) {
		this.allprojet = allprojet;
	}

	public List<ProjetTO> getListProjetFerme() {
		return listProjetFerme;
	}

	public void setListProjetFerme(List<ProjetTO> listProjetFerme) {
		this.listProjetFerme = listProjetFerme;
	}

	public List<ProjetTO> getListProjetNonFerme() {
		return listProjetNonFerme;
	}

	public void setListProjetNonFerme(List<ProjetTO> listProjetNonFerme) {
		this.listProjetNonFerme = listProjetNonFerme;
	}

	public List<ProjetTO> getListProjetCloture() {
		return listProjetCloture;
	}

	public void setListProjetCloture(List<ProjetTO> listProjetCloture) {
		this.listProjetCloture = listProjetCloture;
	}

	public List<ProjetTO> getListProjetNonCloture() {
		return listProjetNonCloture;
	}

	public void setListProjetNonCloture(List<ProjetTO> listProjetNonCloture) {
		this.listProjetNonCloture = listProjetNonCloture;
	}

	public int[] getSelected() {
		return selected;
	}

	public void setSelected(int[] selected) {
		this.selected = selected;
	}

}
