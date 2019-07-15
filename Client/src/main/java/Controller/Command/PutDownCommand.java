package Controller.Command;

import Controller.Controller;
import Controller.ItemController;
import Model.Location.Coordinate;

public class PutDownCommand implements Command {

	private ItemController itemController;
	//get current userID
	private String userID;

	public PutDownCommand(Controller controller){
		this.itemController = (ItemController) controller;
	}

	public void execute(String selectedItem){
		System.out.println("Item "+selectedItem+" is put down on this position");
		itemController.drop(selectedItem,userID);
    }
}
