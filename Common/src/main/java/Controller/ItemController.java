package Controller;

import Model.Entity.Entity;
import Model.Item.Coin;
import Model.Item.Food;
import Model.Item.Item;
import Model.Item.Key;
import Model.Location.Coordinate;
import Model.Location.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemController implements Controller{
    private GameMediator gameMediator;


    public ItemController(GameMediator gameMediator){

        this.gameMediator = gameMediator;
    }

    public void pickUp(String userID){
        System.out.println("itemController");
        // delete from location
        Location location = this.gameMediator.getWorld().getEntityLocation(userID);
        Coordinate coordinate = null;

        for(Map.Entry<Entity, Coordinate> entry : location.getEntities().entrySet()){
            if (entry.getKey().getEntityID().equals(userID))
                coordinate = entry.getValue();
        }

        Item item = null;
        if (location.getItems().get(coordinate) != null){
            item = location.getItems().get(coordinate);
            location.removeItem(coordinate);
            Entity entity = this.gameMediator.getWorld().getEntity(userID);
            if (entity == null)
                return;
            if (item instanceof Coin){
                Coin coin = (Coin) item;
                entity.setCoin(entity.getCoin() + coin.getCoinValue());
            }
            else{
                entity.pickUp(item);  // add to user's bag
            }
        }
        /*
        Entity entity = this.gameMediator.getWorld().getEntity(userID);
        if (entity == null)
            return;
        if (item instanceof Coin){
            Coin coin = (Coin) item;
            entity.setCoin(entity.getCoin() + coin.getCoinValue());
        }
        else{
            entity.pickUp(item);  // add to user's bag
        }
        */
    }

    public String drop(String userID,String itemID){

        Location location = this.gameMediator.getWorld().getEntityLocation(userID);
        Entity entity = this.gameMediator.getWorld().getEntity(userID);
        Item item = null;
        for (Item item1 : entity.getBag()){
            if (item1.getItemID().equals(itemID)){
                item = item1;
                break;
            }
        }

        //find user's coordinate
        Coordinate userCoordinate = null;
        for(Map.Entry<Entity, Coordinate> entry : location.getEntities().entrySet()){
            if (entry.getKey().getEntityID().equals(userID))
                userCoordinate = entry.getValue();
        }

        if (item != null){
            if(location.getItems().get(userCoordinate) == null){
                // delete from bag
                entity.putDown(itemID);
                //add to location
                location.addItem(userCoordinate, item);
            }
            else{
                return "Current tile already exists item";
            }
        }
        else{
          return "Cannot find Item by item id";
        }

        return null;
    }

    public String exchange(String buyerID,String sellerID, HashMap<String, Integer> buyList, int value,String userID) {

        Location currLocation = this.gameMediator.getWorld().getEntityLocation(userID);

        Entity buyer = null;
        Entity seller = null;
        for(Map.Entry<Entity, Coordinate> entry : currLocation.getEntities().entrySet()){
            if(entry.getKey().getEntityID().equals(buyerID)){
                buyer = entry.getKey();
//                break;
            }
            if(entry.getKey().getEntityID().equals(sellerID)){
                seller = entry.getKey();
//                break;
            }

            //find out seller and buyer
            if (seller != null && buyer != null)
                break;
        }

        if (buyer == null)
            return "Cannot find buyer";
        if (seller == null)
            return "Cannot find seller";

        if(buyer.getCoin() < value){
            return "User donesn't have enough money";
        }

        //exchange items
        for (Map.Entry<String, Integer> entry : buyList.entrySet()) {

            List<Item> bag = seller.getBag();
            if (bag == null)
                return "Cannot get bag";

            int sellNum = 0;
            for(int i = 0; i < bag.size(); i++){
                Item item = bag.get(i);
                if (item.getItemID().contains(entry.getKey())
                        && sellNum < entry.getValue()) {

                    //delete from seller's bag
                    seller.putDown(item.getItemID());

                    //add to buyer's bag
                    if (item != null)
                        if(item instanceof Key)
                            ((Key) item).setUsed(false);
                        buyer.pickUp(item);
                    sellNum += 1;
                }
            }
        }


        //buyer decrease money
        buyer.decreaseCoin(value);

        //seller increase money
        seller.increaseCoin(value);

        return null;
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
