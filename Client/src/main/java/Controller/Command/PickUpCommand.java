package Controller.Command;

import Controller.ItemController;

public class PickUpCommand implements Command {
	private ItemController itemController = null;

	public PickUpCommand(ItemController itemController) {
		this.itemController = itemController;
	}
	public void execute(String id) {
		// TODO Auto-generated method stub
		System.out.println("Pick up"+id);
	}
}
