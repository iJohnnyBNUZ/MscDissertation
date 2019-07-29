package Controller.Network;

import Controller.ClientMediator;
import Model.Entity.User;
import Model.World;
import Network.Event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Client implements Runnable {

	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;

	private boolean canRun = true;
	private String userName;
	private Socket socket;
	private ClientMediator clientMediator;

	private String IP = "";
	private int PORT = 0;
	private int SLEEP_TIME = 5000;


	public Client(ClientMediator clientMediator){
		this.clientMediator = clientMediator;
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
		    socket = new Socket(IP, PORT);
			socket.setTcpNoDelay(true);
			Scanner in = new Scanner(socket.getInputStream());
			this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			this.objectInputStream = new ObjectInputStream(socket.getInputStream());
			sendMessageToServer("getWorld");
			new Thread(this).start();
		} catch (Exception ex){
			ex.printStackTrace();
			canRun=false;
		}

		return canRun;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(SLEEP_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
		}
	}

	private void talkToServer() throws IOException, ClassNotFoundException {
		if(clientMediator.getActionQueue().isEmpty()) {
			sendMessageToServer("getUpdates");
		}
		else {
			sendMessageToServer(clientMediator.getActionQueue().remove());
		}
	}

	private void sendMessageToServer(String message) throws IOException, ClassNotFoundException {
		System.out.println(message);
		objectOutputStream.writeObject(message);
		Object input = objectInputStream.readObject();
		if (input instanceof Event) {
			handleEvent((Event)input);
			System.out.println("receiving event: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
		}
		else if(input instanceof World) {
			updateWorld((World)input);
			System.out.println("receiving world" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
		}
		else if(input instanceof String) {
			handleString((String)input);
		}
		else {
			System.out.println("Received unknown object from server");
		}
	}

	private void handleEvent(Event event) {
		System.out.println("Got new event");
		clientMediator.getLocationController().moveTo((event).getEntityID(), (event).getDirection());
		}

	private void handleString(String input) {
		System.out.println((input));
		System.out.println("No new events");
	}

	private void updateWorld(World world) {
		clientMediator.setWorld(world);
	}

	public void login(String type, String uName) throws IOException, ClassNotFoundException {
		this.userName = uName;
		if(type.equals("new")) {
			createUser(uName);
		}else if(type.equals("continue")) {
			((User) clientMediator.getWorld().getEntity(uName)).setOnline(true);
			objectOutputStream.writeObject(clientMediator.getWorld().getEntity(uName));
		}
	}

	public void MoveTo(String command)throws IOException, ClassNotFoundException {
		if(command != null)
			objectOutputStream.writeObject((Object) command);
	}

	public void OpenDoor(String command)throws IOException, ClassNotFoundException{
		if(command.equals("o")) objectOutputStream.writeObject("openDoor");
	}

	private void createUser(String userName) throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject(new User(userName));
		sendMessageToServer("getWorld");
	}

	public boolean isCanRun() {
		return canRun;
	}

	public void setCanRun(boolean canRun) {
		this.canRun = canRun;
	}

	public String getUserName() {
		return userName;
	}
}
