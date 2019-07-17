package Controller;

import Model.Item.Item;
import Model.Item.Key;
import Model.Location.Coordinate;
import Model.Location.Door;
import Model.Location.Location;
import Model.Location.Tile;

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
		String nextLocationId = null;
		for(Item item: gameMediator.getWorld().getEntity(userid).getBag()){
			if(item instanceof Key){
				key = (Key)item;
				if(!key.isUsed()){
					Location currentLocation = gameMediator.getWorld().getEntityLocation(userid);
					for (Tile tile:currentLocation.getTiles().values()){
						if(tile instanceof Door){
							Door door = (Door)tile;
							if (door.getCurrentLocationId() == currentLocation.getLocationID())
								nextLocationId = door.getNextLocationId();
						}
					}
					//set new location for user
					if (nextLocationId != null){
						gameMediator.getWorld().setEntityLocation(userid, nextLocationId);
						gameMediator.getWorld().getLocation(currentLocation.getLocationID()).removeEntity(userid);
						//change key status
						key.setUsed(true);
						System.out.println("The key is used now!");

						int positionX=0,positionY=0;
						for(Coordinate c: gameMediator.getWorld().getEntityLocation(userid).getTiles().keySet()){
							if(c.getxPostion() == positionX && c.getyPosition() == positionY){
								gameMediator.getWorld().getLocation(nextLocationId).addEntity(userid,c);
								System.out.println("USer->"+ userid+"open the door and moves to the new Location");

								return;
							}
						}
					}
				}
			}
		}
		System.out.println("There is no Key object in the bag!");
	}
}
