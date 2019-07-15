package Controller.Observer;

import Controller.ClientMediator;

public class UserObserver implements Observer {

	private ClientMediator clientMediator;

	public UserObserver(ClientMediator clientMediator) {
		this.clientMediator = clientMediator;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
