package Controller;

import Model.Entity.Entity;
import Model.Entity.User;
import Model.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class GamePanel extends JPanel implements KeyListener,Runnable {
	private JLabel lbMove = new JLabel();
	private Scanner in;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;

	private boolean canRun = true;
	private String userName;
	private Socket s;
	private ClientMediator clientMediator;

	public GamePanel(String userName, ClientMediator clientMediator) throws IOException, ClassNotFoundException {

		this.clientMediator = clientMediator;

		this.userName = userName;

		//set whole panel
		this.setLayout(null);
		this.setBackground(Color.DARK_GRAY);
		this.setSize(1000,800);

		JLabel lbEnergy = new JLabel();
		this.add(lbEnergy);
		lbEnergy.setFont(new Font("Arial",Font.BOLD,40));
		lbEnergy.setBackground(Color.YELLOW);
		lbEnergy.setForeground(Color.PINK);
		lbEnergy.setBounds(0,0,this.getWidth(),20);

		this.add(lbMove);
		lbMove.setFont(new Font("Arial",Font.BOLD,20));
		lbMove.setBackground(Color.black);
		lbMove.setForeground(Color.white);
		lbMove.setBounds(0,20,this.getWidth(),200);

		this.addKeyListener(this);

		String IP = "";
		int PORT = 0;
		try{
			ResourceBundle rb = ResourceBundle.getBundle("config");
			IP = rb.getString("ip");
			PORT = Integer.parseInt(rb.getString("port"));
		}
		catch (MissingResourceException e) {
			e.printStackTrace();
		}

		try{
		    s = new Socket(IP, PORT);
			JOptionPane.showMessageDialog(this,"Success connected");
			in = new Scanner(s.getInputStream());
			OutputStream os = s.getOutputStream();
			this.objectOutputStream = new ObjectOutputStream(os);
			this.objectInputStream = new ObjectInputStream((s.getInputStream()));
			if(clientMediator.getWorld().getEntity(userName) instanceof User){
				System.out.println("User "+userName+ " exist, maybe change a name!");
				if (!((User) clientMediator.getWorld().getEntity(userName)).getOnline()){
					((User) clientMediator.getWorld().getEntity(userName)).setOnline(true);
					objectOutputStream.writeObject(clientMediator.getWorld());
				}else
					System.out.println("User "+userName+" is already online!");
			}else{
				createUser();
				getWorldFromServer();
			}

			new Thread(this).start();

		} catch (Exception ex){
			javax.swing.JOptionPane.showMessageDialog(this,"Game exit exception! ");
			System.exit(0);
		}

//		lbEnergy.setText("Current energy: " + World.getInstance().getEntity(userName).getEnergy());
	}

	private void createUser() throws IOException {
		objectOutputStream.writeObject(new User(userName));
	}

	private void getWorldFromServer() throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject("getWorld");
		Object newWorld = this.objectInputStream.readObject();
		if (newWorld instanceof World)
			clientMediator.setWorld((World) newWorld);
		else {
			System.out.println(newWorld);
			System.out.println("Something went wrong");
		}
	}

	@Override
	public void run() {
		while(canRun) {
			try {
				System.out.println("Awaiting message");
			    Object input = objectInputStream.readObject();
			    if(input instanceof World) {
			        clientMediator.setWorld((World) input);
				    for (Entity entity : ((World) input).getEntities()) {
					    System.out.println(entity);
				    }
				    String printString = "";
				    for(Entity entity: clientMediator.getWorld().getEntities()){
				    	String name = entity.getEntityID();
				    	String userinformation = name+"'s coordinate to"+"["+
								clientMediator.getWorld().getEntityLocation(name).getEntities().get(name).getxPostion()
								+","+ clientMediator.getWorld().getEntityLocation(name).getEntities().get(name).getyPosition()+"]";
				    	printString += userinformation;
					}
					//lbMove.setText(printString);
			    }
			    else if(input instanceof String) {
			        System.out.println((String) input);
				    lbMove.setText("Server -> " + input);
			    }
			    else {
				    System.out.println("What");
			    }

			} catch (Exception ex) {
				canRun = false;
				javax.swing.JOptionPane.showMessageDialog(this, "Game exit exception");
				System.exit(0);
				ex.printStackTrace();
			}
		}
	}


	private void checkFail(){
//        if(energy<=0){
//            javax.swing.JOptionPane.showMessageDialog(this,"Energy is exhausted, the game is over!");
//            System.exit(0);
//        }
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e){
		char keyDirection = e.getKeyChar();
		String direction = String.valueOf(keyDirection).toLowerCase();
		try{
			String returnMsg = direction;
			System.out.println(direction);
			objectOutputStream.writeObject(returnMsg);
			returnMsg+="\n";
			returnMsg+=lbMove.getText();
			lbMove.setText(returnMsg);
			checkFail();
		}catch(Exception ex){
			ex.printStackTrace();
			javax.swing.JOptionPane.showMessageDialog(this,"Game exit exception! ");
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}