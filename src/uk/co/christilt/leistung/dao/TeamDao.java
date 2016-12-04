package uk.co.christilt.leistung.dao;

import java.util.NoSuchElementException;

import uk.co.christilt.leistung.model.Team;

/**
 * A Dao for {@link Team} entities.
 */
public interface TeamDao extends Dao<Team, Long> {

	/**
	 * Retrieves a {@link Team} entity from the database matching the specified name.
	 * @param name the name of the team to be retrieved.
	 * @return A {@link Team} entity matching the specified name.
	 * @throws NullPointerException if name is <code>null</code>.
	 * @throws NoSuchElementException if no matching entity exists in the database.
	 */
	Team retrieveByName(String name);
}
