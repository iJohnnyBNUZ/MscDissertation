package Controller.Command;

import Controller.Controller;
import Controller.UserController;

import java.io.IOException;

public class StartGameCommand implements Command {
		private UserController userController = null;

	    public StartGameCommand(Controller userController2){
	        this.userController = (UserController) userController2;
	    }

	/**
	 * Invoke the startGame method in the userController.
	 * @param type selected game type
	 * @param uName input username
	 * @param IP input IP address
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	    public void execute(String type, String uName, String IP) throws IOException, ClassNotFoundException{
	    	userController.startGame(type,uName,IP);
	    }
}
