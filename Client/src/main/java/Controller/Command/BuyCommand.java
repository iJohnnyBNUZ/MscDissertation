package Controller.Command;

import Controller.Controller;
import Controller.ItemController;
import Model.Item.Item;

import java.util.HashMap;
import Controller.ClientMediator;
import Controller.MessageController;

public class BuyCommand implements Command {

	private MessageController messageController = null;
	private String userID;

    public BuyCommand(Controller messageController) {
    	this.messageController = (MessageController) messageController;
	}

	public void execute(String usershopName,HashMap<String,Integer> buyList, int buyValue){
		System.out.println("several items are bought, it costs "+ buyValue +" coins");
		//add queue
		messageController.buyMessage(usershopName,buyList,buyValue);
	}
}
