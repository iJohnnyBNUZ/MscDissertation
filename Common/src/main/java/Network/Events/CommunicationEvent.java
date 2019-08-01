package Network.Events;

import java.io.Serializable;

public class CommunicationEvent implements Event, Serializable {
	private String entityID;
	private boolean communicateWith;

	public CommunicationEvent(String entityID, boolean communicateWithID) {
		this.entityID = entityID;
		this.communicateWith = communicateWith;
	}

	public String getEntityID() {
		return entityID;
	}

	public boolean getCommunicateWithID() {
		return communicateWith;
	}
}
