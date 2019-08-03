package Controller.Save;

import Controller.ServerMediator;
import Model.Entity.Entity;
import Model.Entity.NPC;
import Model.Entity.Shop;
import Model.Entity.User;
import Model.Item.Item;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

public class SaveEntity {

	private ServerMediator serverMediator;
	private String saveFilePath;

	public SaveEntity(ServerMediator serverMediator, String saveFilePath) {
		this.serverMediator = serverMediator;
		this.saveFilePath = saveFilePath;
	}

	public void saveSingleEntity(Entity entity) {
		JsonArray entityArray = new JsonArray();
		entityArray.add(saveEntityAsJson(entity));
		WriteFile writeFile = new WriteFile();
		writeFile.writeJsonArray(entityArray, saveFilePath + "/" + entity.getEntityID() + ".json");
	}

	public JsonObject saveEntityAsJson(Entity entity) {
		JsonObject savedEntity = new JsonObject();
		if (entity instanceof User) {
			savedEntity.addProperty("type", "user");
		}
		else if (entity instanceof Shop) {
			savedEntity.addProperty("type", "shop");
		}
		else if (entity instanceof NPC) {
			savedEntity.addProperty("type", "npc");
		}
		savedEntity.addProperty("id", entity.getEntityID());
		savedEntity.addProperty("energy", entity.getEnergy());
		savedEntity.addProperty("xCoordinate", serverMediator.getWorld().getEntityLocation(entity.getEntityID()).getEntities().get(entity).getXCoordinate());
		savedEntity.addProperty("yCoordinate", serverMediator.getWorld().getEntityLocation(entity.getEntityID()).getEntities().get(entity).getYCoordinate());
		savedEntity.add("bag", createBag(entity.getBag()));
		return savedEntity;
	}

	private JsonArray createBag(List<Item> bag) {
		JsonArray savedBag = new JsonArray();
		for (Item item : bag) {
			savedBag.add(saveItem(item));
		}
		return savedBag;
	}

	private JsonObject saveItem(Item item) {
		JsonObject savedItem = new JsonObject();
		savedItem.addProperty("id", item.getItemID());
		savedItem.addProperty("type", item.getType());
		savedItem.addProperty("value", item.getCoinValue());
		return savedItem;
	}
}
