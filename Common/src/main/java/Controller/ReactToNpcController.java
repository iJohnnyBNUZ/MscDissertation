package Controller;

import Controller.Controller;
import Controller.GameMediator;
import Controller.ReactToController;
import Model.Entity.Entity;
import Model.Entity.NPC;

public class ReactToNpcController implements Controller {

    private GameMediator gameMediator;

    public ReactToNpcController(GameMediator gameMediator){
        this.gameMediator = gameMediator;
    }

    public void reactToNpc(String npcID, String userID){
        Entity npc = gameMediator.getWorld().getEntity(npcID);
        Entity user = gameMediator.getWorld().getEntity(userID);
        ((NPC)npc).reactTo(user);
    }

}
