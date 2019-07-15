package Controller.Observer;

import Controller.GameMediator;

public class ItemObserver implements Observer {

	private GameMediator gameMediator;

	public ItemObserver(GameMediator gameMediator) {
		this.gameMediator = gameMediator;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		gameMediator.getItemView().update(null);

	}
}
