package org.jeinnov.jeitime.api.service.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalculDate {
	public CalculDate() {

	}

	public Timestamp getDateStartWeek(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int day = cal.get(Calendar.DAY_OF_WEEK);
		Timestamp startWeek = null;
		int value = 0;
		switch (day) {
		case 2:
			value = 0;
			startWeek = constructDate(cal, value, true);
			break;
		case 3:
			value = -1;
			startWeek = constructDate(cal, value, true);
			break;
		case 4:
			value = -2;
			startWeek = constructDate(cal, value, true);
			break;
		case 5:
			value = -3;
			startWeek = constructDate(cal, value, true);
			break;
		case 6:
			value = -4;
			startWeek = constructDate(cal, value, true);
			break;
		case 7:
			value = -5;
			startWeek = constructDate(cal, value, true);
			break;
		case 1:
			value = -6;
			startWeek = constructDate(cal, value, true);
			break;
		}
		return startWeek;
	}

	public Timestamp getDateEndWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int day = cal.get(Calendar.DAY_OF_WEEK);
		Timestamp end = null;
		int value = 0;

		switch (day) {
		case 2:
			value = +4;
			end = constructDate(cal, value, false);
			break;
		case 3:
			value = +3;
			end = constructDate(cal, value, false);
			break;
		case 4:
			value = +2;
			end = constructDate(cal, value, false);
			break;
		case 5:
			value = +1;
			end = constructDate(cal, value, false);
			break;
		case 6:
			value = 0;
			end = constructDate(cal, value, false);
			break;
		case 7:
			value = -1;
			end = constructDate(cal, value, false);
			break;
		case 1:
			value = -2;
			end = constructDate(cal, value, false);
			break;
		}
		return end;
	}

	private Timestamp constructDate(Calendar cal, int value, boolean startWeek) {
		Timestamp dateWeek = null;

		int intDateWeek = cal.get(Calendar.DAY_OF_MONTH) + value;
		if (startWeek) {
			constructDateStartWeek(cal, intDateWeek);
		} else {
			constrctDateEndWeek(cal, intDateWeek);
		}

		Date startDate = cal.getTime();
		dateWeek = new Timestamp(startDate.getTime());

		return dateWeek;
	}

	private void constructDateStartWeek(Calendar cal, int intStartWeek) {
		cal.set(Calendar.DAY_OF_MONTH, intStartWeek);
		cal.set(Calendar.HOUR_OF_DAY, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		cal.set(Calendar.MILLISECOND, 00);
	}

	private void constrctDateEndWeek(Calendar cal, int intEndWeek) {
		cal.set(Calendar.DAY_OF_MONTH, intEndWeek);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
	}

	public Timestamp getDateStartMonth(int year, int month) {

		Calendar cal = GregorianCalendar.getInstance();
		if (year == 0) {
			cal.getTime();
			year = cal.get(Calendar.YEAR);
		}

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_MONTH, 01);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.HOUR_OF_DAY, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		cal.set(Calendar.MILLISECOND, 00);

		Date dateStartMonth = cal.getTime();
		Timestamp startMonth = new Timestamp(dateStartMonth.getTime());

		return startMonth;
	}

	public Timestamp getDateEndMonth(int year, int month) {
		Calendar cal = GregorianCalendar.getInstance();
		if (year == 0) {
			cal.getTime();
			year = cal.get(Calendar.YEAR);
		}

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.MILLISECOND, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.HOUR_OF_DAY, 00);
		Date dateEndMonth = cal.getTime();
		Timestamp endMonth = new Timestamp(dateEndMonth.getTime());

		return endMonth;
	}

	public Timestamp getDateStartYear(int year) {
		Calendar cal = GregorianCalendar.getInstance();
		if (year == 0) {
			cal.getTime();
			year = cal.get(Calendar.YEAR);
		}
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_MONTH, 01);
		cal.set(Calendar.MONTH, 0);
		Date dateStart = cal.getTime();
		Timestamp start = new Timestamp(dateStart.getTime());

		return start;
	}

	public Timestamp getDateEndYear(int year) {
		Calendar cal = GregorianCalendar.getInstance();
		if (year == 0) {
			cal.getTime();
			year = cal.get(Calendar.YEAR);
		}

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		cal.set(Calendar.MONTH, 11);
		Date dateEnd = cal.getTime();
		Timestamp end = new Timestamp(dateEnd.getTime());

		return end;
	}

	public String getMonth(int numMonth) {
		String monthName = null;

		switch (numMonth) {
		case 0:
			monthName = "Janvier";
			break;
		case 1:
			monthName = "Février";
			break;
		case 2:
			monthName = "Mars";
			break;
		case 3:
			monthName = "Avril";
			break;
		case 4:
			monthName = "Mai";
			break;
		case 5:
			monthName = "Juin";
			break;
		case 6:
			monthName = "Juillet";
			break;
		case 7:
			monthName = "Août";
			break;
		case 8:
			monthName = "Septembre";
			break;
		case 9:
			monthName = "Octobre";
			break;
		case 10:
			monthName = "Novembre";
			break;
		case 11:
			monthName = "Décembre";
			break;
		}

		return monthName;
	}
}
