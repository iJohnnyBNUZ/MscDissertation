package Network;

import java.io.Serializable;

public class Event implements Serializable {
	private String entityID;
	private String locationID;
	private String direction;
	private String pickUpItem;

	public Event(String entityID, String locationID, String direction, String pickUpItem) {
		this.entityID = entityID;
		this.locationID = locationID;
		this.direction = direction;
		this.pickUpItem = pickUpItem;
	}

	public String getEntityID() {
		return entityID;
	}

	public void setEntityID(String entityID) {
		this.entityID = entityID;
	}

	public String getPickUpItem() {
		return pickUpItem;
	}

	public void setPickUpItem(String pickUpItem) {
		this.pickUpItem = pickUpItem;
	}

	public String getLocationID() {
		return locationID;
	}

	public void setLocationID(String locationID) {
		this.locationID = locationID;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
}
