package Model.Entity;

public class Shop extends Entity {
	public Shop(String id) {
		super(id);
	}

	public void reactTo(Entity entity) {
		entity.increaseEnergy(20);
	}
}
