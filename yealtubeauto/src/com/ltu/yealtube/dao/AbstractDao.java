package com.ltu.yealtube.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.ltu.yealtube.entity.Statistics;
import com.ltu.yealtube.entity.Tube;

public abstract class AbstractDao<T> implements Dao<T> {

	static {
		ObjectifyService.register(Tube.class);
		ObjectifyService.register(Statistics.class);
	}

	public static Objectify ofy() {
		return ObjectifyService.ofy();
	}

	public static ObjectifyFactory factory() {
		return ObjectifyService.factory();
	}

	private final Class<T> clazz;

	public AbstractDao(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T find(Long id) {
		return ofy().load().type(clazz).id(id).now();
	}

	@Override
	public T find(String id) {
		return ofy().load().type(clazz).id(id).now();
	}

	@Override
	public T find(Key<T> key) {
		return ofy().load().key(key).now();
	}

	@Override
	public Key<T> key(T t) {
		return Key.create(t);
	}

	@Override
	public List<T> findAll() {
		return ofy().load().type(clazz).list();
	}

	@Override
	public List<T> findAll(List<Key<T>> keys) {
		if (keys == null) {
			return null;
		}
		final Map<Key<T>, T> map = ofy().load().keys(keys);
		final List<T> list = new ArrayList<T>();
		for (final T t : map.values()) {
			list.add(t);
		}
		return list;
	}

	@Override
	public List<Key<T>> key(List<T> list) {
		if (list == null) {
			return null;
		}
		final List<Key<T>> keys = new ArrayList<Key<T>>(list.size());
		for (final T t : list) {
			final Key<T> key = Key.create(t);
			keys.add(key);
		}
		return keys;
	}

	@Override
	public T persist(T t) {
		ofy().save().entity(t).now();
		return t;
	}

	@Override
	public T update(T t) {
		ofy().save().entity(t).now();
		return t;
	}

	@Override
	public void delete(T t) {
		ofy().delete().entity(t).now();
	}

	@Override
	public CollectionResponse<T> list(String cursorString, Integer count) {
		Query<T> query = ofy().load().type(clazz);
		return executeQuery(query, cursorString, count);
	}

	/**
	 * Gets the query.
	 * 
	 * @return the query
	 */
	public Query<T> getQuery() {
		return ofy().load().type(clazz);
	}
	
	public Query<T> getQuery(String parentId) {
		return ofy().load().type(clazz);
	}

	/**
	 * Gets the query.
	 * 
	 * @param columns
	 *            the columns
	 * @return the query
	 */
	public Query<T> getQuery(Map<String, Object> columns) {
		Query<T> query = ofy().load().type(clazz);
		for (String key : columns.keySet()) {
			query = query.filter(key, columns.get(key));
		}
		return query;
	}

	/**
	 * Gets the query by name.
	 * 
	 * @param field
	 *            the field
	 * @param value
	 *            the value
	 * @return the query by name
	 */
	public Query<T> getQueryByName(String field, String value) {
		Query<T> query = ofy().load().type(clazz).filter(field + " >= ", value).filter(field + " < ", value + "\uFFFD");
		return query;
	}

	/**
	 * Execute query.
	 * 
	 * @param query
	 *            the query
	 * @param cursorString
	 *            the cursor string
	 * @param count
	 *            the count
	 * @return the collection response
	 */
	public CollectionResponse<T> executeQuery(Query<T> query, String cursorString, Integer count) {
		Cursor cursor = null;
		if (query != null) {
			if (count != null)
				query.limit(count);
			if (cursorString != null && cursorString != "") {
				query = query.startAt(Cursor.fromWebSafeString(cursorString));
			}

			List<T> records = new ArrayList<T>();
			QueryResultIterator<T> iterator = query.iterator();
			int num = 0;
			while (iterator.hasNext()) {
				records.add(iterator.next());
				if (count != null) {
					num++;
					if (num == count)
						break;
				}
			}

			// Find the next cursor
			cursor = iterator.getCursor();
			if (cursor != null) {
				cursorString = cursor.toWebSafeString();
			}
			return CollectionResponse.<T> builder().setItems(records).setNextPageToken(cursorString).build();
		} else {
			return null;
		}
	}

	/**
	 * Execute query.
	 * 
	 * @param query
	 *            the query
	 * @param count
	 *            the count
	 * @return the list
	 */
	public List<T> executeQuery(Query<T> query, Integer count) {
		List<T> records = new ArrayList<T>();
		QueryResultIterator<T> iterator = query.iterator();
		int num = 0;
		while (iterator.hasNext()) {
			records.add(iterator.next());
			if (count != null) {
				num++;
				if (num == count)
					break;
			}
		}
		return records;
	}

	/**
	 * Builds the collection response.
	 * 
	 * @param t
	 *            the t
	 * @param cursorString
	 *            the cursor string
	 * @return the collection response
	 */
	public CollectionResponse<T> buildCollectionResponse(T t, String cursorString) {
		List<T> records = new ArrayList<T>();
		if (t != null) {
			records.add(t);
		}
		return CollectionResponse.<T> builder().setItems(records).setNextPageToken(cursorString).build();
	}

	/**
	 * Builds the collection response.
	 * 
	 * @param records
	 *            the records
	 * @return the collection response
	 */
	public CollectionResponse<T> buildCollectionResponse(List<T> records) {
		return CollectionResponse.<T> builder().setItems(records).setNextPageToken(null).build();
	}

	/**
	 * Inits the data.
	 */
	abstract public void initData();

	/**
	 * Clean data.
	 */
	abstract public void cleanData();
}
