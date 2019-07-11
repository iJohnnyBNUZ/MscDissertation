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
        for(Location location:gameMediator.getWorld().getLocations()) {
            Coordinate coordinate = null;
            Iterator<Map.Entry<String, Coordinate>> iterator = location.getEntities().entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Coordinate> entry = iterator.next();
                if (entry.getKey().equals(userid)) {
                    coordinate = entry.getValue();
                    break;
                }
            }
            //can not find user
            if (coordinate == null)
                return;

            switch (direction) {
                case "a":
                    changeUserCoordinate(coordinate.getxPostion() - 1, coordinate.getyPosition(), userid, location);
                    break;
                case "d":
                    changeUserCoordinate(coordinate.getxPostion() + 1, coordinate.getyPosition(), userid, location);
                    break;
                case "w":
                    changeUserCoordinate(coordinate.getxPostion(), coordinate.getyPosition() - 1, userid, location);
                    break;
                case "s":
                    changeUserCoordinate(coordinate.getxPostion(), coordinate.getyPosition() + 1, userid, location);
                    break;
                default:
                    return;
            }

        }
    }

    public void changeUserCoordinate(int positionx,int positiony,String userid,Location location){
        Coordinate coordinate;
        Iterator<Map.Entry<String, Coordinate>> iterator = location.getEntities().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Coordinate> entry = iterator.next();
            if (entry.getValue().getxPostion()==positionx && entry.getValue().getyPosition() == positiony) {
                coordinate = entry.getValue();
                location.changeUserLocation(userid, coordinate);
                return;
            }
        }

    }

    public void openDoor(String keyId){

    }
}
