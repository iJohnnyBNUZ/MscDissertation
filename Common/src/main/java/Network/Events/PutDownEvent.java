package Network.Events;

import java.io.Serializable;

public class PutDownEvent implements Event, Serializable {
	private String entityID;
	private String ItemID;

	public PutDownEvent(String entityID, String ItemID) {
		this.entityID = entityID;
		this.ItemID = ItemID;

	}

	public String getEntityID() {
		return entityID;
	}

	public String getItemID(){return  ItemID;}
}
