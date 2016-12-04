package uk.co.christilt.leistung.test.it.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import uk.co.christilt.leistung.dao.Dao;
import uk.co.christilt.leistung.model.AbstractEntity;

/**
 * Abstract class for integration tests of Dao classes with Spring
 * configuration.
 * 
 * <p>These tests have been placed into a generic abstract class to avoid
 * code duplication and difficulty in refactoring, despite the logic that
 * this places into the tests themselves. Each subclass must define
 * the fields that make up these tests by overriding the abstract methods.
 * The <code>JdbcTemplate</code> and <code>Dao</code> must also be set.</p>
 * 
 * <p>These tests use a dedicated database copy, configured in
 * test-context.xml.</p>
 * 
 * @param <T>
 *            the type of entities accessed by the DAO under test.
 * @param <PK>
 *            the type of primary key used.
 */
@ActiveProfiles("dev")
@ContextConfiguration(locations = { "classpath:uk/co/christilt/leistung/config/dao-context.xml",
		"classpath:uk/co/christilt/leistung/test/config/test-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractDaoIT<T extends AbstractEntity<PK>, PK extends Serializable> {

	private static Logger LOGGER = Logger.getLogger(AbstractDaoIT.class);

	protected JdbcTemplate jdbcTemplate;
	protected Dao<T, PK> dao;
	protected RowMapper<T> rowMapper;
	protected List<T> data;	
	protected String deleteSqlString;
	protected String selectSqlString;
	protected String insertEntityOneSqlString;
	protected String insertEntityTwoSqlString;
	protected String insertEntityThreeSqlString;
	protected PK examplePk;

	public abstract void setJdbcTemplate(DataSource dataSource);

	public abstract void setDao(Dao<T, PK> dao);

	/**
	 * Returns the <code>RowMapper</code> needed to check database entries.
	 * @return the <code>RowMapper</code> needed to check database entries.
	 */
	protected abstract RowMapper<T> defineRowMapper();

	/**
	 * Returns a <code>List</code> of 3 example entities for testing.
	 * @return a <code>List</code> of 3 example entities for testing.
	 */
	protected abstract List<T> defineData();

	/**
	 * Returns a <code>String</code> SQL query to remove all entities from the
	 * database (e.g. "DELETE FROM team").
	 * @return a <code>String</code> SQL query to remove all entities from the
	 * database.
	 */
	protected abstract String defineDeleteSqlString();

	/**
	 * Returns a <code>String</code> SQL query to get all entities from the
	 * database (e.g. "SELECT * FROM team").
	 * @return a <code>String</code> SQL query to get all entities from the
	 * database.
	 */
	protected abstract String defineSelectSqlString();

	/**
	 * Returns a <code>String</code> SQL query to insert an example entity into the
	 * database (e.g. "INSERT INTO team (name) " + "VALUES ('Manchester United F.C.')").
	 * @return a <code>String</code> SQL query to insert an example entity into the
	 * database.
	 */
	protected abstract String defineInsertEntityOneSqlString();

	/**
	 * Returns a <code>String</code> SQL query to insert an example entity into the
	 * database (e.g. "INSERT INTO team (name) " + "VALUES ('Manchester United F.C.')").
	 * @return a <code>String</code> SQL query to insert an example entity into the
	 * database.
	 */
	protected abstract String defineInsertEntityTwoSqlString();

	/**
	 * Returns a <code>String</code> SQL query to insert an example entity into the
	 * database (e.g. "INSERT INTO team (name) " + "VALUES ('Manchester United F.C.')").
	 * @return a <code>String</code> SQL query to insert an example entity into the
	 * database.
	 */
	protected abstract String defineInsertEntityThreeSqlString();

	/**
	 * Returns an example <code>PK</code> primary key for tests needing one.
	 * @return an example <code>PK</code> primary key.
	 */
	protected abstract PK defineExamplePk();

	@Before
	public void init() {
		rowMapper = defineRowMapper();
		data = defineData();
		deleteSqlString = defineDeleteSqlString();
		selectSqlString = defineSelectSqlString();
		insertEntityOneSqlString = defineInsertEntityOneSqlString();
		insertEntityTwoSqlString = defineInsertEntityTwoSqlString();
		insertEntityThreeSqlString = defineInsertEntityThreeSqlString();
		examplePk = defineExamplePk();
		jdbcTemplate.execute(deleteSqlString);
	}

	@Test(expected = NullPointerException.class)
	public void Create_NullAsParameter_NullPointerExceptionThrown() {
		dao.create(null);
		fail();
	}

	/*
	 * Create
	 */
	
	@Test
	public void Create_CalledMultipleTimesWithEntityParameter_SameNumberOfRowsRetrieved() {
		for (T t : data) {
			dao.create(t);
		}
		List<T> retrieved = jdbcTemplate.query(selectSqlString, rowMapper);
		assertEquals(data.size(), retrieved.size());
	}

	@Test
	public void Create_CalledMultipleTimesWithEntityParameter_SameEntityDataRetrieved() {
		for (T t : data) {
			dao.create(t);
		}
		List<T> retrieved = jdbcTemplate.query(selectSqlString, rowMapper);
		for (int i = 0; i < retrieved.size(); i++) {
			assertEquals(data.get(i), retrieved.get(i));
		}
	}

	/*
	 * Exists
	 */
	
	@Test(expected = NullPointerException.class)
	public void Exists_NullAsParameter_NullPointerExceptionThrown() {
		dao.exists(null);
		fail();
	}

	@Test
	public void Exists_QueriedEntityIsPresent_ReturnedTrue() {
		jdbcTemplate.update(insertEntityOneSqlString);
		T retrieved = jdbcTemplate.query(selectSqlString, rowMapper).get(0);
		PK retrievedId = retrieved.getPk();
		assertTrue(dao.exists(retrievedId));
	}

	@Test
	public void Exists_QueriedEntityIsNotPresent_ReturnedFalse() {
		assertFalse(dao.exists(examplePk));
	}

	/*
	 * RetrieveByPk
	 */
	
	@Test(expected = NullPointerException.class)
	public void RetrieveByPk_PkParameterIsNull_NullPointerExceptionThrown() {
		dao.retrieveByPk(null);
		fail();
	}

	@Test(expected = NoSuchElementException.class)
	public void RetrieveByPk_NoEntityWithMatchingPkIsPresent_NoSuchElementExceptionThrown() {
		dao.retrieveByPk(examplePk);
	}

	@Test()
	public void RetrieveByPk_EntityWithMatchingPkIsPresent_EntityRetrievedWithMatchingPk() {
		jdbcTemplate.update(insertEntityOneSqlString);
		T retrieved = jdbcTemplate.query(selectSqlString, rowMapper).get(0);
		PK retrievedId = retrieved.getPk();
		T result = dao.retrieveByPk(retrievedId);
		assertEquals(retrievedId, result.getPk());
	}

	/*
	 * RetrieveAll
	 */
	
	@Test
	public void RetrieveAll_MultipleEntityRowsInserted_SameNumberOfRowsRetrieved() {
		jdbcTemplate.update(insertEntityOneSqlString);
		jdbcTemplate.update(insertEntityTwoSqlString);
		jdbcTemplate.update(insertEntityThreeSqlString);
		List<T> retrieved = dao.retrieveAll();
		assertEquals(3, retrieved.size());
	}

	@Test
	public void RetrieveAll_EntityTableEmpty_EmptyListReturned() {
		List<T> retrieved = dao.retrieveAll();
		assertEquals(0, retrieved.size());
	}

	/*
	 * Update
	 */
	
	@Test(expected = NullPointerException.class)
	public void Update_NullAsParameter_NullPointerExceptionThrown() {
		dao.update(null);
		fail();
	}

	@Test(expected = NoSuchElementException.class)
	public void Update_NoEntityWithMatchingPkIsPresent_NoSuchElementExceptionThrown() {
		dao.update(data.get(0));
		fail();
	}

	@Test(expected = NullPointerException.class)
	public void Delete_NullAsParameter_NullPointerExceptionThrown() {
		dao.delete(null);
		fail();
	}

	/*
	 * Delete
	 */
	
	@Test(expected = NoSuchElementException.class)
	public void Delete_NoEntityWithMatchingPkIsPresent_NoSuchElementExceptionThrown() {
		dao.delete(data.get(0));
		fail();
	}

	@Test
	public void Delete_EntityWithMatchingPkIsPresent_DeletedEntityIsNoLongerPresent() {
		// Insert an entity into the database, and retrieve it.
		// This assures us we have an entity whose primary key matches its
		// database entry.
		jdbcTemplate.update(insertEntityOneSqlString);
		T original = jdbcTemplate.query(selectSqlString, rowMapper).get(0);
		// Delete the entity. The resulting select query should return 0
		// results.
		dao.delete(original);
		List<T> remaining = jdbcTemplate.query(selectSqlString, rowMapper);
		assertEquals(0, remaining.size());
	}
}
