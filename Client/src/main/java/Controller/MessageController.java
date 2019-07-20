package Controller;

import View.View;

import java.util.HashMap;

public class MessageController implements Controller{

    private View view;
    private ClientMediator clientMediator;
    private ItemController itemController;

    public MessageController(ClientMediator clientMediator){
        this.clientMediator = clientMediator;
        this.itemController = clientMediator.getItemController();
    }

    public void buyMessage(String usershopName, HashMap<String,Integer> buyList, int buyValue){
        String userID = clientMediator.getUserName();
        if(clientMediator.getWorld().getEntity(userID).getCoin()>=buyValue){
            showMessage("You have enough money to buy the items");
            itemController.buyItems(userID,usershopName,buyList,buyValue);
        }
        else{
            showMessage("You don't have enough money to buy the items");
        }
    }

    public void sellMessage(String usershopName, HashMap<String, Integer> sellList, int sellVale){
        String userID = clientMediator.getUserName();
        if(clientMediator.getWorld().getEntity(usershopName).getCoin()>=sellVale){
            showMessage(usershopName + " has enough money to buy the items");
            itemController.sellItems(userID,usershopName,sellList,sellVale);
        }
        else{
            showMessage(usershopName + " doesn't have enough money to buy the items");
        }
    }

    public void showMessage(String message){
        view = clientMediator.getView();
        view.showAlert(message);
    }


}
