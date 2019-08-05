package Controller.Network;

import Controller.ClientMediator;
import Model.Entity.User;
import Network.Events.LoginEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ClientUpdater implements Runnable {

	private ObjectOutputStream objectOutputStream;

	private boolean canRun = true;
	private String userName;
	private ClientMediator clientMediator;
	private ClientListener clientListener;

	private String IP = "";
	private int PORT = 0;


	public ClientUpdater(ClientMediator clientMediator){
		this.clientMediator = clientMediator;
		this.clientListener = new ClientListener(clientMediator);
	}

	public Boolean connectToServer(String ip) {
		try{
			ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
			IP = ip;
			PORT = Integer.parseInt(resourceBundle.getString("port"));
		}
		catch (MissingResourceException e) {
			e.printStackTrace();
		}
		try{
			Socket socket = new Socket(IP, PORT);
			this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			clientListener.connectToServer(objectInputStream);
			new Thread(this).start();
		} catch (Exception ex){
			ex.printStackTrace();
			canRun=false;
		}
		return canRun;
	}

	@Override
	public void run() {
		System.out.println("Running server updater");
		while(canRun) {
			try {
				talkToServer();
			} catch (Exception ex) {
				canRun = false;
				ex.printStackTrace();
				System.exit(0);
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				objectOutputStream.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void talkToServer() {
		if (!clientMediator.getEventQueue().isEmpty()) {
			sendMessageToServer(clientMediator.getEventQueue().remove());
		}
	}

	private void sendMessageToServer(Object message) {
		try {
			objectOutputStream.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void login(String uName) throws IOException, ClassNotFoundException {
		this.userName = uName;
		sendMessageToServer(new LoginEvent(uName));
	}

	public void getWorld(){
		sendMessageToServer("getWorld");
	}

	private void createUser(String userName) {
		sendMessageToServer(new User(userName));
		//sendMessageToServer("getWorld");
	}

	public String getUserName() {
		return userName;
	}
}
