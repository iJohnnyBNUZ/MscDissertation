package Controller;

import Model.Entity.Entity;
import Model.Entity.NPC;
import Model.Entity.Shop;
import Model.Entity.User;
import Model.Location.Coordinate;
import Model.Location.Location;

import java.util.Map;

public class CommunicationController implements Controller {

    private ClientMediator clientMediator;
    private String userID;

    public CommunicationController(ClientMediator clientMediator){
        this.clientMediator = clientMediator;
    }

    public void addMessages(String message){
    	this.clientMediator.getWorld().addMessage(message);
    	System.out.println(this.clientMediator.getWorld().getMessageList());
    }

	public boolean communicateWith() {
    	System.out.println("communication Controller!!!!!!!!!!!1");
    	boolean isAvaliable=false;
		userID = clientMediator.getUserName();
		Location location = this.clientMediator.getWorld().getEntityLocation(userID);
		Coordinate coordinate = null;
		for(Map.Entry<Entity, Coordinate> entry : location.getEntities().entrySet()){
			if (entry.getKey().getEntityID().equals(userID))
				coordinate = entry.getValue();
		}

		String id = null;
		String name = null;
		for(Entity entity:location.getEntities().keySet()){
			if(location.getEntities().get(entity).equals(coordinate)&&!entity.getEntityID().equals(userID)){
				id=entity.getEntityID();
				System.out.println("talk with :"+id);
				if(entity instanceof NPC){
					name="npc";
				}else if(entity instanceof User){
					name ="user";
				}else if(entity instanceof Shop){
					name = "shop";
				}
				isAvaliable=true;
			}
		}

		//Coordinate uCor = clientMediator.getWorld().get
		if(name!=null){
			switch(name) {
				//add queue
				case "npc": System.out.println("Talk with NPC:" + id );withNPC(id);break;
				case "shop": System.out.println("Talk with SHOP:" + id );withShop(id);break;
				case "user" : System.out.println("Talk with USER:" + id );withUser(id);break;
				default:break;
			}
		}


		return isAvaliable;
	}

	private void withUser(String id) {
		userID = this.clientMediator.getUserName();
		if(!id.equals(userID))
			this.clientMediator.getTransactionView().updateTransaction(this.clientMediator.getWorld().getEntity(id).getBag(),id,this.clientMediator.getWorld().getEntity(userID).getBag(),this.clientMediator.getWorld().getEntity(userID).getCoin());

	}

	private void withShop(String id) {
    	System.out.println("with shop+{}" + id);
		userID = this.clientMediator.getUserName();
		for(Entity entity: this.clientMediator.getWorld().getEntityLocation(userID).getEntities().keySet()){
			if(entity.getEntityID() == id){
				//System.out.println("shop是 " + entity.getEntityID());
				//System.out.println("shop是 " + entity);
				this.clientMediator.getTansactionView().updateTransaction(entity.getBag(),id,this.clientMediator.getWorld().getEntity(userID).getBag(),this.clientMediator.getWorld().getEntity(userID).getCoin());
				break;
			}
		}
		//this.clientMediator.getTransactionView().updateTransaction(this.clientMediator.getWorld().getEntity(id).getBag(),id,this.clientMediator.getWorld().getEntity(userID).getBag(),this.clientMediator.getWorld().getEntity(userID).getCoin());
	}

	private void withNPC(String id) {
		userID = this.clientMediator.getUserName();
		this.clientMediator.getWorld().getEntity(userID).reactTo(this.clientMediator.getWorld().getEntity(userID));
		String message = "You are communicate with "+ id + ", and you lose 30 energy. " ;
		this.clientMediator.getNPCView().updateNpcView(message);
	}
}

