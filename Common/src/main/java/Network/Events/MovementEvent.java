package Network.Events;

import java.io.Serializable;

public class MovementEvent implements Event, Serializable {
	private String entityID;
	private String direction;

	public MovementEvent(String entityID, String direction) {
		this.entityID = entityID;
		this.direction = direction.toLowerCase();
	}

	public String getEntityID() {
		return entityID;
	}

	public String getDirection() {
		return direction;
	}
}
