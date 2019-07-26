package Controller.Command;


import java.io.IOException;
import java.util.ResourceBundle;

import Controller.Controller;
import Controller.LocationController;
import Controller.Network.Client;
import Controller.ClientMediator;

public class MoveCommand implements Command {
	LocationController locationController;
    ClientMediator clientMediator;

    public MoveCommand(Controller locationController2, ClientMediator clientMediator){
        this.locationController = (LocationController) locationController2;
        this.clientMediator = clientMediator;
    }

    public void excute(String direction) throws IOException, ClassNotFoundException {
    	ResourceBundle rb = ResourceBundle.getBundle("config");
		//String uName = rb.getString("userName");
        String uName = clientMediator.getClient().getUserName();
        System.out.println(uName+"Move to in Command-> "+direction);
        locationController.moveTo(uName, direction.toLowerCase());
		//clientMediator.getClient().MoveTo(direction.toLowerCase());
        clientMediator.addAction(direction.toLowerCase());
    }

}
