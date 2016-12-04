package uk.co.christilt.leistung.test.test.model;

import static org.junit.Assert.assertEquals;

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

import uk.co.christilt.leistung.model.Player;

/**
 * Unit tests for the {@link Team} entity.
 */
@RunWith(MockitoJUnitRunner.class)
public class PlayerTest {
	
	Player Tony;
	Player Gill;
	Player Suleman;
	private List<Player> playerData;
	
	@Before
	public void init() {
		Tony = new Player("Tony", 1, "gk");
		Gill = new Player("Gill", 44, "mf");
		Suleman = new Player("Suleman", 10, "df");
		playerData = new ArrayList<>();
		playerData.add(Tony);
		playerData.add(Gill);
		playerData.add(Suleman);
	}
	
	@Test
	public void CompareTo_UsedWithinCollectionsSort_ResultIsOrderedBySquadNo() {
		Collections.sort(playerData);
		assertEquals(playerData.get(0), Tony);
		assertEquals(playerData.get(1), Suleman);
		assertEquals(playerData.get(2), Gill);
	}
}
