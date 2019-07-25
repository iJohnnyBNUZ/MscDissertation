package Model.Location;

import Model.Entity.Entity;
import Model.Entity.User;
import Model.Item.Item;
import Utils.Observable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Location extends Observable implements Serializable {
    private String locationID;
    private HashMap<Coordinate, Tile> Tiles = new HashMap<Coordinate, Tile>();
    private HashMap<Coordinate, Item> Items = new HashMap<Coordinate, Item>();
    private Map<Entity, Coordinate> Entities = new HashMap<Entity, Coordinate>();

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
        notifyObserver("changeItem");
    }

    public Map<Entity, Coordinate> getEntities() {
        return Entities;
    }

    public void setEntities(Map<Entity, Coordinate> entities) {
        Entities = entities;
        if (entities.size() != this.getEntities().size())
            notifyObserver("changeLocation"); // notify entity change Location
        else
            notifyObserver("changeEntity"); // notify entity move
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

    public void addEntity(Entity entity, Coordinate c) {
        this.Entities.put(entity, c);
    }

    public void removeEntity(Entity entity) {
        this.Entities.remove(entity);
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public void changeUserCoordinate(Entity entity, Coordinate coordinate) {
        Map<Entity, Coordinate> entities_temp = getEntities();
        if (entities_temp == null)
            return;

        entities_temp.put(entity, coordinate);
        setEntities(entities_temp);
    }
}
