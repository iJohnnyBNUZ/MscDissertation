package Controller.Command;

import Controller.LocationController;

public class OpenDoorCommand implements Command {
    LocationController locationController;

    public OpenDoorCommand(LocationController locationController){
        this.locationController = locationController;
    }

    public void excute(String keyId){
        locationController.openDoor(keyId);
    }
}
