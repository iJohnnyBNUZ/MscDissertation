package Controller.Network;

import Controller.ItemController;
import Controller.LocationController;
import Controller.ServerMediator;
import Model.Entity.Entity;
import Model.Entity.User;
import Network.Event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientThread extends Thread implements Runnable {
	private Server server;
	private ObjectInputStream objectInput;
	private ObjectOutputStream objectOutput;
	private boolean canRun = true;
	private String userName;
	private ServerMediator serverMediator;
	private LocationController locationController;
	private ItemController itemController;

	ClientThread(Socket socket, Server server, ServerMediator serverMediator) throws Exception{
		socket.setTcpNoDelay(true);
		this.server = server;
		objectInput = new ObjectInputStream(socket.getInputStream());
		objectOutput = new ObjectOutputStream(socket.getOutputStream());
		this.serverMediator = serverMediator;
		this.locationController = new LocationController(this.serverMediator);
		this.itemController = new ItemController(this.serverMediator);
	}

	@Override
	public void run() {
		while (canRun) {
			try {
				getInputFromClient();
			}
			catch(Exception ex) {
				canRun = false;
				server.removeClient(this);
				ex.printStackTrace();
			}
			try {
				objectOutput.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void getInputFromClient() throws IOException, ClassNotFoundException {
		Object input = objectInput.readObject();
		if (input instanceof String) {
			handleString((String) input);
		}
		else if (input instanceof Entity) {
			handleEntity((User) input);
		}
		else {
			System.out.println("Unknown object type");
		}
	}

	void sendMessage(Object event) {
		try {
			objectOutput.writeObject(event);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleEntity(User user) {
		if (serverMediator.getWorld().getEntity(user.getUserId()) != null) {
			System.out.println("User "+ user.getEntityID()+" exist, continue game.");
			((User)serverMediator.getWorld().getEntity(user.getEntityID())).setOnline(true);
			this.userName = user.getUserId();
		} else {
			serverMediator.getWorld().addEntity(user);
			this.userName = user.getEntityID();
			serverMediator.getWorld().initEntityLocation(userName);
		}
		server.sendWorldToClients();
	}

	private void handleString(String command) throws IOException {
		switch (command) {
			case "left":
			case "right":
			case "up":
			case "down":
				System.out.println(this.userName+" move----->"+command + new SimpleDateFormat("HH:mm:ss").format(new Date()));
				locationController.moveTo(this.userName, command);
				try {
					objectOutput.writeObject(serverMediator.getWorld());
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Change " + userName + "'s coordinate to" + "[" + serverMediator.getWorld().getEntityLocation(userName).getEntities().get(serverMediator.getWorld().getEntity(userName)).getxPostion() + "," + serverMediator.getWorld().getEntityLocation(userName).getEntities().get(serverMediator.getWorld().getEntity(userName)).getyPosition() + "]");
				server.addActionToQueue(new Event(userName, serverMediator.getWorld().getEntityLocation(userName).getLocationID(), command, "none"));
				server.updateClients();
				break;
			case "getUpdates":
				server.updateClients();
				break;
			case "openDoor":
				locationController.openDoor(this.userName);
				try {
					objectOutput.writeObject(serverMediator.getWorld());
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "getWorld":
				sendWorld();
				break;

			case "pickUp":
				itemController.pickUp(this.userName);
				try {
					objectOutput.writeObject(serverMediator.getWorld());
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "logout":
				logout();
				break;
		}
	}

	public void sendWorld() {
		System.out.println("Sending world: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
		try {
			objectOutput.writeObject(serverMediator.getWorld());
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	String getUserName() {
		return userName;
	}
}

