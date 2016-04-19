package com.ltu.yealtube.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.ltu.yealtube.constants.Constant;

/**
 * The Class AppUtils.
 * @author uyphu
 */
public final class AppUtils {
	
	/** The LOG. */
	private static final Logger LOG = Logger.getLogger(AppUtils.class);

	/**
	 * Instantiates a new app utils.
	 */
	private AppUtils() {
		
	}
	
	/**
	 * Gets the current time.
	 *
	 * @return the current time
	 */
	public static String getCurrentTime() {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS Z");
		return dateFormat.format(Calendar.getInstance().getTime());
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
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.LONG_DATE_FORMAT);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			LOG.error(e.getMessage(), e.getCause());
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
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.LONG_DATE_FORMAT);
		return dateFormat.format(date);
	}
	
	/**
	 * To short date string.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static String toShortDateString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.SHORT_DATE_FORMAT);
		return dateFormat.format(date);
	}
	
	/**
	 * Gets the parm value.
	 *
	 * @param id the id
	 * @return the parm value
	 */
	public static int getParmValue(String id) {
		if (id != null) {
			String url = "https://yealtubetest.appspot.com/_ah/api/paramvalueendpoint/v1/paramvalue/" + id;
			JSONObject jsonObject;
			try {
				jsonObject = APIUtils.get(new URL(url));
				if (jsonObject != null && jsonObject.getString("value") != null) {
					return Integer.parseInt(jsonObject.getString("value"));
				}
			} catch (MalformedURLException e) {
				LOG.error(e.getMessage(), e.getCause());
			} catch (NumberFormatException e) {
				LOG.error(e.getMessage(), e.getCause());
			} catch (JSONException e) {
				LOG.error(e.getMessage(), e.getCause());
			}
		}
		return 0;
	}
}
