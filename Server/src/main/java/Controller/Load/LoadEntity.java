package Controller.Load;

import Controller.ServerMediator;
import Model.Entity.Entity;
import Model.Entity.NPC;
import Model.Entity.Shop;
import Model.Entity.User;
import Model.Item.Item;
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

	public Entity buildEntity(File file) throws IOException {
		String saveString = readFile.readJSON(file.getAbsolutePath());
		JsonArray entityArray = new JsonParser().parse(saveString).getAsJsonArray();
		for(int i = 0; i < entityArray.size(); i++) {
			Entity entity = parseEntity(entityArray.get(i).getAsJsonObject());
			serverMediator.getWorld().addEntity(entity);
			serverMediator.getWorld().getLocation(entityArray.get(i).getAsJsonObject().get("location").getAsString()).addEntity(entity.getEntityID(), new Coordinate(entityArray.get(i).getAsJsonObject().get("xCoordinate").getAsInt(), entityArray.get(i).getAsJsonObject().get("yCoordinate").getAsInt()));
		}
		return null;
	}

	public Entity parseEntity(JsonObject entityObject) {
		Entity entity;
		switch (entityObject.get("type").getAsString()) {
			case "user":
				entity = new User(entityObject.get("id").getAsString());
			case "shop":
				entity = new Shop(entityObject.get("id").getAsString());
			case "npc":
				entity = new NPC(entityObject.get("id").getAsString());
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
			Item newItem = new Item(item.get("id").getAsString(), item.get("value").getAsInt(), item.get("type").getAsString());
			newItem.setCollectible(item.get("isCollectible").getAsBoolean());
			newItem.setEdible(item.get("isEdible").getAsBoolean());
			entity.addToBag(newItem);
		}
	}
}
