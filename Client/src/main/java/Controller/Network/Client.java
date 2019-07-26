package Controller.Network;

import Controller.ClientMediator;
import Model.Entity.Entity;
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

	private Scanner in;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;

	private boolean canRun = true;
	private String userName;
	private Socket s;
	private ClientMediator clientMediator;
	
	private String IP = "";
	private int PORT = 0;
	
	public Client(ClientMediator clientMediator){
		this.clientMediator = clientMediator;
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

	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}

	public ClientMediator getClientMediator() {
		return clientMediator;
	}

	public void setClientMediator(ClientMediator clientMediator) {
		this.clientMediator = clientMediator;
	}

	
	public Boolean connectToServer(String Ip) {
		try{
			ResourceBundle rb = ResourceBundle.getBundle("config");
			IP = Ip;
			PORT = Integer.parseInt(rb.getString("port"));
		}
		catch (MissingResourceException e) {
			e.printStackTrace();
		}

		try{
		    s = new Socket(IP, PORT);
			in = new Scanner(s.getInputStream());
			this.objectOutputStream = new ObjectOutputStream(s.getOutputStream());
			this.objectInputStream = new ObjectInputStream((s.getInputStream()));
			getWorldFromServer();
			Object input = objectInputStream.readObject();
			if(input instanceof World) {
				updateWorld((World)input);
			}
			new Thread(this).start();
		} catch (Exception ex){
			ex.printStackTrace();
			canRun=false;
		}
		
		return canRun;
	}
		
	public void getWorldFromServer() throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject("getWorld");
	}
	
	public void login(String type, String uName) throws IOException, ClassNotFoundException {
		this.userName = uName;
		if(type.equals("new")) {
			createUser(uName);
			//getWorldFromServer();
		}else if(type.equals("continue")) {
			if (!((User) clientMediator.getWorld().getEntity(uName)).getOnline()){
				((User) clientMediator.getWorld().getEntity(uName)).setOnline(true);
				objectOutputStream.writeObject(clientMediator.getWorld().getEntity(uName));
			}
		}
		
	}

	public void MoveTo(String command){
		if(command != null) {
			try {
				objectOutputStream.writeObject((Object) command);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("exception in MoveTo method in client");
			}
		}
	}

	public void OpenDoor(String command){
		if(command != null || command.equals("o")) {
			try {
				objectOutputStream.writeObject((Object) "OpenDoor");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("exception in openDoor method in client");
			}
		}
	}

	public void logout(){
		try {
			objectOutputStream.writeObject((Object)"logout");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("exception in logout function from client");
		}
	}
	
	private void createUser(String userName) throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject(new User(userName));
	}

	@Override
	public void run() {
		while(canRun) {
			try {
				System.out.println("Tick");
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
				try {
					Thread.sleep(500);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}

			} catch (Exception ex) {
				canRun = false;
				ex.printStackTrace();
				System.exit(0);
			}
		}
	}

	private void handleString(String input) {
		System.out.println((input));
		clientMediator.getIndexView().showMessage("Cannot connect to the server");
	}

	private void updateWorld(World world) {
		clientMediator.setWorld(world);
		System.out.print("Entity in the world:");
		for (Entity entity : world.getEntities()) {
			System.out.print(entity.getEntityID()+", ");
		}
	}

}
