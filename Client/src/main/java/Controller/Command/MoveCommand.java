package Controller.Command;


import Controller.LocationController;

public class MoveCommand implements Command {
    LocationController locationController;

    public MoveCommand(LocationController locationController){
        this.locationController = locationController;
    }

    public void excute(String direction){
        locationController.moveTo(direction.toLowerCase());
    }

}
