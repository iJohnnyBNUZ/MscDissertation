package Controller.Network;

import Controller.GameMediator;
import Model.Entity.Entity;
import Model.Entity.User;
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
		gameMediator.getWorld().addEntity(user);
		userName = user.getEntityID();
	}

	private void handleString(String d) {
		System.out.println("Username ->" + userName);
		switch (d) {
			case "a":
				System.out.println("A");
				break;
			case "d":
				System.out.println("D");
				break;
			case "w":
				System.out.println("W");
				break;
			case "s":
				System.out.println("S");
				break;
			case "getWorld":
				try {
					objectOutput.writeObject(gameMediator.getWorld());
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
		}
	}
}

