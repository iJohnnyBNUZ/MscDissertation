package Controller.Command;

import Controller.ClientMediator;
import Controller.Controller;
import Controller.ItemController;
import Network.Events.PutDownEvent;
import javafx.concurrent.Task;

public class PutDownCommand implements Command {

	private ItemController itemController;
	private ClientMediator clientMediator;
	private String userID;

	public PutDownCommand(Controller controller,ClientMediator clientMediator){
		this.itemController = (ItemController) controller;
		this.clientMediator = clientMediator;
	}


	/**
	 * Invoke the drop method in the itemController.
	 * Add the PutDownEvent to the queue if this action is processed, otherwise show a dialog box to the user
	 * @param selectedItem The ID of the item that will put down by the user
	 */
	public void execute(String selectedItem){

		Task<Void> progressTask = new Task<Void>(){
			String message = null;
			@Override
			protected Void call() throws Exception {
				// run in the current thread.
				userID = clientMediator.getUserName();
				message = itemController.drop(userID,selectedItem);
				return null;
			}

			@Override
			protected void succeeded() {
				System.out.println("JavaFx thread");
				// run in the JavaFx thread.
				if(message!=null){
					clientMediator.getView().showAlert(message,null);
				}else{
					clientMediator.getEventQueue().add(new PutDownEvent(clientMediator.getUserName(),selectedItem));
				}
			}

		};

		new Thread(progressTask).start();



    }
}
