package Model.Entity;

public class NPC extends Entity {
	private boolean isFriendly;

	public NPC(String id, boolean isFriendly) {
		super(id);
		this.isFriendly=isFriendly;
	}

	public String reactTo(Entity entity) {
		if(isFriendly){
			entity.increaseEnergy(20);
			notifyObserver();
			return "increase 20 energy";
		}else{
			entity.decreaseEnergy(20);
			notifyObserver();
			return "decrease 20 energy";
		}

	}
}
