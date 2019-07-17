package Controller.Network;

import Controller.ClientMediator;
import Model.Entity.Entity;
import Model.Entity.User;
import Model.World;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
			OutputStream os = s.getOutputStream();
			this.objectOutputStream = new ObjectOutputStream(os);
			this.objectInputStream = new ObjectInputStream((s.getInputStream()));
			new Thread(this).start();
			
			getWorldFromServer();

		} catch (Exception ex){
			canRun=false;
		}
		
		return canRun;
	}
		
	private void getWorldFromServer() throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject("getWorld");
	}
	
	public void login(String type, String uName) throws IOException, ClassNotFoundException {
		this.userName = uName;
		if(type.equals("new")) {
			createUser(uName);
		}else if(type.equals("continue")) {
			if (!((User) clientMediator.getWorld().getEntity(uName)).getOnline()){
				((User) clientMediator.getWorld().getEntity(uName)).setOnline(true);
				objectOutputStream.writeObject(clientMediator.getWorld());
			}
		}
		
	}
	
	private void createUser(String userName) throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject(new User(userName));
		getWorldFromServer();
	}

	@Override
	public void run() {
		while(canRun) {
			try {
				Thread.sleep(500);
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

			} catch (Exception ex) {
				canRun = false;
				System.exit(0);
				ex.printStackTrace();
			}
		}
	}

	private void handleString(String input) {
		System.out.println((input));
		clientMediator.getIndexView().showMessage("Cannot connect to the server");
	}

	private void updateWorld(World world) {
		clientMediator.setWorld(world);
		for (Entity entity : world.getEntities()) {
			System.out.println(entity);
		}
		StringBuilder printString = new StringBuilder();
		for(Entity entity: clientMediator.getWorld().getEntities()){
			String name = entity.getEntityID();
			String userInfo = name+"'s coordinate to"+"["+
					clientMediator.getWorld().getEntityLocation(name).getEntities().get(name).getxPostion()
					+","+ clientMediator.getWorld().getEntityLocation(name).getEntities().get(name).getyPosition()+"]";
			printString.append(userInfo);
		}
	}

}
