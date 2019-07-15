package Model.Location;

import Model.Entity.User;
import Model.Item.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Location implements Serializable {
    private String locationID;
    private HashMap<Coordinate, Tile> Tiles = new HashMap<Coordinate, Tile>();
    private HashMap<Coordinate, Item> Items = new HashMap<Coordinate, Item>();
    private Map<String, Coordinate> Entities = new HashMap<String, Coordinate>();

    public HashMap<Coordinate, Tile> getTiles() {
        return Tiles;
    }

    public void setTiles(HashMap<Coordinate, Tile> tiles) {
        Tiles = tiles;
    }

    public HashMap<Coordinate, Item> getItems() {
        return Items;
    }

    public void setItems(HashMap<Coordinate, Item> items) {
        Items = items;
    }

    public Map<String, Coordinate> getEntities() {
        return Entities;
    }

    public void setEntities(Map<String, Coordinate> entities) {
        Entities = entities;
    }

    public Location(String id) {
        this.locationID = id;
    }

    public void addTile(Coordinate c, Tile t) {
        this.Tiles.put(c, t);
    }

    public void removeTile(Coordinate c) {
        this.Tiles.remove(c);
    }

    public void addItem(Coordinate c, Item i) {
        this.Items.put(c, i);
    }

    public Item removeItem(Coordinate c) {
        this.Items.remove(c);
        return this.Items.get(c);
    }

    public void addEntity(String id, Coordinate c) {
        this.Entities.put(id, c);
    }

    public void removeEntity(String id) {
        this.Entities.remove(id);
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public void changeUserCoordinate(String userID, Coordinate coordinate) {
        Entities.put(userID, coordinate);
    }
}
