package Controller.Network;

import Model.World;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread extends Thread implements Runnable {
	private Socket s = null;
	private PrintStream ps =null;
	private Scanner in;
	private boolean canRun = true;
	private PrintWriter out;
	ObjectOutputStream objectOutput;
	Server server;

	public ClientThread(Socket s, Server server) throws Exception{
		//create input and output channels for this Thread
		this.s = s;
		in = new Scanner(new InputStreamReader(this.s.getInputStream()));
		objectOutput = new ObjectOutputStream(s.getOutputStream());
		this.server = server;
	}

	//share the messages from other users
	@Override
	public void run() {
		try{
			while(canRun){
				String input = in.nextLine();
				System.out.println(input);
				findNewCoordinate(input);
				sendMessage(input);
			}
		}catch (Exception ex){
			canRun = false;
			server.removeClient(this);

		}
	}

	public void sendMessage(String msg){
		out.println(msg);
	}

	public void findNewCoordinate(String d) {

		if (d.equals("a")) {
			System.out.println("A");
		}

		else if(d.equals("d")){
			System.out.println("D");
		}

		else if(d.equals("w")){
			System.out.println("W");
		}

		else if(d.equals("d")){
			System.out.println("D");
		}

		else if (d.equals("getWorld")) {
			try {
				objectOutput.writeObject(World.getInstance());
				System.out.println("Sending world");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

