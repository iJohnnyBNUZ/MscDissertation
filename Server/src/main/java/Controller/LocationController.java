package Controller;

import Model.Item.Item;
import Model.Item.Key;
import Model.Location.Coordinate;
import Model.Location.Location;

public class LocationController {
    private GameMediator gameMediator;

    public LocationController(GameMediator gameMediator){
        this.gameMediator = gameMediator;
    }

    public void moveTo(String userid, String direction){
        //gameMediator.getWorld().getEntity(userid);
        Location entityLocation = gameMediator.getWorld().getEntityLocation(userid);

        Coordinate entityCoordinate = entityLocation.getEntities().get(userid);

            //can not find user
            if (entityCoordinate == null)
                return;

            switch (direction) {
                case "a":
                    changeUserCoordinate(entityCoordinate.getxPostion() - 1, entityCoordinate.getyPosition(), userid);
                    break;
                case "d":
                    changeUserCoordinate(entityCoordinate.getxPostion() + 1, entityCoordinate.getyPosition(), userid);
                    break;
                case "w":
                    changeUserCoordinate(entityCoordinate.getxPostion(), entityCoordinate.getyPosition() - 1, userid);
                    break;
                case "s":
                    changeUserCoordinate(entityCoordinate.getxPostion(), entityCoordinate.getyPosition() + 1, userid);
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
                gameMediator.getWorld().getEntity(userid).setCurrentLocation(gameMediator.getWorld().getLocations().get(indexOfCurrentLocation+1));

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
}
