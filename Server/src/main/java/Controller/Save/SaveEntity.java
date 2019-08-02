package Controller.Save;

import Controller.ServerMediator;
import Model.Entity.Entity;
import Model.Entity.NPC;
import Model.Entity.Shop;
import Model.Entity.User;
import Model.Item.Item;
import Model.Location.Coordinate;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

public class SaveEntity {

	private ServerMediator serverMediator;

	public SaveEntity(ServerMediator serverMediator) {
		this.serverMediator = serverMediator;
	}

	public JsonObject formatUserAsJson(Entity entity) {
		JsonObject userObject = new JsonObject();

		return userObject;
	}

	public JsonObject saveEntity(Map.Entry<Entity, Coordinate> entity) {
		JsonObject savedEntity = new JsonObject();
		if (entity.getKey() instanceof User) {
			savedEntity.addProperty("type", "user");
		}
		else if (entity.getKey() instanceof Shop) {
			savedEntity.addProperty("type", "shop");
		}
		else if (entity.getKey() instanceof NPC) {
			savedEntity.addProperty("type", "npc");
		}
		savedEntity.addProperty("id", entity.getKey().getEntityID());
		savedEntity.addProperty("energy", entity.getKey().getEnergy());
		savedEntity.addProperty("xCoordinate", entity.getValue().getxPostion());
		savedEntity.addProperty("yCoordinate", entity.getValue().getyPosition());
		savedEntity.add("bag", createBag(entity.getKey().getBag()));
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
