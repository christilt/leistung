package uk.co.christilt.leistung.dao.hibernate;

import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import uk.co.christilt.leistung.dao.TeamDao;
import uk.co.christilt.leistung.model.Team;

/**
 * A Hibernate implementation of the {@link TeamDao} interface.
 */
@Repository
@Qualifier("teamDao")
public final class HibernateTeamDaoImpl
	extends AbstractHibernateDao<Team, Long> implements TeamDao {
	
	private static Logger LOGGER = Logger.getLogger(HibernateTeamDaoImpl.class);
	
	@Autowired 
	public HibernateTeamDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory, Team.class);
	}

	@Override
	public Team retrieveByName(String name) {
		if (name == null) throw new NullPointerException();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(type);
		criteria.add(Restrictions.eq("name", name));
		Team result = (Team)criteria.uniqueResult();
		if (result == null) throw new NoSuchElementException();
		return result;
	}

}
