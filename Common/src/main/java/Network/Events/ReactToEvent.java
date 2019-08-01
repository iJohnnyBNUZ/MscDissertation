package Network.Events;

import java.io.Serializable;

public class ReactToEvent implements Event, Serializable {
	private String entityID;
	private String reactToID;

	public ReactToEvent(String reactToID, String entityID) {
		this.reactToID = reactToID;
		this.entityID = entityID;
	}

	public String getEntityID() {
		return entityID;
	}

	public String getReactToID() {
		return reactToID;
	}
}