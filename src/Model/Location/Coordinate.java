package Model.Location;

public class Coordinate {
    private int xPostion;
    private int yPosition;

    public Coordinate(int x, int y) {
        this.xPostion = x;
        this.yPosition = y;
    }

    public int getxPostion() {
        return xPostion;
    }

    public void setxPostion(int xPostion) {
        this.xPostion = xPostion;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }
}
