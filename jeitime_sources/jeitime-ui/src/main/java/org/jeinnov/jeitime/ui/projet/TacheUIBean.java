package org.jeinnov.jeitime.ui.projet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jeinnov.jeitime.api.service.projet.NomTacheManager;
import org.jeinnov.jeitime.api.service.projet.ProjetException;
import org.jeinnov.jeitime.api.service.projet.TacheManager;
import org.jeinnov.jeitime.api.service.projet.TypeTacheManager;
import org.jeinnov.jeitime.api.to.projet.NomTacheTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.jeinnov.jeitime.api.to.projet.TypeTacheTO;
import org.ow2.opensuit.core.error.IError;
import org.ow2.opensuit.core.error.NonLocalizedError;


import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TacheUIBean {
	private final Logger logger = Logger.getLogger(this.getClass());
	private TypeTacheManager typeTacheManager = TypeTacheManager.getInstance();
	private NomTacheManager nomTacheManager = NomTacheManager.getInstance();
	private TacheManager tacheManager = TacheManager.getInstance();

	private int idTache;
	private String budgetprevu = "0";
	private String tpsprevu = "0";
	private int priorite;

	private boolean eligible;
	private boolean modif = false;
	private boolean visible = true;

	private TypeTacheTO type;
	private List<TypeTacheTO> alltype;
	private int idType;
	private String nomType;

	private NomTacheTO nom;
	private List<NomTacheTO> allnom;
	private int idNom;
	private String nomTache;

	private TacheTO tache;
	private List<TacheTO> alltache;

	private int[] selectedTache;

	private static int[] ALL_PRIORITE = { 0, 1, 2 };

	public void load(HttpServletRequest iRequest) throws IError {
		alltype = new ArrayList<TypeTacheTO>();

		TypeTacheTO aucunType = new TypeTacheTO();
		aucunType.setIdTypTach(0);
		aucunType.setNomTypTach("aucun élément sélectionné");

		alltype = typeTacheManager.getAll();
		alltype.add(0, aucunType);

		idType = 0;
		budgetprevu = "0";
		tpsprevu = "0";

		int id = Integer.parseInt(iRequest.getParameter("id"));

		try {
			alltache = tacheManager.getAllTacheInProject(id);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError("Attention : ",
					"Une erreur est survenue", e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		visible = true;

	}

	public void select(HttpServletRequest iRequest) throws ProjetException {
		idTache = Integer.parseInt(iRequest.getParameter("ID"));
		tache = tacheManager.get(idTache);
		idType = tache.getNomtache().getTypeTache().getIdTypTach();
		idNom = tache.getNomtache().getIdNomTache();
		budgetprevu = String.valueOf(tache.getBudgetprevu());
		tpsprevu = String.valueOf(tache.getTpsprevu());
		priorite = tache.getPriorite();
		eligible = tache.isEligible();
		allnom = nomTacheManager.getAllByIdTypeTache(idType);

		visible = false;
		modif = true;
	}

	public void createOrUpdate(int idProjet) throws IError, ProjetException {
		if (idTache != 0) {
			modif = false;
			visible = true;
		}
		tache = null;
		NumberFormat nf = NumberFormat.getInstance(Locale.FRANCE);
		float budget = 0;
		float tmps = 0;
		try {
			budget = Float.parseFloat(budgetprevu);
		} catch (Exception e) {
			try {
				budget = nf.parse(budgetprevu).floatValue();
			} catch (ParseException pe) {
				logger.error(pe.getMessage(), pe);
			}
		}
		try {
			tmps = Float.parseFloat(tpsprevu);
		} catch (Exception e) {
			try {
				tmps = nf.parse(tpsprevu).floatValue();
			} catch (ParseException pe) {
				logger.error(pe.getMessage(), pe);
			}
		}
		type = typeTacheManager.get(idType);
		nomType = type.getNomTypTach();
		type = new TypeTacheTO(idType, nomType);
		try {
			nom = nomTacheManager.get(idNom);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. Aucun nom de tâche n'est spécifié",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		nomTache = nom.getNomTache();
		nom = new NomTacheTO(idNom, nomTache, type);
		ProjetTO projetTO = new ProjetTO();
		projetTO.setIdProjet(idProjet);
		tache = new TacheTO(idTache, nom, budget, tmps, priorite, eligible,
				projetTO);
		try {
			tacheManager.saveOrUpdate(tache);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue la tâche n'a pas été sauvegardé. Cette tâche existe peut-être déjà pour ce projet.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		try {
			alltache = tacheManager.getAllTacheInProject(idProjet);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue, lors de la récupération des tâches du projet",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		cancel();
	}

	public void cancel() {
		idType = 0;
		allnom = null;
		modif = false;
		visible = true;
		idTache = 0;
		tache = null;
		budgetprevu = "0";
		tpsprevu = "0";
		priorite = 0;
		eligible = false;
	}

	public void selectNomtache(int idTyp) throws ProjetException {
		if (idTyp == 0) {
			allnom = null;
		} else {
			allnom = nomTacheManager.getAllByIdTypeTache(idTyp);
		}
		if (allnom == null || allnom.isEmpty()) {
			allnom = null;
		}
	}

	public void deselectTache(HttpServletRequest iRequest) throws IError {
		int id = Integer.parseInt(iRequest.getParameter("id"));

		for (int i = 0; i < alltache.size(); i++) {
			if (alltache.get(i).getIdTache() == id) {
				alltache.remove(i);
				break;
			}
		}
		try {
			tacheManager.delete(id);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError("Attention : ",
					"Une erreur est survenue", e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
	}

	public boolean isInLienTach(int id) throws ProjetException {
		boolean verif = tacheManager.isInLienTachUtil(id);

		return verif;
	}

	/*******************************************************************************
	 * Méthodes permettant de sélectionner toutes les taches ou de les
	 * déselectionner
	 ********************************************************************************/
	public void selectAll() {
		selectedTache = new int[alltache.size()];
		for (int i = 0; i < alltache.size(); i++) {
			selectedTache[i] = alltache.get(i).getIdTache();
		}
	}

	public void deSelectAll() {
		selectedTache = null;
	}

	// --- Getters et Setters --- //
	public int getIdTache() {
		return idTache;
	}

	public void setIdTache(int idTache) {
		this.idTache = idTache;
	}

	public String getBudgetprevu() {
		return budgetprevu;
	}

	public void setBudgetprevu(String budgetprevu) {
		this.budgetprevu = budgetprevu;
	}

	public String getTpsprevu() {
		return tpsprevu;
	}

	public void setTpsprevu(String tpsprevu) {
		this.tpsprevu = tpsprevu;
	}

	public int getPriorite() {
		return priorite;
	}

	public void setPriorite(int priorite) {
		this.priorite = priorite;
	}

	public boolean isEligible() {
		return eligible;
	}

	public void setEligible(boolean eligible) {
		this.eligible = eligible;
	}

	public TypeTacheTO getType() {
		return type;
	}

	public void setType(TypeTacheTO type) {
		this.type = type;
	}

	public List<TypeTacheTO> getAlltype() {
		return alltype;
	}

	public void setAlltype(List<TypeTacheTO> alltype) {
		this.alltype = alltype;
	}

	public int getIdType() {
		return idType;
	}

	public void setIdType(int idType) {
		this.idType = idType;
	}

	public NomTacheTO getNom() {
		return nom;
	}

	public void setNom(NomTacheTO nom) {
		this.nom = nom;
	}

	public List<NomTacheTO> getAllnom() {
		return allnom;
	}

	public void setAllnom(List<NomTacheTO> allnom) {
		this.allnom = allnom;
	}

	public int getIdNom() {
		return idNom;
	}

	public void setIdNom(int idNom) {
		this.idNom = idNom;
	}

	public static int[] getAllPriorite() {
		return ALL_PRIORITE;
	}

	public TacheTO getTache() {
		return tache;
	}

	public void setTache(TacheTO tache) {
		this.tache = tache;
	}

	public int[] getSelectedTache() {
		return selectedTache;
	}

	public void setSelectedTache(int[] selectedTache) {
		this.selectedTache = selectedTache;
	}

	public List<TacheTO> getAlltache() {
		return alltache;
	}

	public void setAlltache(List<TacheTO> alltache) {
		this.alltache = alltache;
	}

	public boolean isModif() {
		return modif;
	}

	public void setModif(boolean modif) {
		this.modif = modif;
	}

	public boolean isVisible() {
		return visible;
	}

}
