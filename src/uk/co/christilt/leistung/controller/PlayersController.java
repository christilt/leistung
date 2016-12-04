package uk.co.christilt.leistung.controller;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import uk.co.christilt.leistung.model.Player;
import uk.co.christilt.leistung.service.PlayerService;
import uk.co.christilt.leistung.service.PlayerServiceImpl;

/**
 * Controller for the players page.
 */
@Controller
public class PlayersController {
	
	private static Logger LOGGER = Logger.getLogger(PlayersController.class);

	private PlayerService playerService;

	@Autowired
	@Qualifier("playerService")
	public void setPlayerService(PlayerService playerService) {
		this.playerService = playerService;
	}

	/**
	 * Renders the players page, showing player details.
	 */
	@RequestMapping(value="/players", method=RequestMethod.GET)
	public String showPlayers(Model model) {
		Collection<Player> players = playerService.retrieveAll();

		model.addAttribute("players", players);

		return "players";
	}
}
