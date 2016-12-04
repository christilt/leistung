package uk.co.christilt.leistung.service;

import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;

import uk.co.christilt.leistung.model.AbstractEntity;

/**
 * A service for entities of the specified type.
 * @param <T> the type of entities accessed by this service.
 * @param <PK> the type of primary key used.
 */
public interface Service<T extends AbstractEntity<PK>, PK extends Serializable> {

	/**
	 * Saves an entity to the database.
	 * @param t The entity to be saved.
	 * @throws NullPointerException if t is <code>null</code>.
	 */
	void create(T t);
	
	/**
	 * Returns <code>true</code> if the database contains an entity matching the specified
	 * primary key.
	 * @param pk the primary key of the entity in question.
	 * @return <code>true</code> if the database contains an entity matching the specified
	 * primary key.
	 * @throws NullPointerException if the specified pk is <code>null</code>.
	 */
	boolean exists(PK pk);
	
	/**
	 * Retrieves an entity matching the specified primary key.
	 * @param pk the primary key of the matching entity.
	 * @return An entity with the corresponding primary key.
	 * @throws NullPointerException if the specified pk is <code>null</code>.
	 * @throws NoSuchElementException if no matching entity exists in the database.
	 */
	T retrieveByPk(PK pk);
	
	/**
	 * Retrieves all entities of the specified type from the database.
	 * @return A <code>List</code> of all objects for the specified type.
	 * Returns an empty <code>List</code> if none exist in the database.
	 */
	List<T> retrieveAll();
	
	/**
	 * Updates the database with the specified entity.
	 * @param t the entity to be updated.
	 * @throws NullPointerException if the specified entity is <code>null</code>.
	 * @throws NoSuchElementException if no matching entity exists in the database.
	 */
	void update(T t);
	
	/**
	 * Removes the specified entity from the database.
	 * @param t the entity to be removed.
	 * @throws NullPointerException if the specified entity is <code>null</code>.
	 * @throws NoSuchElementException if no matching entity exists in the database. 
	 */
	void delete(T t);
}
