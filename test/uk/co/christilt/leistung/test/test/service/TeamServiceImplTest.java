package uk.co.christilt.leistung.test.test.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import uk.co.christilt.leistung.dao.TeamDao;
import uk.co.christilt.leistung.model.Team;
import uk.co.christilt.leistung.service.TeamService;
import uk.co.christilt.leistung.service.TeamServiceImpl;

/**
 * Unit tests of the {@link TeamServiceImpl} class.
 * Dependencies are mocked using Mockito.
 * <p>Methods with no logic added to that are provided by the DAO layer are
 * tested separately through integration tests.</p>
 */
@RunWith(MockitoJUnitRunner.class)
public class TeamServiceImplTest {

	private TeamService teamService;
	private TeamDao teamDao;
	
	@Before
	public void init() {
		teamDao = mock(TeamDao.class);
		teamService = new TeamServiceImpl(teamDao);
	}

	// TODO remove this test - it is unnecessary.
	@Test
	public void RetrieveAll_TeamTableEmpty_EmptyListReturned() {
		when(teamDao.retrieveAll()).thenReturn(new ArrayList<Team>());
		List<Team> retrievedTeams = teamService.retrieveAll();
		assertEquals(0, retrievedTeams.size());
	}
}
