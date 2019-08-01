package Controller.Observer;

import Controller.ClientMediator;
import Model.Entity.Entity;
import Model.Location.Coordinate;
import Model.Location.Location;
import Utils.Observer;
import javafx.concurrent.Task;

import java.util.Map;

public class TransactionObserver implements Observer {

    private ClientMediator clientMediator;
    private String userID;

    public TransactionObserver(ClientMediator clientMediator){
        this.clientMediator = clientMediator;
    }

    public void update(){
        Task<Void> progressTask = new Task<Void>(){

            @Override
            protected Void call() throws Exception {
                userID = clientMediator.getUserName();
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                if(clientMediator.getReactTo() != null){
                    userID = clientMediator.getUserName();
                    String transactionwithType = clientMediator.getReactTo().replaceAll("[0-9]","");
                    Location currLocation = clientMediator.getWorld().getEntityLocation(userID);
                    Entity transactionwith = null;
                    if(transactionwithType.equals("store")){
                        for(Map.Entry<Entity, Coordinate> entry : currLocation.getEntities().entrySet()){
                            if(entry.getKey().getEntityID().equals(clientMediator.getReactTo())){
                                transactionwith = entry.getKey();
                                break;
                            }
                        }
                    }
                    else{
                        transactionwith = clientMediator.getWorld().getEntity(clientMediator.getReactTo());
                    }
                    clientMediator.getTransactionView().updateTransaction(transactionwith.getBag(),clientMediator.getReactTo(),clientMediator.getWorld().getEntity(userID).getBag(),clientMediator.getWorld().getEntity(userID).getCoin());
                }
            }

        };

        new Thread(progressTask).start();

    }

}
