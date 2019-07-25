package Controller.Network;

import Controller.ServerMediator;
import Model.Entity.Entity;

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
		System.out.println("Server is ready");
		try{
			while(true){
				Socket socket = serverSocket.accept();
				ClientThread clientThread = new ClientThread(socket, this, serverMediator);
				clients.add(clientThread);
				clientThread.start();
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
			System.out.println("-----Updating clients-----");
			for (Entity entity : serverMediator.getWorld().getEntities()) {
				System.out.println(entity.getEntityID());
			}
			ct.sendMessage(serverMediator.getWorld());
		}
	}
}
