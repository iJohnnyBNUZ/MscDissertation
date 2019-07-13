package Controller.Command;

import Controller.Controller;
import Controller.ItemController;
import Model.Location.Coordinate;

public class PutDownCommand implements Command {

	private ItemController itemController = null;
    public PutDownCommand(Controller itemController2) {
		// TODO Auto-generated constructor stub
    	this.itemController = (ItemController) itemController2;
	}

	public void execute(String selectedItem){
        System.out.println("Item "+selectedItem+" is put down on this position");
    }
}
