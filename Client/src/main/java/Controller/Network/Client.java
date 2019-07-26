package Controller.Network;

import Controller.ClientMediator;
import Model.Entity.User;
import Model.World;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
	private int SLEEP_TIME = 500;

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

	//run with timer.
	@Override
	public void run() {
		while(canRun) {
			try {
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
				sendCommand();
				getWorldFromServer();
			} catch (Exception ex) {
				canRun = false;
				System.exit(0);
				ex.printStackTrace();
			}
		}
	}

	public void sendCommand() throws IOException{
		try{
			if(clientMediator.getQueue().size()>0){
				String command = clientMediator.getQueue().get(0);
				System.out.println("send command: +" + command);
				objectOutputStream.writeObject((Object) command);
				clientMediator.removeAction(command);
			}
		}catch (IOException ie){
			ie.getStackTrace();
		}
	}

	private void getWorldFromServer() throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject("getWorld");
		Object input = objectInputStream.readObject();
		if(input instanceof World) {
			updateWorld((World)input);
		}
		else if(input instanceof String) {
			handleString((String)input);
		}
		else {
			System.out.println("Received unknown object from server");
		}
	}

	private void handleString(String input) {
		System.out.println((input));
		clientMediator.getIndexView().showMessage("Cannot connect to the server");
	}

	private void updateWorld(World world) {
		System.out.println("Got new world");
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
		if(command.equals("o")) objectOutputStream.writeObject((Object) "OpenDoor");
	}
	
	private void createUser(String userName) throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject(new User(userName));
	}

	public Scanner getIn() {
		return in;
	}

	public void setIn(Scanner in) {
		this.in = in;
	}

	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}

	public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
		this.objectOutputStream = objectOutputStream;
	}

	public ObjectInputStream getObjectInputStream() {
		return objectInputStream;
	}

	public void setObjectInputStream(ObjectInputStream objectInputStream) {
		this.objectInputStream = objectInputStream;
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

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ClientMediator getClientMediator() {
		return clientMediator;
	}

	public void setClientMediator(ClientMediator clientMediator) {
		this.clientMediator = clientMediator;
	}
}
