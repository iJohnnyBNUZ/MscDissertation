package Controller.Command;

import java.io.IOException;

import Controller.CommunicationController;
import Controller.Controller;
import Controller.UserController;

public class StartGameCommand {
	 private UserController userController = null;

	    public StartGameCommand(Controller userController2){
	        this.userController = (UserController) userController2;
	    }

	    public void execute(String type, String uName, String IP) throws IOException, ClassNotFoundException{
	        //add messages to messageList in World
	    	userController.startGame(type,uName,IP);
	    }
}
