package Utils.example;

import Utils.Observer;

public class Camera implements Observer {

    private int id;

    public Camera(int id){
        this.id = id;
    }

    @Override
    public void takeAction(Object... msg) {
        System.out.println("camera " + id + " say: there are " + msg[2] + " people");
    }
}
