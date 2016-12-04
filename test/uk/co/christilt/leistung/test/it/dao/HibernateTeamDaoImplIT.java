package uk.co.christilt.leistung.test.it.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import uk.co.christilt.leistung.dao.Dao;
import uk.co.christilt.leistung.dao.TeamDao;
import uk.co.christilt.leistung.dao.TeamRowMapper;
import uk.co.christilt.leistung.model.Team;

/**
 * Integration tests for the {@link TeamDao} class with Spring configuration.
 */
@ActiveProfiles("dev")
@ContextConfiguration(locations = { "classpath:uk/co/christilt/leistung/config/dao-context.xml",
		"classpath:uk/co/christilt/leistung/test/config/test-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class HibernateTeamDaoImplIT extends AbstractDaoIT<Team, Long> {

	private static Logger LOGGER = Logger.getLogger(HibernateTeamDaoImplIT.class);
	
	@Override
	@Autowired
	public void setJdbcTemplate(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	@Autowired
	@Qualifier("teamDao")
	public void setDao(Dao<Team, Long> dao) {
		this.dao = dao;
	}

	/*
	 * Define fields used in abstract class.
	 */
	
	@Override
	protected RowMapper<Team> defineRowMapper() {
		return new TeamRowMapper();
	}

	@Override
	protected List<Team> defineData() {
		List<Team> teamData = new ArrayList<>();
		teamData.add(new Team("Manchester United F.C."));
		teamData.add(new Team("Chelsea F.C."));
		teamData.add(new Team("Q.P.R."));
		return teamData;
	}

	@Override
	protected String defineDeleteSqlString() {
		return "DELETE FROM team";
	}

	@Override
	protected String defineSelectSqlString() {
		return "SELECT * FROM team";
	}

	@Override
	protected String defineInsertEntityOneSqlString() {
		return "INSERT INTO team (name) " + "VALUES ('Manchester United F.C.')";
	}

	@Override
	protected String defineInsertEntityTwoSqlString() {
		return "INSERT INTO team (name)" + "VALUES ('Chelsea F.C.')";
	}

	@Override
	protected String defineInsertEntityThreeSqlString() {
		return "INSERT INTO team (name)" + "VALUES ('Q.P.R')";
	}

	@Override
	protected Long defineExamplePk() {
		return 1L;
	}

	@Test
	public void Update_EntityWithMatchingPkIsPresent_UpdatedEntityMatchesEntityUsedAsParameter() {
		// Insert an entity into the database, and retrieve it.
		// This assures us we have an entity whose primary key matches its
		// database entry.
		jdbcTemplate.update(this.insertEntityOneSqlString);
		Team original = jdbcTemplate.query(this.selectSqlString, this.rowMapper).get(0);
		// Change the entity retrieved and update.
		original.setName("Manchester City F.C.");
		this.dao.update(original);
		// Retrieve the updated entity; this should match the original.
		Team updatedTeam = jdbcTemplate.query(this.selectSqlString, this.rowMapper).get(0);
		assertEquals(original, updatedTeam);
	}
	
	/*
	 * Test additional methods present in TeamDao.
	 */
	
	/*
	 * RetrieveByName
	 */

	@Test(expected = NullPointerException.class)
	public void RetrieveByName_NullAsParameter_NullPointerExceptionThrown() {
		((TeamDao) dao).retrieveByName(null);
		fail();
	}

	@Test(expected = NoSuchElementException.class)
	public void RetrieveByName_NoEntityWithMatchingNameIsPresent_NoSuchElementExceptionThrown() {
		jdbcTemplate.update(this.insertEntityOneSqlString);
		((TeamDao) dao).retrieveByName("Sheffield Wednesday");
		fail();
	}

	@Test
	public void RetrieveByName_EntityWithMatchingNameIsPresent_EntityRetrievedWithMatchingName() {
		String name = "Manchester United F.C.";
		jdbcTemplate.update("INSERT INTO team (name) " + "VALUES ('" + name + "')");
		Team retrieved = ((TeamDao) dao).retrieveByName(name);
		assertEquals(name, retrieved.getName());
	}

}
