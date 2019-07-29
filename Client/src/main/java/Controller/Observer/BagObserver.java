package Controller.Observer;

import Controller.ClientMediator;
import Model.Location.Coordinate;
import Model.Location.Location;
import Utils.Observer;
import javafx.concurrent.Task;

public class BagObserver implements Observer {

    private ClientMediator clientMediator;
    private String userID;

    public BagObserver(ClientMediator clientMediator){
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
                clientMediator.getBagView().updateBag(clientMediator.getWorld().getEntity(userID).getBag(),clientMediator.getWorld().getEntity(userID).getCoin());
            }

        };

        new Thread(progressTask).start();

    }

}
