package Controller.Command;

import Controller.Controller;
import Controller.ItemController;

public class BuyCommand implements Command {
	
	private ItemController itemController = null;
    public BuyCommand(Controller itemController2) {
		// TODO Auto-generated constructor stub
    	this.itemController = (ItemController) itemController2;
	}

	public void execute(String boughtItemId, int money){
        //current user's money decreases
        //current user's bag add item
        //shop or other user remove item
        System.out.println("item "+boughtItemId+" is bought, it costs "+ money +" coins");
    }
}
