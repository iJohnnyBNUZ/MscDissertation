package Controller.Network;

import Controller.LocationController;
import Controller.ServerMediator;
import Model.Entity.Entity;
import Model.Entity.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread extends Thread implements Runnable {
	private Server server;
	private ObjectInputStream objectInput;
	private ObjectOutputStream objectOutput;
	private boolean canRun = true;
	private String userName = null;
	private ServerMediator serverMediator;
	private LocationController locationController;

	ClientThread(Socket socket, Server server, ServerMediator serverMediator) throws Exception{
		this.server = server;
		objectInput = new ObjectInputStream(socket.getInputStream());
		objectOutput = new ObjectOutputStream(socket.getOutputStream());
		this.serverMediator = serverMediator;
		this.locationController = new LocationController(this.serverMediator);
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

	public void getInputFromClient() throws IOException, ClassNotFoundException {
		Object input = objectInput.readObject();
		if (input instanceof Entity) {
			handleEntity((User) input);
		}
		else if (input instanceof String) {
			handleString((String) input);
		}
		else {
			System.out.println("Unknown object type");
		}
		try {
			server.updateClients();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void sendMessage(Object msg) {
		try {
			objectOutput.writeObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleEntity(User user) {
		//old users, continue the game
		if (serverMediator.getWorld().getEntity(user.getUserId()) != null){
			System.out.println("User "+ user.getEntityID()+" exist, continue game.");
			((User)serverMediator.getWorld().getEntity(user.getEntityID())).setOnline(true);
			this.userName = user.getUserId();
		}else{
			//new user
			serverMediator.getWorld().addEntity(user);
			this.userName = user.getEntityID();
			serverMediator.getWorld().initEntityLocation(userName);
		}
	}



	private void handleString(String command) {
		switch (command) {
			case "left":
			case "right":
			case "up":
			case "down":
				System.out.println(this.userName+" move----->"+command);
				locationController.moveTo(this.userName, command);
				try {
					objectOutput.writeObject(serverMediator.getWorld());
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Change " + userName + "'s coordinate to" + "[" + serverMediator.getWorld().getEntityLocation(userName).getEntities().get(serverMediator.getWorld().getEntity(userName)).getxPostion() + "," + serverMediator.getWorld().getEntityLocation(userName).getEntities().get(serverMediator.getWorld().getEntity(userName)).getyPosition() + "]");
				break;
			case "OpenDoor":
				locationController.openDoor(this.userName);
				try {
					objectOutput.writeObject(serverMediator.getWorld());
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "getWorld":
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

	private void logout() {
		Entity entity =  serverMediator.getWorld().getEntity(userName);
		System.out.println("Logging out " + userName);
		if (entity instanceof User){
			((User) entity).logout();
			((User) entity).setOnline(false);
			server.removeClient(this);
		}
	}
}

