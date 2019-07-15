package Controller.Observer;

import Controller.ClientMediator;

public class ItemObserver implements Observer {

	private ClientMediator clientMediator;

	public ItemObserver(ClientMediator clientMediator) {
		this.clientMediator = clientMediator;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		clientMediator.getItemView().update(null);

	}
}
