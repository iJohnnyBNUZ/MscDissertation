package Controller.Command;

import Controller.LocationController;

public class OpenDoorCommand implements Command {
    LocationController locationController = new LocationController();

    public void excute(String keyId){
        locationController.openDoor(keyId);
    }
}
