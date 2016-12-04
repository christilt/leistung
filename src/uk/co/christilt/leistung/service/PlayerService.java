package uk.co.christilt.leistung.service;

import java.util.NoSuchElementException;

import uk.co.christilt.leistung.model.Player;

/**
 * A service for {@link Player} entities.
 */
public interface PlayerService extends Service<Player, Long> {

	/**
	 * Retrieves a {@link Player} entity from the database matching the specified name.
	 * @param name the name of the player to be retrieved.
	 * @return A {@link Player} entity matching the specified name.
	 * @throws NullPointerException if name is <code>null</code>.
	 * @throws NoSuchElementException if no matching entity exists in the database.
	 */
	Player retrieveByName(String name);

	/**
	 * Retrieves a {@link Player} entity from the database matching
	 * the specified squad number.
	 * @param squadNo the squad number of the player to be retrieved.
	 * @return A {@link Player} entity matching the specified squad number.
	 * @throws IllegalArgumentException if squadNo is less than or equal to 0.
	 * @throws NoSuchElementException if no matching entity exists in the
	 * database.
	 */
	Player retrieveBySquadNo(int squadNo);
}
