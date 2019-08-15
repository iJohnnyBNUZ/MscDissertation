package Controller;

import Model.Entity.Entity;
import Model.Entity.User;
import Model.Item.Item;
import Model.Item.Key;
import Model.Location.Coordinate;
import Model.Location.Door;
import Model.Location.Location;
import Model.Location.Tile;

import java.util.Map;

public class LocationController implements Controller{
	private GameMediator gameMediator;

	public LocationController(GameMediator gameMediator){
		this.gameMediator = gameMediator;
	}

	/**
	 * check user energy and calculate next position(x,y) by direction move to
	 * @param userID input userId
	 * @param direction input direction user what to move
	 * @return return reasons why user can not move. If move successfully, return null
	 */
	public String moveTo(String userID, String direction){
		//check if user exist,avoid NULLPointException
	    if(gameMediator.getWorld().getEntity(userID)==null)
	        return "Cannot find user";

	    //get user location
		Location entityLocation = gameMediator.getWorld().getEntityLocation(userID);

		//get current coordinate
		Coordinate entityCoordinate = null;
		Entity user = null;
		for ( Map.Entry<Entity, Coordinate> coordinateMap: entityLocation.getEntities().entrySet()){
			if (coordinateMap.getKey().getEntityID().equals(userID)) {
			    user = coordinateMap.getKey();
				entityCoordinate = coordinateMap.getValue();
			}
		}

		//check user energy, if energy don not enough, can not move
		if (user.getEnergy() <= 0){
		    return "There is no energy! Game Over!";
        }
		//check whether they have coordinate, avoid NULLPointException
		if (entityCoordinate == null)
			return "Cannot find user's coordinate";

		//calculate coordinate (x,y) for next tile by direction and call changeUserCoordinate to change user's to next tile
		if(direction.equals("a") || direction.equals("left")){
			changeUserCoordinate(entityCoordinate.getXCoordinate() , entityCoordinate.getYCoordinate()-1, userID);
		}else if(direction.equals("d")|| direction.equals("right")){
			changeUserCoordinate(entityCoordinate.getXCoordinate() , entityCoordinate.getYCoordinate()+1, userID);
		}else if (direction.equals("w") || direction.equals("up")){
			changeUserCoordinate(entityCoordinate.getXCoordinate()-1, entityCoordinate.getYCoordinate() , userID);
		}else if (direction.equals("s") || direction.equals("down")){
			changeUserCoordinate(entityCoordinate.getXCoordinate()+1, entityCoordinate.getYCoordinate() , userID);
		}

		//if move to action successful, return null
		return null;
	}

	/**
	 * check if next tile is movable, if so, change user's tile
	 * @param xCoordinate input coordinate x for next tile
	 * @param yCoordinate input coordinate y for next tile
	 * @param userID input userID
	 */
	void changeUserCoordinate(int xCoordinate, int yCoordinate, String userID){
		//get current location
		Location location = gameMediator.getWorld().getEntityLocation(userID);
		//find coordinate by x and y
		for(Coordinate c: location.getTiles().keySet()){
			if(c.getXCoordinate() == xCoordinate && c.getYCoordinate() == yCoordinate){
				//check if next tile movable
				if (location.getTiles().get(c).isMovable()){
					//movable, change user to next tile
					gameMediator.getWorld().getEntityLocation(userID).changeUserCoordinate(gameMediator.getWorld().getEntity(userID), c);
					//decrease energy cost for this tile
					gameMediator.getWorld().getEntity(userID).decreaseEnergy(location.getTiles().get(c).getEnergyCost());
				}
			}
		}

	}

	/**
	 * user open door and change it to next location
	 * @param userID input userID
	 * @return return reasons why user can not open door. If action successfully, return null
	 */
	public String openDoor(String userID){
		// a key need to open door
		Key key;
		// the door need to open
		Door door = null;
		//current location for user
		Location currentLocation = gameMediator.getWorld().getEntityLocation(userID);
		//coordinate for user
		Coordinate coordinate = currentLocation.getEntities().get(gameMediator.getWorld().getEntity(userID));

		//check if user have enough energy
		if (gameMediator.getWorld().getEntity(userID).getEnergy() <= 0){
			return "There is no energy!";
		}

		//find the door object
		if(currentLocation.getTiles().get(coordinate) instanceof Door){
			if (((Door)currentLocation.getTiles().get(coordinate)).getCurrentLocationId().equals(currentLocation.getLocationID())){
				door = (Door)currentLocation.getTiles().get(coordinate);
			}
		}

		//check if door exist, avoid NULLPointException
		if(door != null){
			//check if user opened this door before, if so, they can move to next location directly
			if (((User)gameMediator.getWorld().getEntity(userID)).getOpenedDoors().contains(door.getNextLocationId())){
				//user have already opened this door,only to give a initial coordinate in next location
				moveUserToNextLocation(door,userID);
				//add current location to opened doors
				((User)gameMediator.getWorld().getEntity(userID)).addOpenedDoors(door.getCurrentLocationId());

				return null;
			}else{
				//not a opened door, for new door, key item is required
				//get user's bag
				for(Item item: gameMediator.getWorld().getEntity(userID).getBag()){
					//find key in bag
					if(item instanceof Key){
						key = (Key)item;
						//check status of key
						if(!key.isUsed()){
							//unused key, open the door, change user's location by call moveUserToNextLocation method
							moveUserToNextLocation(door,userID);
							//change key status
							key.setUsed(true);
							//add opened door for user
							((User)gameMediator.getWorld().getEntity(userID)).addOpenedDoors(door.getCurrentLocationId());

							return null;
						}
					}
				}
				//return wrong reason to notify user
				return "The keys in bag are all used or there is no key in the bag!";
			}
		}else
			return null;
		}

	/**
	 * change user to next location with a coordinate near the door
	 * @param door input door tile user opens
	 * @param userID input userID
	 */
	void moveUserToNextLocation(Door door,String userID){
		//check if door exist, avoid NULLPointException
		if(door != null){
			//get next location id
			String nextLocationId = door.getNextLocationId();
			Coordinate appearDoor = null;

			//set new location for user
			//check if next location exist, avoid NULLPointException
			if (nextLocationId != null){
				//get current location
				Location currentLocation = gameMediator.getWorld().getLocation(door.getCurrentLocationId());
				//remove user from current location
				currentLocation.removeEntity(gameMediator.getWorld().getEntity(userID));

				//coordinate for user in next location
				int coordinateX=0,coordinateY=0;

				//get next location object
				Location nextLocation;
				if(gameMediator.getWorld().getLocation(nextLocationId) != null)
					nextLocation = gameMediator.getWorld().getLocation(nextLocationId);
				else
					return;

				//get door that user will appear in next location that related with door in current location
				for (Map.Entry<Coordinate, Tile> entry : nextLocation.getTiles().entrySet()) {
					if(entry.getValue() instanceof Door){
						if(((Door)entry.getValue()).getNextLocationId().equals(door.getCurrentLocationId())){
							appearDoor = entry.getKey();
							break;
						}
					}
				}
				//check if user appearDoor exist, get coordinate near the door
				if (appearDoor != null){
					coordinateX = appearDoor.getXCoordinate();
					coordinateY = appearDoor.getYCoordinate()+1;
				}

				//initial user coordinate to next Location, near the door
				for(Coordinate c: nextLocation.getTiles().keySet()){
					//find coordinate with x,y
					if((c.getXCoordinate() == coordinateX && c.getYCoordinate() == coordinateY) || (c.getXCoordinate() == coordinateX && c.getYCoordinate() == coordinateY-2)){
						//check if this tile is movable
						if(nextLocation.getTiles().get(c).isMovable()){
							//add user in entity list in next location
							gameMediator.getWorld().getLocation(nextLocationId).addEntity(gameMediator.getWorld().getEntity(userID),c);
							System.out.println("USer->"+ userID+" open the door and moves to the new Location");
							//decrease user energy
							if(gameMediator.getWorld().getEntity(userID).getEnergy()<=nextLocation.getTiles().get(c).getEnergyCost())
								//if user exhause energy, change energy to 0
								gameMediator.getWorld().getEntity(userID).setEnergy(0);
							else
								gameMediator.getWorld().getEntity(userID).decreaseEnergy(nextLocation.getTiles().get(c).getEnergyCost());

							return;
						}
					}
				}
			}
		}else
			return;
	}
}
