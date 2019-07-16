package Controller.Observer;

import Controller.ClientMediator;
import Model.Location.Coordinate;
import Model.Location.Location;

import java.util.HashMap;
import java.util.Map;

public class LocationObserver implements Observer {

	private ClientMediator clientMediator;

	public LocationObserver(ClientMediator clientMediator) {
		this.clientMediator = clientMediator;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		String uId = clientMediator.getUserName();
		Location curLocation = clientMediator.getWorld().getEntityLocation(uId);
		Map<String, Coordinate> tiles = new HashMap<String,Coordinate>();

		for(Coordinate cor: curLocation.getTiles().keySet()) {
			tiles.put(curLocation.getTiles().get(cor).getTerrain(), cor);
		}
		clientMediator.getLocationView().update(tiles);
	}

}
