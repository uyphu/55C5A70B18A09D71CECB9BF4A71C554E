package com.ltu.yealtube.dao;

import java.util.List;

import com.google.api.server.spi.response.CollectionResponse;
import com.googlecode.objectify.Key;

/**
 * The Interface Dao.
 *
 * @param <T> the generic type
 */
public interface Dao<T> {

	/**
	 * Find.
	 *
	 * @param id the id
	 * @return the t
	 */
	T find(Long id);

	/**
	 * Find.
	 *
	 * @param id the id
	 * @return the t
	 */
	T find(String id);

	/**
	 * Find.
	 *
	 * @param key the key
	 * @return the t
	 */
	T find(Key<T> key);

	/**
	 * Key.
	 *
	 * @param t the t
	 * @return the key
	 */
	Key<T> key(T t);

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	List<T> findAll();

	/**
	 * Find all.
	 *
	 * @param keys the keys
	 * @return the list
	 */
	List<T> findAll(List<Key<T>> keys);

	/**
	 * Key.
	 *
	 * @param list the list
	 * @return the list
	 */
	List<Key<T>> key(List<T> list);

	/**
	 * Persist.
	 *
	 * @param t the t
	 * @return the t
	 */
	T persist(T t);
	
	/**
	 * Update.
	 *
	 * @param t the t
	 * @return the t
	 */
	T update(T t);

	/**
	 * Delete.
	 *
	 * @param t the t
	 */
	void delete(T t);
	
	/**
	 * List.
	 *
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the collection response
	 */
	public CollectionResponse<T> list(String cursorString, Integer count);
	
	/**
	 * Builds the collection response.
	 *
	 * @param t the t
	 * @param cursorString the cursor string
	 * @return the collection response
	 */
	public CollectionResponse<T> buildCollectionResponse(T t, String cursorString);
	
}
