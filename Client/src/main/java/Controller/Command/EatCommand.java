package Controller.Command;

import Controller.Controller;
import Controller.ItemController;

public class EatCommand implements Command {

    private ItemController itemController;

    public EatCommand(Controller controller){
        this.itemController = (ItemController) controller;
    }

    public void execute(String selectedItemId){
        System.out.println("Item "+selectedItemId+" is eatten");
        itemController.eat(selectedItemId);
    }
}
