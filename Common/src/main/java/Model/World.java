package Model;

import Model.Entity.Entity;
import Model.Location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class World implements Serializable {

	private ArrayList<Location> Locations = new ArrayList<Location>();
	private ArrayList<Entity> Entities = new ArrayList<Entity>();

	public World(){

	}

	public List<Location> getLocations() {
		return Locations;
	}

	public void setLocations(List<Location> locations) {
		Locations = (ArrayList<Location>) locations;
	}

	public List<Entity> getEntities() {
		return Entities;
	}

	public void setEntities(List<Entity> entities) {
		Entities = (ArrayList<Entity>) entities;
	}

	public void addLocation(Location location) {
		this.Locations.add(location);
	}

	public void addEntity(Entity entity) {
		this.Entities.add(entity);
	}

	public void removeLocation(Location location) {
		this.Locations.remove(location);
	}

	public void removeEntity(Entity entity) {
		this.Entities.remove(entity);
	}

	public Location getEntityLocation(String locationID) {
		for(Location location : Locations) {
			if(location.getLocationID().equals(locationID)) {
				return location;
			}
		}
		return null;
	}

	public Entity getEntity(String id) {
		for(Entity entity: Entities) {
			if (entity.getEntityID().equals(id)) {
				return entity;
			}
		}
		return null;
	}

	public Location getLocation(String id) {
		for(Location location: Locations) {
			if (location.getLocationID().equals(id)) {
				return location;
			}
		}
		return null;
	}

	public void notifyObservers() {

	}
}
