package Controller;

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

	public void moveTo(String uName, String direction){
		Location entityLocation = gameMediator.getWorld().getEntityLocation(uName);

		Coordinate entityCoordinate = entityLocation.getEntities().get(uName);

		if (entityCoordinate == null)
			return;

		switch (direction) {
			case "left":
				changeUserCoordinate(entityCoordinate.getxPostion() - 1, entityCoordinate.getyPosition(), uName);
				break;
			case "right":
				changeUserCoordinate(entityCoordinate.getxPostion() + 1, entityCoordinate.getyPosition(), uName);
				break;
			case "up":
				changeUserCoordinate(entityCoordinate.getxPostion(), entityCoordinate.getyPosition() - 1, uName);
				break;
			case "down":
				changeUserCoordinate(entityCoordinate.getxPostion(), entityCoordinate.getyPosition() + 1, uName);
				break;
			default:
		}

	}

	public void changeUserCoordinate(int positionx,int positiony,String userid){

		for(Coordinate c: gameMediator.getWorld().getEntityLocation(userid).getTiles().keySet()){
			if(c.getxPostion() == positionx && c.getyPosition() == positiony){
				gameMediator.getWorld().getEntityLocation(userid).changeUserCoordinate(userid, c);
				return;
			}
		}

	}

	public void openDoor(String userid){
		Key key;
		Door door = null;
		Location currentLocation = gameMediator.getWorld().getEntityLocation(userid);

		//find the door for nextLocation
		for (Tile tile:currentLocation.getTiles().values()){
			if(tile instanceof Door){
				if (((Door)tile).getCurrentLocationId() == currentLocation.getLocationID()){
					door = (Door)tile;
					break;
				}

			}
		}

		if(door != null){
			if (((User)gameMediator.getWorld().getEntity(userid)).getOpenedDoors().contains(door.getDoorId())){
				//user have already opened this door,only to give a initial coordinate in next location
				moveUserToNextLocation(door,userid);
				return;
			}else{
				for(Item item: gameMediator.getWorld().getEntity(userid).getBag()){
					if(item instanceof Key){
						key = (Key)item;
						if(!key.isUsed()){
							moveUserToNextLocation(door,userid);
							//change key status
							key.setUsed(true);
							//add opened door for user
							((User)gameMediator.getWorld().getEntity(userid)).addOpenedDoors(door.getDoorId());
							System.out.println("The key is used now!");
						}
					}
				}
				System.out.println("There is no Key object in the bag!");
			}
		}else
			System.out.println("door is none!");
		}


	public void moveUserToNextLocation(Door door,String userid){
		if(door != null){
			String nextLocationId = door.getNextLocationId();
			Coordinate appearDoor = null;

			//set new location for user
			if (nextLocationId != null){
				gameMediator.getWorld().setEntityLocation(userid, nextLocationId);
				gameMediator.getWorld().getLocation(door.getCurrentLocationId()).removeEntity(userid);

				int positionX=0,positionY=0;
				//initial user coordinate to nextLocation, near the door
				for (Map.Entry<Coordinate, Tile> entry : gameMediator.getWorld().getLocation(nextLocationId).getTiles().entrySet()) {
					if(entry.getValue() instanceof Door){
						if(((Door)entry.getValue()).getNextLocationId() == door.getCurrentLocationId()){
							appearDoor = entry.getKey();
							break;
						}
					}
				}
				if (appearDoor != null){
					positionX = appearDoor.getxPostion()+1;
					positionY = appearDoor.getyPosition();
				}

				for(Coordinate c: gameMediator.getWorld().getEntityLocation(userid).getTiles().keySet()){
					if(c.getxPostion() == positionX && c.getyPosition() == positionY){
						gameMediator.getWorld().getLocation(nextLocationId).addEntity(userid,c);
						System.out.println("USer->"+ userid+"open the door and moves to the new Location");

						return;
					}
				}
			}
		}else
			System.out.println("Door is null!");

	}
}
