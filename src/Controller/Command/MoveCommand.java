package Controller.Command;

import Controller.LocationController;

public class MoveCommand implements Command {
    LocationController locationController = new LocationController();

    public void excute(String direction){
        locationController.moveTo(direction);
    }

}
