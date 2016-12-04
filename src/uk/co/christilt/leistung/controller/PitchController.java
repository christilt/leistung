package uk.co.christilt.leistung.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for the pitch page.
 */
@Controller
public class PitchController {
	
	private static Logger LOGGER = Logger.getLogger(PitchController.class);
	
	/**
	 * Renders the pitch page.
	 */
	@RequestMapping(value={"/", "/pitch"}, method=RequestMethod.GET)
	public String showPitch() {
		return "pitch";
	}
}
