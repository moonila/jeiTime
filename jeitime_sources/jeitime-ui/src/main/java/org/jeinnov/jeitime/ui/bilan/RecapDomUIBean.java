package org.jeinnov.jeitime.ui.bilan;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.jeinnov.jeitime.api.service.bilan.BilanException;
import org.jeinnov.jeitime.api.service.bilan.DomaineBilanManager;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurManager;
import org.jeinnov.jeitime.api.service.projet.DomaineManager;
import org.jeinnov.jeitime.api.to.bilan.RecapProjetTO;
import org.jeinnov.jeitime.api.to.bilan.SousTotal;
import org.jeinnov.jeitime.api.to.bilan.Total;
import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.projet.DomaineTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.ow2.opensuit.core.error.IError;
import org.ow2.opensuit.core.error.LocalizedError;
import org.ow2.opensuit.core.error.NonLocalizedError;
import org.ow2.opensuit.core.validation.LocalizedValidationError;
import org.ow2.opensuit.core.validation.ValidationErrors;


public class RecapDomUIBean {
	private Date dateDeb;
	private String dateD;
	private Date dateFin;
	private String dateF;

	private DomaineBilanManager domaineBilanManager = DomaineBilanManager
			.getInstance();
	private int[] selectedDom;
	private List<DomaineTO> allDomaine = new ArrayList<DomaineTO>();

	private List<CollaborateurTO> collaborateurs = new ArrayList<CollaborateurTO>();

	private List<Object> items = new ArrayList<Object>();

	private List<RecapProjetTO> listRecap = new ArrayList<RecapProjetTO>();

	private boolean voirTabl = false;
	private boolean voirDom;
	private boolean voirDate;

	public void load() {
		allDomaine = DomaineManager.getInstance().getAll();
		voirDate = true;
	}

	public void envoyer() {
		voirTabl = false;
		voirDom = true;
	}

	public void reset() {
		voirDate = true;
		voirTabl = false;
		voirDom = true;
		selectedDom = null;

	}

	public void voirTableauDomaine() throws IError {
		Locale locale = Locale.FRANCE;
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT,
				locale);
		dateD = dateFormat.format(dateDeb);
		dateF = dateFormat.format(dateFin);

		collaborateurs = CollaborateurManager.getInstance().getAll();
		items = new ArrayList<Object>();
		try {
			items = domaineBilanManager.listTacheDomaine(selectedDom);
		} catch (BilanException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention, aucun domaine n'est associé", e.getMessage(), e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		try {
			listRecap = domaineBilanManager.creerListRecapProjetDomaine(
					selectedDom, dateDeb, dateFin);
		} catch (BilanException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention, aucun domaine n'est associé", e.getMessage(), e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		voirTabl = true;
		voirDom = false;
		voirDate = false;
	}

	public String getHtmlName(Object item) {
		if (item instanceof String) {
			// this is the project name
			return "\\html\\<span class=titleTable>" + item + "</span>";
		} else if (item instanceof Integer) {
			return "Total du Domaine";
		} else if (item instanceof ProjetTO) {
			return "\\html\\<span class=sousTitleTable>"
					+ ((ProjetTO) item).getNomProjet() + "</span>";
		} else if (item instanceof SousTotal) {
			return ((SousTotal) item).getNomSousTotal();
		} else if (item instanceof Total) {
			return "Total du Projet";
		}
		// else: this is a task
		else {
			return ((TacheTO) item).getNomtache().getNomTache();
		}
	}

	public String getValue(Object item, CollaborateurTO collab) {

		if (item instanceof String) {
			// this is the project name
			return null;
		} else if (item instanceof ProjetTO) {
			return null;
		} else if (item instanceof Integer) {
			return itemsInstanceOfInteger(item, collab);
		} else if (item instanceof SousTotal) {
			return itemsInstanceOfSousTotal(item, collab);
		} else if (item instanceof Total) {
			return itemsInstanceOfTotal(item, collab);
		} else {
			return itemsInstanceOfTacheTO(item, collab);
		}
	}

	public void validate() throws ValidationErrors {
		ValidationErrors errors = new ValidationErrors();
		if (dateDeb != null && dateFin != null && dateFin.before(dateDeb)) {
			errors.addItemError("recapBeanDom.dateFin",
					new LocalizedValidationError("validation.date"));
		}
		if (errors.hasErrors()) {
			throw errors;
		}
	}
	
	private String itemsInstanceOfInteger(Object item, CollaborateurTO collab) {
		String nb = "0";
		int idP = (Integer) item;
		double nbht = 0;

		for (int i = 0; i < listRecap.size(); i++) {

			if (listRecap.get(i).getCollab().getIdColl() == collab.getIdColl()
					&& listRecap.get(i).getNomProjet().getDomaine()
							.getIdDomaine() == idP) {
				nbht = listRecap.get(i).getNbheure() + nbht;

				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				df.setMinimumFractionDigits(2);
				df.setDecimalSeparatorAlwaysShown(true);

				nb = df.format(nbht);
			}
		}
		return nb;
	}

	private String itemsInstanceOfSousTotal(Object item, CollaborateurTO collab) {
		String nb = "0";
		int idP = ((SousTotal) item).getIdProjet();
		boolean rd = ((SousTotal) item).isRd();
		double nbht = 0;
		for (int i = 0; i < listRecap.size(); i++) {
			if (listRecap.get(i).getCollab().getIdColl() == collab.getIdColl()
					&& listRecap.get(i).getNomProjet().getIdProjet() == idP
					&& listRecap.get(i).getTache().isEligible() == rd) {

				nbht = listRecap.get(i).getNbheure() + nbht;

				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				df.setMinimumFractionDigits(2);
				df.setDecimalSeparatorAlwaysShown(true);

				nb = df.format(nbht);
			}
		}
		return nb;
	}

	private String itemsInstanceOfTotal(Object item, CollaborateurTO collab) {
		String nb = "0";
		int idP = ((Total) item).getIdProjet();

		double nbht = 0;
		for (int i = 0; i < listRecap.size(); i++) {

			if (listRecap.get(i).getCollab().getIdColl() == collab.getIdColl()
					&& listRecap.get(i).getNomProjet().getIdProjet() == idP) {

				nbht = listRecap.get(i).getNbheure() + nbht;

				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				df.setMinimumFractionDigits(2);
				df.setDecimalSeparatorAlwaysShown(true);

				nb = df.format(nbht);
			}
		}
		return nb;
	}

	private String itemsInstanceOfTacheTO(Object item, CollaborateurTO collab) {
		double nbh;
		int idT = ((TacheTO) item).getIdTache();
		int idCo = collab.getIdColl();
		String nb = "";
		for (int i = 0; i < listRecap.size(); i++) {
			if ((idT == listRecap.get(i).getTache().getIdTache())
					&& (idCo == listRecap.get(i).getCollab().getIdColl())) {

				nbh = listRecap.get(i).getNbheure();
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				df.setMinimumFractionDigits(2);
				df.setDecimalSeparatorAlwaysShown(true);

				nb = df.format(nbh);
			}
		}
		return nb;
	}

	public String getTotal(Object item) {
		double nbh = 0;
		if (item instanceof String) {
			// this is the project name
			return null;
		} else if (item instanceof ProjetTO) {
			// this is the project name
			return null;
		} else if (item instanceof Integer) {
			return totalInstanceOfInteger(item, nbh);
		} else if (item instanceof SousTotal) {
			// Insertion du total de la ligne sous total
			return totalInstanceOfSousTotal(item);
		} else if (item instanceof Total) {
			return totalInstanceOfTotal(item);
		} else {
			return totalInstanceOfTacheTO(item, nbh);
		}
		// return total;

	}

	private String totalInstanceOfInteger(Object item, double nbh) {
		for (int i = 0; i < listRecap.size(); i++) {
			int idP = (Integer) item;
			if (idP == listRecap.get(i).getNomProjet().getDomaine()
					.getIdDomaine()) {
				nbh = listRecap.get(i).getNbheure() + nbh;
			}
		}
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		df.setDecimalSeparatorAlwaysShown(true);

		return df.format(nbh);
	}

	private String totalInstanceOfSousTotal(Object item) {
		String totalTmp = null;
		int idP = ((SousTotal) item).getIdProjet();
		boolean rd = ((SousTotal) item).isRd();
		double nbht = 0;
		for (int i = 0; i < listRecap.size(); i++) {
			if (listRecap.get(i).getNomProjet().getIdProjet() == idP
					&& listRecap.get(i).getTache().isEligible() == rd) {

				nbht = listRecap.get(i).getNbheure() + nbht;

				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				df.setMinimumFractionDigits(2);
				df.setDecimalSeparatorAlwaysShown(true);

				totalTmp = df.format(nbht);
			}
		}
		return totalTmp;
	}

	private String totalInstanceOfTotal(Object item) {
		String totalTmp = null;
		int idP = ((Total) item).getIdProjet();

		double nbht = 0;
		for (int i = 0; i < listRecap.size(); i++) {
			if (listRecap.get(i).getNomProjet().getIdProjet() == idP) {

				nbht = listRecap.get(i).getNbheure() + nbht;
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				df.setMinimumFractionDigits(2);
				df.setDecimalSeparatorAlwaysShown(true);

				totalTmp = df.format(nbht);
			}
		}
		return totalTmp;
	}

	private String totalInstanceOfTacheTO(Object item, double nbh) {
		int idT = ((TacheTO) item).getIdTache();

		for (int i = 0; i < listRecap.size(); i++) {
			if ((idT == (listRecap.get(i).getTache().getIdTache()))) {
				nbh = listRecap.get(i).getNbheure() + nbh;
			}
		}
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		df.setDecimalSeparatorAlwaysShown(true);
		return df.format(nbh);
	}

	public void selectAllDom() {
		selectedDom = new int[allDomaine.size()];
		for (int i = 0; i < allDomaine.size(); i++) {
			selectedDom[i] = allDomaine.get(i).getIdDomaine();
		}
	}

	public void deSelectAllDom() {
		selectedDom = null;
	}

	public void download(HttpServletResponse response) throws LocalizedError,
			IOException {
		response.setContentType("text/csv");
		PrintWriter output = response.getWriter();

		try {
			for (Object o : items) {
				output.print(o + ";");
			}

		} catch (Exception e) {

			throw new LocalizedError("database_technical", new Object[] { e
					.getMessage() });
		}

		output.flush();
		output.close();
	}

	/******
	 * 
	 * Getters and Setters
	 * 
	 */

	public Date getDateDeb() {
		return dateDeb;
	}

	public void setDateDeb(Date dateDeb) {
		this.dateDeb = dateDeb;
	}

	public String getDateD() {
		return dateD;
	}

	public void setDateD(String dateD) {
		this.dateD = dateD;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public String getDateF() {
		return dateF;
	}

	public void setDateF(String dateF) {
		this.dateF = dateF;
	}

	public List<CollaborateurTO> getCollaborateurs() {
		return collaborateurs;
	}

	public void setCollaborateurs(List<CollaborateurTO> collaborateurs) {
		this.collaborateurs = collaborateurs;
	}

	public List<Object> getItems() {
		return items;
	}

	public void setItems(List<Object> items) {
		this.items = items;
	}

	public List<RecapProjetTO> getListRecap() {
		return listRecap;
	}

	public void setListRecap(List<RecapProjetTO> listRecap) {
		this.listRecap = listRecap;
	}

	public boolean isVoirTabl() {
		return voirTabl;
	}

	public void setVoirTabl(boolean voirTabl) {
		this.voirTabl = voirTabl;
	}

	public boolean isVoirDate() {
		return voirDate;
	}

	public void setVoirDate(boolean voirDate) {
		this.voirDate = voirDate;
	}

	public int[] getSelectedDom() {
		return selectedDom;
	}

	public void setSelectedDom(int[] selectedDom) {
		this.selectedDom = selectedDom;
	}

	public List<DomaineTO> getAllDomaine() {
		return allDomaine;
	}

	public void setAllDomaine(List<DomaineTO> allDomaine) {
		this.allDomaine = allDomaine;
	}

	public boolean isVoirDom() {
		return voirDom;
	}

	public void setVoirDom(boolean voirDom) {
		this.voirDom = voirDom;
	}

}
