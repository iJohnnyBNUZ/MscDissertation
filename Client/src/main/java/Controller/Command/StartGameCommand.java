package Controller.Command;

import Controller.CommunicationController;
import Controller.UserController;

public class StartGameCommand {
	 private UserController userController = null;

	    public StartGameCommand(UserController userController){
	        this.userController = userController;
	    }

	    public void execute(String type, String uName, String IP){
	        //add messages to messageList in World
	    	userController.startGame(type,uName,IP);
	        System.out.println(uName+" is loged");
	    }
}
