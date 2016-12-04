package uk.co.christilt.leistung.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import uk.co.christilt.leistung.model.Player;

/**
 * {@link RowMapper} implementation mapping data from a {@link ResultSet}
 * to the corresponding fields of the {@link Player} class.
 */
public class PlayerRowMapper implements RowMapper<Player> {

	@Override
	public Player mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Player player = new Player();
		player.setPk(rs.getLong("id"));
		player.setName(rs.getString("name"));
		player.setSquadNo(rs.getInt("squad_no"));
		player.setPosition(rs.getString("position"));
		return player;
	}
}
