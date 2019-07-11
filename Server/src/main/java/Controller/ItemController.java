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
        entity.addToBag(item);
    }

    public void drop(String itemID, String userID){

    }

    public void give(String itemID, String fromUserID, String toUserID){

    }
}
