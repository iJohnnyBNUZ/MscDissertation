package Controller.Command;

import Controller.Controller;
import Controller.ClientMediator;
import Controller.ReactToController;
import Network.Events.ReactToEvent;

public class ReactToCommand implements Command {
	//private CommunicationController communicationController = null;
	private ClientMediator clientMediator = null;
	private ReactToController reactToController = null;
	
	public ReactToCommand(Controller reactToController, ClientMediator clientMediator) {
		this.reactToController = (ReactToController) reactToController;
		this.clientMediator = clientMediator;
	}

	//There are something wrong since the communication between two user and between user and shop will only
	//open the transaction view, the model won't changed, but the interaction between user and npc will change the user energy,
	//this action need to be added to the queue.
	public void execute() {
		String userID = clientMediator.getUserName();
		String id = reactToController.communicateWith(userID);
		if(id != null){
			clientMediator.setReactTo(id);
			reactToController.reactToEntity(userID);
		    if(id.replaceAll("[0-9]","").equals("npc")){
			   clientMediator.getEventQueue().add(new ReactToEvent(id,userID));
		    }
		}
	}
}
