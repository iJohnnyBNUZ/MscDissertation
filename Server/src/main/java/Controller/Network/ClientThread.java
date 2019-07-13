package Controller.Network;

import Controller.GameMediator;
import Controller.LocationController;
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
	private String userName;
	private GameMediator gameMediator;
	private LocationController locationController;

	ClientThread(Socket socket, Server server, GameMediator gameMediator) throws Exception{
		this.server = server;
		objectInput = new ObjectInputStream(socket.getInputStream());
		objectOutput = new ObjectOutputStream(socket.getOutputStream());
		this.gameMediator = gameMediator;
	}

	@Override
	public void run() {
		while (canRun) {
			try {
				Thread.sleep(300);
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
				System.out.println("Tick");
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
		System.out.println("Sending message to user: " +userName);
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
		gameMediator.getWorld().addEntity(user);
		this.userName = userName;
		userName = user.getEntityID();
		initEntityLocation(userName);
	}

	private void initEntityLocation(String userName){
		//inital the user in first location in the world
		gameMediator.getWorld().getEntity(userName).setCurrentLocation(gameMediator.getWorld().getLocations().get(0));
		gameMediator.getWorld().getLocations().get(0).addUser((User)gameMediator.getWorld().getEntity(userName));
		//init the coordinate for user with random
		int max =2,min =0;
		int positionX = min + (int)(Math.random() * (max-min+1));
		int positionY = min + (int)(Math.random() * (max-min+1));

		for(Coordinate coordinate:gameMediator.getWorld().getLocations().get(0).getTiles().keySet()){
			if(coordinate.getxPostion() == positionX && coordinate.getyPosition() == positionY){
				gameMediator.getWorld().getLocations().get(0).addEntity(userName,coordinate);
				System.out.println("gives user:"+userName+" an initial coordinate!");
				break;
			}
		}
	}

	private void handleString(String d) {
/*		System.out.println("Username ->" + userName+"now is in coordinate-> ["+
				gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getxPostion()
				+","+gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getyPosition()+"]");*/
		if(d.equals("a") || d.equals("d") || d.equals("w") ||d.equals("s")){
			locationController = new LocationController(gameMediator);
			locationController.moveTo(userName,d);
			try {
				objectOutput.writeObject(gameMediator.getWorld());
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Change "+userName+"'s coordinate to"+"["+
					gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getxPostion()
					+","+gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getyPosition()+"]");
		}else if(d.equals("getWorld")){
			try {
				objectOutput.writeObject(gameMediator.getWorld());
				System.out.println("user get the world object when they login");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(d.equals("logout")) {
			logout();
		}

	}

	public void logout() {
		Entity entity =  gameMediator.getWorld().getEntity(userName);

		if (entity instanceof User){
			((User) entity).logout();  // set isOnline is false
			server.removeClient(this);  // remove clint thread
		}
	}
}

