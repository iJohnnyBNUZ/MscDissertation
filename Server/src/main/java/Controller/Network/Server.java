package Controller.Network;

import Controller.ServerMediator;
import Network.Event;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class Server implements Runnable{
	private ServerSocket serverSocket;
	private ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	private ServerMediator serverMediator;
	private LinkedList<Event> EventQueue = new LinkedList<>();

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

	void addActionToQueue(Event event) {
		EventQueue.add(event);
	}

	void updateClients() throws IOException {
		if (!EventQueue.isEmpty()) {
			for(ClientThread ct:clients) {
				if(!EventQueue.getFirst().getEntityID().equals(ct.getUserName())) {
					ct.sendMessage(EventQueue.getFirst());
					System.out.println("Event sent to user");
				}
				else {
					System.out.println("Event not sent to user");
					ct.sendMessage("None");
				}
			}
			EventQueue.remove();
		}
		else {
			for (ClientThread ct:clients) {
				ct.sendMessage("None");
			}
		}
	}

	public void sendWorldToClients() {
		System.out.println("Sending world to all clients");
		for(ClientThread ct:clients) {
			ct.sendWorld();
		}
	}
}
