package Controller.Command;

import Controller.Controller;
import Controller.ItemController;
import Controller.ClientMediator;
import Network.Events.PutDownEvent;

public class PutDownCommand implements Command {

	private ItemController itemController = null;
	private ClientMediator clientMediator = null;
	private String userID;

	public PutDownCommand(Controller controller,ClientMediator clientMediator){
		this.itemController = (ItemController) controller;
		this.clientMediator = clientMediator;
	}

	public void execute(String selectedItem){
		userID = clientMediator.getUserName();
		System.out.println("Item "+selectedItem+" is put down on this position by " + userID);
		String message = itemController.drop(userID,selectedItem);
		if(message!=null){
			clientMediator.getView().showAlert(message);
		}else{
			clientMediator.getEventQueue().add(new PutDownEvent(clientMediator.getUserName(),selectedItem));
		}

    }
}
