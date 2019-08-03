package Controller.Command;

import Controller.ClientMediator;
import Network.Events.SaveGameEvent;

public class SaveGameCommand implements Command{
	private ClientMediator clientMediator;
	
	public SaveGameCommand(ClientMediator clientMediator) {
		this.clientMediator = clientMediator;
	}
	public void execute() {
		System.out.println("Saving Game");
		clientMediator.getEventQueue().add(new SaveGameEvent(clientMediator.getUserName()));
	}
}
