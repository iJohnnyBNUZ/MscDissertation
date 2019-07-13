package Model.Entity;

import Model.Item.Item;
import Model.Location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Serializable {
    private String entityID;
    private int energy;
    private int coin;
    private Location currentLocation;
    private List<Item> Bag = new ArrayList<Item>();

    public Entity(String id) {
        this.entityID = id;
    }

    public void useItem(Item item) {

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

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }
}
