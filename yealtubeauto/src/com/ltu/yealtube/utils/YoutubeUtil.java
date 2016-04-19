package com.ltu.yealtube.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.api.client.http.HttpStatusCodes;
import com.googlecode.objectify.Key;
import com.ltu.yealtube.constants.Constant;
import com.ltu.yealtube.entity.Playlist;
import com.ltu.yealtube.entity.Statistics;
import com.ltu.yealtube.entity.Tube;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.exeptions.ErrorCodeDetail;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * The Class YoutubeUtil.
 * 
 * @author uyphu
 */
public final class YoutubeUtil {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(YoutubeUtil.class);

	/**
	 * Gets the video.
	 * 
	 * @param id
	 *            the id
	 * @param part
	 *            the part
	 * @return the video
	 */
	public static JSONObject getVideo(String id, String part) {
		try {

			URL url = new URL("https://www.googleapis.com/youtube/v3/videos?part=" + part + "&id=" + id + "&key="
					+ Constant.API_KEY);
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

			JSONObject json = new JSONObject(builder.toString());

			conn.disconnect();

			return json;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e.getCause());
		} 
		return null;
	}

	/**
	 * Gets the playlist.
	 *
	 * @param id the id
	 * @param part the part
	 * @return the playlist
	 */
	public static JSONObject getPlaylist(String id, String part) {
		try {

			URL url = new URL("https://www.googleapis.com/youtube/v3/playlists?part=" + part + "&id=" + id + "&key="
					+ Constant.API_KEY);
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

			JSONObject json = new JSONObject(builder.toString());

			conn.disconnect();

			return json;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e.getCause());
		} 
		return null;
	}

	/**
	 * Call youtube.
	 * 
	 * @param url
	 *            the url
	 * @return the JSON object
	 */
	public static JSONObject callYoutube(URL url) {
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

			JSONObject json = new JSONObject(builder.toString());

			conn.disconnect();

			return json;
		} catch (IOException e) {
			LOG.error(e.getMessage(), e.getCause());
		} catch (JSONException e) {
			LOG.error(e.getMessage(), e.getCause());
		}
		return null;
	}

	/**
	 * Gets the.
	 * 
	 * @param url
	 *            the url
	 * @return the JSON object
	 */
	public static JSONObject get(URL url) {
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

			JSONObject json = new JSONObject(builder.toString());

			conn.disconnect();

			return json;
		} catch (IOException e) {
			LOG.error(e.getMessage(), e.getCause());
		} catch (JSONException e) {
			LOG.error(e.getMessage(), e.getCause());
		}
		return null;
	}

	/**
	 * Gets the tube.
	 * 
	 * @param id
	 *            the id
	 * @return the tube
	 */
	public static Tube getTube(String id) {
		try {
			Tube tube = new Tube();
			JSONObject json = getVideo(id, "snippet");
			if (json != null) {
				JSONArray jsonArray = (JSONArray) json.get("items");
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
			LOG.error(e.getMessage(), e.getCause());
		}

		return null;
	}

	/**
	 * Gets the play list.
	 *
	 * @param id the id
	 * @return the play list
	 */
	public static Playlist getPlayList(String id) {
		try {
			Playlist tube = new Playlist();
			JSONObject json = getPlaylist(id, "snippet");
			if (json != null) {
				JSONArray jsonArray = (JSONArray) json.get("items");
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
			LOG.error(e.getMessage(), e.getCause());
		}
		return null;
	}

	/**
	 * Gets the play list everage view.
	 *
	 * @param id the id
	 * @return the play list everage view
	 */
	public static int getPlayListEverageView(String id) {
		try {
			String pageToken = null;
			String urlString = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + id + "&key="
					+ Constant.API_KEY + "&maxResults=30";
			int viewCount = 0;
			int count = 0;
			do {
				if (pageToken != null) {
					urlString = urlString + "&pageToken=" + pageToken;
				}
				JSONObject json = callYoutube(new URL(urlString));
				if (json != null) {
					pageToken = json.has("nextPageToken") ? json.getString("nextPageToken") : null;
					JSONArray jsonArray = (JSONArray) json.get("items");
					if (jsonArray != null) {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = new JSONObject(jsonArray.get(i).toString());
							item = new JSONObject(item.getString("snippet"));
							item = new JSONObject(item.getString("resourceId"));
							String videoId = item.getString("videoId");
							if (isValid(videoId)) {
								Statistics statistics = getStatistics(videoId);
								viewCount += statistics.getViewCount();
								count++;
							} 
						}
					}
				}

			} while (pageToken != null);
			return viewCount / count;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e.getCause());
			e.printStackTrace();
		}

		return 0;
		
	}
	
	/**
	 * Gets the play list view.
	 *
	 * @param id the id
	 * @return the play list view
	 */
	public static Integer getPlayListView(String id) {
		
		try {
			Document doc = Jsoup.connect("https://www.youtube.com/playlist?list="+id+"&gl=GB&hl=en-GB").get();
			String text = doc.body().text();
			int start = text.indexOf("videos");
			if (start != -1 ) {
				int end = text.indexOf("views", start + 6);
				String viewStr = text.substring(start + 6, end);
				viewStr  = viewStr.trim();
				viewStr = viewStr.replace(",", "");
				return Integer.parseInt(viewStr);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e.getCause());
		}
		return null;
	}

	/**
	 * Gets the statistics.
	 * 
	 * @param id
	 *            the id
	 * @return the statistics
	 */
	public static Statistics getStatistics(String id) {
		try {
			JSONObject json = getVideo(id, "statistics");
			if (json != null) {
				JSONArray jsonArray = (JSONArray) json.get("items");
				if (jsonArray != null && jsonArray.length() > 0) {
					JSONObject item = new JSONObject(jsonArray.get(0).toString());
					item = new JSONObject(item.get("statistics").toString());
					Statistics statistics = new Statistics();
					statistics.setVideo(Key.create(Tube.class, id));
					statistics.setViewCount(Integer.parseInt(item.has("viewCount") ? item.getString("viewCount") : "0"));
					statistics.setLikeCount(Integer.parseInt(item.has("likeCount") ? item.getString("likeCount") : "0"));
					statistics.setDislikeCount(Integer.parseInt(item.has("dislikeCount") ? item.getString("dislikeCount") : "0"));
					statistics.setFavoriteCount(Integer.parseInt(item.has("favoriteCount") ? item.getString("favoriteCount") : "0"));
					statistics.setCommentCount(Integer.parseInt(item.has("commentCount") ? item.getString("commentCount") : "0"));
					return statistics;
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e.getCause());
		}
		return null;
	}

	/**
	 * Gets the search url.
	 * 
	 * @param pageToken
	 *            the page token
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
			String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=viewCount&publishedAfter=" + after
					+ "&publishedBefore=" + before + "&type=video&key=" + Constant.API_KEY;
			if (pageToken != null) {
				url = url + "&pageToken=" + pageToken;
			}
			return new URL(url);
		} catch (MalformedURLException e) {
			LOG.error(e.getMessage(), e.getCause());
		}
		return null;
	}

	/**
	 * Gets the playlist url.
	 *
	 * @param pageToken the page token
	 * @return the playlist url
	 */
	private static URL getPlaylistUrl(String pageToken) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, -45);
			SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_FORMAT);
			String after = format.format(calendar.getTime()) + "Z";
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			String before = format.format(calendar.getTime()) + "Z";
			String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=viewCount&publishedAfter=" + after
					+ "&publishedBefore=" + before + "&type=playlist&key=" + Constant.API_KEY;
			if (pageToken != null) {
				url = url + "&pageToken=" + pageToken;
			}
			return new URL(url);
		} catch (MalformedURLException e) {
			LOG.error(e.getMessage(), e.getCause());
		}
		return null;
	}

	/**
	 * Gets the video url.
	 * 
	 * @param part
	 *            the part
	 * @param id
	 *            the id
	 * @return the video url
	 */
	public static URL getVideoUrl(String part, String id) {
		try {
			URL url = new URL("https://www.googleapis.com/youtube/v3/videos?part=" + part + "&id=" + id + "&key="
					+ Constant.API_KEY);
			return url;
		} catch (MalformedURLException e) {
			LOG.error(e.getMessage(), e.getCause());
		}
		return null;
	}

	/**
	 * Gets the comment thread url.
	 * 
	 * @param part
	 *            the part
	 * @param videoId
	 *            the video id
	 * @param pageToken
	 *            the page token
	 * @return the comment thread url
	 */
	public static URL getCommentThreadUrl(String part, String videoId, String pageToken) {
		try {
			String url = "https://www.googleapis.com/youtube/v3/commentThreads?part=" + part + "&videoId=" + videoId + "&key="
					+ Constant.API_KEY;
			if (pageToken != null) {
				url = url + "&pageToken=" + pageToken;
			}
			return new URL(url);
		} catch (MalformedURLException e) {
			LOG.error(e.getMessage(), e.getCause());
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
					pageToken = json.has("nextPageToken") ? json.getString("nextPageToken") : null;
					JSONArray jsonArray = (JSONArray) json.get("items");
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
				count++;
			} while (count <= 5);
		} catch (JSONException e) {
			LOG.error(e.getMessage(), e.getCause());
		}
		return tubes;
	}

	/**
	 * Checks for good comment.
	 * 
	 * @param videoId
	 *            the video id
	 * @return true, if successful
	 */
	public static boolean hasGoodComment(String videoId) {
		try {
			int count = 0;
			String pageToken = null;
			URL url;
			JSONObject json;
			do {
				url = getCommentThreadUrl("snippet", videoId, pageToken);
				json = callYoutube(url);
				if (json != null) {
					pageToken = json.has("nextPageToken") ? json.getString("nextPageToken") : null;
					JSONArray jsonArray = (JSONArray) json.get("items");
					if (jsonArray != null) {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = new JSONObject(jsonArray.get(i).toString());
							item = new JSONObject(item.get("snippet").toString());
							item = new JSONObject(item.get("topLevelComment").toString());
							String comment = getComment(item.getString("id"));
							if (hasGoodWord(comment)) {
								return true;
							}
						}
					}
				}
				count++;
			} while (count < Constant.MAX_PAGE_COMMENT);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e.getCause());
		}
		return false;
	}

	/**
	 * Gets the comment.
	 * 
	 * @param commentId
	 *            the comment id
	 * @return the comment
	 */
	public static String getComment(String commentId) {
		URL url;
		try {
			String strUrl = "https://www.googleapis.com/youtube/v3/comments?part=snippet&id=" + commentId + "&key="
					+ Constant.API_KEY;
			url = new URL(strUrl);
			JSONObject json = callYoutube(url);
			JSONArray jsonArray = (JSONArray) json.get("items");
			JSONObject item = new JSONObject(jsonArray.get(0).toString());
			item = new JSONObject(item.get("snippet").toString());
			return item.getString("textDisplay");
		} catch (MalformedURLException e) {
			LOG.error(e.getMessage(), e.getCause());
		} catch (JSONException e) {
			LOG.error(e.getMessage(), e.getCause());
		}

		return null;
	}

	/**
	 * Checks for good word.
	 * 
	 * @param text
	 *            the text
	 * @return true, if successful
	 */
	private static boolean hasGoodWord(String text) {
		text = text.toLowerCase();
		String[] words = { "lol", "good", "hay", "thank you", "like", "best", "thich", "qua dinh", "qua hay", "tuyet voi",
				"fantastic", "wonderful", "perfect", "incredible", "unbelievable", "vui", "vui qua", "happy", "bien", "ok",
				"enjoy", "nice", "pretty", "beautiful", "well", "awsome", "loz﻿" };
		for (int i = 0; i < words.length; i++) {
			if (text.indexOf(words[i]) != -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the config.
	 * 
	 * @return the config
	 */
	public static Properties getConfig() {

		try {
			InputStream is = YoutubeUtil.class.getClassLoader().getResourceAsStream("/config.properties");
			Properties props = new Properties();
			props.load(is);
			return props;
		} catch (IOException e) {
			LOG.error(e.getMessage(), e.getCause());
		}
		return null;
	}

	/**
	 * Send tube.
	 * 
	 * @param videoId
	 *            the video id
	 * @return true, if successful
	 */
	public static boolean sendTube(String videoId, String rating) throws CommonException {
		String endpoint = "https://yealtubetest.appspot.com/_ah/api/youtubeendpoint/v1/insertVideo";
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", videoId);
		params.put("rating", rating);
		post(endpoint, params);
		return true;
	}
	
	/**
	 * Send playlist.
	 *
	 * @param playlistId the playlist id
	 * @return true, if successful
	 * @throws CommonException the common exception
	 */
	public static boolean sendPlaylist(Playlist playlist) throws CommonException {
		String endpoint = "https://yealtubetest.appspot.com/_ah/api/youtubeendpoint/v1/insertPlaylist";
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", playlist.getId());
		params.put("viewCount", String.valueOf(playlist.getViewCount()));
		post(endpoint, params);
		return true;
	}

	/**
	 * Post.
	 * 
	 * @param endpoint
	 *            the endpoint
	 * @param params
	 *            the params
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void post(String endpoint, Map<String, String> params) throws CommonException {
		URL url;
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e) {
			throw new CommonException(HttpStatusCodes.STATUS_CODE_BAD_GATEWAY, ErrorCodeDetail.ERROR_URL_NOT_VALID.getMsg());
		}
		StringBuilder bodyBuilder = new StringBuilder();
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		// constructs the POST body using the parameters
		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			bodyBuilder.append(param.getKey()).append('=').append(param.getValue());
			if (iterator.hasNext()) {
				bodyBuilder.append('&');
			}
		}
		String body = bodyBuilder.toString();
		byte[] bytes = body.getBytes();
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setFixedLengthStreamingMode(bytes.length);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			// post the request
			OutputStream out = conn.getOutputStream();
			out.write(bytes);
			out.close();
			// handle the response
			int status = conn.getResponseCode();
			if (status != 200) {
				throw new CommonException(status, ErrorCodeDetail.ERROR_INPUT_NOT_VALID.getMsg());
			}

		} catch (IOException e) {
			if (conn != null) {
				conn.disconnect();
			}
			throw new CommonException(HttpStatusCodes.STATUS_CODE_SERVER_ERROR, ErrorCodeDetail.ERROR_INSERT_ENTITY.getMsg());
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	/**
	 * Checks if is valid.
	 *
	 * @param id the id
	 * @return true, if is valid
	 */
	public static boolean isValid(String id) {
		try {
			String urlString = "https://www.googleapis.com/youtube/v3/videos?part=id&id=" + id + "&key=" + Constant.API_KEY;
			JSONObject jsonObject = callYoutube(new URL(urlString));
			JSONArray jsonArray = (JSONArray) jsonObject.get("items");
			if (jsonArray != null && jsonArray.length() > 0) {
				return true;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e.getCause());
		}
		return false;

	}

	/**
	 * Gets the hot play list.
	 * 
	 * @return the hot play list
	 */
	public static List<Playlist> getHotPlayList() {
		List<Playlist> playlists = new ArrayList<Playlist>();
		try {
			String pageToken = null;
			do {
				URL url = getPlaylistUrl(pageToken);
				JSONObject json = callYoutube(url);
				if (json != null) {
					pageToken = json.has("nextPageToken") ? json.getString("nextPageToken") : null;
					JSONArray jsonArray = (JSONArray) json.get("items");
					if (jsonArray != null) {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = new JSONObject(jsonArray.get(i).toString());
							item = new JSONObject(item.get("id").toString());
							String playlistId = item.getString("playlistId");
							Playlist playlist = getPlayList(playlistId);
							if (playlist != null) {
								Integer viewCount = getPlayListView(playlistId);
								//System.out.println("playlist:" + playlistId + ", view count: "+viewCount);
								if (viewCount != null) {
									if (viewCount < Constant.MAX_PLAYLIST_VIEW) {
										return playlists;
									} else {
										playlist.setViewCount(viewCount);
										playlists.add(playlist);
									}
								}
								
							}
						}
					}
				}
			} while (pageToken != null);
		} catch (JSONException e) {
			LOG.error(e.getMessage(), e.getCause());
		}
		return playlists;
	}
	
	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		// Properties properties = getConfig();
		// System.out.println(properties.getProperty("tube.analyse.days"));
		// try {
		// SimpleDateFormat format = new
		// SimpleDateFormat(Constant.LONG_DATE_FORMAT);
		// Date date = format.parse("2015-05-23T23:01:32.000Z");
		// System.out.println(date.toString());
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// Tube tube = getTube("DQkrfti22mo");
		// System.out.println(tube.toString());
		// List<Tube> tubes = getHotTube();
		// System.out.println(tubes.size());
		// for (Tube tube : tubes) {
		// System.out.println(tube.toString());
		// }

		// sendTube("21jVawyO0m8");

		// try {
		// sendTube("oOyGODrV6aU");
		// } catch (CommonException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		try {
			// sendTube("U_eGg7mGJys", String.valueOf(3.7f));
			// System.out.println(isValid("h8RSgh-aFH4"));
//			List<Playlist> list = getHotPlayList();
//			for (Playlist playlist : list) {
//				System.out.println(playlist.toString());
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
