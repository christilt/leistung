package uk.co.christilt.leistung.test.it.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import uk.co.christilt.leistung.controller.DataController;
import uk.co.christilt.leistung.model.Player;
import uk.co.christilt.leistung.service.PlayerService;

/**
 * Integration tests of the {@link DataController} class with the Dispatcher
 * Servlet and Spring configuration. The tests integrate with the container
 * using MockMvc and mock dependencies using Mockito.
 */
@ActiveProfiles("dev")
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/leistung-servlet.xml",
		"classpath:uk/co/christilt/leistung/config/dao-context.xml",
		"classpath:uk/co/christilt/leistung/config/service-context.xml",
		"classpath:uk/co/christilt/leistung/test/config/test-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class DataControllerTest {
	
	private MockMvc mockMvc;

	@Autowired
	private PlayerService playerServiceMock;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void init() {
		Mockito.reset(playerServiceMock);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void ShowPlayers_ServiceReturnsListOfPlayers_PageRenderedWithPlayersAddedToModel() throws Exception {
		List<Player> retrievedPlayers = defineRetrievedPlayers();
		when(playerServiceMock.retrieveAll()).thenReturn(retrievedPlayers);
		mockMvc.perform(get("/data"))
			   .andExpect(status().isOk())
			   .andExpect(view().name("data"))
			   .andExpect(forwardedUrl("/WEB-INF/tile/data.jsp"))
			   .andExpect(model().attribute("players", hasSize(2)))
			   .andExpect(model().attribute("players", hasItem(retrievedPlayers.get(0))))
			   .andExpect(model().attribute("players", hasItem(retrievedPlayers.get(1))));
		
		verify(playerServiceMock, times(1)).retrieveAll();
		verifyNoMoreInteractions(playerServiceMock);
	}
	
	private List<Player> defineRetrievedPlayers() {
		List<Player> retrievedPlayers = new ArrayList<>();
		Player player1 = new Player();
		player1.setPk(1L);
		player1.setName("Florence");
		player1.setPosition("gk");
		player1.setSquadNo(1);
		Player player2 = new Player();
		player2.setPk(2L);
		player2.setName("The Machine");
		player2.setPosition("mf");
		player2.setSquadNo(2);
		return retrievedPlayers;
	}
}
