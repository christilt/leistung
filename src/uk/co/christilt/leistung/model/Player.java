package uk.co.christilt.leistung.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity representing a player.
 * 
 * <p>Equality for this entity does not include its primary key, because
 * this is auto generated.</p>
 * 
 * <p>Entities are compared based on their squad number.</p>
 */
@Entity
@Table(name="player")
public class Player 
	extends AbstractNamedEntity<Long> implements Comparable<Player> {

	@Id
	@GeneratedValue
	@Column(name="id")
	private long id;
	
	@Column(name="squad_no")
	private int squadNo;

	@Column(name="position")
	private String position;
	
	public Player() {
		// No args constructor
	}

	public Player(String name, int squadNo, String position) {
		super(name);
		this.squadNo = squadNo;
		this.position = position;
	}

	@Override
	public Long getPk() {
		return id;
	}

	@Override
	public void setPk(Long id) {
		this.id = id;
	}

	public int getSquadNo() {
		return squadNo;
	}

	public void setSquadNo(int squadNo) {
		this.squadNo = squadNo;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + squadNo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (squadNo != other.squadNo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Player [id=" + this.getPk() + ", name=" + this.getName() + 
				", squadNo=" + squadNo + ", position=" + position + "]";
	}
	
	@Override
	public int compareTo(Player other) {
		return Integer.compare(squadNo, other.getSquadNo());
	}
}
