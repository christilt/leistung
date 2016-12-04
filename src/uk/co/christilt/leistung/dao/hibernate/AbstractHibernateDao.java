package uk.co.christilt.leistung.dao.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import uk.co.christilt.leistung.dao.Dao;
import uk.co.christilt.leistung.model.AbstractEntity;

/**
 * A skeletal Hibernate implementation of the {@link Dao} interface.
 * @param <T> the type of entities accessed by this DAO.
 * @param <PK> the type of primary key used.
 */
@Transactional
public abstract class AbstractHibernateDao
	<T extends AbstractEntity<PK>, PK extends Serializable>	implements Dao<T, PK> {
	
	private static Logger LOGGER = Logger.getLogger(AbstractHibernateDao.class);

	protected SessionFactory sessionFactory;
	protected Class<T> type;
	
	public AbstractHibernateDao() {
		// No args constructor.
	}

	public AbstractHibernateDao(SessionFactory sessionFactory, Class<T> type) {
		this.sessionFactory = sessionFactory;
		this.type = type;
	}

	@Override
	public void create(T t) {
		if (t == null) throw new NullPointerException();
		sessionFactory.getCurrentSession().save(t);
	}

	@Override
	public boolean exists(PK pk) {
		if (pk == null) throw new NullPointerException();
		// Correct to suppress unchecked warnings because the object returned is
		// of the same type provided in construction.
		@SuppressWarnings("unchecked")
		T result = (T)sessionFactory.getCurrentSession().get(type,  pk);
		return result != null ? true : false;
	}

	@Override
	public T retrieveByPk(PK pk) {
		if (pk == null) throw new NullPointerException();
		// Correct to suppress unchecked warnings because the object returned is
		// of the same type provided in construction.
		@SuppressWarnings("unchecked")
		T result = (T)sessionFactory.getCurrentSession().get(type,  pk);
		if (result == null) throw new NoSuchElementException();
		return result;
	}

	@Override
	public List<T> retrieveAll() {
		LOGGER.info("......got to abs dao retrieve all...");
		LOGGER.info("...type:"+type);
		LOGGER.info("...sessionFactory:"+sessionFactory);
		LOGGER.info("...currentSession:"+sessionFactory.getCurrentSession());
		// Correct to suppress unchecked warnings because the list returned is
		// of the same type provided in construction.
		@SuppressWarnings("unchecked")
		List<T> result = sessionFactory.getCurrentSession().createCriteria(type).list();
		return result;
	}

	@Override
	public void update(T t) {
		if (t == null) throw new NullPointerException();
		if (!exists(t.getPk())) throw new NoSuchElementException();
		sessionFactory.getCurrentSession().merge(t);
		sessionFactory.getCurrentSession().flush();
	}

	@Override
	public void delete(T t) {
		if (t == null) throw new NullPointerException();
		if (!exists(t.getPk())) throw new NoSuchElementException();
		sessionFactory.getCurrentSession().clear();
		sessionFactory.getCurrentSession().delete(t);
	}

	
}
