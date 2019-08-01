package Controller.Command;

import Controller.ClientMediator;
import Network.Events.LogoutEvent;

public class LogOutCommand implements Command{
	ClientMediator clientMediator;
	public LogOutCommand(ClientMediator clientMediator){
		this.clientMediator = clientMediator;
	}

	public void execute() {
		clientMediator.getEventQueue().add(new LogoutEvent(clientMediator.getUserName()));
		System.out.println("log out!");
	}
}
