package Model.Item;

import java.io.Serializable;

public abstract class Item implements Serializable {
    private String itemID;
    private int coinValue = 0;
    private boolean isEdible = false;
    private boolean isCollectible = true;

    public Item(String id) {
        this.itemID = id;
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
}
