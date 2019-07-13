package Controller.Command;

import Controller.CommunicationController;
import Controller.Controller;
import View.ChatView;

public class PostCommand implements Command {


    private CommunicationController communicationController = null;

    public PostCommand(Controller communicationController2){
        this.communicationController = (CommunicationController) communicationController2;
    }

    public void execute(String sentence){
        //add messages to messageList in World
        communicationController.addMessages(sentence);
        System.out.println(sentence+" is posting");
    }
}
