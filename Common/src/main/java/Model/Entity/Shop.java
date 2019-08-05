package Model.Entity;

public class Shop extends Entity {
	public Shop(String id) {
		super(id);
	}

	public String reactTo(Entity entity) {
		notifyObserver();
		return "transaction with shop";
	}
}
