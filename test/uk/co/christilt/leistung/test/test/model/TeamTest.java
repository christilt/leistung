package uk.co.christilt.leistung.test.test.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import uk.co.christilt.leistung.model.Team;

/**
 * Unit tests for the {@link Team} entity.
 */
@RunWith(MockitoJUnitRunner.class)
public class TeamTest {

	Team ManchesterUnited;
	Team Chelsea;
	Team QPR;
	
	private List<Team> teamData;

	@Before
	public void init() {
		ManchesterUnited = new Team("Manchester United F.C.");
		Chelsea = new Team("Chelsea F.C.");
		QPR = new Team("Q.P.R.");
		teamData = new ArrayList<>();
		teamData.add(ManchesterUnited);
		teamData.add(Chelsea);
		teamData.add(QPR);
	}
	
	@Test
	public void CompareTo_UsedWithinCollectionsSort_ResultIsLexicographicallyOrdered() {
		Collections.sort(teamData);
		assertEquals(teamData.get(0), Chelsea);
		assertEquals(teamData.get(1), ManchesterUnited);
		assertEquals(teamData.get(2), QPR);
	}
}
