package Model.Location;

public class Door extends Tile {
    private String currentLocationId;
    private String nextLocationId;

    public String getCurrentLocationId() {
        return currentLocationId;
    }

    public String getNextLocationId() {
        return nextLocationId;
    }

    public Door(boolean isMovable, String terrain, int energyCost,String currentLocationId,String nextLocationId) {
        super(isMovable, terrain, energyCost);
        this.currentLocationId = currentLocationId;
        this.nextLocationId = nextLocationId;
    }
}
