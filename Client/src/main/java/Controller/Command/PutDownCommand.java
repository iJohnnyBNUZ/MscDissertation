package Controller.Command;

import Controller.Controller;
import Controller.ItemController;
import Controller.ClientMediator;

public class PutDownCommand implements Command {

	private ItemController itemController = null;
	private ClientMediator clientMediator = null;
	private String userID;

	public PutDownCommand(Controller controller,ClientMediator clientMediator){
		this.itemController = (ItemController) controller;
		this.clientMediator = clientMediator;
	}

	public void execute(String selectedItem){
		userID = clientMediator.getUserName();
		System.out.println("Item "+selectedItem+" is put down on this position by " + userID);
		itemController.drop(userID,selectedItem);
    }
}
