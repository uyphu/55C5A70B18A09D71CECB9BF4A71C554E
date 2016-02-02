package com.ltu.yealtube.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.googlecode.objectify.Key;
import com.ltu.yealtube.constants.Constant;
import com.ltu.yealtube.entity.Statistics;
import com.ltu.yealtube.entity.Tube;


/**
 * The Class YoutubeUtil.
 * @author uyphu
 */
public class YoutubeUtil {

	/** The Constant log. */
	private static final Logger log = Logger.getLogger(YoutubeUtil.class);

	/**
	 * Gets the video.
	 *
	 * @param id the id
	 * @param part the part
	 * @return the video
	 */
	public static JSONObject getVideo(String id, String part) {
		try {

			URL url = new URL(
					"https://www.googleapis.com/youtube/v3/videos?part=" + part
							+ "&id=" + id + "&key=" + Constant.API_KEY);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				log.error("Failed : HTTP error code : " + conn.getResponseCode());
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Constant.UTF_8));

			String output;
			StringBuilder builder = new StringBuilder();
			while ((output = br.readLine()) != null) {
				builder.append(output);
			}

			JSONObject json = new JSONObject(builder.toString());

			conn.disconnect();
			
			return json;
		} catch (MalformedURLException e) {
			log.error(e.getMessage(), e.getCause());
		} catch (IOException e) {
			log.error(e.getMessage(), e.getCause());
		} catch (JSONException e) {
			log.error(e.getMessage(), e.getCause());
		}
		return null;
	}
	
	/**
	 * Call youtube.
	 *
	 * @param url the url
	 * @return the JSON object
	 */
	public static JSONObject callYoutube(URL url) {
		try {

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				log.error("Failed : HTTP error code : " + conn.getResponseCode());
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Constant.UTF_8));

			String output;
			StringBuilder builder = new StringBuilder();
			while ((output = br.readLine()) != null) {
				builder.append(output);
			}

			JSONObject json = new JSONObject(builder.toString());

			conn.disconnect();
			
			return json;
		}  catch (IOException e) {
			log.error(e.getMessage(), e.getCause());
		} catch (JSONException e) {
			log.error(e.getMessage(), e.getCause());
		}
		return null;
	}
	
	/**
	 * Gets the tube.
	 *
	 * @param id the id
	 * @return the tube
	 */
	public static Tube getTube(String id) {
		try {
			Tube tube = new Tube();
			JSONObject json = getVideo(id, "snippet");
			if (json != null) {
				JSONArray jsonArray = (JSONArray)json.get("items");
				if (jsonArray != null) {
					JSONObject item = new JSONObject(jsonArray.get(0).toString());
					item = new JSONObject(item.get("snippet").toString());
					tube.setId(id);
					tube.setTitle(item.getString("title"));
					tube.setDescription(item.getString("description"));
					SimpleDateFormat format = new SimpleDateFormat(Constant.LONG_DATE_FORMAT);
					tube.setPublishedAt(format.parse(item.getString("publishedAt")));
					return tube;
				}
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e.getCause());
		}
		
		return null;
	}
	
	/**
	 * Gets the statistics.
	 *
	 * @param id the id
	 * @return the statistics
	 */
	public static Statistics getStatistics(String id) {
		try {
			Tube tube = new Tube();
			JSONObject json = getVideo(id, "statistics");
			if (json != null) {
				JSONArray jsonArray = (JSONArray)json.get("items");
				if (jsonArray != null) {
					JSONObject item = new JSONObject(jsonArray.get(0).toString());
					item = new JSONObject(item.get("statistics").toString());
					tube.setId(id);
					Statistics statistics = new Statistics();
					statistics.setVideo(Key.create(Tube.class, id));
					statistics.setViewCount(Integer.parseInt(item.get("viewCount") != null ? item.get("viewCount").toString() : "0"));
					statistics.setLikeCount(Integer.parseInt(item.get("likeCount") != null ? item.get("likeCount").toString() : "0"));
					statistics.setDislikeCount(Integer.parseInt(item.get("dislikeCount") != null ? item.get("dislikeCount").toString() : "0"));
					statistics.setFavoriteCount(Integer.parseInt(item.get("favoriteCount") != null ? item.get("favoriteCount").toString() : "0"));
					statistics.setCommentCount(Integer.parseInt(item.get("commentCount") != null ? item.get("commentCount").toString() : "0"));
					//statistics.setRating(Float.parseFloat(item.get("rating") != null ? item.get("rating").toString() : "0"));
					return statistics;
				}
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e.getCause());
		}
		return null;
	}
	
	/**
	 * Gets the search url.
	 *
	 * @param pageToken the page token
	 * @return the search url
	 */
	private static URL getSearchUrl(String pageToken) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, -15);
			SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_FORMAT);
			String after = format.format(calendar.getTime()) + "Z";
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			String before = format.format(calendar.getTime()) + "Z";
			String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=viewCount&publishedAfter="
					+ after
					+ "&publishedBefore="
					+ before
					+ "&type=video&key="
					+ Constant.API_KEY;
			if (pageToken != null) {
				url = url + "&pageToken=" + pageToken;
			}
			return new URL(url);
		} catch (MalformedURLException e) {
			log.error(e.getMessage(), e.getCause());
		}
		return null;
	}
	
	/**
	 * Gets the video url.
	 *
	 * @param part the part
	 * @param id the id
	 * @return the video url
	 */
	public static URL getVideoUrl(String part, String id) {
		try {
			URL url = new URL(
					"https://www.googleapis.com/youtube/v3/videos?part=" + part
							+ "&id=" + id + "&key=" + Constant.API_KEY);
			return url;
		} catch (MalformedURLException e) {
			log.error(e.getMessage(), e.getCause());
		}
		return null;
	}
	
	/**
	 * Gets the comment thread url.
	 *
	 * @param part the part
	 * @param videoId the video id
	 * @param pageToken the page token
	 * @return the comment thread url
	 */
	public static URL getCommentThreadUrl(String part, String videoId, String pageToken) {
		try {
			String url = "https://www.googleapis.com/youtube/v3/commentThreads?part="
					+ part + "&videoId=" + videoId + "&key=" + Constant.API_KEY;
			if (pageToken != null) {
				url = url + pageToken;
			}
			return new URL(url);
		} catch (MalformedURLException e) {
			log.error(e.getMessage(), e.getCause());
		}
		return null;
	}
	
	/**
	 * Gets the hot tube.
	 *
	 * @return the hot tube
	 */
	public static List<Tube> getHotTube() {
		List<Tube> tubes = new ArrayList<Tube>();
		try {
			String pageToken = null;
			int count = 0;
			do {
				URL url = getSearchUrl(pageToken);
				JSONObject json = callYoutube(url);
				if (json != null) {
					pageToken = json.get("nextPageToken").toString();
					JSONArray jsonArray = (JSONArray)json.get("items");
					if (jsonArray != null) {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = new JSONObject(jsonArray.get(i).toString());
							item = new JSONObject(item.get("id").toString());
							String videoId = item.getString("videoId");
							Tube tube = getTube(videoId);
							if (tube != null) {
								Statistics statistics = getStatistics(videoId);
								if (statistics != null) {
									if (statistics.getViewCount() < Constant.MAX_VIEW) {
										return tubes;
									} else {									
										if (hasGoodComment(videoId)) {
											List<Statistics> list = new ArrayList<Statistics>();
											list.add(statistics);
											tube.setStatistics(list);
											tubes.add(tube);
										}
									}
								}
							}
						}
					}
				}
				System.out.println(count);
				count++;
			} while (true);
		} catch (JSONException e) {
			log.error(e.getMessage(), e.getCause());
		}
		return tubes;
	}
	
	/**
	 * Checks for good comment.
	 *
	 * @param videoId the video id
	 * @return true, if successful
	 */
	public static boolean hasGoodComment(String videoId) {
		try {
			int count = 0;
			do {
				String pageToken = null;
				URL url = getCommentThreadUrl("snippet", videoId, pageToken);
				JSONObject json = callYoutube(url);
				if (json != null) {
					pageToken = json.get("nextPageToken").toString();
					JSONArray jsonArray = (JSONArray)json.get("items");
					if (jsonArray != null) {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = new JSONObject(jsonArray.get(i).toString());
							item = new JSONObject(item.get("snippet").toString());
							item = new JSONObject(item.get("topLevelComment").toString());
							item = new JSONObject(item.get("snippet").toString());
							String textDisplay = item.getString("textDisplay");
							if (hasGoodWord(textDisplay)) {
								return true;
							}
						}
					}
				}
				count ++;
			} while (count < 20);
		} catch (Exception e) {
			log.error(e.getMessage(), e.getCause());
		}
		return false;
	}
	
	/**
	 * Checks for good word.
	 *
	 * @param text the text
	 * @return true, if successful
	 */
	private static boolean hasGoodWord(String text) {
		text = text.toLowerCase();
		String[] words = { "lol", "good", "hay", "thank you", "like", "best",
				"thich", "qua dinh", "qua hay", "tuyet voi", "fantastic",
				"wonderful", "perfect", "incredible", "unbelievable", "vui",
				"vui qua", "happy", "bien", "ok", "enjoy", "nice", "pretty",
				"beautiful", "well" };
		for (int i = 0; i < words.length; i++) {
			if (text.indexOf(words[i]) != -1) {
				return true;
			}
		}
		return false;
	}
	
	public static Properties getConfig() {
		
		try {
			InputStream is =  YoutubeUtil.class.getClassLoader().getResourceAsStream("/config.properties");
			Properties props = new Properties();
			props.load(is);
			return props;
		} catch (IOException e) {
			log.error(e.getMessage(), e.getCause());
		}
		return null;
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		Properties properties = getConfig();
		System.out.println(properties.getProperty("tube.analyse.days"));
		
//		try {
//			SimpleDateFormat format = new SimpleDateFormat(Constant.LONG_DATE_FORMAT);
//			Date date = format.parse("2015-05-23T23:01:32.000Z");
//			System.out.println(date.toString());
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		Tube tube = getTube("DQkrfti22mo");
//		System.out.println(tube.toString());
//		List<Tube> tubes =  getHotTube();
//		System.out.println(tubes.size());
//		for (Tube tube : tubes) {
//			System.out.println(tube.toString());
//		}
	}
	
}
