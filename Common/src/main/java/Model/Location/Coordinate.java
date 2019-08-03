package Model.Location;

import java.io.Serializable;

public class Coordinate implements Serializable {
    private int xCoordinate;
    private int yCoordinate;

    public Coordinate(int x, int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xPosition) {
        this.xCoordinate = xPosition;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Coordinate){
            Coordinate coordinate = (Coordinate) obj;
            return coordinate.getXCoordinate() == this.getXCoordinate() && coordinate.getYCoordinate() == this.getYCoordinate();
        }
        else {
            return false;
        }
    }
    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + xCoordinate;
        result = result * 31 + yCoordinate;

        return result;
    }

}
