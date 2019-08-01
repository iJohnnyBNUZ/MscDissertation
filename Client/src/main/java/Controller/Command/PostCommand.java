package Controller.Command;

import Controller.CommunicationController;
import Controller.Controller;
import Controller.ClientMediator;
import Network.Events.ChatEvent;
import View.ChatView;

import java.text.SimpleDateFormat;

public class PostCommand implements Command {

    private CommunicationController communicationController = null;
    private ClientMediator clientMediator=null;

    public PostCommand(Controller communicationController2, ClientMediator clientMediator){
        this.communicationController = (CommunicationController) communicationController2;
        this.clientMediator = clientMediator;
    }

    public void execute(String sentence, String date){
        //add messages to messageList in World
        String userID = this.clientMediator.getUserName();
        String message = userID + " : "+sentence + "  " + date;
        communicationController.addMessages(message);
        System.out.println(message+" is posting");
        clientMediator.getEventQueue().add(new ChatEvent(clientMediator.getUserName(),message));
    }
}
