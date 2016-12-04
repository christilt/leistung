package uk.co.christilt.leistung.test.it.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
import uk.co.christilt.leistung.dao.PlayerDao;
import uk.co.christilt.leistung.dao.PlayerRowMapper;
import uk.co.christilt.leistung.model.Player;

/**
 * Integration tests for the {@link PlayerDao} class with Spring configuration.
 */
@ActiveProfiles("dev")
@ContextConfiguration(locations = { "classpath:uk/co/christilt/leistung/config/dao-context.xml",
		"classpath:uk/co/christilt/leistung/test/config/test-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class HibernatePlayerDaoImplIT extends AbstractDaoIT<Player, Long> {

	private static Logger LOGGER = Logger.getLogger(HibernatePlayerDaoImplIT.class);
	
	@Override
	@Autowired
	public void setJdbcTemplate(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	@Autowired
	@Qualifier("playerDao")
	public void setDao(Dao<Player, Long> dao) {
		this.dao = dao;
	}

	/*
	 * Define fields used in abstract class.
	 */
	
	@Override
	protected RowMapper<Player> defineRowMapper() {
		return new PlayerRowMapper();
	}
	
	@Override
	protected List<Player> defineData() {
		List<Player> playerData = new ArrayList<>();
		playerData.add(new Player("Tony", 1, "gk"));
		playerData.add(new Player("Gill", 44, "mf"));
		playerData.add(new Player("Suleman", 10, "df"));
		return playerData;
	}

	@Override
	protected String defineDeleteSqlString() {
		return "DELETE FROM player";
	}

	@Override
	protected String defineSelectSqlString() {
		return "SELECT * FROM player";
	}

	@Override
	protected String defineInsertEntityOneSqlString() {
		return "INSERT INTO player (name, squad_no, position)"+
				"VALUES ('Tony', 1, 'gk')";
	}

	@Override
	protected String defineInsertEntityTwoSqlString() {
		return "INSERT INTO player (name, squad_no, position)"+
				"VALUES ('Gill', 44, 'mf')";
	}

	@Override
	protected String defineInsertEntityThreeSqlString() {
		return "INSERT INTO player (name, squad_no, position)"+
				"VALUES ('Suleman', 10, 'df')";
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
		Player original = jdbcTemplate.query(this.selectSqlString, this.rowMapper).get(0);
		// Change the entity retrieved and update.
		original.setName("Jeremy");
		this.dao.update(original);
		// Retrieve the updated entity; this should match the original.
		Player updatedPlayer = jdbcTemplate.query(this.selectSqlString, this.rowMapper).get(0);
		assertEquals(original, updatedPlayer);
	}
	
	/*
	 * Test additional methods present in PlayerDao.
	 */
	
	/*
	 * RetrieveByName
	 */

	@Test(expected = NullPointerException.class)
	public void RetrieveByName_NullAsParameter_NullPointerExceptionThrown() {
		((PlayerDao) dao).retrieveByName(null);
		fail();
	}

	@Test(expected = NoSuchElementException.class)
	public void RetrieveByName_NoEntityWithMatchingNameIsPresent_NoSuchElementExceptionThrown() {
		jdbcTemplate.update(this.insertEntityOneSqlString);
		((PlayerDao) dao).retrieveByName("Jonny");
		fail();
	}

	@Test
	public void RetrieveByName_EntityWithMatchingNameIsPresent_EntityRetrievedWithMatchingName() {
		String name = "Tony";
		jdbcTemplate.update("INSERT INTO player (name, squad_no, position)"+
				"VALUES ('"+name+"', 1, 'gk')");
		Player retrieved = ((PlayerDao) dao).retrieveByName(name);
		assertEquals(name, retrieved.getName());
	}
	
	/*
	 * RetrieveBySquadNo
	 */
	
	@Test(expected = IllegalArgumentException.class)
	public void RetrieveBySquadNo_ZeroAsParameter_IllegalArgumentExceptionThrown() {
		((PlayerDao) dao).retrieveBySquadNo(0);
		fail();
	}

	@Test(expected = NoSuchElementException.class)
	public void RetrieveBySquadNo_NoEntityWithMatchingNameIsPresent_NoSuchElementExceptionThrown() {
		jdbcTemplate.update(this.insertEntityOneSqlString);
		((PlayerDao) dao).retrieveBySquadNo(100);
		fail();
	}

	@Test
	public void RetrieveBySquadNo_EntityWithMatchingNameIsPresent_EntityRetrievedWithMatchingSquadNo() {
		int squadNo = 1;
		jdbcTemplate.update("INSERT INTO player (name, squad_no, position)"+
				"VALUES ('Tony', "+squadNo+", 'gk')");
		Player retrieved = ((PlayerDao) dao).retrieveBySquadNo(squadNo);
		assertEquals(squadNo, retrieved.getSquadNo());
	}

}
