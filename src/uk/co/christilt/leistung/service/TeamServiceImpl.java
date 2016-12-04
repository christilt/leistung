package uk.co.christilt.leistung.service;

import uk.co.christilt.leistung.dao.TeamDao;
import uk.co.christilt.leistung.model.Team;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * An implementation of the {@link TeamService} interface.
 */
@Service
@Qualifier("teamService")
public class TeamServiceImpl 
	extends AbstractService<Team, Long> implements TeamService {

	private static Logger LOGGER = Logger.getLogger(TeamServiceImpl.class);
	
	private TeamDao teamDao;
	
	@Autowired
	public TeamServiceImpl(TeamDao teamDao) {
		super(teamDao);
		this.teamDao = teamDao;
	}
	
	@Override
	public Team retrieveByName(String name) {
		return teamDao.retrieveByName(name);
	}

}
