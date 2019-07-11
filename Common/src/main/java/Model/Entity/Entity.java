package Model.Entity;

import Model.Item.Item;
import Model.Location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Serializable {
    private String entityID;
    private int Energy;
    private int Coin;
    private Location currentLocation;
    private List<Item> Bag = new ArrayList<Item>();

    public Entity(String id) {
        this.entityID = id;
    }

    public String getEntityID() {
        return entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public int getEnergy() {
        return Energy;
    }

    public void setEnergy(int energy) {
        Energy = energy;
    }

    public int getCoin() {
        return Coin;
    }

    public void setCoin(int coin) {
        Coin = coin;
    }

    public void addToBag(Item item) {
        this.Bag.add(item);
    }

    public Item removeFromBag(String id) {
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
