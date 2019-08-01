package Model.Entity;

public class Shop extends Entity {
	public Shop(String id) {
		super(id);
	}

	public String reactTo(Entity entity) {
		entity.increaseEnergy(20);
		return "decrease 20 energy";
	}
}
