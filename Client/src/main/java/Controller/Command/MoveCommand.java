package Controller.Command;


import Controller.Controller;
import Controller.LocationController;

public class MoveCommand implements Command {
	LocationController locationController;

    public MoveCommand(Controller locationController2){
        this.locationController = (LocationController) locationController2;
    }

    public void excute(String direction){
        locationController.moveTo(direction.toLowerCase());
    }

}
