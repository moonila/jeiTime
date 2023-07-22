package org.jeinnov.jeitime.ui.utils;

import java.util.List;
import java.util.Calendar;
import java.util.Date;

import org.jeinnov.jeitime.api.to.bilan.JourMois;


public class CalculJourDate {

	public CalculJourDate() {
		super();

	}

	public void nbrJour(int annee, int mois, List<JourMois> jour) {
		if (mois == 0 || mois == 2 || mois == 4 || mois == 6 || mois == 7
				|| mois == 9 || mois == 11) {
			for (int i = 1; i < 32; i++) {
				jour.add(new JourMois(i));
			}
		}
		if (mois == 3 || mois == 5 || mois == 8 || mois == 10) {
			for (int i = 1; i < 31; i++) {
				jour.add(new JourMois(i));
			}
		}
		if (mois == 1) {
			if (annee == 2012 || annee == 2016 || annee == 2020) {
				for (int i = 1; i < 30; i++) {
					jour.add(new JourMois(i));
				}
			} else {
				for (int i = 1; i < 29; i++) {
					jour.add(new JourMois(i));
				}
			}
		}
	}

	public Date calculDateFin(Calendar cal, int annee, int mois) {
		cal.set(Calendar.YEAR, annee);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		cal.set(Calendar.MONTH, mois);
		cal.set(Calendar.MILLISECOND, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.HOUR_OF_DAY, 00);
		return cal.getTime();
	}

	public Date calculDateDebut(Calendar cal, int annee, int mois) {
		cal.set(Calendar.YEAR, annee);
		cal.set(Calendar.DAY_OF_MONTH, 01);
		cal.set(Calendar.MONTH, mois);
		cal.set(Calendar.HOUR_OF_DAY, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		cal.set(Calendar.MILLISECOND, 00);

		return cal.getTime();
	}

	public Date calculDateMois30Fin(Calendar cal, int annee, int mois) {
		cal.set(Calendar.YEAR, annee);
		cal.set(Calendar.DAY_OF_MONTH, 30);
		cal.set(Calendar.MONTH, mois);
		cal.set(Calendar.MILLISECOND, 00);
		cal.set(Calendar.SECOND, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.HOUR_OF_DAY, 00);

		return cal.getTime();
	}

	public Date calculDateMois31Fin(Calendar cal, int annee, int mois) {
		cal.set(Calendar.YEAR, annee);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		cal.set(Calendar.MONTH, mois);
		cal.set(Calendar.MILLISECOND, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.HOUR_OF_DAY, 00);

		return cal.getTime();
	}

	public Date calculDateMoisDebut(Calendar cal, int annee, int mois) {
		cal.set(Calendar.YEAR, annee);
		cal.set(Calendar.DAY_OF_MONTH, 01);
		cal.set(Calendar.MONTH, mois);
		cal.set(Calendar.HOUR_OF_DAY, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		cal.set(Calendar.MILLISECOND, 00);

		return cal.getTime();
	}

	public Date calculDateFevrierFin28(Calendar cal, int annee, int mois) {
		cal.set(Calendar.YEAR, annee);
		cal.set(Calendar.DAY_OF_MONTH, 28);
		cal.set(Calendar.MONTH, mois);
		cal.set(Calendar.MILLISECOND, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.HOUR_OF_DAY, 00);

		return cal.getTime();
	}

	public Date calculDateFevrierFin29(Calendar cal, int annee, int mois) {
		cal.set(Calendar.YEAR, annee);
		cal.set(Calendar.DAY_OF_MONTH, 29);
		cal.set(Calendar.MONTH, mois);
		cal.set(Calendar.MILLISECOND, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.HOUR_OF_DAY, 00);

		return cal.getTime();
	}

	public Date calculDateFevrierDebut(Calendar cal, int annee, int mois) {
		cal.set(Calendar.YEAR, annee);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, mois);
		cal.set(Calendar.HOUR_OF_DAY, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		cal.set(Calendar.MILLISECOND, 00);

		return cal.getTime();
	}

}
