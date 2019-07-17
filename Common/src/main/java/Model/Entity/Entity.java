package Model.Entity;

import Model.Item.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Serializable {
    private String entityID;
    private int energy;
    private int coin;
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
    }

    public void interactWith(Entity entity) {
        entity.reactTo(this);
    }

    public void reactTo(Entity entity) {
        entity.increaseEnergy(20);
    }

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
    }

    public void increaseEnergy(int amount) {
        energy += amount;
    }

    public void decreaseEnergy(int amount) {
        energy -= amount;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public void increaseCoin(int amount) { coin += amount; }

    public void decreaseCoin(int amount) { coin -= amount; }

    public void pickUp(Item item) {
        this.Bag.add(item);
    }

    public Item putDown(String id) {
        for(Item item: Bag) {
            if(item.getItemID().equals(id)) {
                this.Bag.remove(item);
                return item;
            }
        }
        return null;
    }

    public void removeFromBag(Item item){
        this.Bag.remove(item);
    }

    public void addToBag(Item item) { this.Bag.add(item); }
}
