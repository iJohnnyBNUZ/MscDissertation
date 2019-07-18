package Controller.Command;

import Controller.Controller;
import Controller.ItemController;
import Model.Item.Item;

import java.util.HashMap;
import java.util.List;
import Controller.ClientMediator;

public class SellCommand implements Command {

    private ItemController itemController = null;
    private ClientMediator clientMediator = null;
    private String userID;

    public SellCommand(Controller itemController2, ClientMediator clientMediator) {
		// TODO Auto-generated constructor stub
    	this.itemController = (ItemController) itemController2;
    	this.clientMediator = clientMediator;
	}

    public void execute(String usershopName, HashMap<String, Integer> sellList, int sellVale){
        //current user's money increases
        //current user remove item
        //shop or other user add item
        userID = clientMediator.getUserName();
        System.out.println("items are sold by " + userID + ", it earns "+ sellVale +" coins");
        itemController.sellItems(userID,usershopName,sellList,sellVale);
    }
}
