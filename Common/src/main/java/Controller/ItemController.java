package Controller;

import Model.Entity.Entity;
import Model.Item.Food;
import Model.Item.Item;
import Model.Location.Coordinate;
import Model.Location.Location;

import java.util.HashMap;
import java.util.List;

public class ItemController implements Controller{
    private GameMediator gameMediator;
    private String userID;

    ItemController(GameMediator gameMediator){
        this.gameMediator = gameMediator;
    }

    public void pickUp(Coordinate coordinate){
        // delete from location
        Location location = this.gameMediator.getWorld().getEntityLocation(userID);
        Item item = null;
        if (location.getItems().get(coordinate) != null){
            item = location.getItems().remove(coordinate);
        }

        // add to user's bag
        Entity entity = this.gameMediator.getWorld().getEntity(userID);
        entity.pickUp(item);
    }

    public void drop(String itemID, String userID){
        // delete from bag
        Entity entity = this.gameMediator.getWorld().getEntity(userID);
        Item item = entity.putDown(itemID);

        //add to location
        Location location = this.gameMediator.getWorld().getEntityLocation(userID);
        Coordinate userCoordinate = location.getEntities().get(userID);
        if (item != null){
            location.addItem(userCoordinate, item);
        }

    }

    /*
    public void exchange(String itemID, String sellerID, String buyerID){
        //delete from seller's bag
        Entity seller = this.gameMediator.getWorld().getEntity(sellerID);
        Item item = seller.putDown(itemID);

        //add to buyer's bag
        Entity buyer = this.gameMediator.getWorld().getEntity(buyerID);
        if (item != null){
            buyer.pickUp(item);
        }
    }
    */

    public void eat(String itemID){

        int addEnergy = 0;
        Food food;
        for(Item item:this.gameMediator.getWorld().getEntity(userID).getBag()){
            if(item.getItemID()==itemID){
                food = (Food)item;
                food.use(this.gameMediator.getWorld().getEntity(userID));
                //add user's energy
                this.gameMediator.getWorld().getEntity(userID).increaseEnergy(addEnergy);
                //delete from user's bag
                this.gameMediator.getWorld().getEntity(userID).removeFromBag(food);
                break;
            }
        }

    }

    public void buyItems(String usershopName,HashMap<String, Integer> buyList, int buyValue){

        //if user has enough money to buy,decrease user's money,current user add item,other user or shop remove item
        if(this.gameMediator.getWorld().getEntity(userID).getCoin()>=buyValue){
            this.gameMediator.getWorld().getEntity(userID).decreaseCoin(buyValue);
            this.gameMediator.getWorld().getEntity(usershopName).increaseCoin(buyValue);
            for(Item item:this.gameMediator.getWorld().getEntity(usershopName).getBag()){
                final String itemName = item.getItemID().replaceAll("[0-9]","");
                if(buyList.containsKey(itemName) && buyList.get(itemName)>=1){
                    //current user add items
                    this.gameMediator.getWorld().getEntity(userID).addToBag(item);
                    //other user or shop remove item
                    this.gameMediator.getWorld().getEntity(usershopName).removeFromBag(item);
                    buyList.put(itemName,buyList.get(itemName)-1);
                }
            }
        }
        else{
            //tell user you don't have enough money
            System.out.println("Sorry you don't have enough coins to buy");
        }
    }

    public void sellItems(String usershopName,HashMap<String,Integer> sellList,int sellValue){
        final String nameType = usershopName.replaceAll("[0-9]","");
        //that user or shop has enough money to buy these items
        if(this.gameMediator.getWorld().getEntity(usershopName).getCoin()>=sellValue){
            this.gameMediator.getWorld().getEntity(userID).increaseCoin(sellValue);
            this.gameMediator.getWorld().getEntity(usershopName).decreaseCoin(sellValue);
            for(Item item:this.gameMediator.getWorld().getEntity(userID).getBag()){
                final String itemString = item.getItemID().replaceAll("[0-9]","");
                if(sellList.containsKey(itemString) && sellList.get(itemString)>=1){
                    this.gameMediator.getWorld().getEntity(usershopName).addToBag(item);
                    this.gameMediator.getWorld().getEntity(userID).removeFromBag(item);
                    sellList.put(itemString,sellList.get(itemString)-1);
                }
            }

        }
        else{
            System.out.println("The user or the shop don't have enough money to buy");
            //tell user that user or shop don't have enough money to buy these items
        }

    }
}
