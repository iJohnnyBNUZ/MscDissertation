package Controller;

import Model.Location.Coordinate;
import Model.Location.Location;
import java.util.Iterator;
import java.util.Map;

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

    public void openDoor(String keyId){

    }
}
