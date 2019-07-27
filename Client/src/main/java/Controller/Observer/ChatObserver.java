package Controller.Observer;

import Controller.ClientMediator;
import Utils.Observer;

public class ChatObserver implements Observer {

    private ClientMediator clientMediator;

    public ChatObserver(ClientMediator clientMediator){
        this.clientMediator = clientMediator;
    }

    public void update(){
        clientMediator.getChatView().updateChat(clientMediator.getWorld().getMessageList());
    }
}
