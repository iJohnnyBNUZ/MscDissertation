import Controller.Command.MoveCommand;
import Model.Location.*;
import Model.World;

import javax.swing.*;
import java.awt.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class GamePanel extends JPanel implements KeyListener,Runnable {
    private int energy = 15;
    private char keyDirection;
    private JLabel lbEnergy = new JLabel(); //show energy
    private JLabel lbMove = new JLabel();
    private Socket s = null;
    private Scanner in;
    private PrintStream ps = null;

    private boolean canRun = true; //when energy = 0, it will be false
    private String userName = null;
    private Coordinate initCoordinate = null;
    private World world = null;

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
        lbEnergy.setText("Current energy: "+energy);

        //set move label
        this.add(lbMove);
        lbMove.setFont(new Font("Arial",Font.BOLD,10));
        lbMove.setBackground(Color.black);
        lbMove.setForeground(Color.white);
        lbMove.setBounds(0,20,this.getWidth(),200);


        //init the world
        this.initWorld();
        this.init();
        this.addKeyListener(this);

        try{
            s = new Socket("127.0.0.1",7777);
            JOptionPane.showMessageDialog(this,"Success connected");
            in = new Scanner(s.getInputStream());
            OutputStream os = s.getOutputStream();
            ps = new PrintStream(os);
            new Thread(this).start();

        }catch (Exception ex){
            javax.swing.JOptionPane.showMessageDialog(this,"Game exit exception! ");
            System.exit(0);
        }
    }

    @Override
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


    public void init(){
        lbEnergy.setText("Current energy: "+energy);
    }

    public void initWorld(){
        world = new World(1);
        Location l1 = new Location("location1");
        world.setCurrentLocation(l1);

        Tile t1 = new Grass(true,"Grass",1);
        Tile t2 = new Water(true,"Water",1);
        Tile t3 = new Grass(true,"Grass",1);
        Tile t4 = new Stone(true,"Stone",1);
        Tile t5 = new Grass(true,"Grass",1);
        Tile t6 = new Water(true,"water",1);
        Tile t7 = new Door(true,"Door",1);
        Tile t8 = new Grass(true,"Grass",1);
        Tile t9 = new Grass(true,"Grass",1);

        Coordinate c1 = new Coordinate(0,0);
        Coordinate c2 = new Coordinate(0,1);
        Coordinate c3 = new Coordinate(0,2);
        Coordinate c4 = new Coordinate(1,0);
        Coordinate c5 = new Coordinate(1,1);
        Coordinate c6 = new Coordinate(1,2);
        Coordinate c7 = new Coordinate(2,0);
        Coordinate c8 = new Coordinate(2,1);
        Coordinate c9 = new Coordinate(2,2);

        initCoordinate = c5;
        l1.addEntity(userName,initCoordinate);
        l1.addTile(c1,t1);
        l1.addTile(c2,t2);
        l1.addTile(c3,t3);
        l1.addTile(c4,t4);
        l1.addTile(c5,t5);
        l1.addTile(c6,t6);
        l1.addTile(c7,t7);
        l1.addTile(c8,t8);
        l1.addTile(c9,t9);
    }

    public void checkFail(){
        this.init();
        if(energy<=0){
            javax.swing.JOptionPane.showMessageDialog(this,"Energy is exhausted, the game is over!");
            System.exit(0);
        }
    }

    public void findNewCoordinate(String d) {
        Iterator<Map.Entry<Coordinate, Tile>> iterator = this.world.getCurrentLocation().getTiles().entrySet().iterator();

        if (d.equals("a")) {
            while (iterator.hasNext()) {
                Map.Entry<Coordinate, Tile> entry = iterator.next();
                if (entry.getKey().getxPostion() == initCoordinate.getxPostion() - 1 && entry.getKey().getyPosition() == initCoordinate.getyPosition()) {
                    initCoordinate = entry.getKey();
                }
            }
        }else if(d.equals("d")){
            while (iterator.hasNext()) {
                Map.Entry<Coordinate, Tile> entry = iterator.next();
                if (entry.getKey().getxPostion() == initCoordinate.getxPostion() + 1 && entry.getKey().getyPosition() == initCoordinate.getyPosition()) {
                    initCoordinate = entry.getKey();
                }
            }
        }else if(d.equals("w")){
            while (iterator.hasNext()) {
                Map.Entry<Coordinate, Tile> entry = iterator.next();
                if (entry.getKey().getxPostion() == initCoordinate.getxPostion() && entry.getKey().getyPosition() == initCoordinate.getyPosition()-1) {
                    initCoordinate = entry.getKey();
                }
            }
        }else if(d.equals("d")){
            while (iterator.hasNext()) {
                Map.Entry<Coordinate, Tile> entry = iterator.next();
                if (entry.getKey().getxPostion() == initCoordinate.getxPostion() && entry.getKey().getyPosition() == initCoordinate.getyPosition()+1) {
                    initCoordinate = entry.getKey();
                }
            }
        }
    }

    public void keyPressed(KeyEvent e){
        keyDirection = e.getKeyChar();
        String direction = String.valueOf(keyDirection).toLowerCase();
        //System.out.println(direction+"in key");
        try{
            energy-=1;
            MoveCommand mc = new MoveCommand(world);
            String returnMsg = mc.excute(userName,initCoordinate,direction);
            findNewCoordinate(direction);
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
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
