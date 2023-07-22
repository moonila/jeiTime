package org.jeinnov.jeitime.ui.affecter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jeinnov.jeitime.api.service.affecter.AffecterException;
import org.jeinnov.jeitime.api.service.affecter.AffecterManager;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurException;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurManager;
import org.jeinnov.jeitime.api.service.collaborateur.EquipeManager;
import org.jeinnov.jeitime.api.service.projet.ProjetException;
import org.jeinnov.jeitime.api.service.projet.ProjetManager;
import org.jeinnov.jeitime.api.service.projet.TacheManager;
import org.jeinnov.jeitime.api.to.affecter.RecapAffectTO;
import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.collaborateur.EquipeTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.ow2.opensuit.core.error.IError;
import org.ow2.opensuit.core.error.NonLocalizedError;


public class AffecterUIBean {

	// Variables

	private AffecterManager affecterManager = AffecterManager.getInstance();

	private int idProjet;
	private List<ProjetTO> allprojet;

	private int idTache;
	private List<TacheTO> alltache;
	private int[] selectedTache;

	private int idColl;
	private List<CollaborateurTO> allCollabEquip;
	private int[] selectedColl;

	private int idEq;
	private List<EquipeTO> allequipe;

	private List<RecapAffectTO> allAffect;
	private List<RecapAffectTO> listRecapAffect;
	private List<String> listSupprAffect;

	private int suivant = 0;
	
	private boolean voirTache;
	private boolean voirCollab;
	private boolean voirRecap;

	// private boolean voirNvTache = true;

	public AffecterUIBean() {

	}

	public void loadAllProjets() throws IError {
		allprojet = ProjetManager.getInstance().getAllNotLock();
		if (allprojet == null || allprojet.isEmpty()) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Pour pouvoir effectuer les affectations des ressources "
							+ "vous devez au préalable créer au moins un projet avec ses tâches");
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
	}

	public void loadAllTache(HttpServletRequest iRequest) throws IError {
		if (iRequest.getParameter("id") != null
				&& !iRequest.getParameter("id").equalsIgnoreCase("0")) {
			idProjet = Integer.parseInt(iRequest.getParameter("id"));
		}
		try {
			alltache = TacheManager.getInstance()
					.getAllTacheInProject(idProjet);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError("Attention : ",
					"Une erreur est survenue. Le projet avec l'id " + idProjet
							+ "n'existe pas. ", e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		voirTache = true;
		voirCollab = false;
		voirRecap = false;
	}

	public void loadAllEquipe() {
		allequipe = new ArrayList<EquipeTO>();
		idEq = 0;
		EquipeTO aucuneEqu = new EquipeTO();
		aucuneEqu.setIdEquip(0);
		aucuneEqu.setNomEquip("--Sélectionnez une Equipe--");
		EquipeTO tousCollabs = new EquipeTO();
		tousCollabs.setIdEquip(9999);
		tousCollabs.setNomEquip("--Tous les Collaborateurs--");

		allequipe = EquipeManager.getInstance().getAll();
		allequipe.add(0, aucuneEqu);
		allequipe.add(1, tousCollabs);
		
		voirTache = false;
		voirCollab = true;
		voirRecap = false;
	}

	public void loadAllAffectation(HttpServletRequest iRequest) throws IError {
		idProjet = Integer.parseInt(iRequest.getParameter("id"));
		try {
			allAffect = affecterManager.getAllByIdProjet(idProjet);
		} catch (AffecterException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Une erreur est survenue, Le projet avec l'id " + idProjet
							+ "n'existe pas.", e.getMessage(), e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
	}

	public void create(List<RecapAffectTO> listRecapAffect) throws IError {
		try {
			affecterManager.saveAll(listRecapAffect);
		} catch (AffecterException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Une erreur est survenue, l'enregistrement des affectations est impossible",
					e.getMessage(), e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		loadAllEquipe();
		loadAllProjets();
		reset();
	}

	public void supprAffect() throws IError {

		for (String rA : listSupprAffect) {
			String[] params = rA.split(";");
			for (int i = 0; i < params.length; i++) {
				if (i == 0) {
					idColl = Integer.valueOf(params[0]);
				}
				if (i == 1) {
					idTache = Integer.valueOf(params[1]);
				}
			}
			try {
				affecterManager.delete(idTache, idColl);
			} catch (AffecterException e) {
				NonLocalizedError error = new NonLocalizedError(
						"Une erreur est survenue, la suppression de l'affectation n'a pas pu être effectuée."
								+ "Le collaborateur avec l'id "
								+ idColl
								+ "n'est peut être pas associé à la tache avec l'id "
								+ idTache, e.getMessage(), e);
				error.setType(IError.FUNCTIONAL_ERROR);
				throw error;
			}
			allAffect = new ArrayList<RecapAffectTO>();
			try {
				allAffect = affecterManager.getAllByIdProjet(idProjet);
			} catch (AffecterException e) {
				NonLocalizedError error = new NonLocalizedError(
						"Une erreur est survenue, la liste des affectations n'a pas pu être chargée. "
								+ "Le projet avec l'id " + idProjet
								+ "n'existe pas.", e.getMessage(), e);
				error.setType(IError.FUNCTIONAL_ERROR);
				throw error;
			}
		}
	}

	public void selectCollaborateur(int idEq) {
		if (idEq == 0) {
			allCollabEquip = null;
		} else if (idEq == 9999) {
			allCollabEquip = CollaborateurManager.getInstance()
					.getAllWithoutAdmin();
		} else {
			allCollabEquip = CollaborateurManager.getInstance()
					.getAllByIdEquipe(idEq);
		}
	}

	/*******************************************************************************
	 * Méthodes permettant de sélectionner toutes les taches ou de les
	 * déselectionner
	 * 
	 ********************************************************************************/
	public void selectAllTache() {
		selectedTache = new int[alltache.size()];
		for (int i = 0; i < alltache.size(); i++) {
			selectedTache[i] = alltache.get(i).getIdTache();
		}
	}

	public void deSelectAllTache() {
		selectedTache = null;
	}

	/***************************************************************************************
	 *Méthodes permettant de sélectionner tous les collaborateurs ou de les
	 * déselectionner *
	 ***************************************************************************************/

	public void selectAllColl(int idEq) {

		selectedColl = new int[allCollabEquip.size()];
		for (int i = 0; i < allCollabEquip.size(); i++) {
			selectedColl[i] = allCollabEquip.get(i).getIdColl();
		}
	}

	public void deSelectAllColl() {
		selectedColl = null;
	}

	/*****************************************************************************************
	 * Cette méthode permet de "désaffecter" un collaborateur d'une tâche avant
	 * d'enregistrer* les affectations *
	 *****************************************************************************************/

	public void deselectColl(HttpServletRequest iRequest) {
		int id = Integer.parseInt(iRequest.getParameter("id"));

		for (int i = 0; i < listRecapAffect.size(); i++) {
			if (listRecapAffect.get(i).getCollaborateur().getIdColl() == id) {
				listRecapAffect.remove(i);
				break;
			}
		}
	}

	public void voirTableauRecap() throws ProjetException,
			CollaborateurException
	{
		List<TacheTO> soustaches = new ArrayList<TacheTO>();
		List<CollaborateurTO> souscollaborateurs = new ArrayList<CollaborateurTO>();

		CollaborateurManager collaborateurManager = CollaborateurManager
				.getInstance();
		TacheManager tacheManager = TacheManager.getInstance();

		listRecapAffect = new ArrayList<RecapAffectTO>();
		for (int id : selectedTache) {
			TacheTO t = tacheManager.get(id);
			soustaches.add(t);
		}

		for (int id : selectedColl)

		{
			CollaborateurTO c = collaborateurManager.get(id);
			souscollaborateurs.add(c);
		}
		for (CollaborateurTO c : souscollaborateurs) {
			for (TacheTO t : soustaches) {
				RecapAffectTO recap = new RecapAffectTO();
				recap.setTache(t);
				recap.setCollaborateur(c);
				listRecapAffect.add(recap);
			}
		}
		
		voirTache = false;
		voirCollab = false;
		voirRecap = true;
	}

	public void reset() {
		selectedTache = null;
		selectedColl = null;
		allAffect = new ArrayList<RecapAffectTO>();
	}

	// getters and setters

	public List<ProjetTO> getAllprojet() {
		return allprojet;
	}

	public void setAllprojet(List<ProjetTO> allprojet) {
		this.allprojet = allprojet;
	}

	public List<TacheTO> getAlltache() {
		return alltache;
	}

	public void setAlltache(List<TacheTO> alltache) {
		this.alltache = alltache;
	}

	public int getIdProjet() {
		return idProjet;
	}

	public void setIdProjet(int idProjet) {
		this.idProjet = idProjet;
	}

	public int getSuivant() {
		return suivant;
	}

	public void setSuivant(int suivant) {
		this.suivant = suivant;
	}

	public int[] getSelectedTache() {
		return selectedTache;
	}

	public void setSelectedTache(int[] selectedTache) {
		this.selectedTache = selectedTache;
	}

	public int[] getSelectedColl() {
		return selectedColl;
	}

	public void setSelectedColl(int[] selectedColl) {
		this.selectedColl = selectedColl;
	}

	public List<CollaborateurTO> getAllCollabEquip() {
		return allCollabEquip;
	}

	public void setAllCollabEquip(List<CollaborateurTO> allCollabEquip) {
		this.allCollabEquip = allCollabEquip;
	}

	public List<RecapAffectTO> getAllAffect() {
		return allAffect;
	}

	public void setAllAffect(List<RecapAffectTO> allAffect) {
		this.allAffect = allAffect;
	}

	public int getIdEq() {
		return idEq;
	}

	public void setIdEq(int idEq) {
		this.idEq = idEq;
	}

	public List<EquipeTO> getAllequipe() {
		return allequipe;
	}

	public List<RecapAffectTO> getListRecapAffect() {
		return listRecapAffect;
	}

	public void setListRecapAffect(List<RecapAffectTO> listRecapAffect) {
		this.listRecapAffect = listRecapAffect;
	}

	public int getIdTache() {
		return idTache;
	}

	public void setIdTache(int idTache) {
		this.idTache = idTache;
	}

	public int getIdColl() {
		return idColl;
	}

	public void setIdColl(int idColl) {
		this.idColl = idColl;
	}

	public List<String> getListSupprAffect() {
		return listSupprAffect;
	}

	public void setListSupprAffect(List<String> listSupprAffect) {
		this.listSupprAffect = listSupprAffect;
	}

	public boolean isVoirTache() {
		return voirTache;
	}

	public void setVoirTache(boolean voirTache) {
		this.voirTache = voirTache;
	}

	public boolean isVoirCollab() {
		return voirCollab;
	}

	public void setVoirCollab(boolean voirCollab) {
		this.voirCollab = voirCollab;
	}

	public boolean isVoirRecap() {
		return voirRecap;
	}

	public void setVoirRecap(boolean voirRecap) {
		this.voirRecap = voirRecap;
	}

}
