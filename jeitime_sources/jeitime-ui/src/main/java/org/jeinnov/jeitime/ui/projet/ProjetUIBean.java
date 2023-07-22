package org.jeinnov.jeitime.ui.projet;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jeinnov.jeitime.api.service.projet.ClientPartManager;
import org.jeinnov.jeitime.api.service.projet.ContratManager;
import org.jeinnov.jeitime.api.service.projet.DomaineManager;
import org.jeinnov.jeitime.api.service.projet.ProjetException;
import org.jeinnov.jeitime.api.service.projet.ProjetManager;
import org.jeinnov.jeitime.api.service.projet.TacheManager;
import org.jeinnov.jeitime.api.service.projet.ThematiqueManager;
import org.jeinnov.jeitime.api.service.projet.TypeProjetManager;
import org.jeinnov.jeitime.api.to.affecter.RecapAffectTO;
import org.jeinnov.jeitime.api.to.projet.ClientPartTO;
import org.jeinnov.jeitime.api.to.projet.DomaineTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.jeinnov.jeitime.api.to.projet.ThematiqueTO;
import org.jeinnov.jeitime.api.to.projet.TypeProjetTO;
import org.ow2.opensuit.core.error.IError;
import org.ow2.opensuit.core.error.NonLocalizedError;
import org.ow2.opensuit.core.validation.LocalizedValidationError;
import org.ow2.opensuit.core.validation.ValidationErrors;


public class ProjetUIBean {

	private final Logger logger = Logger.getLogger(this.getClass());
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

	private List<TypeProjetTO> allTypPro;
	private int idTypPro;
	private String nomTypPro;

	private List<DomaineTO> allDom;
	private int idDom;
	private String nomDom;

	private List<ThematiqueTO> allThema;
	private int idThema;
	private String nomThem;

	private List<ClientPartTO> allCliPart;
	private List<ClientPartTO> allCliPartSelect;
	private int idCliPart;
	private int typeCli;
	private static int[] ALL_TYPESCLI = { 0, 1 };

	private List<TacheTO> alltache;

	private List<RecapAffectTO> listRecapAffect;

	private ProjetTO projet;
	private List<ProjetTO> projets;
	private int idSousProjet;

	// --- Actions Methods --- //

	public void load() throws IError {
		allTypPro = new ArrayList<TypeProjetTO>();
		allTypPro = TypeProjetManager.getInstance().getAll();

		if (allTypPro == null || allTypPro.isEmpty()) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Vous ne pouvez pas créer un nouveau projet, "
							+ "il faut au préalable avoir créé au moins un type de projet");
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		allDom = new ArrayList<DomaineTO>();
		DomaineTO aucunDom = new DomaineTO();
		aucunDom.setIdDomaine(0);
		aucunDom.setNomDomaine("Non Concerné");
		allDom = DomaineManager.getInstance().getAll();
		allDom.add(0, aucunDom);

		allThema = new ArrayList<ThematiqueTO>();
		ThematiqueTO aucuneThema = new ThematiqueTO();
		aucuneThema.setIdThema(0);
		aucuneThema.setNomThema("aucun élément sélectionné");
		allThema = ThematiqueManager.getInstance().getAll();
		allThema.add(0, aucuneThema);

		allCliPart = new ArrayList<ClientPartTO>();
		ClientPartTO aucunCliPart = new ClientPartTO();
		aucunCliPart.setIdClientPart(0);
		aucunCliPart.setNomClientPart("aucun élément sélectionné");
		allCliPart = ClientPartManager.getInstance().getAll();
		allCliPart.add(0, aucunCliPart);

		projets = new ArrayList<ProjetTO>();
		ProjetTO aucunProjet = new ProjetTO();
		aucunProjet.setIdProjet(0);
		aucunProjet.setNomProjet("aucun élément sélectionné");
		projets = projetManager.getAll();
		projets.add(0, aucunProjet);
	}

	public void create(int idP) throws IError, ValidationErrors {
//		validate();
		NumberFormat nf = NumberFormat.getInstance(Locale.FRANCE);
		float budget = 0;
		float tmps = 0;
		try {
			budget = Float.parseFloat(budgeprevu);
		} catch (Exception e) {
			try {
				budget = nf.parse(budgeprevu).floatValue();
			} catch (ParseException pe) {
				logger.error(e.getMessage(), e);
			}
		}
		try {
			tmps = Float.parseFloat(tpsprevu);
		} catch (Exception e) {
			try {
				tmps = nf.parse(tpsprevu).floatValue();
			} catch (ParseException pe) {
				logger.error(e.getMessage(), e);
			}
		}

		projet = new ProjetTO(idP, nomProjet, dateDebu, dateFin, dateCloture,
				dateFermeture, budget, tmps, new TypeProjetTO(idTypPro),
				new DomaineTO(idDom), new ThematiqueTO(idThema), new ProjetTO(
						idSousProjet));

		try {
			projetManager.saveOrUpdate(projet);
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
	}

	public void addCliPart(int idProjetTmp) throws IError {
		for (int i = 0; i < allCliPartSelect.size(); i++) {
			int idCliPartTmp = allCliPartSelect.get(i).getIdClientPart();
			int typeCliTmp = allCliPartSelect.get(i).getType();

			try {
				ContratManager.getInstance().save(idProjetTmp, idCliPartTmp,
						typeCliTmp);
			} catch (ProjetException e) {
				NonLocalizedError error = new NonLocalizedError(
						"Attention : ",
						"Une erreur est survenue. Le contrat n'a pas pu être enregistré.",
						e);
				error.setType(IError.FUNCTIONAL_ERROR);
				throw error;
			}
		}
	}

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

	public void cancel() {
		// ne fait rien
	}

	public void selectCli(int typeCli, int idProjetTmp) throws ValidationErrors, IError {
		// ClientPartTO clientPart = null;
		int id = idCliPart;
		if (id != 0) {
			ClientPartTO clientPart;
			try {
				clientPart = ClientPartManager.getInstance().get(id);
			} catch (ProjetException e) {
				NonLocalizedError error = new NonLocalizedError(
						"Attention : ",
						"Une erreur est survenue. Le client/partenaire n'a pas pu être chargé.",
						e);
				error.setType(IError.FUNCTIONAL_ERROR);
				throw error;
			}
			clientPart.setType(typeCli);
			allCliPartSelect.add(clientPart);
		} else {
			ValidationErrors errors = new ValidationErrors();
			errors.addItemError("projetBean.idCliPart",
					new LocalizedValidationError("validation.client"));
		}
		addCliPart(idProjetTmp);
	}

	public void deselecteCli(HttpServletRequest iRequest, int idProjetTmp) throws IError {
		int id = Integer.parseInt(iRequest.getParameter("id"));

		for (int i = 0; i < allCliPartSelect.size(); i++) {
			if (allCliPartSelect.get(i).getIdClientPart() == id) {
				allCliPartSelect.remove(i);
				break;
			}
		}
		supprLienCli(id, idProjetTmp);
	}

	public void supprLienCli(int idCliPartTmp, int idProjetTmp)
			throws IError {
//		int idCliPartTmp = Integer.parseInt(iRequest.getParameter("id"));

		try {
			ContratManager.getInstance().delete(idProjetTmp, idCliPartTmp);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. Le contrat n'a pas pu être supprimé.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
	}

	public void deselectColl(HttpServletRequest iRequest) {
		int id = Integer.parseInt(iRequest.getParameter("id"));

		for (int i = 0; i < listRecapAffect.size(); i++) {
			if (listRecapAffect.get(i).getCollaborateur().getIdColl() == id) {
				listRecapAffect.remove(i);
				break;
			}
		}
	}

	public void validate() throws ValidationErrors {
		ValidationErrors errors = new ValidationErrors();
		if (dateDebu != null && dateFin != null && dateFin.before(dateDebu)) {
			errors.addItemError("projetBean.dateFin",
					new LocalizedValidationError("validation.date"));
		}
		if (errors.hasErrors()) {
			throw errors;
		}
	}

	public boolean isInLienTach(int id) {
		boolean verif = projetManager.isInLienTachUtil(id);// .verifLien(id);

		return verif;
	}

	// --- Getters et Setters --- //

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

	public int getIdProjet() {
		return idProjet;
	}

	public void setIdProjet(int idProjet) {
		this.idProjet = idProjet;
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

	public List<TypeProjetTO> getAllTypPro() {
		return allTypPro;
	}

	public void setAllTypPro(List<TypeProjetTO> allTypPro) {
		this.allTypPro = allTypPro;
	}

	public int getIdTypPro() {
		return idTypPro;
	}

	public void setIdTypPro(int idTypPro) {
		this.idTypPro = idTypPro;
	}

	public List<DomaineTO> getAllDom() {
		return allDom;
	}

	public void setAllDom(List<DomaineTO> allDom) {
		this.allDom = allDom;
	}

	public int getIdDom() {
		return idDom;
	}

	public void setIdDom(int idDom) {
		this.idDom = idDom;
	}

	public List<ThematiqueTO> getAllThema() {
		return allThema;
	}

	public void setAllThema(List<ThematiqueTO> allThema) {
		this.allThema = allThema;
	}

	public int getIdThema() {
		return idThema;
	}

	public void setIdThema(int idThema) {
		this.idThema = idThema;
	}

	public List<ClientPartTO> getAllCliPart() {
		return allCliPart;
	}

	public void setAllCliPart(List<ClientPartTO> allCliPart) {
		this.allCliPart = allCliPart;
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

	public int getTypeCli() {
		return typeCli;
	}

	public void setTypeCli(int typeCli) {
		this.typeCli = typeCli;
	}

	public static int[] getAllTypesCli() {
		return ALL_TYPESCLI;
	}

	/*
	 * public boolean isVoirNvTache() { return voirNvTache; }
	 * 
	 * public void setVoirNvTache(boolean voirNvTache) { this.voirNvTache =
	 * voirNvTache; }
	 */
	public List<ProjetTO> getProjets() {
		return projets;
	}

	public void setProjets(List<ProjetTO> projets) {
		this.projets = projets;
	}

	public String getNomTypPro() {
		return nomTypPro;
	}

	public void setNomTypPro(String nomTypPro) {
		this.nomTypPro = nomTypPro;
	}

	public String getNomDom() {
		return nomDom;
	}

	public void setNomDom(String nomDom) {
		this.nomDom = nomDom;
	}

	public String getNomThem() {
		return nomThem;
	}

	public void setNomThem(String nomThem) {
		this.nomThem = nomThem;
	}

	public String getSousProjet() {
		return sousProjet;
	}

	public void setSousProjet(String sousProjet) {
		this.sousProjet = sousProjet;
	}

	public int getIdSousProjet() {
		return idSousProjet;
	}

	public void setIdSousProjet(int idSousProjet) {
		this.idSousProjet = idSousProjet;
	}

}
