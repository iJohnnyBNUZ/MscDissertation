package Controller.Load;

import Controller.ServerMediator;
import Model.Entity.Entity;
import Model.Item.Coin;
import Model.Item.Food;
import Model.Item.Key;
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

	Location buildLocation(File file) {
		String saveString = null;
		try {
			saveString = readFile.readJSON(file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		JsonArray locationArray = null;
		if (saveString != null) {
			locationArray = new JsonParser().parse(saveString).getAsJsonArray();
		}
		if (locationArray != null) {
			for(int i = 0; i < locationArray.size(); i++) {
				JsonObject locationObject = locationArray.get(i).getAsJsonObject();
				Location location = new Location(locationObject.get("id").getAsString());
				addTiles(location, locationObject.get("tiles").getAsJsonArray());
				addItems(location, locationObject.get("items").getAsJsonArray());
				addEntities(location, locationObject.get("entities").getAsJsonArray());
				return location;
			}
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
					newTile = new Door(tile.get("isMovable").getAsBoolean(), "door", tile.get("energyCost").getAsInt(), location.getLocationID(), tile.get("nextLocationID").getAsString());
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
			switch(item.get("type").getAsString()){
				case "food":
					Food newFood = new Food(item.get("id").getAsString(),item.get("energy").getAsInt(),item.get("value").getAsInt(),item.get("type").getAsString());
					newFood.setCollectible(item.get("isCollectible").getAsBoolean());
					newFood.setEdible(item.get("isEdible").getAsBoolean());
					location.addItem(new Coordinate(item.get("xCoordinate").getAsInt(),item.get("yCoordinate").getAsInt()),newFood);
					break;
				case "key":
					Key newKey = new Key(item.get("id").getAsString(),item.get("value").getAsInt(),item.get("type").getAsString());
					newKey.setCollectible(item.get("isCollectible").getAsBoolean());
					newKey.setEdible(item.get("isEdible").getAsBoolean());
					location.addItem(new Coordinate(item.get("xCoordinate").getAsInt(),item.get("yCoordinate").getAsInt()),newKey);
					break;
				case "coin":
					Coin newCoin = new Coin(item.get("id").getAsString(),item.get("value").getAsInt(),item.get("type").getAsString());
					newCoin.setCollectible(item.get("isCollectible").getAsBoolean());
					newCoin.setEdible(item.get("isEdible").getAsBoolean());
					location.addItem(new Coordinate(item.get("xCoordinate").getAsInt(),item.get("yCoordinate").getAsInt()),newCoin);
					break;
			}
		}
	}

	private void addEntities(Location location, JsonArray entities) {
		LoadEntity loadEntity = new LoadEntity(serverMediator);
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = loadEntity.parseEntity(entities.get(i).getAsJsonObject());
			location.addEntity(entity, new Coordinate(entities.get(i).getAsJsonObject().get("xCoordinate").getAsInt(), entities.get(i).getAsJsonObject().get("yCoordinate").getAsInt()));
		}
	}

}
