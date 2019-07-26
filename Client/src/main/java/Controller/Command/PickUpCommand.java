package Controller.Command;

import Controller.Controller;
import Controller.ItemController;
import Controller.ClientMediator;
import Model.Location.Coordinate;

public class PickUpCommand implements Command {
	private ItemController itemController = null;
	private ClientMediator clientMediator = null;

	public PickUpCommand(Controller itemController2, ClientMediator clientMediator) {
		this.itemController = (ItemController) itemController2;
		this.clientMediator = (ClientMediator) clientMediator;
	}
	public void execute(Coordinate cor) {
		String uName = clientMediator.getUserName();
		itemController.pickUp(uName);
		clientMediator.addAction("pickUp");
	}
}
