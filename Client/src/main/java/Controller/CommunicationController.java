package Controller;

import Model.Entity.Entity;
import Model.Entity.NPC;
import Model.Entity.Shop;
import Model.Entity.User;
import Model.Location.Coordinate;
import Model.Location.Location;
import View.TransactionView;
import javafx.concurrent.Task;

import java.util.Map;

public class CommunicationController implements Controller {

    private ClientMediator clientMediator;
    private String userID;

    public CommunicationController(ClientMediator clientMediator){
        this.clientMediator = clientMediator;
    }

    public void addMessages(String sentence){
    	//add queue
    	userID = this.clientMediator.getUserName();
    	String message = userID + " : " + sentence;
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

		Task<Void> progressTask = new Task<Void>(){
			boolean isAvaliable=false;
			@Override
			protected Void call() throws Exception {
				userID = clientMediator.getUserName();
				if(!id.equals(userID))
					isAvaliable=true;
				return null;
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				if(isAvaliable)
					clientMediator.getTransactionView().updateTransaction(clientMediator.getWorld().getEntity(id).getBag(),id,clientMediator.getWorld().getEntity(userID).getBag(),clientMediator.getWorld().getEntity(userID).getCoin());

			}

		};

		new Thread(progressTask).start();
	}




	private void withShop(String id) {
		Task<Void> progressTask = new Task<Void>(){
			boolean isAvaliable=false;
			Entity selectedEntity = null;
			@Override
			protected Void call() throws Exception {
				userID = clientMediator.getUserName();
				for(Entity entity: clientMediator.getWorld().getEntityLocation(userID).getEntities().keySet()){
					if(entity.getEntityID() == id){
						selectedEntity=entity;
						isAvaliable=true;
						break;
					}
				}
				return null;
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				if(isAvaliable)
					clientMediator.getTansactionView().updateTransaction(selectedEntity.getBag(),id,clientMediator.getWorld().getEntity(userID).getBag(),clientMediator.getWorld().getEntity(userID).getCoin());

			}

		};

		new Thread(progressTask).start();



	}

	private void withNPC(String id) {
		Task<Void> progressTask = new Task<Void>(){
			boolean isAvaliable=false;
			String message = null;
			@Override
			protected Void call() throws Exception {
				userID = clientMediator.getUserName();
				for(Entity entity: clientMediator.getWorld().getEntityLocation(userID).getEntities().keySet()){
					if(entity.getEntityID() == id){
						clientMediator.getWorld().getEntity(userID).reactTo(clientMediator.getWorld().getEntity(userID));
						message = "You are communicate with "+ id + ", and you lose 30 energy. " ;
						isAvaliable=true;
						break;
					}
				}

				return null;
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				if(isAvaliable)
					clientMediator.getNPCView().updateNpcView(message);

			}

		};

		new Thread(progressTask).start();

	}
}

