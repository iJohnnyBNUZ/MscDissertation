package Controller.Network;

import Controller.ClientMediator;
import Controller.UserController;
import Model.World;
import Network.Events.*;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientListener implements Runnable {

	private ObjectInputStream objectInputStream;
	private UserController userController;

	private boolean canRun = true;
	private ClientMediator clientMediator;

	private String IP = "";
	private int PORT = 0;


	ClientListener(ClientMediator clientMediator){
		this.clientMediator = clientMediator;
		this.userController = clientMediator.getUserController();
	}

	void connectToServer(ObjectInputStream objectInputStream) {
		this.objectInputStream = objectInputStream;
		try{
			new Thread(this).start();
		} catch (Exception ex){
			ex.printStackTrace();
			canRun=false;
		}
	}

	@Override
	public void run() {
		System.out.println("Running server listener");
		while(canRun) {
			try {
				listenToServer();
			} catch (Exception ex) {
				canRun = false;
				ex.printStackTrace();
				System.exit(0);
			}
		}
	}

	private void listenToServer() throws IOException, ClassNotFoundException {
		Object input = objectInputStream.readObject();
		System.out.println("Got input: " + input);
		if (input instanceof MovementEvent) {
			handleMovementEvent((MovementEvent) input);
		}
		else if (input instanceof PickUpEvent) {
			handlePickUpEvent((PickUpEvent) input);
		}
		else if(input instanceof PutDownEvent){
			handlePutDownEvent((PutDownEvent) input);
		}
		else if (input instanceof OpenDoorEvent) {
			handleOpenDoorEvent((OpenDoorEvent) input);
		}
		else if (input instanceof ReactToEvent) {
			handleReactToEvent((ReactToEvent) input);
		}
		else if(input instanceof TransactionEvent){
			handleTransactionEvent((TransactionEvent) input);
		}
		else if(input instanceof  EatEvent){
			handleEatEvent((EatEvent) input);
		}
		else if(input instanceof PostEvent){
			handlePostEvent((PostEvent) input);
		}
		else if(input instanceof World) {
			updateWorld((World)input);
		}
		else if(input instanceof String) {
			handleString((String)input);
		}
		else {
			System.out.println("Received unknown object from server");
		}
	}

	private void handleReactToEvent(ReactToEvent input) {
		clientMediator.getReactToController().reactToEntity(input.getReactToID(),input.getEntityID());
	}

	private void handleOpenDoorEvent(OpenDoorEvent input) {
		clientMediator.getLocationController().openDoor(input.getEntityID());
	}

	private void handlePickUpEvent(PickUpEvent input) {
		clientMediator.getItemController().pickUp(input.getEntityID());
	}

	private void handlePutDownEvent(PutDownEvent input){
		clientMediator.getItemController().drop(input.getEntityID(),input.getItemID());
	}

	private void handleMovementEvent(MovementEvent event) {
		System.out.println("Moving " + event.getEntityID() + " " + event.getDirection());
		clientMediator.getLocationController().moveTo(event.getEntityID(), event.getDirection());
	}

	private void handleTransactionEvent(TransactionEvent event) {
		clientMediator.getItemController().exchange(event.getBuyerID(),event.getSellerID(),event.getTranList(),event.getValue(),event.getEntityID());
	}

	private void handleEatEvent(EatEvent eatEvent) {
		clientMediator.getItemController().eat(eatEvent.getEntityID(),eatEvent.getSelectedItemID());
	}

	private void handlePostEvent(PostEvent event){
		clientMediator.setAtFrom(event.getEntityID());
		clientMediator.setAtUser(event.getAtUser());
		clientMediator.setMessage(event.getPostMessage());
		clientMediator.getPostController().addPostMessage(event.getPostMessage());
	}

	private void handleString(String input) {
		System.out.println((input));
		System.out.println("No new events");
	}

	private void updateWorld(World world) throws IOException, ClassNotFoundException{
		System.out.println("Got new world: " + world.getEntities());
		clientMediator.setWorld(world);
		if(clientMediator.getUserName()==null)
			userController.enterGame();
	}
}
