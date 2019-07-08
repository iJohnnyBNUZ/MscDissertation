package Model.Location;

public class Coordinate {
    private int xPosition;
    private int yPosition;

    public Coordinate(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
    }

    public int getxPostion() {
        return xPosition;
    }

    public void setxPostion(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }
}
