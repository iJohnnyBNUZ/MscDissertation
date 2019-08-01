package Controller.Network;

import Controller.ServerMediator;
import Network.Events.Event;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class Server implements Runnable{
	private ServerSocket serverSocket;
	private ArrayList<ServerListener> clients = new ArrayList<ServerListener>();
	private ServerMediator serverMediator;
	private LinkedList<Event> eventQueue = new LinkedList<>();

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
				ServerListener serverListener = new ServerListener(socket, this, serverMediator);
				clients.add(serverListener);
				serverListener.start();
			}
		}catch (Exception ex){
			ex.printStackTrace();
			System.exit(0);
		}
	}

	void removeClient(ServerListener client) {
		clients.remove(client);
	}

	void addEventToQueue(Event event) {
		eventQueue.add(event);
	}

	void updateOtherClients(ServerListener eventCreator) {
		if (!eventQueue.isEmpty()) {
			for(ServerListener client : clients) {
				if(client != eventCreator) {
					client.sendMessage(eventQueue.getFirst());
				}
			}
			eventQueue.remove();
		}
	}

	void sendWorldToClients() {
		System.out.println("Sending world to all clients");
		for(ServerListener client : clients) {
			client.updateMyClient();
		}
	}
}
