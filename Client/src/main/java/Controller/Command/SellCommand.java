package Controller.Command;

import Controller.ItemController;

import java.util.HashMap;
import Controller.ClientMediator;
import Controller.TransactionMessageController;
import Network.Events.TransactionEvent;

public class SellCommand implements Command {

    private TransactionMessageController transactionMessageController = null;
    private ItemController itemController = null;
    private ClientMediator clientMediator = null;
    private String userID;

    public SellCommand(TransactionMessageController transactionMessageController, ItemController itemController, ClientMediator clientMediator) {
    	this.transactionMessageController = transactionMessageController;
    	this.itemController = itemController;
    	this.clientMediator = clientMediator;
	}

    public void execute(String usershopName, HashMap<String, Integer> sellList, int sellVale){
        userID = clientMediator.getUserName();
        Boolean bool = transactionMessageController.sellMessage(usershopName,sellList,sellVale);
        if(bool = true){
            clientMediator.getView().showAlert(usershopName + " has enough money to buy the items");
            clientMediator.setTransactionWith(usershopName);
            //add to the event queue
            itemController.exchange(usershopName, userID,sellList,sellVale,userID);
            clientMediator.getEventQueue().add(new TransactionEvent(usershopName, userID,sellList,sellVale,userID));
        }
        else{
            clientMediator.getView().showAlert(usershopName + " doesn't have enough money to buy the items");
        }
    }
}
