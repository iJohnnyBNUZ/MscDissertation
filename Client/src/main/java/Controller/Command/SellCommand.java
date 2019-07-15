package Controller.Command;

import Controller.Controller;
import Controller.ItemController;
import Model.Item.Item;

import java.util.HashMap;
import java.util.List;

public class SellCommand implements Command {

    private ItemController itemController = null;
    public SellCommand(Controller itemController2) {
		// TODO Auto-generated constructor stub
    	this.itemController = (ItemController) itemController2;
	}

    public void execute(String usershopName, HashMap<String, Integer> sellList, int sellVale){
        //current user's money increases
        //current user remove item
        //shop or other user add item
        System.out.println("items are sold, it earns "+ sellVale +" coins");
        itemController.sellItems(usershopName,sellList,sellVale);
    }
}
