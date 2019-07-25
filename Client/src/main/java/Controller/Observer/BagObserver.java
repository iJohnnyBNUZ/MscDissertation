package Controller.Observer;

import Controller.ClientMediator;
import Utils.Observer;

public class BagObserver implements Observer {

    private ClientMediator clientMediator;
    private String userID;

    public BagObserver(ClientMediator clientMediator){
        this.clientMediator = clientMediator;
    }

    public void takeAction(Object... msg){
        if (msg[0].equals("changeBag")){
            userID = this.clientMediator.getUserName();
            clientMediator.getBagView().updateBag(this.clientMediator.getWorld().getEntity(userID).getBag(),this.clientMediator.getWorld().getEntity(userID).getCoin());
        }
    }

}
