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
	private Location currentLocation;

	public World(int i){

	}

	public static World getOurInstance() {
		return ourInstance;
	}

	public static void setOurInstance(World ourInstance) {
		World.ourInstance = ourInstance;
	}

	public List<Location> getLocations() {
		return Locations;
	}

	public void setLocations(List<Location> locations) {
		Locations = locations;
	}

	public List<Entity> getEntities() {
		return Entities;
	}

	public void setEntities(List<Entity> entities) {
		Entities = entities;
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}


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
