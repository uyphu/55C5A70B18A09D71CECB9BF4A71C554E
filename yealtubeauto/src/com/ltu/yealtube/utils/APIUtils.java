package com.ltu.yealtube.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.ltu.yealtube.constants.Constant;

/**
 * The Class APIUtils.
 * @author uyphu
 */
public final class APIUtils {
	
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(APIUtils.class);
	
	/**
	 * Instantiates a new API utils.
	 */
	private APIUtils() {
		
	}
	
	/**
	 * Gets the.
	 *
	 * @param url the url
	 * @return the JSON object
	 */
	public static JSONObject get(final URL url) {
		try {

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				LOG.error("Failed : HTTP error code : " + conn.getResponseCode());
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Constant.UTF_8));

			String output;
			StringBuilder builder = new StringBuilder();
			while ((output = br.readLine()) != null) {
				builder.append(output);
			}
			conn.disconnect();
			
			return new JSONObject(builder.toString());
			
		} catch (IOException e) {
			LOG.error(e.getMessage(), e.getCause());
		} catch (JSONException e) {
			LOG.error(e.getMessage(), e.getCause());
		}
		return null;
	}

}
