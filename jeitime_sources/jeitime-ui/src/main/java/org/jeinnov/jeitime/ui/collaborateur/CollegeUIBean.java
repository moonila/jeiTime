package org.jeinnov.jeitime.ui.collaborateur;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jeinnov.jeitime.api.service.collaborateur.AppartientCollegeManager;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurException;
import org.jeinnov.jeitime.api.service.collaborateur.CollegeManager;
import org.jeinnov.jeitime.api.to.collaborateur.CollegeTO;
import org.ow2.opensuit.core.error.IError;
import org.ow2.opensuit.core.error.NonLocalizedError;


public class CollegeUIBean {

	private final Logger logger = Logger.getLogger(this.getClass());

	private CollegeManager collegeManager = CollegeManager.getInstance();
	private ResultTransformerUIBean resultTransformer = new ResultTransformerUIBean();

	private int idCollegeUI;
	private String nomCollegeUI;
	private String nbHeureLun;
	private String nbHeureMar;
	private String nbHeureMerc;
	private String nbHeureJeu;
	private String nbHeureVend;

	private float nbAnn = 0;
	private float nbLun = 0;
	private float nbMar = 0;
	private float nbMerc = 0;
	private float nbJeu = 0;
	private float nbVen = 0;
	private float nbMens = 0;
	private float nbHeb = 0;

	private String nbHeureHebdoCollegeUI;

	private String nbHeureMensCollegeUI;
	private String nbHeureAnnCollegeUI;
	private int nbJourCongeAnnCollegeUI;
	private int nbJourRttAnnCollegeUI;
	private boolean alertMoisUI;
	private boolean alertSemUI;
	private boolean alertAnnUI;

	private String listSaisie = null;

	private CollegeTO selected;
	private List<CollegeTO> allCollege;
	private int[] selectCollege;

	private boolean visible = false;

	// ===========================================================
	// === Actions Methods
	// ===========================================================
	public void loadAll() {
		allCollege = new ArrayList<CollegeTO>();
		allCollege = collegeManager.getAll();

		nbHeureLun = "0";
		nbHeureMar = "0";
		nbHeureMerc = "0";
		nbHeureJeu = "0";
		nbHeureVend = "0";
		nbHeureMensCollegeUI = "0";
		nbHeureAnnCollegeUI = "0";
		listSaisie = null;

	}

	public void select(HttpServletRequest iRequest) throws IError {
		selected = null;
		int id = Integer.parseInt(iRequest.getParameter("ID"));
		try {
			selected = collegeManager.get(id);
		} catch (CollaborateurException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Aucun college n'est sélectionné ou le college sélectionné n'existe plus dans la base",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		idCollegeUI = selected.getIdCollege();
		nomCollegeUI = selected.getNomCollege();

		floatToStringCollege();

		nbJourCongeAnnCollegeUI = selected.getNbJourCongeAnnCollege();
		nbJourRttAnnCollegeUI = selected.getNbJourRttAnnCollege();

		alertMoisUI = selected.isAlertMois();
		alertSemUI = selected.isAlertJour();
		alertAnnUI = selected.isAlertAnn();
		listSaisie = selected.getListSaisie();
		visible = true;

	}

	public void create() throws IError {
		NumberFormat nf = NumberFormat.getInstance();
		createDataWeekNumber(nf);

		createDataYearMonthNumber(nf);

		nbHeb = nbLun + nbMar + nbMerc + nbJeu + nbVen;

		CollegeTO college = new CollegeTO(idCollegeUI, nomCollegeUI, nbLun,
				nbMar, nbMerc, nbJeu, nbVen, nbHeb, nbMens, nbAnn,
				nbJourCongeAnnCollegeUI, nbJourRttAnnCollegeUI, alertMoisUI,
				alertSemUI, alertAnnUI, listSaisie);
		try {
			collegeManager.saveOrUpdate(college);
		} catch (CollaborateurException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue, le collège n'a pas pu être enregistré dans la base, "
							+ "un collège avec ce même nom existe peut-être déjà dans la base",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		refresh();
	}

	public void update() throws IError {
		NumberFormat nf = NumberFormat.getInstance();

		createDataWeekNumber(nf);

		createDataYearMonthNumber(nf);

		nbHeb = nbLun + nbMar + nbMerc + nbJeu + nbVen;

		if (listSaisie.equals("")) {
			listSaisie = null;
		}

		CollegeTO college = new CollegeTO(idCollegeUI, nomCollegeUI, nbLun,
				nbMar, nbMerc, nbJeu, nbVen, nbHeb, nbMens, nbAnn,
				nbJourCongeAnnCollegeUI, nbJourRttAnnCollegeUI, alertMoisUI,
				alertSemUI, alertAnnUI, listSaisie);

		try {
			collegeManager.saveOrUpdate(college);
		} catch (CollaborateurException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue, le collège n'a pas pu être enregistré dans la base. "
							+ "un collège avec ce même nom existe peut-être déjà dans la base",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		refresh();
	}

	public void delete(HttpServletRequest iRequest) throws IError,
			CollaborateurException {
		int id = Integer.parseInt(iRequest.getParameter("ID"));

		AppartientCollegeManager.getInstance().deleteByIdCollege(id);
		try {
			collegeManager.delete(id);
		} catch (CollaborateurException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Aucun college n'est sélectionné ou le college sélectionné n'existe plus dans la base",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		refresh();
	}

	public void cancel() {
		selected = null;
		visible = false;
		nomCollegeUI = null;
	}

	public void refresh() {
		idCollegeUI = 0;
		nomCollegeUI = null;

		nbJourCongeAnnCollegeUI = 0;
		nbJourRttAnnCollegeUI = 0;

		nbHeureLun = "0";
		nbHeureMar = "0";
		nbHeureMerc = "0";
		nbHeureJeu = "0";
		nbHeureVend = "0";
		nbHeureMensCollegeUI = "0";
		nbHeureAnnCollegeUI = "0";

		alertAnnUI = false;
		alertMoisUI = false;
		alertSemUI = false;

		listSaisie = null;

		selected = null;

		visible = false;

		allCollege = new ArrayList<CollegeTO>();
		allCollege = collegeManager.getAll();
	}

	private void floatToStringCollege() {

		nbHeureLun = resultTransformer.floatToStringLundi(selected);
		nbHeureMar = resultTransformer.floatToStringMardi(selected);
		nbHeureMerc = resultTransformer.floatToStringMercredi(selected);
		nbHeureJeu = resultTransformer.floatToStringJeudi(selected);
		nbHeureVend = resultTransformer.floatToStringVendredi(selected);
		nbHeureAnnCollegeUI = resultTransformer.floatToStringAnnuel(selected);
		nbHeureMensCollegeUI = resultTransformer.floatToStringMensuel(selected);
	}

	private void createDataYearMonthNumber(NumberFormat nf) {
		try {
			nbAnn = Float.parseFloat(nbHeureAnnCollegeUI);
		} catch (Exception e) {
			try {
				nbAnn = nf.parse(nbHeureAnnCollegeUI).floatValue();
			} catch (ParseException pe) {
				logger.error(e.getMessage(), pe);
			}
		}
		try {
			nbMens = Float.parseFloat(nbHeureMensCollegeUI);
		} catch (Exception e) {
			try {
				nbMens = nf.parse(nbHeureMensCollegeUI).floatValue();
			} catch (ParseException pe) {
				logger.error(e.getMessage(), pe);
			}
		}
	}

	private void createDataWeekNumber(NumberFormat nf) {
		try {
			nbLun = Float.parseFloat(nbHeureLun);
		} catch (Exception e) {
			try {
				nbLun = nf.parse(nbHeureLun).floatValue();
			} catch (ParseException pe) {
				logger.error(e.getMessage(), pe);
			}
		}

		try {
			nbMar = Float.parseFloat(nbHeureMar);
		} catch (Exception e) {
			try {
				nbMar = nf.parse(nbHeureMar).floatValue();
			} catch (ParseException pe) {
				logger.error(e.getMessage(), pe);
			}
		}
		try {
			nbMerc = Float.parseFloat(nbHeureMerc);
		} catch (Exception e) {
			try {
				nbMerc = nf.parse(nbHeureMerc).floatValue();
			} catch (ParseException pe) {
				logger.error(e.getMessage(), pe);
			}
		}
		try {
			nbJeu = Float.parseFloat(nbHeureJeu);
		} catch (Exception e) {
			try {
				nbJeu = nf.parse(nbHeureJeu).floatValue();
			} catch (ParseException pe) {
				logger.error(e.getMessage(), pe);
			}
		}
		try {
			nbVen = Float.parseFloat(nbHeureVend);
		} catch (Exception e) {
			try {
				nbVen = nf.parse(nbHeureVend).floatValue();
			} catch (ParseException pe) {
				logger.error(e.getMessage(), pe);
			}
		}
	}

	// ===========================================================
	// === Getter / Setters
	// ===========================================================

	public CollegeTO getSelected() {
		return selected;
	}

	public int[] getSelectCollege() {
		return selectCollege;
	}

	public void setSelectCollege(int[] selectCollege) {
		this.selectCollege = selectCollege;
	}

	public List<CollegeTO> getAllCollege() {
		return allCollege;
	}

	public void setAllCollege(List<CollegeTO> allCollege) {
		this.allCollege = allCollege;
	}

	public int getIdCollegeUI() {
		return idCollegeUI;
	}

	public void setIdCollegeUI(int idCollegeUI) {
		this.idCollegeUI = idCollegeUI;
	}

	public String getNomCollegeUI() {
		return nomCollegeUI;
	}

	public void setNomCollegeUI(String nomCollegeUI) {
		this.nomCollegeUI = nomCollegeUI;
	}

	public String getNbHeureLun() {
		return nbHeureLun;
	}

	public void setNbHeureLun(String nbHeureLun) {
		this.nbHeureLun = nbHeureLun;
	}

	public String getNbHeureMar() {
		return nbHeureMar;
	}

	public void setNbHeureMar(String nbHeureMar) {
		this.nbHeureMar = nbHeureMar;
	}

	public String getNbHeureMerc() {
		return nbHeureMerc;
	}

	public void setNbHeureMerc(String nbHeureMerc) {
		this.nbHeureMerc = nbHeureMerc;
	}

	public String getNbHeureJeu() {
		return nbHeureJeu;
	}

	public void setNbHeureJeu(String nbHeureJeu) {
		this.nbHeureJeu = nbHeureJeu;
	}

	public String getNbHeureVend() {
		return nbHeureVend;
	}

	public void setNbHeureVend(String nbHeureVend) {
		this.nbHeureVend = nbHeureVend;
	}

	public String getNbHeureMensCollegeUI() {
		return nbHeureMensCollegeUI;
	}

	public void setNbHeureMensCollegeUI(String nbHeureMensCollegeUI) {
		this.nbHeureMensCollegeUI = nbHeureMensCollegeUI;
	}

	public String getNbHeureAnnCollegeUI() {
		return nbHeureAnnCollegeUI;
	}

	public void setNbHeureAnnCollegeUI(String nbHeureAnnCollegeUI) {
		this.nbHeureAnnCollegeUI = nbHeureAnnCollegeUI;
	}

	public int getNbJourCongeAnnCollegeUI() {
		return nbJourCongeAnnCollegeUI;
	}

	public void setNbJourCongeAnnCollegeUI(int nbJourCongeAnnCollegeUI) {
		this.nbJourCongeAnnCollegeUI = nbJourCongeAnnCollegeUI;
	}

	public int getNbJourRttAnnCollegeUI() {
		return nbJourRttAnnCollegeUI;
	}

	public void setNbJourRttAnnCollegeUI(int nbJourRttAnnCollegeUI) {
		this.nbJourRttAnnCollegeUI = nbJourRttAnnCollegeUI;
	}

	public boolean isAlertMoisUI() {
		return alertMoisUI;
	}

	public void setAlertMoisUI(boolean alertMoisUI) {
		this.alertMoisUI = alertMoisUI;
	}

	public boolean isAlertSemUI() {
		return alertSemUI;
	}

	public void setAlertSemUI(boolean alertSemUI) {
		this.alertSemUI = alertSemUI;
	}

	public boolean isAlertAnnUI() {
		return alertAnnUI;
	}

	public void setAlertAnnUI(boolean alertAnnUI) {
		this.alertAnnUI = alertAnnUI;
	}

	public String getListSaisie() {
		return listSaisie;
	}

	public void setListSaisie(String listSaisie) {
		this.listSaisie = listSaisie;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getNbHeureHebdoCollegeUI() {
		return nbHeureHebdoCollegeUI;
	}

	public void setNbHeureHebdoCollegeUI(String nbHeureHebdoCollegeUI) {
		this.nbHeureHebdoCollegeUI = nbHeureHebdoCollegeUI;
	}

}
