package Controller.Command;


import Controller.ClientMediator;
import Controller.Controller;
import Controller.LocationController;
import Network.Events.MovementEvent;

import java.io.IOException;
import java.util.ResourceBundle;

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
        String uName = clientMediator.getClientUpdater().getUserName();
        System.out.println(uName+"Move to in Command-> "+direction);
        String message = locationController.moveTo(uName, direction.toLowerCase());
        if(message!=null){
            clientMediator.getView().showAlert(message);
        }else{
            clientMediator.getEventQueue().add(new MovementEvent(clientMediator.getUserName(), direction));
        }


    }

}
