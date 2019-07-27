package Controller.Observer;

import Controller.ClientMediator;
import Model.Location.Coordinate;
import Model.Location.Location;
import Utils.Observer;
import javafx.concurrent.Task;

import java.util.*;

public class ChatObserver implements Observer {

    private ClientMediator clientMediator;

    public ChatObserver(ClientMediator clientMediator){
        this.clientMediator = clientMediator;
    }

    public void update(){
        Task<Void> progressTask = new Task<Void>(){
            List<String> messageList = new LinkedList<String>();

            @Override
            protected Void call() throws Exception {
                messageList = clientMediator.getWorld().getMessageList();
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                clientMediator.getChatView().updateChat(messageList);
            }

        };

        new Thread(progressTask).start();

    }
}
