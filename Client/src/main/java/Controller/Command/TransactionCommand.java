package Controller.Command;

import Controller.ClientMediator;
import Controller.Controller;
import Controller.ItemController;
import Network.Events.OpenDoorEvent;
import Network.Events.TransactionEvent;
import javafx.concurrent.Task;

import java.util.HashMap;

public class TransactionCommand {

    private ItemController itemController = null;
    private ClientMediator clientMediator = null;
    private String userID;

    public TransactionCommand(Controller itemController, ClientMediator clientMediator){
        this.itemController = (ItemController) itemController;
        this.clientMediator = clientMediator;
    }

    /**
     * Invoke the exchange method in the itemController.
     * Add the TransactionEvent to the queue if this action is processed, otherwise just show a dialog box to the user
     * @param tranUserID The ID of the entity that the user trades with
     * @param tranList A HashMap that contains the type and number of items that will be bought or sold
     * @param value The value of this transaction
     * @param tranType The type of this transaction for the current user
     */
    public void execute(String tranUserID, HashMap<String,Integer> tranList, int value, String tranType) {
        Task<Void> progressTask = new Task<Void>(){
            String message = null;
            @Override
            protected Void call() throws Exception {
                // run in the current thread.
                userID = clientMediator.getUserName();
                return null;
            }

            @Override
            protected void succeeded() {
                // run in the JavaFx thread.
                if(tranType.equals("buy")){
                    message = itemController.exchange(userID,tranUserID,tranList,value,userID);
                    if(message != null){
                        clientMediator.getView().showAlert(message,null);
                    }
                    else{
                        clientMediator.getView().showAlert("You bought successfully",null);
                        clientMediator.getEventQueue().add(new TransactionEvent(userID,tranUserID,tranList,value,userID));
                    }
                }
                else if(tranType.equals("sell")){
                    message = itemController.exchange(tranUserID,userID,tranList,value,userID);
                    clientMediator.getEventQueue().add(new TransactionEvent(tranUserID,userID,tranList,value,userID));
                    if(message != null){
                        clientMediator.getView().showAlert(message,null);
                    }
                    else{
                        clientMediator.getView().showAlert("You sold successfully",null);
                        clientMediator.getEventQueue().add(new TransactionEvent(userID,tranUserID,tranList,value,userID));
                    }
                }
            }

        };

        new Thread(progressTask).start();




    }
}
