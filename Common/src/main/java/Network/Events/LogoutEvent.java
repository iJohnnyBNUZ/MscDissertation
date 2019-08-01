package Network.Events;

import java.io.Serializable;

public class LogoutEvent implements Event, Serializable {
	private String entityID;

	public LogoutEvent(String entityID) {
		this.entityID = entityID;
	}

	public String getEntityID() {
		return entityID;
	}
}
