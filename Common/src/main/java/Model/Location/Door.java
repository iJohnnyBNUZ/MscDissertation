package Model.Location;

public class Door extends Tile {
    private String currentLocationId;
    private String nextLocationId;

    public String getCurrentLocationId() {
        return currentLocationId;
    }

    public void setCurrentLocationId(String currentLocationId) {
        this.currentLocationId = currentLocationId;
    }

    public String getNextLocationId() {
        return nextLocationId;
    }

    public void setNextLocationId(String nextLocationId) {
        this.nextLocationId = nextLocationId;
    }

    public Door(boolean isMovable, String terrain, int energyCost,String currentLocationId,String nextLocationId) {
        super(isMovable, terrain, energyCost);
        this.currentLocationId = currentLocationId;
        this.nextLocationId = nextLocationId;
    }
}
