package Controller;

import Model.Entity.Entity;
import Model.Item.Food;
import Model.Item.Item;
import Model.Location.Coordinate;
import Model.Location.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemController implements Controller{
    private GameMediator gameMediator;


    public ItemController(GameMediator gameMediator){

        this.gameMediator = gameMediator;
    }

    public void pickUp(String userID, Coordinate coordinate){
        // delete from location
        Location location = this.gameMediator.getWorld().getEntityLocation(userID);
        Item item = null;
        if (location.getItems().get(coordinate) != null){
            item = location.getItems().get(coordinate);
            location.getItems().remove(coordinate);
        }

        // add to user's bag
        Entity entity = this.gameMediator.getWorld().getEntity(userID);
        entity.pickUp(item);
    }

    public void drop(String userID,String itemID){
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

    public void exchange(String buyerID,String sellerID, HashMap<String, Integer> buyList, int value) {

        Entity buyer = this.gameMediator.getWorld().getEntity(buyerID);
        if (buyer == null)
            return;

        Entity seller = this.gameMediator.getWorld().getEntity(sellerID);
        if (seller == null)
            return;

        //exchange items
        for (Map.Entry<String, Integer> entry : buyList.entrySet()) {

            List<Item> bag = seller.getBag();
            if (bag == null)
                return;

            int sellNum = 0;
            for(int i = 0; i < bag.size(); i++){
                Item item = bag.get(i);
                if (item.getItemID().contains(entry.getKey())
                        && sellNum < entry.getValue()) {

                    //delete from seller's bag
                    seller.putDown(item.getItemID());

                    //add to buyer's bag
                    if (item != null)
                        buyer.pickUp(item);
                    sellNum += 1;
                }
            }
        }


        //buyer decrease money
        if (buyer.getCoin() >= value) {
            this.gameMediator.getWorld().getEntity(buyerID).decreaseCoin(value);
        }

        //seller increase money
        seller.increaseCoin(value);
    }

    public void eat(String userID,String itemID){
        Entity user = this.gameMediator.getWorld().getEntity(userID);
        if (user == null)
            return;

        List<Item> items = user.getBag();
        if(items == null)
            return;

        for(Item item : items){
            if(item.getItemID().equals(itemID) && item.getType().equals("food")){
                Food food = (Food)item;
                //add user's energy
                food.use(this.gameMediator.getWorld().getEntity(userID));
                //delete from user's bag
                this.gameMediator.getWorld().getEntity(userID).removeFromBag(food);
                break;
            }
        }
    }
}
