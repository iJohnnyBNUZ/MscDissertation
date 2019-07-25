package Controller.Observer;

import Controller.ClientMediator;
import Utils.Observer;

public class CommunicationObserver implements Observer {

	private ClientMediator clientMediator;

	public CommunicationObserver(ClientMediator clientMediator) {
		this.clientMediator = clientMediator;
	}

	@Override
	public void takeAction(Object... msg) {
		clientMediator.getChatView().updateChat(this.clientMediator.getWorld().getMessageList());
	}
}
