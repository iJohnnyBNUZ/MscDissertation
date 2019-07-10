package Controller.Network;

import Model.Entity.Entity;
import Model.Entity.User;
import Model.World;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread implements Runnable {
	private Socket socket;
	Server server;
	private ObjectInputStream in;
	private boolean canRun = true;
	ObjectOutputStream objectOutput;
	private PrintWriter out;

	public ClientThread(Socket socket, Server server) throws Exception{
		//create input and output channels for this Thread
		this.socket = socket;
		this.server = server;
		in = new ObjectInputStream(socket.getInputStream());
//		in = new Scanner(new InputStreamReader(this.socket.getInputStream()));
		objectOutput = new ObjectOutputStream(socket.getOutputStream());
	}

	//share the messages from other users
	@Override
	public void run() {
		try{
			while(canRun){
				Object input = (Object) in.readObject();
				System.out.println(input);
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

		}
	}

	public void sendMessage(String msg){
		out.println(msg);
	}

	public void handleEntity(User user) {
		World.getInstance().addEntity(user);
	}

	public void handleString(String d) {

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
					objectOutput.writeObject(World.getInstance());
					System.out.println("Sending world");
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
		}
	}
}

