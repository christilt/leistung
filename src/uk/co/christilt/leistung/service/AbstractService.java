package uk.co.christilt.leistung.service;

import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import uk.co.christilt.leistung.controller.DataController;
import uk.co.christilt.leistung.dao.Dao;
import uk.co.christilt.leistung.model.AbstractEntity;

/**
 * A skeletal implementation of the {@link Service} interface.
 * @param <T> the type of entities accessed by this service.
 * @param <PK> the type of primary key used.
 */
public abstract class AbstractService
	<T extends AbstractEntity<PK>, PK extends Serializable> implements Service<T, PK> {

	private static Logger LOGGER = Logger.getLogger(AbstractService.class);
	
	private Dao<T, PK> dao;
	
	@Autowired
	public AbstractService(Dao<T, PK> dao) {
		this.dao = dao;
	}
	
	@Override
	@Transactional
	public void create(T t) {
		dao.create(t);
	}

	@Override
	@Transactional
	public boolean exists(PK pk) {
		return dao.exists(pk);
	}

	@Override
	@Transactional
	public T retrieveByPk(PK pk) {
		return dao.retrieveByPk(pk);
	}

	@Override
	@Transactional
	public List<T> retrieveAll() {
		return dao.retrieveAll();
	}

	@Override
	@Transactional
	public void update(T t) {
		dao.update(t);
	}

	@Override
	@Transactional
	public void delete(T t) {
		dao.delete(t);
	}
}
