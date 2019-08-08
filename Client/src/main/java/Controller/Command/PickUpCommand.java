package Controller.Command;

import Controller.ClientMediator;
import Controller.Controller;
import Controller.ItemController;
import Network.Events.PickUpEvent;

public class PickUpCommand implements Command {
	private ItemController itemController = null;
	private ClientMediator clientMediator = null;

	public PickUpCommand(Controller itemController2, ClientMediator clientMediator) {
		this.itemController = (ItemController) itemController2;
		this.clientMediator = clientMediator;
	}

	/**
	 * Invoke the pickUp method in the itemController.
	 Add the PickUpEvent to the queue if this action is processed successfully.
	 */
	public void execute() {
		String uName = clientMediator.getUserName();
		String message=itemController.pickUp(uName);
		if(message == null){
			clientMediator.getEventQueue().add(new PickUpEvent(clientMediator.getUserName()));
		}

	}
}
