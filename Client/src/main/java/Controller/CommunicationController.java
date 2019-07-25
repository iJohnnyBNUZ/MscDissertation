package Controller;

public class CommunicationController implements Controller {

    private ClientMediator clientMediator;
    private String userID;

    public CommunicationController(ClientMediator clientMediator){
        this.clientMediator = clientMediator;
    }

    public void addMessages(String message){
    	this.clientMediator.getWorld().addMessage(message);
    }

	public void communicateWith(String id,String time) {
		// TODO Auto-generated method stub
		String name = id.replaceAll("[0-9]", "");
		switch(name) {
		case "npc": System.out.println("Talk with NPC:" + id );withNPC(id,time);break;
		case "store": System.out.println("Talk with STORE:" + id );withStore(id);break;
		case "user" : System.out.println("Talk with USER:" + id );withUser(id);break;
		}
	}

	private void withUser(String id) {
		// TODO Auto-generated method stub
		userID = this.clientMediator.getUserName();
		this.clientMediator.getTransactionView().updateTransaction(this.clientMediator.getWorld().getEntity(id).getBag(),id,this.clientMediator.getWorld().getEntity(userID).getBag(),this.clientMediator.getWorld().getEntity(userID).getCoin());
	}

	private void withStore(String id) {
		// TODO Auto-generated method stub
		userID = this.clientMediator.getUserName();
		this.clientMediator.getTransactionView().updateTransaction(this.clientMediator.getWorld().getEntity(id).getBag(),id,this.clientMediator.getWorld().getEntity(userID).getBag(),this.clientMediator.getWorld().getEntity(userID).getCoin());
	}

	private void withNPC(String id,String time) {
		// TODO Auto-generated method stub
		userID = this.clientMediator.getUserName();
		this.clientMediator.getWorld().getEntity(userID).reactTo(this.clientMediator.getWorld().getEntity(id));
		String message = "You are communicate with "+ id + ", and you lose 20 energy at " + time;
		this.clientMediator.getNPCView().updateNpcView(message);
	}
}

