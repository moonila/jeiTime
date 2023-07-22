package org.jeinnov.jeitime.ui.alerte;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


import org.jeinnov.jeitime.api.service.alerte.AlerteManager;
import org.jeinnov.jeitime.api.to.alerte.AlertesTO;
import org.ow2.opensuit.core.validation.LocalizedValidationError;
import org.ow2.opensuit.core.validation.ValidationErrors;

public class AlerteUIBean {
	private AlerteManager alerteManager = AlerteManager.getInstance();

	private int annee;
	private Date dateDeb;
	private Date dateFin;

	private List<AlertesTO> allAlertes = new ArrayList<AlertesTO>();

	private static int[] ALL_MOIS = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
	private int mois;

	private boolean visible = false;

	public void load() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.getTime();
		annee = cal.get(Calendar.YEAR);
	}

	public void afficheAnnee() {
		allAlertes = alerteManager.getAlertForYear(annee);
		visible = true;
	}

	public void afficheMois() {
		allAlertes = new ArrayList<AlertesTO>();
		allAlertes = alerteManager.getAlertForMonth(annee, mois);
		visible = true;
	}

	public void afficheSem() {
		allAlertes = new ArrayList<AlertesTO>();
		allAlertes = alerteManager.getAlertForWeek(dateDeb);
		visible = true;
	}

	public void validate() throws ValidationErrors {
		ValidationErrors errors = new ValidationErrors();
		if (dateDeb != null && dateFin != null && dateFin.before(dateDeb)) {
			errors.addItemError("alerteBean.dateFin",
					new LocalizedValidationError("validation.date"));
		}
		if (errors.hasErrors()) {
			throw errors;
		}

	}

	public static int[] getAllMois() {
		return ALL_MOIS;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public Date getDateDeb() {
		return dateDeb;
	}

	public void setDateDeb(Date dateDeb) {
		this.dateDeb = dateDeb;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public List<AlertesTO> getAllAlertes() {
		return allAlertes;
	}

	public void setAllAlertes(List<AlertesTO> allAlertes) {
		this.allAlertes = allAlertes;
	}

	public int getMois() {
		return mois;
	}

	public void setMois(int mois) {
		this.mois = mois;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
