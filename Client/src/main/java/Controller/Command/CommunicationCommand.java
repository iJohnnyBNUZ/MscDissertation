package Controller.Command;

import Controller.CommunicationController;

public class CommunicationCommand implements Command {
	private CommunicationController communicationController = null;
	
	public CommunicationCommand(CommunicationController communicationController) {
		this.communicationController = communicationController;
	}

	public void execute(String id) {
		// TODO Auto-generated method stub
		System.out.println("Communicate with " + id);
		communicationController.communicateWith(id);
	}
}
