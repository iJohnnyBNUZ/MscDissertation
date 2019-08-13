package Controller.Load;

import Model.Location.Grass;
import Model.Location.Location;
import Model.Location.Tile;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class LoadLocationTest {

    @Before
    public void setUp() throws Exception {
        Location location = new Location("location");
        String tileString = "[{\"terrain\": \"grass\", \"isMovable\": true, \"energyCost\": 1, \"xCoordinate\": 0, \"yCoordinate\": 0 }]";
        JsonParser jsonParser = new JsonParser();
        JsonArray tileArray = (JsonArray) jsonParser.parse(tileString);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void buildLocation() {
    }
}