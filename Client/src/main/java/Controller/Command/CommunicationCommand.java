package Controller.Command;

import Controller.CommunicationController;
import Controller.Controller;

public class CommunicationCommand implements Command {
	private CommunicationController communicationController = null;
	
	public CommunicationCommand(Controller communicationController2) {
		this.communicationController = (CommunicationController) communicationController2;
	}

	public void execute(String id,String time) {
		// TODO Auto-generated method stub
		System.out.println("Communicate with " + id);
		communicationController.communicateWith(id,time);
	}
}
