package Controller;

import Model.Entity.User;
import Model.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
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

	public GamePanel(String userName) throws IOException, ClassNotFoundException {

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
			Socket s = new Socket(IP, PORT);
			JOptionPane.showMessageDialog(this,"Success connected");
			in = new Scanner(s.getInputStream());
			OutputStream os = s.getOutputStream();
			this.objectOutputStream = new ObjectOutputStream(os);
			this.objectInputStream = new ObjectInputStream((s.getInputStream()));
			createUser();
			getWorldFromServer();
			new Thread(this).start();

		} catch (Exception ex){
			javax.swing.JOptionPane.showMessageDialog(this,"Game exit exception! ");
			System.exit(0);
		}

		lbEnergy.setText("Current energy: " + World.getInstance().getEntity(userName).getEnergy());
	}

	private void createUser() throws IOException {
		objectOutputStream.writeObject(new User(userName));
	}

	private void getWorldFromServer() throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject("getWorld");

		Object obj = this.objectInputStream.readObject();
		if (obj != null){
			World world = (World) obj;
			World.setOurInstance((World) world);
		}
	}

	public void run() {
		try{
			while(canRun) {
				String str = in.nextLine();
				System.out.println(str);
				lbMove.setText(str);
				checkFail();
			}
		} catch(Exception ex) {
			canRun = false;
			javax.swing.JOptionPane.showMessageDialog(this,"Game exit exception");
			System.exit(0);
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
