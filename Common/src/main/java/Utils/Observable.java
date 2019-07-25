package Utils;

import java.util.HashSet;
import java.util.Set;

public class Observable {

    private Set<Observer> observerSet = new HashSet<>();

    protected void notifyObserver(){
        for(Observer observer: observerSet){
            observer.update();
        }
    }

    public void addObserver(Observer observer){
        observerSet.add(observer);
    }

    public void removeObserver(Observer observer){
        observerSet.remove(observer);
    }

    public Set<Observer> getObserverSet() {
        return observerSet;
    }

    public void setObserverSet(Set<Observer> observerSet) {
        this.observerSet = observerSet;
    }
}
