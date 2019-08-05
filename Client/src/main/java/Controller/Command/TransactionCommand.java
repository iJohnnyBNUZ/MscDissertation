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

    public TransactionCommand(Controller itemController, ClientMediator clientMediator){
        this.itemController = (ItemController) itemController;
        this.clientMediator = clientMediator;
    }

    public void execute(String tranUserID, HashMap<String,Integer> tranList, int value, String tranType) {


        userID = clientMediator.getUserName();
        String message;
        if(tranType.equals("buy")){
            message = itemController.exchange(userID,tranUserID,tranList,value,userID);
            clientMediator.getEventQueue().add(new TransactionEvent(userID,tranUserID,tranList,value,userID));
            if(message != null){
                clientMediator.getView().showAlert(message);
            }
            else{
                clientMediator.getView().showAlert("You bought successfully");
            }
        }
        else if(tranType.equals("sell")){
            message = itemController.exchange(userID,tranUserID,tranList,value,userID);
            clientMediator.getEventQueue().add(new TransactionEvent(userID,tranUserID,tranList,value,userID));
            if(message != null){
                clientMediator.getView().showAlert(message);
            }
            else{
                clientMediator.getView().showAlert("You sold successfully");
            }
        }

    }
}
