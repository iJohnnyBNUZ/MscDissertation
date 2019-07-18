package Controller.Command;

import Controller.Controller;
import Controller.ItemController;
import Model.Item.Item;

import java.util.HashMap;
import Controller.ClientMediator;

public class BuyCommand implements Command {
	
	private ItemController itemController = null;
	private ClientMediator clientMediator = null;
	private String userID;

    public BuyCommand(Controller itemController2,ClientMediator clientMediator) {
		// TODO Auto-generated constructor stub
    	this.itemController = (ItemController) itemController2;
    	this.clientMediator = clientMediator;
	}

	public void execute(String usershopName,HashMap<String,Integer> buyList, int buyValue){
    	userID = clientMediator.getUserName();
		System.out.println("several items are bought by " + userID + " , it costs "+ buyValue +" coins");
		itemController.buyItems(userID,usershopName,buyList,buyValue);
	}
}
