import Controller.GamePanel;
import Model.World;

import javax.swing.*;
import java.io.IOException;

public class Client extends JFrame {
    private GamePanel gp;

    public Client() throws IOException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String UserName = JOptionPane.showInputDialog("Input your userName:");
        System.out.println(UserName+"+++++");
        this.setTitle(UserName);
        gp = new GamePanel();
        gp.setUserName(UserName);

        this.add(gp);
        gp.setFocusable(true);
        this.setSize(gp.getWidth(),gp.getHeight());
        this.setResizable(true);
        this.setVisible(true);

        World.getInstance();
    }

    public static void main(String[] args) throws IOException {
        new Client();
    }
}
