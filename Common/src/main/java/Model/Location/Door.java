package Model.Location;

public class Door extends Tile {
    private String CurrentLocationId;
    private String NextLocationId;
    private String doorId;

    public String getCurrentLocationId() {
        return CurrentLocationId;
    }

    public void setCurrentLocationId(String currentLocationId) {
        CurrentLocationId = currentLocationId;
    }

    public String getNextLocationId() {
        return NextLocationId;
    }

    public void setNextLocationId(String nextLocationId) {
        NextLocationId = nextLocationId;
    }

    public String getDoorId() {
        return doorId;
    }

    public void setDoorId(String doorId) {
        this.doorId = doorId;
    }

    public Door(boolean isMovable, String terrain, int energyCost) {
        super(isMovable, terrain, energyCost);
    }
}
