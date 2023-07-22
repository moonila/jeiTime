package org.jeinnov.jeitime.ui.heure;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalculDateIntervalle {

	public String trouveMois(int numMois) {
		String mois = null;
		switch (numMois) {
		case 0:
			mois = "Janvier";
			break;
		case 1:
			mois = "Février";
			break;
		case 2:
			mois = "Mars";
			break;
		case 3:
			mois = "Avril";
			break;
		case 4:
			mois = "Mai";
			break;
		case 5:
			mois = "Juin";
			break;
		case 6:
			mois = "Juillet";
			break;
		case 7:
			mois = "Août";
			break;
		case 8:
			mois = "Septembre";
			break;
		case 9:
			mois = "Octobre";
			break;
		case 10:
			mois = "Novembre";
			break;
		case 11:
			mois = "Décembre";
		}
		return mois;
	}

	public String trouveJourSemaine(int numJourSem) {
		String dateSem = null;
		switch (numJourSem) {
		case 2:
			dateSem = "Lundi";
			break;
		case 3:
			dateSem = "Mardi";
			break;
		case 4:
			dateSem = "Mercredi";
			break;
		case 5:
			dateSem = "Jeudi";
			break;
		case 6:
			dateSem = "Vendredi";
			break;
		}
		return dateSem;
	}

	public String dateIntervalleMoins6(DateFormat dateFormat, Calendar cal,
			Date date) {
		int intLun;
		Date daLun;

		cal.setTime(date);
		intLun = cal.get(Calendar.DAY_OF_MONTH) - 6;
		cal.set(Calendar.DAY_OF_MONTH, intLun);
		daLun = cal.getTime();

		return dateFormat.format(daLun);
	}

	public String dateIntervalleMoins5(DateFormat dateFormat, Calendar cal,
			Date date) {
		int intDay;
		Date d;

		cal.setTime(date);
		intDay = cal.get(Calendar.DAY_OF_MONTH) - 5;
		cal.set(Calendar.DAY_OF_MONTH, intDay);
		d = cal.getTime();

		return dateFormat.format(d);
	}

	public String dateIntervalleMoins4(DateFormat dateFormat, Calendar cal,
			Date date) {
		int intDay;
		Date d;

		cal.setTime(date);
		intDay = cal.get(Calendar.DAY_OF_MONTH) - 4;
		cal.set(Calendar.DAY_OF_MONTH, intDay);
		d = cal.getTime();

		return dateFormat.format(d);
	}

	public String dateIntervalleMoins3(DateFormat dateFormat, Calendar cal,
			Date date) {
		int intDay;
		Date d;

		cal.setTime(date);
		intDay = cal.get(Calendar.DAY_OF_MONTH) - 3;
		cal.set(Calendar.DAY_OF_MONTH, intDay);
		d = cal.getTime();

		return dateFormat.format(d);
	}

	public String dateIntervalleMoins2(DateFormat dateFormat, Calendar cal,
			Date date) {
		int intDay;
		Date d;

		cal.setTime(date);
		intDay = cal.get(Calendar.DAY_OF_MONTH) - 2;
		cal.set(Calendar.DAY_OF_MONTH, intDay);
		d = cal.getTime();

		return dateFormat.format(d);
	}

	public String dateIntervalleMoins1(DateFormat dateFormat, Calendar cal,
			Date date) {
		int intDay;
		Date d;

		cal.setTime(date);
		intDay = cal.get(Calendar.DAY_OF_MONTH) - 1;
		cal.set(Calendar.DAY_OF_MONTH, intDay);
		d = cal.getTime();

		return dateFormat.format(d);
	}

	public String dateIntervallePlus1(DateFormat dateFormat, Calendar cal,
			Date date) {
		int intDay;
		Date d;

		cal.setTime(date);
		intDay = cal.get(Calendar.DAY_OF_MONTH) + 1;
		cal.set(Calendar.DAY_OF_MONTH, intDay);
		d = cal.getTime();
		return dateFormat.format(d);
	}

	public String dateIntervallePlus2(DateFormat dateFormat, Calendar cal,
			Date date) {
		int intDay;
		Date d;

		cal.setTime(date);
		intDay = cal.get(Calendar.DAY_OF_MONTH) + 2;
		cal.set(Calendar.DAY_OF_MONTH, intDay);
		d = cal.getTime();
		return dateFormat.format(d);
	}

	public String dateIntervallePlus3(DateFormat dateFormat, Calendar cal,
			Date date) {
		int intDay;
		Date d;

		cal.setTime(date);
		intDay = cal.get(Calendar.DAY_OF_MONTH) + 3;
		cal.set(Calendar.DAY_OF_MONTH, intDay);
		d = cal.getTime();
		return dateFormat.format(d);
	}

	public String dateIntervallePlus4(DateFormat dateFormat, Calendar cal,
			Date date) {
		int intDay;
		Date d;

		cal.setTime(date);
		intDay = cal.get(Calendar.DAY_OF_MONTH) + 4;
		cal.set(Calendar.DAY_OF_MONTH, intDay);
		d = cal.getTime();
		return dateFormat.format(d);
	}

}
