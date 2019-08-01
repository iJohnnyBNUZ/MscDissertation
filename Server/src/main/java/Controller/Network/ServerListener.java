package Controller.Network;

import Controller.ItemController;
import Controller.LocationController;
import Controller.ServerMediator;
import Model.Entity.Entity;
import Model.Entity.User;
import Network.Events.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerListener extends Thread implements Runnable {
	private Server server;
	private ObjectInputStream objectInput;
	private boolean canRun = true;
	private String userName;
	private ServerMediator serverMediator;
	private LocationController locationController;
	private ItemController itemController;
	private ServerUpdater serverUpdater;

	ServerListener(Socket socket, Server server, ServerMediator serverMediator) throws Exception {
		this.server = server;
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		this.objectInput = new ObjectInputStream(socket.getInputStream());
		this.serverMediator = serverMediator;
		this.locationController = new LocationController(this.serverMediator);
		this.itemController = new ItemController(this.serverMediator);
		this.serverUpdater = new ServerUpdater(objectOutputStream, serverMediator);
		serverUpdater.start();
	}

	@Override
	public void run() {
		System.out.println("Running client sender");
		while (canRun) {
			try {
				getInputFromClient();
			}
			catch(Exception ex) {
				canRun = false;
				serverUpdater.setCanRun(false);
				server.removeClient(this);
				ex.printStackTrace();
			}
		}
	}

	private void getInputFromClient() throws IOException, ClassNotFoundException {
		System.out.println("Waiting for input from " + userName);
		Object input = objectInput.readObject();
		if (input instanceof MovementEvent) {
			handleMovementEvent((MovementEvent) input);
		}
		else if (input instanceof PickUpEvent) {
			handlePickUpEvent((PickUpEvent) input);
		}
		else if(input instanceof  PutDownEvent){
			handlePutDownEvent((PutDownEvent) input);
		}
		else if (input instanceof OpenDoorEvent) {
			handleOpenDoorEvent((OpenDoorEvent) input);
		}
		else if (input instanceof CommunicationEvent) {
			handleCommunicationEvent((CommunicationEvent) input);
		}
		else if(input instanceof NPCEvent){
			handleNPCEvent((NPCEvent) input);
		}

		else if (input instanceof LogoutEvent) {
			logout();
		}else if (input instanceof ChatEvent){
			handleChatEvent((ChatEvent) input);
		}
		else if (input instanceof String) {
			handleString((String) input);
		}
		else if (input instanceof Entity) {
			handleEntity((User) input);
		}
		else if(input instanceof TransactionEvent) {
			handleTransactionEvent((TransactionEvent) input);
		}
		else {
			System.out.println("Unknown object type");
		}
	}

	private void handleCommunicationEvent(CommunicationEvent input) {
		System.out.println("Unhandled communication");
	}

	private void handleOpenDoorEvent(OpenDoorEvent input) {
		locationController.openDoor(input.getEntityID());
		server.addEventToQueue(input);
		server.updateOtherClients(this);
	}

	private void handlePickUpEvent(PickUpEvent input) {
		itemController.pickUp(input.getEntityID());
		server.addEventToQueue(input);
		server.updateOtherClients(this);
	}

	private void handlePutDownEvent(PutDownEvent input){
		itemController.drop(input.getEntityID(),input.getItemID());
		server.addEventToQueue(input);
		server.updateOtherClients(this);
	}

	private void handleNPCEvent(NPCEvent input){

	}

	private void handleMovementEvent(MovementEvent input) {
		System.out.println("Moving " + input.getEntityID() + " " + input.getDirection());
		locationController.moveTo(input.getEntityID(), input.getDirection());
		server.addEventToQueue(input);
		server.updateOtherClients(this);
		System.out.println(this.userName + " location is " + serverMediator.getWorld().getEntityLocation(this.userName));
	}

	private void handleChatEvent(ChatEvent input){
		System.out.println(input.getEntityID()+"send chat message"+input.getcommunicateMessage());
		//TODO add CommunicationController after this controller move to Common
		server.addEventToQueue(input);
		server.updateOtherClients(this);
	}

	private void handleEntity(User user) {
		System.out.println("Creating new user");
		if (serverMediator.getWorld().getEntity(user.getUserId()) != null) {
			System.out.println("User "+ user.getEntityID()+" exist, continue game.");
			((User)serverMediator.getWorld().getEntity(user.getEntityID())).setOnline(true);
			this.userName = user.getUserId();
		} else {
			serverMediator.getWorld().addEntity(user);
			this.userName = user.getEntityID();
			serverMediator.getWorld().initEntityLocation(userName);
			System.out.println("Entities in world: " + serverMediator.getWorld().getEntities());
		}
		serverUpdater.sendWorld();
	}

	private void handleString(String command) throws IOException {
		switch (command) {
			case "getWorld":
				sendWorld();
				break;
			case "logout":
				logout();
				break;
			default:
				break;
		}
	}

	private void handleTransactionEvent(TransactionEvent input){
		System.out.println(input.getEntityID() + " is buying or selling...");
		itemController.exchange(input.getCurrUser(),input.getUsershopname(),input.getTransactionList(),input.getValue(),input.getEntityID());
		server.addEventToQueue(input);
		server.updateOtherClients(this);
	}

	private void sendWorld() {
		server.sendWorldToClients();
	}

	void updateMyClient() {
		serverUpdater.sendWorld();
	}

	private void logout() {
		Entity entity =  serverMediator.getWorld().getEntity(userName);
		System.out.println("User " + userName + " Logout!");
		if (entity instanceof User){
			((User) entity).logout();
			((User) entity).setOnline(false);
			server.removeClient(this);
		}
	}

	void sendMessage(Event event) {
		serverUpdater.sendMessage(event);
	}
}

