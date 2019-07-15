package Model.Item;

import Model.Entity.Entity;

public class Coin extends Item {
	public Coin(String id,int coinValue,String type) {
		super(id,coinValue,type);
	}

	@Override
	public void use(Entity entity) {
		super.use(entity);
	}
}
