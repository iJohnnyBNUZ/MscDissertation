package Controller.Command;

import Controller.CommunicationController;
import View.ChatView;

public class PostCommand implements Command {


    private CommunicationController communicationController = null;

    public PostCommand(CommunicationController communicationController){
        this.communicationController = communicationController;
    }

    public void execute(String sentence){
        //add messages to messageList in World
        communicationController.addMessages(sentence);
        System.out.println(sentence+" is posting");
    }
}
