package org.jeinnov.jeitime.api.service.alerte;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.service.collaborateur.ResultTransformerCollaborateur;
import org.jeinnov.jeitime.api.service.utils.CalculDate;
import org.jeinnov.jeitime.api.to.alerte.AlertesTO;
import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.persistence.bo.projet.LienTachUtilP;
import org.jeinnov.jeitime.persistence.dao.alerte.AlerteDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


public class AlerteManager {
	private final Logger logger = Logger.getLogger(this.getClass());
	private AlerteDAO alerteDAO = AlerteDAO.getInstance();
	private CalculDate calculDate = new CalculDate();

	private static AlerteManager manager;

	public AlerteManager() {

	}

	public static AlerteManager getInstance() {
		if (manager == null) {
			manager = new AlerteManager();
		}
		return manager;
	}

	public List<AlertesTO> getAlertForYear(int year) {

		List<AlertesTO> listAlert = new ArrayList<AlertesTO>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			Timestamp startDate = calculDate.getDateStartYear(year);
			Timestamp endDate = calculDate.getDateEndYear(year);

			List<LienTachUtilP> listLTU = alerteDAO
					.getAllByStartDateAndEndDate(startDate, endDate);

			String indicDate = String.valueOf(year);

			listAlert = getAlertesTO(listLTU, indicDate, 1);

			tx.commit();

		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return listAlert;
	}

	public List<AlertesTO> getAlertForMonth(int year, int month) {
		List<LienTachUtilP> listLTU = new ArrayList<LienTachUtilP>();
		CalculDate calculteDate = new CalculDate();
		List<AlertesTO> listAlert = new ArrayList<AlertesTO>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			Timestamp startDate = calculDate.getDateStartMonth(year, month);
			Timestamp endDate = calculDate.getDateEndMonth(year, month);
			listLTU = alerteDAO.getAllByStartDateAndEndDate(startDate, endDate);
			String indicDate = calculteDate.getMonth(month);

			listAlert = getAlertesTO(listLTU, indicDate, 2);

			tx.commit();

		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return listAlert;
	}

	public List<AlertesTO> getAlertForWeek(Date date) {

		List<LienTachUtilP> listLTU = new ArrayList<LienTachUtilP>();
		List<AlertesTO> listAlert = new ArrayList<AlertesTO>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			Timestamp startDate = calculDate.getDateStartWeek(date);
			Timestamp endDate = calculDate.getDateEndWeek(date);
			listLTU = alerteDAO.getAllByStartDateAndEndDate(startDate, endDate);

			Locale locale = Locale.FRANCE;
			DateFormat dateFormat = DateFormat.getDateInstance(
					DateFormat.SHORT, locale);

			String dateFe = dateFormat.format(endDate);
			String dateDe = dateFormat.format(startDate);

			String indicDate = "du " + dateDe + " au " + dateFe;

			listAlert = getAlertesTO(listLTU, indicDate, 3);

			tx.commit();

		}catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return listAlert;
	}

	private List<AlertesTO> getAlertesTO(List<LienTachUtilP> listLTU,
			String indicDate, int alerteType) {
		ResultTransformerCollaborateur resultTransformer = new ResultTransformerCollaborateur();

		List<AlertesTO> listAlert = new ArrayList<AlertesTO>();

		float nbheureSaisie = 0;

		for (int i = 0; i < listLTU.size(); i++) {
			nbheureSaisie = (Float) listLTU.get(i).getNbHeure();
			int j = i + 1;

			while (j < listLTU.size()) {
				if (listLTU.get(i).getCollaborateur().getIdColl() == listLTU
						.get(j).getCollaborateur().getIdColl()) {
					float nbheureS = (Float) listLTU.get(j).getNbHeure();
					nbheureSaisie = nbheureSaisie + nbheureS;
					listLTU.remove(j);
				} else {
					j++;
				}
			}

			CollaborateurTO collab = resultTransformer
					.toCollaborateurTO(listLTU.get(i).getCollaborateur());

			float nbCollege = 0;
			if (alerteType == 1) {
				nbCollege = (Float) collab.getNbHeureAnn();

			} else if (alerteType == 2) {
				nbCollege = (Float) collab.getNbHeureMens();
			} else {
				nbCollege = (Float) collab.getNbHeureHeb();
			}

			float difference = 0;

			if (nbheureSaisie > nbCollege) {

				difference = nbheureSaisie - nbCollege;

				AlertesTO alerte = new AlertesTO();
				alerte.setIndicDate(indicDate);
				alerte.setCollab(collab);
				alerte.setDifference(difference);
				alerte.setNbheureCollege(nbCollege);
				alerte.setNbheureSaisie(nbheureSaisie);

				listAlert.add(alerte);
			}
		}

		return listAlert;
	}
}
