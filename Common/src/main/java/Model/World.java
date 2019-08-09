package Model;

import Model.Entity.Entity;
import Model.Location.Coordinate;
import Model.Location.Location;
import Utils.Observable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class World extends Observable implements Serializable {

	private ArrayList<Location> Locations = new ArrayList<Location>();
	private ArrayList<Entity> Entities = new ArrayList<Entity>();
	private List<String> messageList = new LinkedList<String>();

	public World(){

	}

	public List<Location> getLocations() {
		return Locations;
	}

	public List<Entity> getEntities() {
		return Entities;
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

	public Location getEntityLocation(String userName){
		Location location = null;
		for(Location l:this.getLocations()) {
			for(Map.Entry<Entity, Coordinate> entry : l.getEntities().entrySet()){
				if (entry.getKey().getEntityID().equals(userName)){
					location = l;
					break;
				}
			}
		}
		return location;
	}


	public void initEntityLocation(String userName){
		//this.setEntityLocation(userName, "location0");
		int max =2,min =0;
		int positionX = min + (int)(Math.random() * (max-min+1));
		int positionY = min + (int)(Math.random() * (max-min+1));

		for(Coordinate coordinate: this.getLocations().get(0).getTiles().keySet()){
			if(coordinate.getXCoordinate() == positionX && coordinate.getYCoordinate() == positionY){
				this.getLocations().get(0).addEntity(this.getEntity(userName),coordinate);
				System.out.println("gives user:"+userName+" an initial coordinate! ["+positionX+","+positionY+"]");
				break;
			}
		}
	}

	public Entity getEntity(String id) {
		for(Entity entity: Entities) {
			if (entity.getEntityID().equals(id)) {
				return entity;
			}
		}
		return null;
	}

	public Location getLocation(String locatoinID) {
		for(Location location: Locations) {
			if (location.getLocationID().equals(locatoinID)) {
				return location;
			}
		}
		return null;
	}

	public List<String> getMessageList(){
		return messageList;
	}

	public void addMessage(String message){
		messageList.add(message);
		notifyObserver();
	}

	public boolean equals(World newWorld){
		Boolean result = false;
		return result;
	}

}
