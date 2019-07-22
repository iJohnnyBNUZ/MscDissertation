package Model.Location;

import java.io.Serializable;
import java.util.Objects;

public class Coordinate implements Serializable {
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
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Coordinate){
            Coordinate coordinate = (Coordinate) obj;
            return coordinate.getxPostion() == this.getxPostion() && coordinate.getyPosition() == this.getyPosition();
        }
        else {
            return false;
        }
    }
    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + xPosition;
        result = result * 31 + yPosition;

        return result;
    }

}
