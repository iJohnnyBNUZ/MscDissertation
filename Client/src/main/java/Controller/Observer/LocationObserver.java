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

	/*@Override
	public void update() {
		//if (msg[0].equals("changeLocation")){
			String uId = clientMediator.getUserName();
			Location curLocation = clientMediator.getWorld().getEntityLocation(uId);
			Map<Coordinate, String> tiles = new HashMap<Coordinate, String>();

			for(Coordinate cor: curLocation.getTiles().keySet()) {
				tiles.put(cor, curLocation.getTiles().get(cor).getTerrain());
			}
			clientMediator.getLocationView().update(tiles);
		//}
	}*/

	@Override
	public void update() {
		Map<Coordinate, String> tiles = new HashMap<Coordinate, String>();
		Task<Void> progressTask = new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				String uId = clientMediator.getUserName();
				Location curLocation = clientMediator.getWorld().getEntityLocation(uId);
				for(Coordinate cor: curLocation.getTiles().keySet()) {
					tiles.put(cor, curLocation.getTiles().get(cor).getTerrain());
				}
				return null;
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				clientMediator.getLocationView().update(tiles);
			}

		};

		new Thread(progressTask).start();
	}
}
