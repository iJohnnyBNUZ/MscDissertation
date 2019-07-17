package Controller.Command;


import java.util.ResourceBundle;

import Controller.Controller;
import Controller.LocationController;

public class MoveCommand implements Command {
	LocationController locationController;

    public MoveCommand(Controller locationController2){
        this.locationController = (LocationController) locationController2;
    }

    public void excute(String direction){
        // TODO pass userName to MoveTO()
    	ResourceBundle rb = ResourceBundle.getBundle("config");
		String uName = rb.getString("userName");
        locationController.moveTo(uName, direction.toLowerCase());
    }

}
