package uk.co.christilt.leistung.model;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.MappedSuperclass;

/**
 * Abstract class for entities.
 * @param <PK> the type of primary key used.
 */
@MappedSuperclass
public abstract class AbstractEntity<PK extends Serializable> {

	public abstract PK getPk();
	
	public abstract void setPk(PK pk);
}
