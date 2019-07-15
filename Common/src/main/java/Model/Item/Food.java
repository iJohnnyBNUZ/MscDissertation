package Model.Item;

import Model.Entity.Entity;

public class Food extends Item {

	private int energy;

	public Food(String id,int energy,int coinValue,String type) {
		super(id,coinValue,type);

		this.energy = energy;
	}

	public int getEnergy() { return energy; }

	@Override
	public void use(Entity entity) {
		super.use(entity);
		entity.increaseEnergy(energy);
	}
}
