package Controller.Command;

import Model.Location.Coordinate;
import Model.World;

public class MoveCommand implements Command {
    LocationController locationController;

    public MoveCommand(World world){
        this.locationController = new LocationController(world);
    }

    public String excute(String UserId,Coordinate c,String direction){
        return locationController.moveTo(UserId,c,direction);
    }

    /*LocationController locationController = new LocationController();

    public void excute(String direction){
        locationController.moveTo(direction);
    }*/
}
