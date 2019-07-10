package Controller;

import Model.Location.Coordinate;
import Model.Location.Location;
import Model.World;

import java.util.Iterator;
import java.util.Map;

public class LocationController {

    public LocationController(){}

    public void moveTo(String userid, String direction){
        for(Location location:World.getInstance().getLocations()) {
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

            if(direction == "a"){
                changeUserCoordinate(coordinate.getxPostion()-1,coordinate.getyPosition(),userid,location);
            }else if(direction == "d"){
                changeUserCoordinate(coordinate.getxPostion()+1,coordinate.getyPosition(),userid,location);
            }else if(direction == "w"){
                changeUserCoordinate(coordinate.getxPostion(),coordinate.getyPosition()-1,userid,location);
            }else if(direction == "s"){
                changeUserCoordinate(coordinate.getxPostion(),coordinate.getyPosition()+1,userid,location);
            }else
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
                location.changeUserLocation(userid, coordinate);
                return;
            }
        }

    }

    public void openDoor(String keyId){

    }
}
