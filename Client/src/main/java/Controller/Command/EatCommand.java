package Controller.Command;

import Controller.ClientMediator;
import Controller.Controller;
import Controller.ItemController;
import Network.Events.EatEvent;

public class EatCommand implements Command {

    private ItemController itemController;
    private ClientMediator clientMediator;

    public EatCommand(Controller controller,ClientMediator clientMediator){
        this.itemController = (ItemController) controller;
        this.clientMediator = clientMediator;
    }

    public void execute(String selectedItemId){
        String userID = clientMediator.getUserName();
        itemController.eat(userID,selectedItemId);
        clientMediator.getEventQueue().add(new EatEvent(userID,selectedItemId));
    }
}
