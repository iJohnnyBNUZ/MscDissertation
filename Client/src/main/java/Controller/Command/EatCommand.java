package Controller.Command;

import Controller.Controller;
import Controller.ItemController;
import Controller.ClientMediator;

public class EatCommand implements Command {

    private ItemController itemController = null;
    private ClientMediator clientMediator = null;
    private String userID;

    public EatCommand(Controller controller,ClientMediator clientMediator){
        this.itemController = (ItemController) controller;
        this.clientMediator = clientMediator;
    }

    public void execute(String selectedItemId){
        userID = clientMediator.getUserName();
        System.out.println("Item "+selectedItemId+" is eatten by " + userID);
        itemController.eat(userID,selectedItemId);
    }
}
