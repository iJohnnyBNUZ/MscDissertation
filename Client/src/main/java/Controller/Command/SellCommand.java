package Controller.Command;

import Controller.Controller;
import Controller.ItemController;

public class SellCommand implements Command {

    private ItemController itemController = null;
    public SellCommand(Controller itemController2) {
		// TODO Auto-generated constructor stub
    	this.itemController = (ItemController) itemController2;
	}
	public void execute(String soldItemId, int money){
        //current user's money increases
        //current user remove item
        //shop or other user add item
        System.out.println("item "+soldItemId+" is sold, it earns "+ money +" coins");
    }
}
