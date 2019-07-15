package Controller.Observer;

import Controller.GameMediator;
import Model.Location.Coordinate;
import Model.Location.Location;

import java.util.HashMap;
import java.util.Map;

public class LocationObserver implements Observer {

	private GameMediator gameMediator;

	public LocationObserver(GameMediator gameMediator) {
		this.gameMediator = gameMediator;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		String uId = gameMediator.getClient().getUserName();
		Location curLocation = gameMediator.getWorld().getEntityLocation(uId);
		Map<String, Coordinate> tiles = new HashMap<String,Coordinate>();

		for(Coordinate cor: curLocation.getTiles().keySet()) {
			tiles.put(curLocation.getTiles().get(cor).getTerrain(), cor);
		}

		gameMediator.getLocationView().update(tiles);
	}

}
