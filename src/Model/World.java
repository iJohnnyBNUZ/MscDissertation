package Model;

import Model.Entity.Entity;
import Model.Location.Location;

import java.util.ArrayList;
import java.util.List;

public class World {
	private static World ourInstance = new World();

	public static World getInstance() {
		return ourInstance;
	}

	private List<Location> Locations = new ArrayList<Location>();
	private List<Entity> Entities = new ArrayList<Entity>();

	private World() {
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
