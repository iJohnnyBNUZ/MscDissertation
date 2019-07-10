package Model.Location;

import java.io.Serializable;

public abstract class Tile implements Serializable {
    private boolean isMovable = true;
    private String Terrain = "Grass";
    private int energyCost = 1;

    public Tile(boolean isMovable, String terrain, int energyCost) {
        this.isMovable = isMovable;
        this.Terrain = terrain;
        this.energyCost = energyCost;
    }

    public boolean isMovable() {
        return isMovable;
    }

    public void setMovable(boolean movable) {
        isMovable = movable;
    }

    public String getTerrain() {
        return Terrain;
    }

    public void setTerrain(String terrain) {
        Terrain = terrain;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    public void setEnergyCost(int energyCost) {
        this.energyCost = energyCost;
    }
}
