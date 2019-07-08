import javax.swing.*;
import java.awt.*;

import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class Server extends JFrame implements Runnable{
    private Socket s = null;
    private ServerSocket ss = null;
    private ArrayList<ChatThread> clients = new ArrayList<ChatThread>();

    public Server() throws Exception{
        this.setTitle("ServerEnd");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.DARK_GRAY);
        this.setSize(200,200);
        this.setVisible(true);
        ss = new ServerSocket(7777);
        new Thread(this).start();
    }

    //receive the thread when clients connected in
    public void run() {
        try{
            while(true){
                s = ss.accept(); //wait for client connect
                ChatThread ct = new ChatThread(s); //create new ChatThread for user
                clients.add(ct); //add new Thread to List
                ct.start(); //start the new Thread to implement communicate
            }
        }catch (Exception ex){
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this,"Game exit exception");
            System.exit(0);
        }
    }

    class ChatThread extends Thread implements Runnable{
        private Socket s = null;
        private PrintStream ps =null;
        private Scanner in;
        private boolean canRun = true;
        private PrintWriter out;

        public ChatThread(Socket s) throws Exception{
            //creat input and output channels for this Thread
            this.s = s;
            in = new Scanner(new InputStreamReader(this.s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(),true);
        }

        //share the messages from other users
        @Override
        public void run() {
            try{

                while(canRun){
                    String input = in.nextLine();
                    //System.out.println(input+"=====+++++++");
                    sendMessage(input);
                }
            }catch (Exception ex){
                canRun = false;
                clients.remove(this); //remove this Thread from the clients list
            }
        }

        public void sendMessage(String msg){
            //System.out.println("send Messages from server"+msg+"-------------");
            for(ChatThread ct:clients){
                ct.out.println(msg);
                System.out.println("print Messages by client");
            }
        }

    }

    public static void main(String[] args) throws Exception{
        Server server = new Server();
    }
}
