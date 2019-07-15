package Controller.Network;

import Controller.GameMediator;
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
	private GameMediator gameMediator;
	
	private String IP = "";
	private int PORT = 0;
	
	public Client(GameMediator gameMediator){
		this.gameMediator= gameMediator;
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

	public GameMediator getGameMediator() {
		return gameMediator;
	}

	public void setGameMediator(GameMediator gameMediator) {
		this.gameMediator = gameMediator;
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
			//added by myself
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
			if (!((User) gameMediator.getWorld().getEntity(uName)).getOnline()){
				((User) gameMediator.getWorld().getEntity(uName)).setOnline(true);
				objectOutputStream.writeObject(gameMediator.getWorld());
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
				System.out.println("Awaiting message");
			    Object input = objectInputStream.readObject();
				System.out.println(input);
			    if(input instanceof World) {
			        gameMediator.setWorld((World) input);
				    for (Entity entity : ((World) input).getEntities()) {
					    System.out.println(entity);
				    }
				    String printString = "";
				    for(Entity entity:gameMediator.getWorld().getEntities()){
				    	String name = entity.getEntityID();
				    	String userinformation = name+"'s coordinate to"+"["+
								gameMediator.getWorld().getEntityLocation(name).getEntities().get(name).getxPostion()
								+","+gameMediator.getWorld().getEntityLocation(name).getEntities().get(name).getyPosition()+"]";
				    	printString += userinformation;
					}
			    }
			    else if(input instanceof String) {
			        System.out.println((String) input);
			        gameMediator.getIndexView().showMessage("Cannot connect to the server");
			    }
			    else {
				    System.out.println("What");
			    }

			} catch (Exception ex) {
				canRun = false;
				
				System.exit(0);
				ex.printStackTrace();
			}
		}
	}
	
}
