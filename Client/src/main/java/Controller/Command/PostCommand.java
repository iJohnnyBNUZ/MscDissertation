package Controller.Command;

import Controller.CommunicationController;
import Controller.Controller;
import View.ChatView;

import java.text.SimpleDateFormat;

public class PostCommand implements Command {


    private CommunicationController communicationController = null;

    public PostCommand(Controller communicationController2){
        this.communicationController = (CommunicationController) communicationController2;
    }

    public void execute(String sentence, String date){
        //add messages to messageList in World
        String message = sentence + "  " + date;
        communicationController.addMessages(message);
        System.out.println(message+" is posting");
    }
}
