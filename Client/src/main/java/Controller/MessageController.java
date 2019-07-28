package Controller;

import Model.Entity.Entity;
import Model.Location.Coordinate;
import Model.Location.Location;
import View.View;

import java.util.HashMap;
import java.util.Map;

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
            itemController.exchange(userID,usershopName,buyList,buyValue,userID);
        }
        else{
            showMessage("You don't have enough money to buy the items");
        }
    }

    public void sellMessage(String usershopName, HashMap<String, Integer> sellList, int sellVale){
        String userID = clientMediator.getUserName();
        Location currLocation = clientMediator.getWorld().getEntityLocation(userID);
        for(Map.Entry<Entity, Coordinate> entry : currLocation.getEntities().entrySet()){
            if (entry.getKey().getEntityID().equals(usershopName)){
                if(entry.getKey().getCoin()>=sellVale){
                    showMessage(usershopName + " has enough money to buy the items");
                    itemController.exchange(usershopName, userID,sellList,sellVale,userID);
                }
                else{
                    showMessage(usershopName + " doesn't have enough money to buy the items");
                }
            }
        }
        /*
        //System.out.println("商店的钱是" + clientMediator.getWorld().getEntityLocation());
        if(clientMediator.getWorld().getEntity(usershopName).getCoin()>=sellVale){
            showMessage(usershopName + " has enough money to buy the items");
            itemController.exchange(usershopName, userID,sellList,sellVale);
        }
        else{
            showMessage(usershopName + " doesn't have enough money to buy the items");
        }
        */
    }

    public void showMessage(String message){
        view = clientMediator.getView();
        view.showAlert(message);
    }


}
