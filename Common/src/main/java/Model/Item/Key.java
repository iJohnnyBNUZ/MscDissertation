package Model.Item;

import Model.Entity.Entity;

public class Key extends Item {
	public Key(String id,int coinValue,String type) {
		super(id,coinValue,type);
	}

	@Override
	public void use(Entity entity) {
		super.use(entity);
	}
}
