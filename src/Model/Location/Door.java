package Model.Location;

public class Door extends Tile {
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Door(boolean isMovable, String terrain, int energyCost) {
        super(isMovable, terrain, energyCost);
    }
}
