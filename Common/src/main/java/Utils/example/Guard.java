package Utils.example;

import Utils.Observer;

public class Guard implements Observer {

    private String name;
    public Guard(){}

    public Guard(String name){this.name = name;}

    @Override
    public void takeAction(Object... msg) {
        if (msg[0].equals("in")){
            System.out.println(this.name + " say：" + msg[1] + " come in！");
        }else if(msg[0].equals("in")){
            System.out.println(this.name + " say：" + msg[1] + " come out！");
        }
    }
}
