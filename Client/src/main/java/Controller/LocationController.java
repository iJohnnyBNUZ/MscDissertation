package Controller;

import java.util.HashMap;
import java.util.Map;

import Model.Item.Item;
import Model.Item.Key;
import Model.Location.Coordinate;
import Model.Location.Location;

public class LocationController implements Controller{
    private GameMediator gameMediator;

    public LocationController(GameMediator gameMediator){
        this.gameMediator = gameMediator;
    }

    public void moveTo(String direction){
        String uName = gameMediator.getClient().getUserName();
        Location entityLocation = gameMediator.getWorld().getEntityLocation(uName);

        Coordinate entityCoordinate = entityLocation.getEntities().get(uName);

            //can not find user
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
                    return;
            }

    }

    public void changeUserCoordinate(int positionx,int positiony,String userid){

        for(Coordinate c:gameMediator.getWorld().getEntityLocation(userid).getTiles().keySet()){
            if(c.getxPostion() == positionx && c.getyPosition() == positiony){
                gameMediator.getWorld().getEntityLocation(userid).changeUserCoordinate(userid, c);
                return;
            }
        }

    }

    public void openDoor(String userid){
        for(Item item:gameMediator.getWorld().getEntity(userid).getBag()){
            if(item instanceof Key){
                //get currentLocation index
                int indexOfCurrentLocation = gameMediator.getWorld().getLocations().indexOf(gameMediator.getWorld().getEntityLocation(userid));
                //new location will be index+1 in the Location list
                //gameMediator.getWorld().getEntity(userid).setCurrentLocation(gameMediator.getWorld().getLocations().get(indexOfCurrentLocation+1));

                //initial the user in Coordinate(0,0) in the next Location
                int positionX=0,positionY=0;

                for(Coordinate c:gameMediator.getWorld().getLocations().get(indexOfCurrentLocation+1).getTiles().keySet()){
                    if(c.getxPostion() == positionX && c.getyPosition() == positionY){
                        gameMediator.getWorld().getLocations().get(indexOfCurrentLocation+1).addEntity(userid,c);
                        gameMediator.getWorld().getLocations().get(indexOfCurrentLocation).removeEntity(userid);
                        System.out.println("USer->"+ userid+"open the door and moves to the new Location");
                        //remove the used key from User's bag
                        gameMediator.getWorld().getEntity(userid).getBag().remove(item);
                        System.out.println("The key used has removed from bag!");
                        return;
                    }
                }
            }
        }
        System.out.println("There is no Key object in the bag!");
    }

	@Override
	public void update() {
		// TODO Auto-generated method stub
		String uId = gameMediator.getClient().getUserName();
		Location curLocation = gameMediator.getWorld().getEntityLocation(uId);
		Map<String,Coordinate> tiles = new HashMap<String,Coordinate>();
		
		for(Coordinate cor: curLocation.getTiles().keySet()) {
			tiles.put(curLocation.getTiles().get(cor).getTerrain(), cor);
		}
		
		gameMediator.getLocationView().update(tiles);
	}
}
