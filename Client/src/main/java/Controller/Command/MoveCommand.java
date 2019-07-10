package Controller.Command;

import Model.Location.Coordinate;
import Controller.LocationController;
import Model.World;

public class MoveCommand implements Command {
    LocationController locationController;

    public MoveCommand(World world){
        this.locationController = new LocationController(world);
    }

    public void excute(String direction){
        locationController.moveTo(direction.toLowerCase());
    }

}
