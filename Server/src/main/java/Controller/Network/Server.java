package Controller.Network;

import Model.Location.*;
import Model.World;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
	private ServerSocket serverSocket;
	private ArrayList<ClientThread> clients = new ArrayList<ClientThread>();

	public Server() throws Exception{
		serverSocket = new ServerSocket(7777);
		new Thread(this).start();
		this.initWorld();

	}

	public void run() {
		try{
			while(true){
				Socket socket = serverSocket.accept();
				ClientThread ct = new ClientThread(socket, this);
				clients.add(ct);
				ct.start();
			}
		}catch (Exception ex){
			ex.printStackTrace();
			System.exit(0);
		}
	}

	void removeClient(ClientThread client) {
		clients.remove(client);
	}

	public void updateClients(String msg) throws IOException {
		for(ClientThread ct:clients) {
			System.out.println("Responding to client");
			ct.sendMessage(msg);
		}
	}

	private void initWorld(){
		Location l1 = new Location("location1");
		World.getInstance().addLocation(l1);

		Tile t1 = new Grass(true,"Grass",1);
		Tile t2 = new Water(true,"Water",1);
		Tile t3 = new Grass(true,"Grass",1);
		Tile t4 = new Stone(true,"Stone",1);
		Tile t5 = new Grass(true,"Grass",1);
		Tile t6 = new Water(true,"water",1);
		Tile t7 = new Door(true,"Door",1);
		Tile t8 = new Grass(true,"Grass",1);
		Tile t9 = new Grass(true,"Grass",1);

		Coordinate c1 = new Coordinate(0,0);
		Coordinate c2 = new Coordinate(0,1);
		Coordinate c3 = new Coordinate(0,2);
		Coordinate c4 = new Coordinate(1,0);
		Coordinate c5 = new Coordinate(1,1);
		Coordinate c6 = new Coordinate(1,2);
		Coordinate c7 = new Coordinate(2,0);
		Coordinate c8 = new Coordinate(2,1);
		Coordinate c9 = new Coordinate(2,2);

		l1.addTile(c1,t1);
		l1.addTile(c2,t2);
		l1.addTile(c3,t3);
		l1.addTile(c4,t4);
		l1.addTile(c5,t5);
		l1.addTile(c6,t6);
		l1.addTile(c7,t7);
		l1.addTile(c8,t8);
		l1.addTile(c9,t9);

	}
}
