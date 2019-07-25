package Model;

import Model.Entity.Entity;
import Model.Entity.NPC;
import Model.Entity.User;
import Model.Item.Key;
import Model.Location.Coordinate;
import Model.Location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class World implements Serializable {

	private ArrayList<Location> Locations = new ArrayList<Location>();
	private ArrayList<Entity> Entities = new ArrayList<Entity>();
	private List<String> messageList = new LinkedList<String>();

	public World(){

	}

	public List<Location> getLocations() {
		return Locations;
	}

	public void setLocations(List<Location> locations) {
		for (Location newLocation: locations){
			for (Location localLocation: getLocations()){
				if (localLocation.getLocationID().equals(newLocation.getLocationID())){
					localLocation.setTiles(newLocation.getTiles());
					localLocation.setEntities(newLocation.getEntities());
					localLocation.setItems(newLocation.getItems());
				}
			}
		}
//		Locations = (ArrayList<Location>) locations;
	}

	public List<Entity> getEntities() {
		return Entities;
	}

	public void setEntities(List<Entity> entities) {
//		Entities = (ArrayList<Entity>) entities;
		for (Entity newEntity: entities){
			for (Entity localEntity: getEntities()){
				if (localEntity.getEntityID().equals(newEntity.getEntityID())){
					localEntity.setEnergy(newEntity.getEnergy());
					localEntity.setCoin(newEntity.getCoin());
					localEntity.setBag(newEntity.getBag());
				}
			}
		}
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
			if(l.getEntities().get(this.getEntity(userName)) != null){
				location = l;
				break;
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
			if(coordinate.getxPostion() == positionX && coordinate.getyPosition() == positionY){
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
	}

}
