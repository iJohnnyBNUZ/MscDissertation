import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class GamePanel extends JPanel implements KeyListener,Runnable {
    private char keyDirection;
    private JLabel lbEnergy = new JLabel(); //show energy
    private JLabel lbMove = new JLabel();
    private Socket s = null;
    private Scanner in;
    private PrintStream ps = null;

    private boolean canRun = true; //when energy = 0, it will be false
    private String userName = null;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public GamePanel(){
        //set whole panel
        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);
        this.setSize(500,320);

        //set energy label
        this.add(lbEnergy);
        lbEnergy.setFont(new Font("Arial",Font.BOLD,20));
        lbEnergy.setBackground(Color.YELLOW);
        lbEnergy.setForeground(Color.PINK);
        lbEnergy.setBounds(0,0,this.getWidth(),20);

        //set move label
        this.add(lbMove);
        lbMove.setFont(new Font("Arial",Font.BOLD,10));
        lbMove.setBackground(Color.black);
        lbMove.setForeground(Color.white);
        lbMove.setBounds(0,20,this.getWidth(),200);

        this.addKeyListener(this);

        try{
            s = new Socket("127.0.0.1",7777);
            JOptionPane.showMessageDialog(this,"Success connected");
            in = new Scanner(s.getInputStream());
            OutputStream os = s.getOutputStream();
            ps = new PrintStream(os);
            new Thread(this).start();

        } catch (Exception ex){
            javax.swing.JOptionPane.showMessageDialog(this,"Game exit exception! ");
            System.exit(0);
        }

        lbEnergy.setText("Current energy: " + 100);
    }

    public void run() {
        try{
            while(canRun){
                String str = in.nextLine();
                System.out.println(str);
                lbMove.setText(str);
                checkFail();
            }
        }catch (Exception ex){
            canRun = false;
            javax.swing.JOptionPane.showMessageDialog(this,"Game exit exception! ");
            System.exit(0);
        }
    }


    public void checkFail(){
//        if(energy<=0){
//            javax.swing.JOptionPane.showMessageDialog(this,"Energy is exhausted, the game is over!");
//            System.exit(0);
//        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e){
        keyDirection = e.getKeyChar();
        String direction = String.valueOf(keyDirection).toLowerCase();
        try{
            String returnMsg = direction;
            System.out.println(direction);
            ps.println(returnMsg);
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
