package Controller;

import Model.Entity.Entity;
import Model.Entity.User;
import Model.Item.Item;
import Model.Item.Key;
import Model.Location.Coordinate;
import Model.Location.Door;
import Model.Location.Location;
import Model.Location.Tile;

import java.util.HashMap;
import java.util.Map;

public class LocationController implements Controller{
	private GameMediator gameMediator;

	public LocationController(GameMediator gameMediator){
		this.gameMediator = gameMediator;
	}

	public String moveTo(String uName, String direction){
	    if(gameMediator.getWorld().getEntity(uName)==null)
	        return "Cannot find user";

		Location entityLocation = gameMediator.getWorld().getEntityLocation(uName);

		Coordinate entityCoordinate = null;
		Entity user = null;
		for ( Map.Entry<Entity, Coordinate> coordinateMap: entityLocation.getEntities().entrySet()){
			if (coordinateMap.getKey().getEntityID().equals(uName)) {
			    user = coordinateMap.getKey();
				entityCoordinate = coordinateMap.getValue();
			}
		}

		if (user.getEnergy() <= 0){
		    return "There is no energy!";
        }
		if (entityCoordinate == null)
			return "Cannot find user's coordinate";

		switch (direction) {
			case "a":
				changeUserCoordinate(entityCoordinate.getxPostion() , entityCoordinate.getyPosition()-1, uName);
				break;
			case "d":
				changeUserCoordinate(entityCoordinate.getxPostion() , entityCoordinate.getyPosition()+1, uName);
				break;
			case "w":
				changeUserCoordinate(entityCoordinate.getxPostion()-1, entityCoordinate.getyPosition() , uName);
				break;
			case "s":
				changeUserCoordinate(entityCoordinate.getxPostion()+1, entityCoordinate.getyPosition() , uName);
				break;

			case "left":
				changeUserCoordinate(entityCoordinate.getxPostion() , entityCoordinate.getyPosition()-1, uName);
				break;
			case "right":
				changeUserCoordinate(entityCoordinate.getxPostion() , entityCoordinate.getyPosition()+1, uName);
				break;
			case "up":
				changeUserCoordinate(entityCoordinate.getxPostion()-1, entityCoordinate.getyPosition() , uName);
				break;
			case "down":
				changeUserCoordinate(entityCoordinate.getxPostion()+1, entityCoordinate.getyPosition() , uName);
				break;
			default:
		}

		return null;
	}

	public void changeUserCoordinate(int xCoordinate, int yCoordinate, String userID){
		Location location = gameMediator.getWorld().getEntityLocation(userID);
		for(Coordinate c: location.getTiles().keySet()){
			if(c.getxPostion() == xCoordinate && c.getyPosition() == yCoordinate){
				if (location.getTiles().get(c).isMovable()){
					gameMediator.getWorld().getEntityLocation(userID).changeUserCoordinate(gameMediator.getWorld().getEntity(userID), c);
					gameMediator.getWorld().getEntity(userID).decreaseEnergy(location.getTiles().get(c).getEnergyCost());
				}
			}
		}

	}

	public String openDoor(String userid){
		Key key;
		Door door = null;
		Location currentLocation = gameMediator.getWorld().getEntityLocation(userid);
		Coordinate coordinate = currentLocation.getEntities().get(gameMediator.getWorld().getEntity(userid));

		if (gameMediator.getWorld().getEntity(userid).getEnergy() <= 0){
			return "There is no energy!";
		}

		//find the door for nextLocation
		if(currentLocation.getTiles().get(coordinate) instanceof Door){
			if (((Door)currentLocation.getTiles().get(coordinate)).getCurrentLocationId().equals(currentLocation.getLocationID())){
				door = (Door)currentLocation.getTiles().get(coordinate);
			}
		}

		if(door != null){
			if (((User)gameMediator.getWorld().getEntity(userid)).getOpenedDoors().contains(door.getNextLocationId())){
				//user have already opened this door,only to give a initial coordinate in next location
				moveUserToNextLocation(door,userid);
				((User)gameMediator.getWorld().getEntity(userid)).addOpenedDoors(door.getCurrentLocationId());
				return null;
			}else{
				for(Item item: gameMediator.getWorld().getEntity(userid).getBag()){
					if(item instanceof Key){
						key = (Key)item;
						if(!key.isUsed()){
							moveUserToNextLocation(door,userid);
							//change key status
							key.setUsed(true);
							//add opened door for user
							((User)gameMediator.getWorld().getEntity(userid)).addOpenedDoors(door.getCurrentLocationId());

							return null;
						}
					}
				}
				return "The keys in bag are all used or there is no key in the bag!";
			}
		}else
			return null;
		}


	public void moveUserToNextLocation(Door door,String userid){
		if(door != null){
			String nextLocationId = door.getNextLocationId();
			Coordinate appearDoor = null;

			//set new location for user
			if (nextLocationId != null){
				Location currentLocation = gameMediator.getWorld().getLocation(door.getCurrentLocationId());
				//gameMediator.getWorld().setEntityLocation(userid, nextLocationId);
				currentLocation.removeEntity(gameMediator.getWorld().getEntity(userid));

				int positionX=0,positionY=0;
				//initial user coordinate to nextLocation, near the door
				Location nextLocation;
				if(gameMediator.getWorld().getLocation(nextLocationId) != null)
					nextLocation = gameMediator.getWorld().getLocation(nextLocationId);
				else
					return;

				for (Map.Entry<Coordinate, Tile> entry : nextLocation.getTiles().entrySet()) {
					if(entry.getValue() instanceof Door){
						if(((Door)entry.getValue()).getNextLocationId().equals(door.getCurrentLocationId())){
							appearDoor = entry.getKey();
							break;
						}
					}
				}
				if (appearDoor != null){
					positionX = appearDoor.getxPostion();
					positionY = appearDoor.getyPosition()+1;
				}

				for(Coordinate c: nextLocation.getTiles().keySet()){
					if(c.getxPostion() == positionX && c.getyPosition() == positionY){
						if(nextLocation.getTiles().get(c).isMovable()){
							gameMediator.getWorld().getLocation(nextLocationId).addEntity(gameMediator.getWorld().getEntity(userid),c);
							System.out.println("USer->"+ userid+" open the door and moves to the new Location");
							//decrease user energy
							if(gameMediator.getWorld().getEntity(userid).getEnergy()<=nextLocation.getTiles().get(c).getEnergyCost())
								gameMediator.getWorld().getEntity(userid).setEnergy(0);
							else
								gameMediator.getWorld().getEntity(userid).decreaseEnergy(nextLocation.getTiles().get(c).getEnergyCost());

							return;
						}
					}else if(c.getxPostion() == positionX && c.getyPosition() == positionY-2){
						if(nextLocation.getTiles().get(c).isMovable()) {
							gameMediator.getWorld().getLocation(nextLocationId).addEntity(gameMediator.getWorld().getEntity(userid), c);
							System.out.println("USer->" + userid + "open the door and moves to the new Location");
							//decrease user energy
							if(gameMediator.getWorld().getEntity(userid).getEnergy()<=nextLocation.getTiles().get(c).getEnergyCost())
								gameMediator.getWorld().getEntity(userid).setEnergy(0);
							else
								gameMediator.getWorld().getEntity(userid).decreaseEnergy(nextLocation.getTiles().get(c).getEnergyCost());

							return;
						}
					}
				}
			}
		}else
			System.out.println("Door is null!");

	}
}
