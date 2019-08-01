package Controller.Command;

import Controller.CommunicationController;
import Controller.Controller;
import Controller.ClientMediator;
import Network.Events.CommunicationEvent;

public class CommunicationCommand implements Command {
	private CommunicationController communicationController = null;
	private ClientMediator clientMediator=null;
	
	public CommunicationCommand(Controller communicationController2, ClientMediator clientMediator) {
		this.communicationController = (CommunicationController) communicationController2;
		this.clientMediator = clientMediator;
	}

	//There are something wrong since the communication between two user and between user and shop will only
	//open the transaction view, the model won't changed, but the interaction between user and npc will change the user energy,
	//this action need to be added to the queue.
	public void execute() {
		if(communicationController.communicateWith())
			clientMediator.getEventQueue().add(new CommunicationEvent(clientMediator.getUserName(), communicationController.communicateWith()));
	}
}
