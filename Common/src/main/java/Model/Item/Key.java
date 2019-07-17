package Model.Item;

import Model.Entity.Entity;

public class Key extends Item {
	private boolean used;

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public Key(String id,int coinValue,String type) {
		super(id,coinValue,type);
		this.used = false;
	}

	@Override
	public void use(Entity entity) {
		super.use(entity);
	}
}
