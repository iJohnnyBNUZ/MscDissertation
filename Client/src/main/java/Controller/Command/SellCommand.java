package Controller.Command;

import Controller.Controller;
import Controller.ItemController;
import Model.Item.Item;

import java.util.HashMap;
import Controller.ClientMediator;
import Controller.MessageController;

public class SellCommand implements Command {

    private MessageController messageController = null;
    private String userID;

    public SellCommand(Controller messageController) {
    	this.messageController = (MessageController) messageController;
	}

    public void execute(String usershopName, HashMap<String, Integer> sellList, int sellVale){
        //current user's money increases
        //current user remove item
        //shop or other user add item
        System.out.println("items are sold, it earns "+ sellVale +" coins");
        messageController.sellMessage(usershopName,sellList,sellVale);
    }
}
