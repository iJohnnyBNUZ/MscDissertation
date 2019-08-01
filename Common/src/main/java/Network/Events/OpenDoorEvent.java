package Network.Events;

import java.io.Serializable;

public class OpenDoorEvent implements Event, Serializable {
	private String entityID;

	public OpenDoorEvent(String entityID) {
		this.entityID = entityID;
	}

	public String getEntityID() {
		return entityID;
	}
}
