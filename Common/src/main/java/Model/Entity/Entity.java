package Model.Entity;

import Model.Item.Item;
import Utils.Observable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity extends Observable implements Serializable {
    private String entityID;
    private int energy=100;
    private int coin=100;
    private List<Item> Bag = new ArrayList<Item>();

    public Entity(String id) {
        this.entityID = id;
    }

    public void useItem(Item item) {
        removeFromBag(item);
    }

    public List<Item> getBag() {
        return Bag;
    }

    public void setBag(List<Item> bag) {
        Bag = bag;
        notifyObserver();
    }

    /*
    public void interactWith(Entity entity) {
        entity.reactTo(this);
    }
    */

    public String reactTo(Entity entity) {
        entity.increaseEnergy(20);
        return "increase 20 energy";
    }


    /*
    public void reactTo() {
        notifyObserver();
    }
    */

    public String getEntityID() {
        return entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
        notifyObserver();
    }

    public void increaseEnergy(int amount) {
        energy += amount;
        if(energy>100)
            energy=100;
        notifyObserver();
    }

    public void decreaseEnergy(int amount) {
        energy -= amount;
        if(energy<0)
            energy=0;
        notifyObserver();
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
        notifyObserver();
    }

    public void increaseCoin(int amount) { coin += amount; notifyObserver();}

    public void decreaseCoin(int amount) { coin -= amount; notifyObserver();}

    public void pickUp(Item item) {
        this.Bag.add(item);
        notifyObserver();
    }

    public Item putDown(String id) {
        for(Item item: Bag) {
            if(item.getItemID().equals(id)) {
                this.Bag.remove(item);
                return item;
            }
        }
        notifyObserver();
        return null;
    }

    public void removeFromBag(Item item){
        this.Bag.remove(item);
        notifyObserver();
    }

    public void addToBag(Item item) { this.Bag.add(item);
        notifyObserver();}
}
