package Model.Entity;

public class NPC extends Entity {
	private boolean isFriendly;

	public NPC(String id, boolean isFriendly) {
		super(id);
		this.isFriendly=isFriendly;
	}

	public String reactTo(Entity entity) {
		if(isFriendly){
			if(entity.getEnergy()<80){
				entity.increaseEnergy(20);
				notifyObserver();
				return "increase 20 energy";
			}
			else{
				entity.setEnergy(100);
				notifyObserver();
				return "have the full energy";
			}


		}else{
			if(entity.getEnergy()<20){
				entity.setEnergy(0);
				notifyObserver();
				return "lost your remaining energy";
			}
			else{
				entity.decreaseEnergy(20);
				notifyObserver();
				return "decrease 20 energy";
			}

		}

	}

	public boolean getIsFriendly() {
		return isFriendly;
	}
}
