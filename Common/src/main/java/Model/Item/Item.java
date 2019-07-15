package Model.Item;

import Model.Entity.Entity;

import java.io.Serializable;

public class Item implements Serializable {
    private String itemID;
    private int coinValue = 0;
    private boolean isEdible = false;
    private boolean isCollectible = true;
    private String type = null;

    public Item(String id,int coinValue,String type) {
        this.itemID = id;
        this.coinValue = coinValue;
        this.type = type;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public int getCoinValue() {
        return coinValue;
    }

    public void setCoinValue(int coinValue) {
        this.coinValue = coinValue;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public void setEdible(boolean edible) {
        isEdible = edible;
    }

    public boolean isCollectible() {
        return isCollectible;
    }

    public void setCollectible(boolean collectible) {
        isCollectible = collectible;
    }

    public void setType(String type) { this.type = type; }

    public String getType() { return type; }

    public void use(Entity entity){}
}
