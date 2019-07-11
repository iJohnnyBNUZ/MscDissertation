package Controller.Network;

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

	ClientThread(Socket socket, Server server) throws Exception{
		this.server = server;
		objectInput = new ObjectInputStream(socket.getInputStream());
		objectOutput = new ObjectOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		try{
			while(canRun) {
				Object input = (Object) objectInput.readObject();

				if(input instanceof Entity) {
					handleEntity((User) input);
				}
				else {
					handleString((String) input);
					sendMessage((String) input);
				}
			}
		}catch (Exception ex){
			canRun = false;
			server.removeClient(this);
			ex.printStackTrace();
		}
	}

	void sendMessage(String msg) {
		try {
			objectOutput.writeObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleEntity(User user) {
		System.out.println(user.getEntityID());
		World.getInstance().addEntity(user);
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
				System.out.println("Getting world");
				try {
					objectOutput.writeObject(World.getInstance());
					System.out.println("Sending world");
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
		}

		try {
			server.updateClients(d);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

