package Controller.Load;

import Controller.ServerMediator;
import Model.Entity.Entity;
import Model.Item.Item;
import Model.Location.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;

class LoadLocation {

	private ReadFile readFile;
	private ServerMediator serverMediator;

	LoadLocation(ServerMediator serverMediator) {
		this.readFile = new ReadFile();
		this.serverMediator = serverMediator;
	}

	Location buildLocation(File file) throws IOException {
		String saveString = readFile.readJSON(file.getAbsolutePath());
		JsonArray locationArray = new JsonParser().parse(saveString).getAsJsonArray();
		for(int i = 0; i < locationArray.size(); i++) {
			JsonObject locationObject = locationArray.get(i).getAsJsonObject();
			Location location = new Location(locationObject.get("id").getAsString());
			addTiles(location, locationObject.get("tiles").getAsJsonArray());
			addItems(location, locationObject.get("items").getAsJsonArray());
			addEntities(location, locationObject.get("entities").getAsJsonArray());

			return location;
		}
		return null;
	}

	private void addTiles(Location location, JsonArray tiles) {
		for (int i = 0; i < tiles.size(); i++) {
			JsonObject tile = tiles.get(i).getAsJsonObject();
			Tile newTile;
			switch (tile.get("terrain").getAsString()) {
				case "grass":
					newTile = new Grass(tile.get("isMovable").getAsBoolean(), "grass", tile.get("energyCost").getAsInt());
					break;
				case "stone":
					newTile = new Stone(tile.get("isMovable").getAsBoolean(), "stone", tile.get("energyCost").getAsInt());
					break;
				case "water":
					newTile = new Water(tile.get("isMovable").getAsBoolean(), "water", tile.get("energyCost").getAsInt());
					break;
				case "door":
					newTile = new Door(tile.get("isMovable").getAsBoolean(), "door", tile.get("energyCost").getAsInt());
					break;
				default:
					throw new IllegalStateException("Unexpected value: " + tile.get("terrain").getAsString());
			}
			location.addTile(new Coordinate(tiles.get(i).getAsJsonObject().get("xCoordinate").getAsInt(), tiles.get(i).getAsJsonObject().get("yCoordinate").getAsInt()), newTile);
		}
	}

	private void addItems(Location location, JsonArray items) {
		for (int i = 0; i < items.size(); i++) {
			JsonObject item = items.get(i).getAsJsonObject();
			Item newItem = new Item(item.get("id").getAsString(), item.get("value").getAsInt(), item.get("type").getAsString());
			newItem.setCollectible(item.get("isCollectible").getAsBoolean());
			newItem.setEdible(item.get("isEdible").getAsBoolean());
			location.addItem(new Coordinate(item.get("xCoordinate").getAsInt(), item.get("yCoordinate").getAsInt()), newItem);
		}
	}

	private void addEntities(Location location, JsonArray entities) {
		LoadEntity loadEntity = new LoadEntity(serverMediator);
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = loadEntity.parseEntity(entities.get(i).getAsJsonObject());
			location.addEntity(entity.getEntityID(), new Coordinate(entities.get(i).getAsJsonObject().get("xCoordinate").getAsInt(), entities.get(i).getAsJsonObject().get("yCoordinate").getAsInt()));
		}
	}

}
