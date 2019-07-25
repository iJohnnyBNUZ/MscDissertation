package Controller.Observer;

import Controller.ClientMediator;
import Model.Location.Coordinate;
import Model.Location.Location;
import Utils.Observer;

import java.util.HashMap;
import java.util.Map;

public class LocationObserver implements Observer {

	private ClientMediator clientMediator;

	public LocationObserver(ClientMediator clientMediator) {
		this.clientMediator = clientMediator;
	}

	@Override
	public void takeAction(Object... msg) {
		if (msg[0].equals("changeLocation")){
			String uId = clientMediator.getUserName();
			Location curLocation = clientMediator.getWorld().getEntityLocation(uId);
			Map<Coordinate, String> tiles = new HashMap<Coordinate, String>();

			for(Coordinate cor: curLocation.getTiles().keySet()) {
				tiles.put(cor, curLocation.getTiles().get(cor).getTerrain());
			}
			clientMediator.getLocationView().update(tiles);
		}
	}
}
