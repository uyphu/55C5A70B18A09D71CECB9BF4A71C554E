package com.ltu.yealtube.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ltu.yealtube.constants.Constant;

public class AppUtils {
	
	/** The log. */
	private static Logger log = Logger.getLogger(AppUtils.class);

	
	/**
	 * Gets the current time.
	 *
	 * @return the current time
	 */
	public static String getCurrentTime() {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS Z");
		return DATE_FORMAT.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * Gets the current date.
	 *
	 * @return the current date
	 */
	public static Date getCurrentDate() {
		return Calendar.getInstance().getTime();
	}
	
	/**
	 * To date.
	 *
	 * @param date the date
	 * @return the date
	 */
	public static Date toDate(String date) {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(Constant.LONG_DATE_FORMAT);
		try {
			return DATE_FORMAT.parse(date);
		} catch (ParseException e) {
			log.error(e.getMessage(), e.getCause());
			return null;
		}
	}
	
	/**
	 * To string.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static String toString(Date date) {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(Constant.LONG_DATE_FORMAT);
		return DATE_FORMAT.format(date);
	}
	
	/**
	 * To short date string.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static String toShortDateString(Date date) {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(Constant.SHORT_DATE_FORMAT);
		return DATE_FORMAT.format(date);
	}
}
