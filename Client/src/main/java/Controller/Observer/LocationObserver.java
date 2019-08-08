package Controller.Observer;

import Controller.ClientMediator;
import Model.Location.Coordinate;
import Model.Location.Location;
import Utils.Observer;
import javafx.concurrent.Task;

import java.util.HashMap;
import java.util.Map;

public class LocationObserver implements Observer {

	private ClientMediator clientMediator;

	public LocationObserver(ClientMediator clientMediator) {
		this.clientMediator = clientMediator;
	}

	/**
	 * Get the tiles data of this location and update the map by using this data.
	 */
	@Override
	public void update() {
		Map<Coordinate, String> tiles = new HashMap<Coordinate, String>();
		Task<Void> progressTask = new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				//run in current thread
				String uId = clientMediator.getUserName();
				// find out which location the user is in
				Location curLocation = clientMediator.getWorld().getEntityLocation(uId);

				//store the tiles data formed this location
				for(Coordinate cor: curLocation.getTiles().keySet()) {
					tiles.put(cor, curLocation.getTiles().get(cor).getTerrain());
				}
				return null;
			}

			@Override
			protected void succeeded() {
				//run in JavaFx thread
				//update the map
				clientMediator.getLocationView().update(tiles);
			}

		};

		new Thread(progressTask).start();
	}
}
