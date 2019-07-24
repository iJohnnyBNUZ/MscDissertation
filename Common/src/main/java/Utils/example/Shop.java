package Utils.example;

import Utils.Observable;

import java.util.HashSet;
import java.util.Set;

public class Shop extends Observable {
    static String IN = "in";
    static String OUT = "out";

    Set<String> personSet = new HashSet<>();
    int pcount = 0;

    public void comeIn(String name){
        personSet.add(name);
        pcount = personSet.size();
        notifyObserver(IN, name, pcount);
    }

    public void getOut(String name){
        personSet.remove(name);
        pcount = personSet.size();
        notifyObserver(OUT, name, pcount);    //通知观察者
    }
}
