package Controller.Observer;

import java.util.HashMap;
import java.util.Map;
import Utils.Observer;

import Controller.ClientMediator;
import Model.Location.Coordinate;
import Model.Location.Location;

public class ItemObserver implements Observer {

	private ClientMediator clientMediator;

	public ItemObserver(ClientMediator clientMediator) {
		this.clientMediator = clientMediator;
	}

	@Override
	public void update() {
		//if (msg[0].equals("changeItem")){
			String uId = clientMediator.getUserName();
			// TODO cannot get curLocation,  because the entities is null
			Location curLocation = clientMediator.getWorld().getEntityLocation(uId);
			Map<String, Coordinate> items = new HashMap<String,Coordinate>();
			System.out.println(curLocation.getItems().keySet().size());
			for(Coordinate cor: curLocation.getItems().keySet()) {
				items.put(curLocation.getItems().get(cor).getItemID(), cor);

			}
			clientMediator.getItemView().update(items);
		//}
	}
}
