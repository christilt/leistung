package uk.co.christilt.leistung.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.constraints.NotBlank;

import uk.co.christilt.leistung.dao.FormValidationGroup;
import uk.co.christilt.leistung.dao.PersistenceValidationGroup;

/**
 * Abstract class for entities needing a name.
 * 
 * <p>By default, equality for sub-classed entities does not include the primary
 * key, because this is likely to include auto generated value(s).</p>
 */
@MappedSuperclass
public abstract class AbstractNamedEntity<PK extends Serializable>
	extends AbstractEntity<PK> {

	@NotBlank(groups={PersistenceValidationGroup.class, FormValidationGroup.class})
	@Column(name="name")
	private String name;
	
	public AbstractNamedEntity() {
		// No args constructor
	}

	public AbstractNamedEntity(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		// Correct because we have already established obj is of the same class.
		@SuppressWarnings("unchecked")
		AbstractNamedEntity<PK> other = (AbstractNamedEntity<PK>) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
