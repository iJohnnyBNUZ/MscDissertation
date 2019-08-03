package Network.Events;

import java.io.Serializable;

public class SaveGameEvent implements Event, Serializable {
	private String entityID;

	public SaveGameEvent(String entityID) {
		this.entityID = entityID;
	}

	public String getEntityID() {
		return entityID;
	}
}
