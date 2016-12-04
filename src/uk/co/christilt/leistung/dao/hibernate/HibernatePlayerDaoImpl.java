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

import uk.co.christilt.leistung.dao.PlayerDao;
import uk.co.christilt.leistung.model.Player;

/**
 * A Hibernate implementation of the {@link PlayerDao} interface.
 */
@Repository
@Qualifier("playerDao")
public class HibernatePlayerDaoImpl
	extends AbstractHibernateDao<Player, Long> implements PlayerDao {
	
	private static Logger LOGGER = Logger.getLogger(HibernatePlayerDaoImpl.class);
	
	@Autowired 
	public HibernatePlayerDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory, Player.class);
	}

	@Override
	public Player retrieveByName(String name) {
		if (name == null) throw new NullPointerException();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(type);
		criteria.add(Restrictions.eq("name", name));
		Player result = (Player)criteria.uniqueResult();
		if (result == null) throw new NoSuchElementException();
		return result;
	}

	@Override
	public Player retrieveBySquadNo(int squadNo) {
		if (squadNo <= 0) throw new IllegalArgumentException();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(type);
		criteria.add(Restrictions.eq("squadNo", squadNo));
		Player result = (Player)criteria.uniqueResult();
		if (result == null) throw new NoSuchElementException();
		return result;
	}

}
