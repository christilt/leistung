package uk.co.christilt.leistung.service;

import uk.co.christilt.leistung.dao.PlayerDao;
import uk.co.christilt.leistung.model.Player;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * An implementation of the {@link PlayerService} interface.
 */
@Service
@Qualifier("playerService")
public class PlayerServiceImpl 
	extends AbstractService<Player, Long> implements PlayerService {

	private static Logger LOGGER = Logger.getLogger(PlayerServiceImpl.class);
	
	private PlayerDao playerDao;
	
	@Autowired
	public PlayerServiceImpl(PlayerDao playerDao) {
		super(playerDao);
		this.playerDao = playerDao;
	}
	
	@Override
	@Transactional
	public Player retrieveByName(String name) {
		return playerDao.retrieveByName(name);
	}

	@Override
	@Transactional
	public Player retrieveBySquadNo(int squadNo) {
		return playerDao.retrieveBySquadNo(squadNo);
	}

}
