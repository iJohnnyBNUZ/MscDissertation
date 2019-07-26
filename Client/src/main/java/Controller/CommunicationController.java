package Controller;

import Model.Entity.Entity;

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

	public void communicateWith(String id,String time) {
		String name = id.replaceAll("[0-9]", "");
		switch(name) {
		case "npc": System.out.println("Talk with NPC:" + id );withNPC(id,time);break;
		case "store": System.out.println("Talk with STORE:" + id );withStore(id);break;
		case "user" : System.out.println("Talk with USER:" + id );withUser(id);break;
		}
	}

	private void withUser(String id) {
		userID = this.clientMediator.getUserName();
		this.clientMediator.getTransactionView().updateTransaction(this.clientMediator.getWorld().getEntity(id).getBag(),id,this.clientMediator.getWorld().getEntity(userID).getBag(),this.clientMediator.getWorld().getEntity(userID).getCoin());
	}

	private void withStore(String id) {
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

	private void withNPC(String id,String time) {
		userID = this.clientMediator.getUserName();
		this.clientMediator.getWorld().getEntity(userID).reactTo(this.clientMediator.getWorld().getEntity(userID));
		String message = "You are communicate with "+ id + ", and you lose 30 energy at " + time;
		this.clientMediator.getNPCView().updateNpcView(message);
	}
}

