package Controller.Command;

import Controller.ClientMediator;
import Controller.Controller;
import Controller.ItemController;
import Controller.TransactionMessageController;
import Network.Events.TransactionEvent;

import java.util.HashMap;

public class TransactionCommand {

    private TransactionMessageController transactionMessageController = null;
    private ItemController itemController = null;
    private ClientMediator clientMediator = null;
    private String userID;

    public TransactionCommand(Controller transactionMessageController, Controller itemController, ClientMediator clientMediator){
        this.transactionMessageController = (TransactionMessageController) transactionMessageController;
        this.itemController = (ItemController) itemController;
        this.clientMediator = clientMediator;
    }

    public void execute(String tranUserID, HashMap<String,Integer> tranList, int value, String tranType) {

        //clientMediator.setTransactionWith(tranUserID);
        userID = clientMediator.getUserName();
        Boolean bool;

        if(tranType.equals("buy")){
            bool = transactionMessageController.tranMessage(userID, tranUserID, tranList, value, userID);
            if(bool = true){
                clientMediator.getView().showAlert("You have enough money to buy the items");
                itemController.exchange(userID,tranUserID,tranList,value,userID);
                clientMediator.getEventQueue().add(new TransactionEvent(userID,tranUserID,tranList,value,userID));
            }
            else{
                clientMediator.getView().showAlert("You don't have enough money to buy the items");
            }
        }
        else if(tranType.equals("sell")){
            bool = transactionMessageController.tranMessage(tranUserID, userID, tranList, value, userID);
            if(bool = true){
                clientMediator.getView().showAlert(tranUserID + " has enough money to buy the items");
                itemController.exchange(tranUserID, userID,tranList,value,userID);
                clientMediator.getEventQueue().add(new TransactionEvent(tranUserID, userID,tranList,value,userID));
            }
            else{
                clientMediator.getView().showAlert(tranUserID + " doesn't have enough money to buy the items");
            }
        }

    }
}
