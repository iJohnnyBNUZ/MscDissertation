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


    /**
     * Return the ID of the entity that the user interacts with
     * @param userID The ID of the user
     * @return the ID of the entity that the user interacts with
     */
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
        return id;
    }

    /**
     * Return the result of interaction between the user and the entity
     * @param id The ID of the entity that the user interacts with
     * @param userID The ID of the user
     * @return the result of interaction between the user and the entity
     */
    public String reactToEntity(String id, String userID){
        String message = null;
        Location currLocation = gameMediator.getWorld().getEntityLocation(userID);
        Entity reaEntity = null;
        Entity entity = gameMediator.getWorld().getEntity(userID);
        for(Map.Entry<Entity, Coordinate> entry : currLocation.getEntities().entrySet()){
            if(entry.getKey().getEntityID().equals(id)){
                reaEntity = entry.getKey();
                break;
            }
        }
        if(reaEntity instanceof User){
           message = ((User)reaEntity).reactTo(entity);
        }
        else if(reaEntity instanceof NPC){
           message = ((NPC)reaEntity).reactTo(entity);
        }
        else if(reaEntity instanceof Shop){
           message = ((Shop)reaEntity).reactTo(entity);
        }
        return message;
    }


}
