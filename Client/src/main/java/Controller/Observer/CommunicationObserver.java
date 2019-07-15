package Controller.Observer;

import Controller.GameMediator;

public class CommunicationObserver implements Observer {

	private GameMediator gameMediator;

	public CommunicationObserver(GameMediator gameMediator) {
		this.gameMediator = gameMediator;
	}

	@Override
	public void update() {
		gameMediator.getChatView().updateChat(null);
	}
}
