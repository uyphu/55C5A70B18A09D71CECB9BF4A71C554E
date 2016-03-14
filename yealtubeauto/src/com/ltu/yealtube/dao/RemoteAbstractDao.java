package com.ltu.yealtube.dao;

import static com.googlecode.objectify.ObjectifyService.run;

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
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.cmd.Query;
import com.ltu.yealtube.exeptions.CommonException;


public abstract class RemoteAbstractDao<T> implements Dao<T> {

	static {
		ObjectifyService.setFactory(new RemoteObjectifyFactory());
	}
	
	private List<T> list;
	private List<Key<T>> keys;
	private T item;
	private CollectionResponse<T> response;
	private Query<T> query;

	public static Objectify ofy() {
		return ObjectifyService.ofy();
	}

	public static ObjectifyFactory factory() {
		return ObjectifyService.factory();
	}
	
	private final Class<T> clazz;
	

	public RemoteAbstractDao(Class<T> clazz) {
		list = null;
		this.clazz = clazz;
	}

	@Override
	public T find(final Long id) {
		item = null;
		run(new VoidWork() {
			
			@Override
			public void vrun() {
				item = ofy().load().type(clazz).id(id).now();
				ofy().clear();
			}
		});
		return item;
	}

	@Override
	public T find(final String id) {
		item = null;
		run(new VoidWork() {
			
			@Override
			public void vrun() {
				item = ofy().load().type(clazz).id(id).now();
				ofy().clear();
			}
		});
		return item;
	}

	@Override
	public T find(final Key<T> key) {
		item = null;
		run(new VoidWork() {
			
			@Override
			public void vrun() {
				item = ofy().load().key(key).now();
				ofy().clear();
			}
		});
		return item;
	}

	@Override
	public Key<T> key(T t) {
		return Key.create(t);
	}

	public List<T> findAll() {
		list = null;
		run(new VoidWork() {
			
			@Override
			public void vrun() {
				list = ofy().load().type(clazz).list();
				ofy().clear();
			}
		});
		return list;
	}

	@Override
	public List<T> findAll(final List<Key<T>> keys) {
		list = null;
		run(new VoidWork() {
			
			@Override
			public void vrun() {
				if (keys == null) {
					list = null;
				}
				final Map<Key<T>, T> map = ofy().load().keys(keys);
				list = new ArrayList<T>();
				for (final T t : map.values()) {
					list.add(t);
				}
				ofy().clear();
			}
		});
		return list;
		
	}

	@Override
	public List<Key<T>> key(final List<T> l) {
		keys = null;
		run(new VoidWork() {
			
			@Override
			public void vrun() {
				if (l == null) {
					list = null;
				}
				keys = new ArrayList<Key<T>>(l.size());
				for (final T t : l) {
					final Key<T> key = Key.create(t);
					keys.add(key);
				}
				ofy().clear();
			}
		});
		return keys;
	}

	@Override
	public T persist(final T t) {
		run(new VoidWork() {
			
			@Override
			public void vrun() {
				ofy().save().entity(t).now();
				ofy().clear();
			}
		});
		return t;
	}

	@Override
	public T update(final T t) {
		run(new VoidWork() {
			
			@Override
			public void vrun() {
				ofy().save().entity(t).now();
				ofy().clear();
			}
		});
		return t;
	}

	@Override
	public void delete(final T t) {
		run(new VoidWork() {
			
			@Override
			public void vrun() {
				ofy().delete().entity(t).now();
				ofy().clear();
			}
		});
		
	}

	@Override
	public CollectionResponse<T> list(final String cursorString, final Integer count) {
		response = null;
		run(new VoidWork() {
			
			@Override
			public void vrun() {
				Query<T> query = ofy().load().type(clazz);
				response = executeQuery(query, cursorString, count);
				ofy().clear();
			}
		});
		return response;
	}

	/**
	 * Gets the query.
	 * 
	 * @return the query
	 */
	public Query<T> getQuery() {
		query = null;
		run(new VoidWork() {
			
			@Override
			public void vrun() {
				query = ofy().load().type(clazz);
				ofy().clear();
			}
		});
		return query;
	}

	/**
	 * Gets the query.
	 * 
	 * @param columns
	 *            the columns
	 * @return the query
	 */
	public Query<T> getQuery(final Map<String, Object> columns) {
		query = null;
		run(new VoidWork() {
			
			@Override
			public void vrun() {
				Query<T> query = ofy().load().type(clazz);
				for (String key : columns.keySet()) {
					query = query.filter(key, columns.get(key));
				}
				ofy().clear();
			}
		});
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
	public Query<T> getQueryByName(final String field, final String value) {
		query = null;
		run(new VoidWork() {
			
			@Override
			public void vrun() {
				query = ofy().load().type(clazz).filter(field + " >= ", value).filter(field + " < ", value + "\uFFFD");
				ofy().clear();
			}
		});
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
	public CollectionResponse<T> executeQuery(final Query<T> queryObject, final String cursorStr, final Integer count) {
		run(new VoidWork() {
			
			@Override
			public void vrun() {
				Cursor cursor = null;
				Query<T> queryObj = queryObject;
				String cursorString = cursorStr;
				if (queryObj != null) {
					if (count != null)
						queryObj.limit(count);
					if (cursorString != null && cursorString != "") {
						queryObj = queryObj.startAt(Cursor.fromWebSafeString(cursorString));
					}

					List<T> records = new ArrayList<T>();
					QueryResultIterator<T> iterator = queryObj.iterator();
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
					response = CollectionResponse.<T> builder().setItems(records).setNextPageToken(cursorString).build();
				} else {
					response = null;
				}
				ofy().clear();
			}
		});
		return response;
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
	abstract public void initData() throws CommonException;

	/**
	 * Clean data.
	 */
	abstract public void cleanData() throws CommonException;
}
