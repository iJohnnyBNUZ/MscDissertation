package Controller.Command;

import Controller.ClientMediator;
import Network.Events.SaveGameEvent;

public class SaveGameCommand implements Command{
	private ClientMediator clientMediator;
	
	public SaveGameCommand(ClientMediator clientMediator) {
		this.clientMediator = clientMediator;
	}

	/**
	 *  Add the SaveGameEvent to the queue in the clientMediator
	 */
	public void execute() {
		clientMediator.getEventQueue().add(new SaveGameEvent(clientMediator.getUserName()));
	}
}
