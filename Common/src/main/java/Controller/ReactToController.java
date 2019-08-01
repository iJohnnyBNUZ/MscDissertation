package Controller;

import Model.Entity.Entity;
import Model.Entity.NPC;
import Model.Entity.Shop;
import Model.Entity.User;
import Model.Location.Coordinate;
import Model.Location.Location;

import java.util.Map;

public class ReactToController implements Controller{

    private GameMediator gameMediator;
    private String commuWith;

    public ReactToController(GameMediator gameMediator){
        this.gameMediator = gameMediator;
    }

    public String communicateWith(String userID){

        //get current user's location and coordinate
        Location currLocation = gameMediator.getWorld().getEntityLocation(userID);
        Coordinate currCoordinate = null;
        for(Map.Entry<Entity, Coordinate> entry : currLocation.getEntities().entrySet()){
            if (entry.getKey().getEntityID().equals(userID))
                currCoordinate = entry.getValue();
        }

        String id = null;
        for(Entity entity:currLocation.getEntities().keySet()){
            if(currLocation.getEntities().get(entity).equals(currCoordinate)&&!entity.getEntityID().equals(userID)){
                if(entity instanceof User){
                    id=entity.getEntityID();
                }else if(entity instanceof NPC){
                    id=entity.getEntityID();
                    break;
                }else if(entity instanceof Shop){
                    id=entity.getEntityID();
                    break;
                }
            }
        }

        System.out.println("I am communicating with " + id);
        return id;
    }

    public void reactToEntity(String userID){
        Entity entity = gameMediator.getWorld().getEntity(userID);
        entity.reactTo();
    }


}
