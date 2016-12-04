package uk.co.christilt.leistung.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import uk.co.christilt.leistung.model.Team;

/**
 * {@link RowMapper} implementation mapping data from a {@link ResultSet}
 * to the corresponding fields of the {@link Team} entity.
 */
public class TeamRowMapper implements RowMapper<Team> {

	@Override
	public Team mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Team team = new Team();
		team.setPk(rs.getLong("id"));
		team.setName(rs.getString("name"));
		return team;
	}
}
