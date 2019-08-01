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
		this.clientMediator = (ClientMediator) clientMediator;
	}
	public void execute() {
		String uName = clientMediator.getUserName();
		itemController.pickUp(uName);
		clientMediator.getEventQueue().add(new PickUpEvent(clientMediator.getUserName()));
	}
}
