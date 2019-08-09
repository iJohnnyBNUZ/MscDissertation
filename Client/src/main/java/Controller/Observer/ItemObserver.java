package Controller.Observer;

import Controller.ClientMediator;
import Model.Location.Coordinate;
import Model.Location.Location;
import Utils.Observer;
import javafx.concurrent.Task;

import java.util.HashMap;
import java.util.Map;

public class ItemObserver implements Observer {

	private ClientMediator clientMediator;

	public ItemObserver(ClientMediator clientMediator) {
		this.clientMediator = clientMediator;
	}

	/**
	 * Get the items data in this location and update the item view layer by using this data.
	 */
	@Override
	public void update(){
		String uId = clientMediator.getUserName();
		// find out which location the user is in
		Location curLocation = clientMediator.getWorld().getEntityLocation(uId);
		Map<String, Coordinate> items = new HashMap<String,Coordinate>();
		Task<Void> progressTask = new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				//run in current thread
				//store the tiles data placed in this location
				for(Coordinate cor: curLocation.getItems().keySet()) {
					items.put(curLocation.getItems().get(cor).getItemID(), cor);

				}
				return null;
			}

			@Override
			protected void succeeded() {
				//run in JavaFx thread
				//update the item view layer
				clientMediator.getItemView().update(items);
			}

		};

		new Thread(progressTask).start();
	}
}
