package Controller;

import Model.Item.Item;
import Model.Item.Key;
import Model.Location.Coordinate;
import Model.Location.Location;

public class LocationController {
    private ServerMediator serverMediator;

    public LocationController(ServerMediator serverMediator){
        this.serverMediator = serverMediator;
    }

    public void moveTo(String userid, String direction){
        //serverMediator.getWorld().getEntity(userid);
        Location entityLocation = serverMediator.getWorld().getEntityLocation(userid);

        Coordinate entityCoordinate = entityLocation.getEntities().get(userid);

            //can not find user
            if (entityCoordinate == null)
                return;

            switch (direction) {
                case "left":
                    changeUserCoordinate(entityCoordinate.getxPostion() - 1, entityCoordinate.getyPosition(), userid);
                    break;
                case "right":
                    changeUserCoordinate(entityCoordinate.getxPostion() + 1, entityCoordinate.getyPosition(), userid);
                    break;
                case "up":
                    changeUserCoordinate(entityCoordinate.getxPostion(), entityCoordinate.getyPosition() - 1, userid);
                    break;
                case "down":
                    changeUserCoordinate(entityCoordinate.getxPostion(), entityCoordinate.getyPosition() + 1, userid);
                    break;
                default:
            }

    }

    public void changeUserCoordinate(int positionx,int positiony,String userid){

        for(Coordinate c: serverMediator.getWorld().getEntityLocation(userid).getTiles().keySet()){
            if(c.getxPostion() == positionx && c.getyPosition() == positiony){
                serverMediator.getWorld().getEntityLocation(userid).changeUserCoordinate(userid, c);
                return;
            }
        }
    }

    public void openDoor(String userid){
        for(Item item: serverMediator.getWorld().getEntity(userid).getBag()){
            if(item instanceof Key){
                //get currentLocation index
                int indexOfCurrentLocation = serverMediator.getWorld().getLocations().indexOf(serverMediator.getWorld().getEntityLocation(userid));
                //new location will be index+1 in the Location list
                //initial the user in Coordinate(0,0) in the next Location
                int positionX=0,positionY=0;

                for(Coordinate c: serverMediator.getWorld().getLocations().get(indexOfCurrentLocation+1).getTiles().keySet()){
                    if(c.getxPostion() == positionX && c.getyPosition() == positionY){
                        serverMediator.getWorld().getLocations().get(indexOfCurrentLocation+1).addEntity(userid,c);
                        serverMediator.getWorld().getLocations().get(indexOfCurrentLocation).removeEntity(userid);
                        System.out.println("USer->"+ userid+"open the door and moves to the new Location");
                        //remove the used key from User's bag
                        serverMediator.getWorld().getEntity(userid).getBag().remove(item);
                        System.out.println("The key used has removed from bag!");
                        return;
                    }
                }
            }
        }
        System.out.println("There is no Key object in the bag!");
    }
}
