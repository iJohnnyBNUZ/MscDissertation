package Controller.Network;

import Controller.ServerMediator;
import Model.Entity.Entity;
import Model.Location.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
	private ServerSocket serverSocket;
	private ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	private ServerMediator serverMediator;

	public Server(ServerMediator serverMediator) throws Exception{
		serverSocket = new ServerSocket(7777);
		this.serverMediator = serverMediator;
		new Thread(this).start();
		this.initWorld();
	}

	public void run() {
		System.out.println("Server is running");
		try{
			while(true){
				Socket socket = serverSocket.accept();
				ClientThread ct = new ClientThread(socket, this, serverMediator);
				clients.add(ct);
				System.out.println("new Thread in Server!");
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

	void updateClients() throws IOException {
		for(ClientThread ct:clients) {
			System.out.println("Responding to client");
			for (Entity entity : serverMediator.getWorld().getEntities()) {
				System.out.println(entity);
			}
			ct.sendMessage(serverMediator.getWorld());
		}
	}

	private void initWorld(){
		Location l1 = new Location("location1");
		serverMediator.getWorld().addLocation(l1);
		
		int num=0;
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				Coordinate tmp_cor = new Coordinate(i, j);
				if(i<5&&j<5) {
					Tile tile = new Grass(true,"grass"+num,1);
					l1.addTile(tmp_cor, tile);
				}else if(i<5&& j >5){
					Tile tile = new Water(true,"water"+num,1);
					l1.addTile(tmp_cor, tile);
				}else if(i>5) {
					Tile tile = new Stone(true,"stone"+num,1);
					l1.addTile(tmp_cor, tile);
				}else {
					Tile tile = new Door(true,"door"+num,1);
					l1.addTile(tmp_cor, tile);
				}
				
				num++;
			}
		}

	}
}
