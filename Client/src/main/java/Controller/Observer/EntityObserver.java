package Controller.Observer;

import Controller.ClientMediator;
import Model.Entity.Entity;
import Model.Entity.NPC;
import Model.Entity.Shop;
import Model.Entity.User;
import Model.Location.Coordinate;
import Model.Location.Location;
import Utils.Observer;
import View.EntityView;
import javafx.concurrent.Task;

import java.util.HashMap;
import java.util.Map;

public class EntityObserver implements Observer {
	private ClientMediator clientMediator;

	public EntityObserver(ClientMediator clientMediator) {
		this.clientMediator = clientMediator;
	}

	/**
	 * Get the entities data in this location and update the entity view layer by using this data.
	 */
	@Override
	public void update() {
		String uId = clientMediator.getUserName();
		// find out which location the user is in
		Location curLocation = clientMediator.getWorld().getEntityLocation(uId);

		Map<String,Coordinate> users = new HashMap<String,Coordinate>();
		Map<String,Coordinate> npcs = new HashMap<String,Coordinate>();
		Map<String,Coordinate> stores = new HashMap<String,Coordinate>();
		int energyPoints = clientMediator.getWorld().getEntity(uId).getEnergy();
		int coins = clientMediator.getWorld().getEntity(uId).getCoin();

		Task<Void> progressTask = new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				//run in current thread

				//iterate the entities in the current location
				for(Entity entity: curLocation.getEntities().keySet()) {
					if(entity instanceof User && ((User) entity).getOnline()) {
						//store the online users data
						if(entity.getEntityID().equals(clientMediator.getUserName())){
							// me.png is only used for the user use this client.
							users.put("me", curLocation.getEntities().get(entity));
						}else{
							users.put("player", curLocation.getEntities().get(entity));
						}

					}else if(entity instanceof NPC) {
						//store the online npcs data
						npcs.put(entity.getEntityID(), curLocation.getEntities().get(entity));
					}else if(entity instanceof Shop){
						//store the online stores data
						stores.put(entity.getEntityID(), curLocation.getEntities().get(entity));
					}
				}
				return null;
			}

			@Override
			protected void succeeded() {
				//run in JavaFx thread
				EntityView entityView = clientMediator.getEntityView();
				entityView.initialBeforeDraw();

				entityView.updateCoin(coins);
				entityView.updateEnergy(energyPoints);

				//update the entity view layer
				entityView.updateNPC(npcs);
				entityView.updateStore(stores);
				entityView.updateUser(users);
			}
		};

		new Thread(progressTask).start();

	}
}
