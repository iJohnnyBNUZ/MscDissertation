package Network.Events;

import java.io.Serializable;

public class PickUpEvent implements Event, Serializable {
	private String entityID;

	public PickUpEvent(String entityID) {
		this.entityID = entityID;
	}

	public String getEntityID() {
		return entityID;
	}
}
