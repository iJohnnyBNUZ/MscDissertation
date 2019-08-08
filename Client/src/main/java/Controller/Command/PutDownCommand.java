package Controller.Command;

import Controller.ClientMediator;
import Controller.Controller;
import Controller.ItemController;
import Network.Events.OpenDoorEvent;
import Network.Events.PutDownEvent;
import javafx.concurrent.Task;

public class PutDownCommand implements Command {

	private ItemController itemController = null;
	private ClientMediator clientMediator = null;
	private String userID;

	public PutDownCommand(Controller controller,ClientMediator clientMediator){
		this.itemController = (ItemController) controller;
		this.clientMediator = clientMediator;
	}

	public void execute(String selectedItem){

		Task<Void> progressTask = new Task<Void>(){
			String message = null;
			@Override
			protected Void call() throws Exception {
				// run in the current thread.
				userID = clientMediator.getUserName();
				System.out.println("Item "+selectedItem+" is put down on this position by " + userID);
				String message = itemController.drop(userID,selectedItem);
				return null;
			}

			@Override
			protected void succeeded() {
				// run in the JavaFx thread.
				if(message!=null){
					clientMediator.getView().showAlert(message);
				}else{
					clientMediator.getEventQueue().add(new PutDownEvent(clientMediator.getUserName(),selectedItem));
				}
			}

		};

		new Thread(progressTask).start();



    }
}
