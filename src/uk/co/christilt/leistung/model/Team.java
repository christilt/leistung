package uk.co.christilt.leistung.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity representing an opposing team.
 * 
 * <p>Equality for this entity does not include its primary key, because
 * this is auto generated.</p>
 * 
 * <p>Entities are compared based on their name.</p>
 */
@Entity
@Table(name="team")
public class Team
	extends AbstractNamedEntity<Long> implements Comparable<Team> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long pk;
	
	public Team() {
		// No args constructor
	}
	
	public Team(String name) {
		super(name);
	}

	@Override
	public Long getPk() {
		return pk;
	}
	
	@Override
	public void setPk(Long pk) {
		this.pk = pk;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Team [id=" + this.getPk() + ", name=" + this.getName() + "]";
	}

	@Override
	public int compareTo(Team other) {
		return this.getName().compareTo(other.getName());
	}
}
