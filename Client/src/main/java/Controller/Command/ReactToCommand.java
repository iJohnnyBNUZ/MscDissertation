package Controller.Command;

import Controller.ClientMediator;
import Controller.Controller;
import Controller.ReactToController;
import Network.Events.ReactToEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReactToCommand implements Command {
	//private CommunicationController communicationController = null;
	private ClientMediator clientMediator;
	private ReactToController reactToController;
	
	public ReactToCommand(Controller reactToController, ClientMediator clientMediator) {
		this.reactToController = (ReactToController) reactToController;
		this.clientMediator = clientMediator;
	}


	/**
	 * Invoke the communicateWith method and reactToEntity method in the ReactToController.
	 * Add the ReactToEvent to the queue if the user is really interacting with someone
	 */
	public void execute() {
		String userID = clientMediator.getUserName();
		String id = reactToController.communicateWith(userID);
		if(id != null){
			clientMediator.setReactTo(id);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String message = reactToController.reactToEntity(id,userID);
			String result = "You just interacted with " + id + ", so you " + message + " at " + df.format(new Date()).toString();
			clientMediator.setReactResult(result);
			clientMediator.getEventQueue().add(new ReactToEvent(id,userID));
		}
	}
}
