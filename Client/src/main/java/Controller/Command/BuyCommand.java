package Controller.Command;

import Controller.Controller;
import Controller.ItemController;

import java.util.HashMap;
import Controller.ClientMediator;
import Controller.TransactionMessageController;

public class BuyCommand implements Command {

	private ClientMediator clientMediator = null;
	private TransactionMessageController transactionMessageController = null;
	private ItemController itemController = null;
	private String userID;


    public BuyCommand(Controller messageController, Controller itemController, ClientMediator clientMediator) {
    	this.transactionMessageController = (TransactionMessageController) messageController;
    	this.itemController = (ItemController) itemController;
    	this.clientMediator = clientMediator;
	}

	public void execute(String usershopName,HashMap<String,Integer> buyList, int buyValue){
    	userID = clientMediator.getUserName();
		System.out.println("several items are bought, it costs "+ buyValue +" coins");
		Boolean bool = transactionMessageController.buyMessage(usershopName,buyList,buyValue);
		if(bool = true){
			clientMediator.getView().showAlert("You have enough money to buy the items");
			clientMediator.setTransactionWith(usershopName);
			//add to the event queue
			itemController.exchange(userID,usershopName,buyList,buyValue,userID);
		}
		else{
			clientMediator.getView().showAlert("You don't have enough money to buy the items");
		}
	}
}
