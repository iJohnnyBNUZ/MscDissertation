package Controller.Network;

import Controller.LocationController;
import Controller.ServerMediator;
import Model.Entity.Entity;
import Model.Entity.User;
import Model.Location.Coordinate;
import Model.World;

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
	}

	@Override
	public void run() {
		while (canRun) {
			try {
				System.out.println("Tock");
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

	void sendMessage(Object msg) {
		System.out.println("Sending message to user: " + userName);
		if (msg instanceof World) {
			for (Entity entity : ((World) msg).getEntities()) {
				System.out.println(entity);
			}
		}
		try {
			objectOutput.writeObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleEntity(User user) {
		System.out.println(user.getEntityID());
		serverMediator.getWorld().addEntity(user);
		this.userName = user.getEntityID();
		initEntityLocation(userName);
	}

	private void initEntityLocation(String userName){
		serverMediator.getWorld().setEntityLocation(userName, "location0");
		int max =2,min =0;
		int positionX = min + (int)(Math.random() * (max-min+1));
		int positionY = min + (int)(Math.random() * (max-min+1));
		Coordinate userCoordinate = new Coordinate(positionX, positionY);
		serverMediator.getWorld().getLocations().get(0).addEntity(userName, userCoordinate);

		for(Coordinate coordinate: serverMediator.getWorld().getLocations().get(0).getTiles().keySet()){
			if(coordinate.getxPostion() == positionX && coordinate.getyPosition() == positionY){
				serverMediator.getWorld().getLocations().get(0).addEntity(userName,coordinate);
				System.out.println("gives user:"+userName+" an initial coordinate! ["+positionX+","+positionY+"]");
				break;
			}
		}
	}

	private void handleString(String command) {
		switch (command) {
			case "left":
			case "right":
			case "up":
			case "down":
				locationController = new LocationController(serverMediator);
				System.out.println(this.userName+" move----->"+command);
				locationController.moveTo(this.userName, command);
				try {
					objectOutput.writeObject(serverMediator.getWorld());
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Change " + userName + "'s coordinate to" + "[" +
						serverMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getxPostion()
						+ "," + serverMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getyPosition() + "]");
				break;
			case "getWorld":
				try {
					objectOutput.writeObject(serverMediator.getWorld());
					System.out.println("user get the world object when they login");
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

		if (entity instanceof User){
			((User) entity).logout();
			server.removeClient(this);
		}
	}
}

