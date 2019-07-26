package Controller.Command;

import Controller.ClientMediator;
import Controller.LocationController;

import java.io.IOException;

public class OpenDoorCommand implements Command {
    private LocationController locationController;
    private ClientMediator clientMediator;

    public OpenDoorCommand(LocationController locationController, ClientMediator clientMediator){
        this.locationController = locationController;
        this.clientMediator = clientMediator;
    }

    public void excute() throws IOException, ClassNotFoundException {
        System.out.println("opendoorCommand");
        locationController.openDoor(clientMediator.getClient().getUserName());
        //clientMediator.getClient().OpenDoor(commandString.toLowerCase());
        clientMediator.addAction("openDoor");
    }
}
