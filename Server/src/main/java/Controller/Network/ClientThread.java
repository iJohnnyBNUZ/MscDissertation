package Controller.Network;

import Model.Entity.Entity;
import Model.Entity.User;
import Model.World;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread extends Thread implements Runnable {
	private Socket socket;
	Server server;
	private boolean canRun = true;
	ObjectOutputStream objectOutput;
	private Scanner in;
	private PrintWriter out;

	public ClientThread(Socket socket, Server server) throws Exception{
		//create input and output channels for this Thread
		this.socket = socket;
		this.server = server;
		in = new Scanner(new InputStreamReader(this.socket.getInputStream()));
		out = new PrintWriter(this.socket.getOutputStream(),true);
	}

	//share the messages from other users
	@Override
	public void run() {
		try{
			while(canRun){
				/*Object input = (Object) in.readObject();
				System.out.println(input);
				if(input instanceof Entity) {
					handleEntity((User) input);
				}
				else {
					//handleString((String) input);
					sendMessage((String) input);
				}*/
				while(canRun){
					String input = in.nextLine();
					//System.out.println(input+"=====+++++++");
					sendMessage(input);
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
		System.out.println(user.getEntityID());
		World.getInstance().addEntity(user);
	}

	/*public void handleString(String d) {

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
	}*/
}

