package Model.Entity;

public class NPC extends Entity {
	public NPC(String id) {
		super(id);
	}

	public void reactTo(Entity entity) {
		entity.decreaseEnergy(20);
	}
}
