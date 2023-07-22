package org.jeinnov.jeitime.ui.collaborateur;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jeinnov.jeitime.api.service.affecter.AffecterException;
import org.jeinnov.jeitime.api.service.affecter.AffecterManager;
import org.jeinnov.jeitime.api.service.collaborateur.AppartientCollegeManager;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurException;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurManager;
import org.jeinnov.jeitime.api.service.collaborateur.CollegeManager;
import org.jeinnov.jeitime.api.service.collaborateur.EquipeManager;
import org.jeinnov.jeitime.api.service.collaborateur.RoleCollabManager;
import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.collaborateur.CollegeTO;
import org.jeinnov.jeitime.api.to.collaborateur.EquipeTO;
import org.ow2.opensuit.core.error.IError;
import org.ow2.opensuit.core.error.NonLocalizedError;
import org.ow2.opensuit.core.validation.LocalizedValidationError;
import org.ow2.opensuit.core.validation.ValidationErrors;


public class CollaborateurUiBean {

	private final Logger logger = Logger.getLogger(this.getClass());

	private EquipeManager equipeManager = EquipeManager.getInstance();
	private CollaborateurManager collaborateurManager = CollaborateurManager
			.getInstance();
	private CollegeManager collegeManager = CollegeManager.getInstance();
	private ResultTransformerUIBean resultTransformer = new ResultTransformerUIBean();

	// ---------Attributs pour le bean CollaborateurUiBean---------//

	private int id;
	private int idColl;
	private String login;
	private String nomColl;
	private String prenomColl;
	private String password;
	private String confirmPass;
	private String oldPass;
	private String newPass;
	private String salaireColl = "0";
	private String chargeColl = "0";

	private int statutColl;
	private int contrat;

	private int[] selectedColl;

	private int[] selectedEquipe;

	private CollaborateurTO collaborateur;
	private List<CollaborateurTO> allCollaborateur;

	private List<CollaborateurTO> listRecap = new ArrayList<CollaborateurTO>();

	private EquipeTO equipColl;
	private List<EquipeTO> allEquip;
	private int idEqu;

	private CollegeTO college;
	private List<CollegeTO> allCollege;
	private Integer idCollege;

	private String strNbHeureLundi = "0";
	private String strNbHeureMardi = "0";
	private String strNbHeureMercredi = "0";
	private String strNbHeureJeudi = "0";
	private String strNbHeureVendredi = "0";
	private String strNbHeureAnnuel = "0";
	private String strNbHeureMens = "0";
	private String strNbHeureHeb = "0";

	private float nbHeureLundi = 0;
	private float nbHeureMardi = 0;
	private float nbHeureMercredi = 0;
	private float nbHeureJeudi = 0;
	private float nbHeureVendredi = 0;
	private float nbHeureHeb = 0;
	private float nbHeureMens = 0;
	private float nbHeureAnn = 0;
	private float salaire = 0;
	private float charge = 0;

	private static int[] ALL_STATUS = { 0, 1, 2, 3 };
	private static int[] ALL_CONTRAT = { 0, 1 };

	private boolean edit;

	// ===========================================================
	// === Actions Methods
	// ===========================================================
	public void loadInformations() {
		allCollege = new ArrayList<CollegeTO>();
		CollegeTO aucunCollege = new CollegeTO();
		aucunCollege.setIdCollege(0);
		aucunCollege.setNomCollege("aucun college");
		allCollege = collegeManager.getAll();
		allCollege.add(0, aucunCollege);

		allEquip = new ArrayList<EquipeTO>();
		EquipeTO aucuneEquipe = new EquipeTO();
		aucuneEquipe.setIdEquip(0);
		aucuneEquipe.setNomEquip("aucune équipe");
		allEquip = equipeManager.getAll();
		allEquip.add(0, aucuneEquipe);

		statutColl = 3;
	}

	public void loadAll() {
		allCollaborateur = new ArrayList<CollaborateurTO>();
		allCollaborateur = collaborateurManager.getAll();
	}

	public void load(HttpServletRequest iRequest) throws IError {
		loadInformations();
		id = Integer.parseInt(iRequest.getParameter("id"));
		load();
	}

	public void load() throws IError {
		try {
			collaborateur = collaborateurManager.get(id);
		} catch (CollaborateurException e) {
			NonLocalizedError error = new NonLocalizedError("Attention : ",
					"Le collaborateur avec l'id " + id + " n'exsite pas.", e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		login = collaborateur.getLogin();
		nomColl = collaborateur.getNomColl();
		prenomColl = collaborateur.getPrenomColl();
		password = collaborateur.getPassword();
		salaireColl = String.valueOf(collaborateur.getSalairAnn());
		chargeColl = String.valueOf(collaborateur.getChargeAnn());
		statutColl = collaborateur.getStatut();
		equipColl = collaborateur.getEquipe();

		if (equipColl != null) {
			idEqu = equipColl.getIdEquip();
		}

		floatToStringCollaborateur();
		contrat = collaborateur.getContrat();
		idCollege = AppartientCollegeManager.getInstance().get(
				collaborateur.getIdColl());

		oldPass = null;
		newPass = null;
		confirmPass = null;
	}

	public void create() throws IError, CollaborateurException,
			ValidationErrors {
		NumberFormat nf = NumberFormat.getInstance(Locale.FRANCE);

		stringToFloatCollab(nf);
		nbHeureHeb = nbHeureLundi + nbHeureMardi + nbHeureMercredi
				+ nbHeureJeudi + nbHeureVendredi;

		creerInfoHeureCollab(nf);
		EquipeTO equip = new EquipeTO(idEqu);

		password = collaborateurManager.passwordCrypting(password);
		confirmPass = collaborateurManager.passwordCrypting(confirmPass);
		collaborateur = new CollaborateurTO(id, login, nomColl, prenomColl,
				password, statutColl, salaire, charge, nbHeureLundi,
				nbHeureMardi, nbHeureMercredi, nbHeureJeudi, nbHeureVendredi,
				nbHeureHeb, nbHeureMens, nbHeureAnn, equip, contrat);

		int idC = idCollege == null ? 0 : idCollege.intValue();

		int idCollab;
		try {
			idCollab = collaborateurManager.saveOrUpdate(collaborateur);
		} catch (CollaborateurException e) {
			NonLocalizedError error = new NonLocalizedError("Attention : ",
					"Une erreur est survenue "
							+ "le collaborateur n'a pas pu être sauvegardé "
							+ "ce login : " + login
							+ " est peut-être déjà utilisé", e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		RoleCollabManager.getInstance().save(statutColl, idCollab);
		if (idC != 0) {
			AppartientCollegeManager.getInstance().saveOrUpdate(idC, idCollab);
		}
	}

	public void update() throws IError, CollaborateurException {
		NumberFormat nf = NumberFormat.getInstance();

		stringToFloatCollab(nf);
		nbHeureHeb = nbHeureLundi + nbHeureMardi + nbHeureMercredi
				+ nbHeureJeudi + nbHeureVendredi;

		creerInfoHeureCollab(nf);
		EquipeTO equipe = new EquipeTO(idEqu);
		CollaborateurTO collab = new CollaborateurTO(id, login, nomColl,
				prenomColl, password, statutColl, salaire, charge,
				nbHeureLundi, nbHeureMardi, nbHeureMercredi, nbHeureJeudi,
				nbHeureVendredi, nbHeureHeb, nbHeureMens, nbHeureAnn, equipe,
				contrat);
		int idCollab;
		try {
			idCollab = collaborateurManager.saveOrUpdate(collab);
		} catch (CollaborateurException e) {
			NonLocalizedError error = new NonLocalizedError("Attention : ",
					"Une erreur est survenue le collaborateur n'a pas pu être sauvegardé."
							+ "ce login : " + login
							+ " est peut-être déjà utilisé", e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		RoleCollabManager.getInstance().save(statutColl, idCollab);
		if (idCollege != 0) {
			AppartientCollegeManager.getInstance().saveOrUpdate(idCollege,
					idCollab);
		} else {
			AppartientCollegeManager.getInstance().deleteByIdCollaborateur(
					idCollab);
		}
	}

	public void validatePassword() throws ValidationErrors {
		ValidationErrors errors = new ValidationErrors();

		if (!password.equalsIgnoreCase(confirmPass)) {
			errors.addItemError("collaborateurBean.confirmPass",
					new LocalizedValidationError("validation.password"));
		}
		if (errors.hasErrors()) {
			throw errors;
		}
	}

	public void validateChangePassword() throws ValidationErrors {
		ValidationErrors errors = new ValidationErrors();
		oldPass = collaborateurManager.passwordCrypting(oldPass);
		if (!newPass.equalsIgnoreCase(confirmPass)) {
			errors.addItemError("collaborateurBean.confirmPass",
					new LocalizedValidationError("validation.password"));
		} else if (!oldPass.equalsIgnoreCase(password)) {
			errors.addItemError("collaborateurBean.oldPass",
					new LocalizedValidationError("validation.oldPassword"));
		}
		if (errors.hasErrors()) {
			throw errors;
		}
	}

	public void validateChangePasswordByAdminUser() throws ValidationErrors {
		ValidationErrors errors = new ValidationErrors();

		if (!newPass.equalsIgnoreCase(confirmPass)) {
			errors.addItemError("collaborateurBean.confirmPass",
					new LocalizedValidationError("validation.password"));
		}
		if (errors.hasErrors()) {
			throw errors;
		}
	}

	public void changePassword(HttpServletRequest iRequest)
			throws CollaborateurException, IError {

		newPass = collaborateurManager.passwordCrypting(newPass);
		password = newPass;
		this.update();
	}

	public void selectCollege(int idC) throws IError {
		if (idC != 0) {
			floatToStringCollege(idC);
		} else {
			strNbHeureLundi = "0";
			strNbHeureMardi = "0";
			strNbHeureMercredi = "0";
			strNbHeureJeudi = "0";
			strNbHeureVendredi = "0";
			strNbHeureAnnuel = "0";
			strNbHeureMens = "0";
		}
	}

	public void delete(HttpServletRequest iRequest) throws IError,
			CollaborateurException {
		loadInformations();
		int idCollab = Integer.parseInt(iRequest.getParameter("ID"));
		try {
			AppartientCollegeManager.getInstance().deleteByIdCollaborateur(
					idCollab);
			RoleCollabManager.getInstance().delete(idCollab);

		} catch (CollaborateurException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Le collaborateur avec l'id " + idCollab
							+ " n'exsite peut-être pas.", e.getMessage(), e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		try {
			AffecterManager.getInstance().deleteAllForCollaborateur(idCollab);
		} catch (AffecterException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Le collaborateur avec l'id " + idCollab
							+ " n'exsite peut-être pas.", e.getMessage(), e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		try {
			collaborateurManager.delete(idCollab);
		} catch (CollaborateurException e) {
			NonLocalizedError error = new NonLocalizedError("Attention : ",
					"Le collaborateur avec l'id " + idCollab
							+ " n'exsite peut-être pas.", e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		loadAll();
	}

	public void selectCollaborateur(int idEq) {
		if (idEq == 0) {
			allCollaborateur = null;
		} else {
			allCollaborateur = collaborateurManager.getAllByIdEquipe(idEq);
		}
	}

	public void refresh() {
		// do nothing
	}

	public boolean isInLienTach(int id, String loginC) {
		boolean verif;
		verif = collaborateurManager.isInLientTachUtil(id);
		if (loginC.equalsIgnoreCase("admin")) {
			verif = true;
		}
		return verif;
	}

	public void rempliSem(int idCo) throws IError {
		floatToStringCollege(idCo);
	}

	private void floatToStringCollaborateur() {
		strNbHeureLundi = resultTransformer.floatToStringLundi(collaborateur);
		strNbHeureMardi = resultTransformer.floatToStringMardi(collaborateur);
		strNbHeureMercredi = resultTransformer
				.floatToStringMercredi(collaborateur);
		strNbHeureJeudi = resultTransformer.floatToStringJeudi(collaborateur);
		strNbHeureVendredi = resultTransformer
				.floatToStringVendredi(collaborateur);
		strNbHeureAnnuel = resultTransformer.floatToStringAnnuel(collaborateur);
		strNbHeureMens = resultTransformer.floatToStringMensuel(collaborateur);
	}

	private void floatToStringCollege(int idC) throws IError {
		CollegeTO c;
		try {
			c = collegeManager.get(idC);
		} catch (CollaborateurException e) {
			NonLocalizedError error = new NonLocalizedError("Attention : ",
					"Aucun college n'est sélectionné", e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		strNbHeureLundi = resultTransformer.floatToStringLundi(c);
		strNbHeureMardi = resultTransformer.floatToStringMardi(c);
		strNbHeureMercredi = resultTransformer.floatToStringMercredi(c);
		strNbHeureJeudi = resultTransformer.floatToStringJeudi(c);
		strNbHeureVendredi = resultTransformer.floatToStringVendredi(c);
		strNbHeureAnnuel = resultTransformer.floatToStringAnnuel(c);
		strNbHeureMens = resultTransformer.floatToStringMensuel(c);
		strNbHeureHeb = resultTransformer.floatToStringHebdo(c);
	}

	private void creerInfoHeureCollab(NumberFormat nf) throws IError {
		if (idCollege != 0) {
			CollegeTO col;
			try {
				col = collegeManager.get(idCollege);
			} catch (CollaborateurException e) {
				NonLocalizedError error = new NonLocalizedError("Attention : ",
						"Aucun college n'est sélectionné", e);
				error.setType(IError.FUNCTIONAL_ERROR);
				throw error;
			}
			nbHeureHeb = col.getNbHeureHeb();
			nbHeureMens = col.getNbHeureMensCollege();
			nbHeureAnn = col.getNbHeureAnnCollege();
		} else {
			try {
				nbHeureMens = Float.parseFloat(strNbHeureMens);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				try {
					nbHeureMens = nf.parse(strNbHeureMens).floatValue();
				} catch (ParseException pe) {
					logger.error(pe.getMessage(), pe);
				}
			}
			try {
				nbHeureAnn = Float.parseFloat(strNbHeureAnnuel);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				try {
					nbHeureAnn = nf.parse(strNbHeureAnnuel).floatValue();
				} catch (ParseException pe) {
					logger.error(pe.getMessage(), pe);
				}
			}
		}
	}

	private void stringToFloatCollab(NumberFormat nf) {
		salaire = resultTransformer.stringToFloatSalaire(nf, salaireColl);
		charge = resultTransformer.stringToFloatChargeCollab(nf, chargeColl);
		nbHeureLundi = resultTransformer
				.stringToFloatLundi(nf, strNbHeureLundi);
		nbHeureMardi = resultTransformer
				.stringToFloatMardi(nf, strNbHeureMardi);
		nbHeureMercredi = resultTransformer.stringToFloatMercredi(nf,
				strNbHeureMercredi);
		nbHeureJeudi = resultTransformer
				.stringToFloatJeudi(nf, strNbHeureJeudi);
		nbHeureVendredi = resultTransformer.stringToFloatVendredi(nf,
				strNbHeureVendredi);
	}

	// ===========================================================
	// === Getter / Setters
	// ===========================================================

	public int getIdColl() {
		return idColl;
	}

	public void setIdColl(int idColl) {
		this.idColl = idColl;
	}

	public int getContrat() {
		return contrat;
	}

	public void setContrat(int contrat) {
		this.contrat = contrat;
	}

	public static int[] getAllStatus() {
		return ALL_STATUS;
	}

	public static int[] getAllContrat() {
		return ALL_CONTRAT;
	}

	// === Collège === //

	public CollegeTO getCollege() {
		return college;
	}

	public void setCollege(CollegeTO college) {
		this.college = college;
	}

	public List<CollegeTO> getAllCollege() {
		return allCollege;
	}

	public void setAllCollege(List<CollegeTO> allCollege) {
		this.allCollege = allCollege;
	}

	public Integer getIdCollege() {
		return idCollege;
	}

	public void setIdCollege(Integer idCollege) {
		this.idCollege = idCollege;
	}

	// === Equipe === //

	public EquipeTO getEquipColl() {
		return equipColl;
	}

	public void setEquipColl(EquipeTO equipColl) {
		this.equipColl = equipColl;
	}

	public List<EquipeTO> getAllEquip() {
		return allEquip;
	}

	public void setAllEquip(List<EquipeTO> allEquip) {
		this.allEquip = allEquip;
	}

	public int getIdEqu() {
		return idEqu;
	}

	public void setIdEqu(int idEqu) {
		this.idEqu = idEqu;
	}

	// === Tous les collaborateurs === //
	public CollaborateurTO getCollaborateur() {
		return collaborateur;
	}

	public void setCollaborateur(CollaborateurTO collaborateur) {
		this.collaborateur = collaborateur;
	}

	public List<CollaborateurTO> getAllCollaborateur() {

		return allCollaborateur;
	}

	public void setAllCollaborateur(List<CollaborateurTO> allCollaborateur) {
		this.allCollaborateur = allCollaborateur;
	}

	public int[] getSelectedEquipe() {
		return selectedEquipe;
	}

	public void setSelectedEquipe(int[] selectedEquipe) {
		this.selectedEquipe = selectedEquipe;
	}

	public List<CollaborateurTO> getListRecap() {
		return listRecap;
	}

	public void setListRecap(List<CollaborateurTO> listRecap) {
		this.listRecap = listRecap;
	}

	public int[] getSelectedColl() {
		return selectedColl;
	}

	public void setSelectedColl(int[] selectedColl) {
		this.selectedColl = selectedColl;
	}

	// === Informations Générales === //

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNomColl() {
		return nomColl;
	}

	public void setNomColl(String nomColl) {
		this.nomColl = nomColl;
	}

	public String getPrenomColl() {
		return prenomColl;
	}

	public void setPrenomColl(String prenomColl) {
		this.prenomColl = prenomColl;
	}

	public String getPassword() {
		return password;
	}

	public String getConfirmPass() {
		return confirmPass;
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatutColl() {
		return statutColl;
	}

	public void setStatutColl(int statutColl) {
		this.statutColl = statutColl;
	}

	public String getSalaireColl() {
		return salaireColl;
	}

	public void setSalaireColl(String salaireColl) {
		this.salaireColl = salaireColl;
	}

	public String getChargeColl() {
		return chargeColl;
	}

	public void setChargeColl(String chargeColl) {
		this.chargeColl = chargeColl;
	}

	public boolean isEdit() {
		edit = false;
		return edit;
	}

	// === Jours de la semaine === //

	public String getStrNbHeureLundi() {
		return strNbHeureLundi;
	}

	public void setStrNbHeureLundi(String nbHeureLundi) {
		this.strNbHeureLundi = nbHeureLundi;
	}

	public String getStrNbHeureMardi() {
		return strNbHeureMardi;
	}

	public void setStrNbHeureMardi(String nbHeureMardi) {
		this.strNbHeureMardi = nbHeureMardi;
	}

	public String getStrNbHeureMercredi() {
		return strNbHeureMercredi;
	}

	public void setStrNbHeureMercredi(String nbHeureMercredi) {
		this.strNbHeureMercredi = nbHeureMercredi;
	}

	public String getStrNbHeureJeudi() {
		return strNbHeureJeudi;
	}

	public void setStrNbHeureJeudi(String nbHeureJeudi) {
		this.strNbHeureJeudi = nbHeureJeudi;
	}

	public String getStrNbHeureVendredi() {
		return strNbHeureVendredi;
	}

	public void setStrNbHeureVendredi(String nbHeureVendredi) {
		this.strNbHeureVendredi = nbHeureVendredi;
	}

	public String getStrNbHeureAnnuel() {
		return strNbHeureAnnuel;
	}

	public void setStrNbHeureAnnuel(String strNbHeureAnnuel) {
		this.strNbHeureAnnuel = strNbHeureAnnuel;
	}

	public String getStrNbHeureMens() {
		return strNbHeureMens;
	}

	public void setStrNbHeureMens(String strNbHeureMens) {
		this.strNbHeureMens = strNbHeureMens;
	}

	public String getStrNbHeureHeb() {
		return strNbHeureHeb;
	}

	public void setStrNbHeureHeb(String strNbHeureHeb) {
		this.strNbHeureHeb = strNbHeureHeb;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getNbHeureLundi() {
		return nbHeureLundi;
	}

	public void setNbHeureLundi(float nbHeureLundi) {
		this.nbHeureLundi = nbHeureLundi;
	}

	public float getNbHeureMardi() {
		return nbHeureMardi;
	}

	public void setNbHeureMardi(float nbHeureMardi) {
		this.nbHeureMardi = nbHeureMardi;
	}

	public float getNbHeureMercredi() {
		return nbHeureMercredi;
	}

	public void setNbHeureMercredi(float nbHeureMercredi) {
		this.nbHeureMercredi = nbHeureMercredi;
	}

	public float getNbHeureJeudi() {
		return nbHeureJeudi;
	}

	public void setNbHeureJeudi(float nbHeureJeudi) {
		this.nbHeureJeudi = nbHeureJeudi;
	}

	public float getNbHeureVendredi() {
		return nbHeureVendredi;
	}

	public void setNbHeureVendredi(float nbHeureVendredi) {
		this.nbHeureVendredi = nbHeureVendredi;
	}

	public float getNbHeureHeb() {
		return nbHeureHeb;
	}

	public void setNbHeureHeb(float nbHeureHeb) {
		this.nbHeureHeb = nbHeureHeb;
	}

	public float getNbHeureMens() {
		return nbHeureMens;
	}

	public void setNbHeureMens(float nbHeureMens) {
		this.nbHeureMens = nbHeureMens;
	}

	public float getNbHeureAnn() {
		return nbHeureAnn;
	}

	public void setNbHeureAnn(float nbHeureAnn) {
		this.nbHeureAnn = nbHeureAnn;
	}

	public float getSalaire() {
		return salaire;
	}

	public void setSalaire(float salaire) {
		this.salaire = salaire;
	}

	public float getCharge() {
		return charge;
	}

	public void setCharge(float charge) {
		this.charge = charge;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}
}
