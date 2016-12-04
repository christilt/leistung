package uk.co.christilt.leistung.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import uk.co.christilt.leistung.model.Player;
import uk.co.christilt.leistung.service.PlayerService;

/**
 * Controller for the data page.
 */
@Controller
public class DataController {
	
	private static Logger LOGGER = Logger.getLogger(DataController.class);

	private PlayerService playerService;

	@Autowired
	@Qualifier("playerService")
	public void setPlayerService(PlayerService playerService) {
		this.playerService = playerService;
	}

	/**
	 * Renders the data page, showing player details.
	 */
	@RequestMapping(value="/data", method=RequestMethod.GET)
	public String showPlayers(Model model) {
		List<Player> players = playerService.retrieveAll();

		model.addAttribute("players", players);

		return "data";
	}
}
