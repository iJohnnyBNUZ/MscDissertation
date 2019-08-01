package Controller;

import Model.Entity.Entity;
import Model.Location.Coordinate;
import Model.Location.Location;
import View.View;

import java.util.HashMap;
import java.util.Map;

public class TransactionMessageController implements Controller{

    private ClientMediator clientMediator;
    private ItemController itemController;

    public TransactionMessageController(ClientMediator clientMediator){
        this.clientMediator = clientMediator;
    }

    public Boolean buyMessage(String usershopName, HashMap<String,Integer> buyList, int buyValue){
        String userID = clientMediator.getUserName();
        if(clientMediator.getWorld().getEntity(userID).getCoin()>=buyValue){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean sellMessage(String usershopName, HashMap<String, Integer> sellList, int sellVale){
        Entity entity = null;
        String userID = clientMediator.getUserName();
        String usershopType = usershopName.replaceAll("[0-9]","");
        if(usershopType.equals("store")){
            Location currLocation = clientMediator.getWorld().getEntityLocation(userID);
            for(Map.Entry<Entity, Coordinate> entry : currLocation.getEntities().entrySet()){
                if (entry.getKey().getEntityID().equals(usershopName)){
                    entity = entry.getKey();
                }
            }
        }
        else{
            entity = clientMediator.getWorld().getEntity(usershopName);
        }
        if(entity.getCoin()>=sellVale){
             return true;
        }
        else{
            return false;
        }
    }

}
