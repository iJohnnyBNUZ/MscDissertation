package Controller.Save;

import Controller.ServerMediator;
import Model.Entity.Entity;
import Model.Item.Item;
import Model.Location.Coordinate;
import Model.Location.Location;
import Model.Location.Tile;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class SaveLocation {

	private ServerMediator serverMediator;

	public SaveLocation(ServerMediator serverMediator) {
		this.serverMediator = serverMediator;
	}

	public JsonObject saveLocationAsJson(Location location) {
		JsonObject locationObject = new JsonObject();
		locationObject.addProperty("id", location.getLocationID());
		locationObject.add("tiles", saveTiles(location.getTiles()));
		locationObject.add("items", saveLocationItems(location.getItems()));
		locationObject.add("entities", saveEntities(location.getEntities()));
		return locationObject;
	}

	private JsonArray saveLocationItems(HashMap<Coordinate, Item> items) {
		JsonArray savedItems = new JsonArray();
		for (Map.Entry<Coordinate, Item> item : items.entrySet()) {
			JsonObject newItem = saveItem(item.getValue());
			newItem.addProperty("xCoordinate", item.getKey().getXCoordinate());
			newItem.addProperty("yCoordinate", item.getKey().getYCoordinate());
			savedItems.add(newItem);
		}
		return savedItems;
	}

	private JsonArray saveTiles(HashMap<Coordinate, Tile> tileMap) {
		JsonArray tiles = new JsonArray();
		for (Map.Entry<Coordinate, Tile> tile : tileMap.entrySet()) {
			JsonObject savedTile = new JsonObject();
			savedTile.addProperty("terrain", tile.getValue().getTerrain());
			savedTile.addProperty("isMovable", tile.getValue().isMovable());
			savedTile.addProperty("energyCost", tile.getValue().getEnergyCost());
			savedTile.addProperty("xCoordinate", tile.getKey().getXCoordinate());
			savedTile.addProperty("yCoordinate", tile.getKey().getYCoordinate());
			tiles.add(savedTile);
		}
		return tiles;
	}

	private JsonElement saveEntities(Map<Entity, Coordinate> entityMap) {
		JsonArray entities = new JsonArray();
		for (Map.Entry<Entity, Coordinate> entity : entityMap.entrySet()) {
			entities.add(serverMediator.getEntitySaver().saveEntityAsJson(entity.getKey()));
		}
		return entities;
	}

	private JsonObject saveItem(Item item) {
		JsonObject savedItem = new JsonObject();
		savedItem.addProperty("id", item.getItemID());
		savedItem.addProperty("type", item.getType());
		savedItem.addProperty("value", item.getCoinValue());
		return savedItem;
	}
}
