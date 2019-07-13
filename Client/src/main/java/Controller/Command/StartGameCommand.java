package Controller.Command;

import java.io.IOException;

import Controller.CommunicationController;
import Controller.UserController;

public class StartGameCommand {
	 private UserController userController = null;

	    public StartGameCommand(UserController userController){
	        this.userController = userController;
	    }

	    public void execute(String type, String uName, String IP) throws IOException, ClassNotFoundException{
	        //add messages to messageList in World
	    	userController.startGame(type,uName,IP);
	        System.out.println(uName+" is loged");
	    }
}
