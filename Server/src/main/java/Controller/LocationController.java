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
                    changeUserCoordinate(entityCoordinate.getxPostion() - 1, entityCoordinate.getyPosition(), userid, entityLocation);
                    break;
                case "d":
                    changeUserCoordinate(entityCoordinate.getxPostion() + 1, entityCoordinate.getyPosition(), userid, entityLocation);
                    break;
                case "w":
                    changeUserCoordinate(entityCoordinate.getxPostion(), entityCoordinate.getyPosition() - 1, userid, entityLocation);
                    break;
                case "s":
                    changeUserCoordinate(entityCoordinate.getxPostion(), entityCoordinate.getyPosition() + 1, userid, entityLocation);
                    break;
                default:
                    return;
            }

    }

    public void changeUserCoordinate(int positionx,int positiony,String userid,Location location){
        Coordinate coordinate;
        Iterator<Map.Entry<String, Coordinate>> iterator = location.getEntities().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Coordinate> entry = iterator.next();
            if (entry.getValue().getxPostion()==positionx && entry.getValue().getyPosition() == positiony) {
                coordinate = entry.getValue();
                location.changeUserCoordinate(userid, coordinate);
                return;
            }
        }

    }

    public void openDoor(String keyId){

    }
}
