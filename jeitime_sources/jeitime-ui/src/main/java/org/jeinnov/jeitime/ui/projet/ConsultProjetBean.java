package org.jeinnov.jeitime.ui.projet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jeinnov.jeitime.api.service.projet.ContratManager;
import org.jeinnov.jeitime.api.service.projet.ProjetException;
import org.jeinnov.jeitime.api.service.projet.ProjetManager;
import org.jeinnov.jeitime.api.service.projet.TacheManager;
import org.jeinnov.jeitime.api.to.projet.ClientPartTO;
import org.jeinnov.jeitime.api.to.projet.DomaineTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.jeinnov.jeitime.api.to.projet.ThematiqueTO;
import org.ow2.opensuit.core.error.IError;
import org.ow2.opensuit.core.error.NonLocalizedError;


public class ConsultProjetBean {
	private ProjetManager projetManager = ProjetManager.getInstance();

	// --- Variables --- //

	private int idProjet;
	private String nomProjet;
	private Date dateDebu;
	private Date dateFin;
	private Date dateCloture;
	private Date dateFermeture;
	private String budgeprevu = "0";
	private String tpsprevu = "0";
	private String sousProjet;

	private int idTypPro;
	private String nomTypPro;

	private int idDom;
	private String nomDom;

	private int idThema;
	private String nomThem;

	private List<ClientPartTO> allCliPartSelect = new ArrayList<ClientPartTO>();
	private int idCliPart;
	private int typeCli;

	private List<TacheTO> alltache;

	private ProjetTO projet;
	private int idSousProjet;

	private boolean editable;

	public void search(HttpServletRequest iRequest) throws IError,
			ProjetException {
		int id = Integer.parseInt(iRequest.getParameter("id"));
		try {
			projet = projetManager.get(id);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue, le projet n'a pas pu être chargé",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		allCliPartSelect = ContratManager.getInstance()
				.getClientPartByProjectIdInContrat(id);

		idProjet = projet.getIdProjet();
		nomProjet = projet.getNomProjet();
		dateDebu = projet.getDateDeb();
		dateFin = projet.getDateFin();
		budgeprevu = String.valueOf(projet.getBudgeprevu());
		tpsprevu = String.valueOf(projet.getTpsprevu());
		idTypPro = projet.getTypeProjet().getIdTypeProj();
		nomTypPro = projet.getTypeProjet().getNomTypePro();

		ProjetTO proj = projet.getProjet();
		if (proj != null) {
			idSousProjet = projet.getProjet().getIdProjet();
			sousProjet = projet.getProjet().getNomProjet();
		} else {
			idSousProjet = 0;
			sousProjet = "Aucun Projet n'est rattaché";
		}

		ThematiqueTO thema = projet.getThematique();
		if (thema != null) {
			idThema = projet.getThematique().getIdThema();
			nomThem = projet.getThematique().getNomThema();
		} else {
			idThema = 0;
			nomThem = "Aucune Thématique n'est rattachée";
		}

		DomaineTO dom = projet.getDomaine();
		if (dom != null) {
			idDom = projet.getDomaine().getIdDomaine();
			nomDom = projet.getDomaine().getNomDomaine();
		} else {
			idDom = 0;
			nomDom = "Aucun Domaine n'est rattaché";
		}

		alltache = TacheManager.getInstance().getAllTacheInProject(id);

		calculBudget(alltache);
	}

	public void calculBudget(List<TacheTO> alltaches) {
		budgeprevu = null;
		tpsprevu = null;
		alltache = alltaches;

		float budget = 0;
		for (int i = 0; i < alltache.size(); i++) {
			budget = budget + alltache.get(i).getBudgetprevu();

		}
		budgeprevu = String.valueOf(budget);

		float tmps = 0;
		for (int i = 0; i < alltache.size(); i++) {
			tmps = tmps + alltache.get(i).getTpsprevu();
		}
		tpsprevu = String.valueOf(tmps);
	}

	public ProjetManager getProjetManager() {
		return projetManager;
	}

	public void setProjetManager(ProjetManager projetManager) {
		this.projetManager = projetManager;
	}

	public int getIdProjet() {
		return idProjet;
	}

	public void setIdProjet(int idProjet) {
		this.idProjet = idProjet;
	}

	public String getNomProjet() {
		return nomProjet;
	}

	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}

	public Date getDateDebu() {
		return dateDebu;
	}

	public void setDateDebu(Date dateDebu) {
		this.dateDebu = dateDebu;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Date getDateCloture() {
		return dateCloture;
	}

	public void setDateCloture(Date dateCloture) {
		this.dateCloture = dateCloture;
	}

	public Date getDateFermeture() {
		return dateFermeture;
	}

	public void setDateFermeture(Date dateFermeture) {
		this.dateFermeture = dateFermeture;
	}

	public String getBudgeprevu() {
		return budgeprevu;
	}

	public void setBudgeprevu(String budgeprevu) {
		this.budgeprevu = budgeprevu;
	}

	public String getTpsprevu() {
		return tpsprevu;
	}

	public void setTpsprevu(String tpsprevu) {
		this.tpsprevu = tpsprevu;
	}

	public String getSousProjet() {
		return sousProjet;
	}

	public void setSousProjet(String sousProjet) {
		this.sousProjet = sousProjet;
	}

	public int getIdTypPro() {
		return idTypPro;
	}

	public void setIdTypPro(int idTypPro) {
		this.idTypPro = idTypPro;
	}

	public String getNomTypPro() {
		return nomTypPro;
	}

	public void setNomTypPro(String nomTypPro) {
		this.nomTypPro = nomTypPro;
	}

	public int getIdDom() {
		return idDom;
	}

	public void setIdDom(int idDom) {
		this.idDom = idDom;
	}

	public String getNomDom() {
		return nomDom;
	}

	public void setNomDom(String nomDom) {
		this.nomDom = nomDom;
	}

	public int getIdThema() {
		return idThema;
	}

	public void setIdThema(int idThema) {
		this.idThema = idThema;
	}

	public String getNomThem() {
		return nomThem;
	}

	public void setNomThem(String nomThem) {
		this.nomThem = nomThem;
	}

	public List<ClientPartTO> getAllCliPartSelect() {
		return allCliPartSelect;
	}

	public void setAllCliPartSelect(List<ClientPartTO> allCliPartSelect) {
		this.allCliPartSelect = allCliPartSelect;
	}

	public int getIdCliPart() {
		return idCliPart;
	}

	public void setIdCliPart(int idCliPart) {
		this.idCliPart = idCliPart;
	}

	public int getTypeCli() {
		return typeCli;
	}

	public void setTypeCli(int typeCli) {
		this.typeCli = typeCli;
	}

	public List<TacheTO> getAlltache() {
		return alltache;
	}

	public void setAlltache(List<TacheTO> alltache) {
		this.alltache = alltache;
	}

	public ProjetTO getProjet() {
		return projet;
	}

	public void setProjet(ProjetTO projet) {
		this.projet = projet;
	}

	public int getIdSousProjet() {
		return idSousProjet;
	}

	public void setIdSousProjet(int idSousProjet) {
		this.idSousProjet = idSousProjet;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
}
