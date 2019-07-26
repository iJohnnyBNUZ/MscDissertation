package Controller.Command;

import Controller.ClientMediator;

public class LogOutCommand implements Command{
	ClientMediator clientMediator;
	public LogOutCommand(ClientMediator clientMediator){
		this.clientMediator = clientMediator;
	}

	public void execute() {
		clientMediator.addAction("logOut");
		System.out.println("log out!");
	}
}
