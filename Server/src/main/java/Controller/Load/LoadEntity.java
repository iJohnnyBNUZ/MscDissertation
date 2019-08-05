package Controller.Load;

import Controller.ServerMediator;
import Model.Entity.Entity;
import Model.Entity.NPC;
import Model.Entity.Shop;
import Model.Entity.User;
import Model.Item.Coin;
import Model.Item.Food;
import Model.Item.Key;
import Model.Location.Coordinate;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;

public class LoadEntity {

	private ReadFile readFile;
	private ServerMediator serverMediator;

	public LoadEntity(ServerMediator serverMediator) {
		this.readFile = new ReadFile();
		this.serverMediator = serverMediator;
	}

	public void buildEntity(File file) throws IOException {
		String saveString = readFile.readJSON(file.getAbsolutePath());
		JsonArray entityArray = new JsonParser().parse(saveString).getAsJsonArray();
		if (entityArray.size() != 0) {
			for(int i = 0; i < entityArray.size(); i++) {
				Entity entity = parseEntity(entityArray.get(i).getAsJsonObject());
				serverMediator.getWorld().addEntity(entity);
				serverMediator.getWorld().getLocation(entityArray.get(i).getAsJsonObject().get("location").getAsString()).addEntity(entity, new Coordinate(entityArray.get(i).getAsJsonObject().get("xCoordinate").getAsInt(), entityArray.get(i).getAsJsonObject().get("yCoordinate").getAsInt()));
			}
		}
	}

	public Entity parseEntity(JsonObject entityObject) {
		Entity entity;
		switch (entityObject.get("type").getAsString()) {
			case "user":
				entity = new User(entityObject.get("id").getAsString());
				break;
			case "shop":
				entity = new Shop(entityObject.get("id").getAsString());
				break;
			case "npc":
				entity = new NPC(entityObject.get("id").getAsString(), entityObject.get("isFriendly").getAsBoolean());
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + entityObject.get("type").getAsString());
		}

		entity.setEnergy(entityObject.get("energy").getAsInt());
		entity.setCoin(entityObject.get("coin").getAsInt());
		createBag(entity, entityObject.get("bag").getAsJsonArray());

		return entity;
	}

	private void createBag(Entity entity, JsonArray bag) {
		for (int i = 0; i < bag.size(); i++) {
			JsonObject item = bag.get(i).getAsJsonObject();
			switch(item.get("type").getAsString()){
				case "food":
					Food newFood = new Food(item.get("id").getAsString(),item.get("energy").getAsInt(),item.get("value").getAsInt(),item.get("type").getAsString());
					newFood.setCollectible(item.get("isCollectible").getAsBoolean());
					newFood.setEdible(item.get("isEdible").getAsBoolean());
					entity.addToBag(newFood);
					break;
				case "key":
					Key newKey = new Key(item.get("id").getAsString(),item.get("value").getAsInt(),item.get("type").getAsString());
					newKey.setCollectible(item.get("isCollectible").getAsBoolean());
					newKey.setEdible(item.get("isEdible").getAsBoolean());
					entity.addToBag(newKey);
					break;
				case "coin":
					Coin newCoin = new Coin(item.get("id").getAsString(),item.get("value").getAsInt(),item.get("type").getAsString());
					newCoin.setCollectible(item.get("isCollectible").getAsBoolean());
					newCoin.setEdible(item.get("isEdible").getAsBoolean());
					entity.addToBag(newCoin);
					break;
			}
			/*
			Item newItem = new Item(item.get("id").getAsString(), item.get("value").getAsInt(), item.get("type").getAsString());
			newItem.setCollectible(item.get("isCollectible").getAsBoolean());
			newItem.setEdible(item.get("isEdible").getAsBoolean());
			entity.addToBag(newItem);
			*/
		}
	}
}
