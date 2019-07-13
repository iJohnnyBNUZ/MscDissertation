package Controller.Command;

import Controller.Controller;
import Controller.ItemController;

public class PickUpCommand implements Command {
	private ItemController itemController = null;

	public PickUpCommand(Controller itemController2) {
		this.itemController = (ItemController) itemController2;
	}
	public void execute(String id) {
		// TODO Auto-generated method stub
		System.out.println("Pick up"+id);
	}
}
