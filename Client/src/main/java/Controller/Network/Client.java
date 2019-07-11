package Controller.Network;

import Controller.GameMediator;
import Controller.GamePanel;

import javax.swing.*;
import java.io.IOException;

public class Client extends JFrame {

	public Client(GameMediator gameMediator) throws IOException, ClassNotFoundException {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		String UserName = JOptionPane.showInputDialog("Input your userName:");
		System.out.println(UserName + "+++++");
		this.setTitle(UserName);
		GamePanel gamePanel = new GamePanel(UserName, gameMediator);

		this.add(gamePanel);
		gamePanel.setFocusable(true);
		this.setSize(gamePanel.getWidth(), gamePanel.getHeight());
		this.setResizable(true);
		this.setVisible(true);
	}
}
