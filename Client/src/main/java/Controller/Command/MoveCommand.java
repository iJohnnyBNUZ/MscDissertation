package Controller.Command;


import Controller.ClientMediator;
import Controller.Controller;
import Controller.LocationController;

import java.io.IOException;
import java.util.ResourceBundle;

public class MoveCommand implements Command {
	LocationController locationController;
    ClientMediator clientMediator;

    public MoveCommand(Controller locationController2, ClientMediator clientMediator){
        this.locationController = (LocationController) locationController2;
        this.clientMediator = clientMediator;
    }

    public void execute(String direction) throws IOException, ClassNotFoundException {
        // TODO pass userName to MoveTO()
    	ResourceBundle rb = ResourceBundle.getBundle("config");
		//String uName = rb.getString("userName");
        String uName = clientMediator.getClient().getUserName();
        System.out.println(uName+"Move to in Command-> "+direction);
        locationController.moveTo(uName, direction.toLowerCase());
		clientMediator.getClient().MoveTo(direction.toLowerCase());
    }

}
