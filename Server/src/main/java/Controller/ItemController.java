package Controller;

import Model.Entity.Entity;
import Model.Item.Item;
import Model.Location.Coordinate;
import Model.Location.Location;

public class ItemController {
    private GameMediator gameMediator;

    private ItemController(GameMediator gameMediator){
        this.gameMediator = gameMediator;
    }
    public void pickUp(Coordinate coordinate, String userID){

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
}
