package Controller.Network;

import Model.Location.*;
import Model.World;

import javax.swing.*;
import java.awt.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends JFrame implements Runnable{
	private Socket s = null;
	private ServerSocket ss = null;
	private ArrayList<ClientThread> clients = new ArrayList<ClientThread>();

	private Coordinate initCoordinate = null;
	private int energy = 100;
	private String userName = " ";

	public Server() throws Exception{
		this.setTitle("ServerEnd");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.DARK_GRAY);
		this.setSize(200,200);
		this.setVisible(true);
		ss = new ServerSocket(7777);
		new Thread(this).start();

		//init the world
		this.initWorld();

	}

	public void initWorld(){
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

		initCoordinate = c5;
		l1.addEntity(userName,initCoordinate);
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

	//receive the thread when clients connected in
	public void run() {
		try{
			while(true){
				s = ss.accept(); //wait for client connect
				ClientThread ct = new ClientThread(s, this); //create new ChatThread for user
				clients.add(ct); //add new Thread to List
				ct.start(); //start the new Thread to implement communicate
			}
		}catch (Exception ex){
			ex.printStackTrace();
			javax.swing.JOptionPane.showMessageDialog(this,"Game exit exception");
			System.exit(0);
		}
	}

	public void removeClient(ClientThread client) {
		clients.remove(client);
	}

	public void updateClients() {
		for(ClientThread ct:clients) {
			System.out.println("Responding to client");
			ct.sendMessage("Updating world");
		}
	}

}
