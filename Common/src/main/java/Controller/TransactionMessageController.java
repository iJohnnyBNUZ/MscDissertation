package Controller;

import Model.Entity.Entity;
import Model.Location.Coordinate;
import Model.Location.Location;

import java.util.HashMap;
import java.util.Map;

public class TransactionMessageController implements Controller{

    private GameMediator gameMediator;
    private ItemController itemController;

    public TransactionMessageController(GameMediator gameMediator){
        this.gameMediator = gameMediator;
    }

    public Boolean tranMessage(String buyerID, String sellerID, HashMap<String,Integer> tranList, int value, String currUser){

        Entity buyer = null;
        Location currLocation = gameMediator.getWorld().getEntityLocation(currUser);

        if(buyerID.equals(currUser)){
            buyer = gameMediator.getWorld().getEntity(buyerID);
        }
        else{
            for(Map.Entry<Entity, Coordinate> entry : currLocation.getEntities().entrySet()){
                if(entry.getKey().getEntityID().equals(buyerID)){
                    buyer = entry.getKey();
                    break;
                }
            }
        }

        if(buyer.getCoin() >= value){
            return true;
        }
        else{
            return false;
        }
    }

}

