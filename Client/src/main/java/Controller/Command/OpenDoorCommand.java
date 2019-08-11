package Controller.Command;

import Controller.ClientMediator;
import Controller.LocationController;
import Network.Events.MovementEvent;
import Network.Events.OpenDoorEvent;
import javafx.concurrent.Task;

import java.io.IOException;

public class OpenDoorCommand implements Command {
    private LocationController locationController;
    private ClientMediator clientMediator;

    public OpenDoorCommand(LocationController locationController, ClientMediator clientMediator){
        this.locationController = locationController;
        this.clientMediator = clientMediator;
    }

    /**
     *  Invoke the openDoor methods in the locationController.
     *  Add the LocationEvent to the queue if this action is processed successfully.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void excute() throws IOException, ClassNotFoundException {
        Task<Void> progressTask = new Task<Void>(){
            String message = null;
            @Override
            protected Void call() throws Exception {
                // run in the current thread.
                message = locationController.openDoor(clientMediator.getClientUpdater().getUserName());
                return null;
            }

            @Override
            protected void succeeded() {
                // run in the JavaFx thread.
                if(message!=null){
                    clientMediator.getView().showAlert(message,null);
                }else{
                    clientMediator.getEventQueue().add(new OpenDoorEvent(clientMediator.getUserName()));
                }
            }

        };

        new Thread(progressTask).start();


    }
}
