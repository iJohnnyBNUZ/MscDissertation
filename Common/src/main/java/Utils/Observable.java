package Utils;

import java.util.HashSet;
import java.util.Set;

public class Observable {

    private Set<Observer> observerSet = new HashSet<>();

    protected void notifyObserver(Object... params){
        for(Observer observer: observerSet){
            observer.takeAction(params);
        }
    }

    public void addObserver(Observer observer){
        observerSet.add(observer);
    }

    public void removeObserver(Observer observer){
        observerSet.remove(observer);
    }
}
