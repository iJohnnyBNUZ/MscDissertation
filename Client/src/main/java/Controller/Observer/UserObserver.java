package Controller.Observer;

import Controller.GameMediator;

public class UserObserver implements Observer {

	private GameMediator gameMediator;

	public UserObserver(GameMediator gameMediator) {
		this.gameMediator = gameMediator;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
