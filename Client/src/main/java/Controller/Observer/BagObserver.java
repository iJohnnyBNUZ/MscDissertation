package Controller.Observer;

import Controller.ClientMediator;

public class BagObserver implements Observer {

    private ClientMediator clientMediator;
    private String userID;

    public BagObserver(ClientMediator clientMediator){
        this.clientMediator = clientMediator;
        this.userID = clientMediator.getUserName();
    }

    public void update(){
        clientMediator.getBagView().updateBag(this.clientMediator.getWorld().getEntity(userID).getBag(),this.clientMediator.getWorld().getEntity(userID).getCoin());
    }

}
