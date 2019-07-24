package Controller.Network;

import Controller.ServerMediator;
import Model.Entity.Entity;
import Model.Entity.NPC;
import Model.Entity.Shop;
import Model.Item.Food;
import Model.Item.Item;
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
		serverMediator.newGame();
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
}
