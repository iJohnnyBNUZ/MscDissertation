package Controller.Network;

import Controller.ClientMediator;
import Model.World;
import Network.Events.*;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientListener implements Runnable {

	private ObjectInputStream objectInputStream;

	private boolean canRun = true;
	private ClientMediator clientMediator;

	private String IP = "";
	private int PORT = 0;


	ClientListener(ClientMediator clientMediator){
		this.clientMediator = clientMediator;
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
		else if (input instanceof OpenDoorEvent) {
			handleOpenDoorEvent((OpenDoorEvent) input);
		}
		else if (input instanceof CommunicationEvent) {
			handleCommunicationEvent((CommunicationEvent) input);
		}else if(input instanceof ChatEvent){
			handleChatEvent((ChatEvent) input);
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

	private void handleCommunicationEvent(CommunicationEvent input) {
		System.out.println("Unhandled communication event");
	}

	private void handleOpenDoorEvent(OpenDoorEvent input) {
		clientMediator.getLocationController().openDoor(input.getEntityID());
	}

	private void handlePickUpEvent(PickUpEvent input) {
		clientMediator.getItemController().pickUp(input.getEntityID());
	}

	private void handleMovementEvent(MovementEvent event) {
		System.out.println("Moving " + event.getEntityID() + " " + event.getDirection());
		clientMediator.getLocationController().moveTo(event.getEntityID(), event.getDirection());
	}

	private void handleChatEvent(ChatEvent event){
		clientMediator.getCommunicationController().addMessages(event.getcommunicateMessage());
	}

	private void handleString(String input) {
		System.out.println((input));
		System.out.println("No new events");
	}

	private void updateWorld(World world) {
		System.out.println("Got new world: " + world.getEntities());
		clientMediator.setWorld(world);
	}
}
