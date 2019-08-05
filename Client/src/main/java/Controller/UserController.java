package Controller;

import Model.Entity.User;
import Model.Location.Coordinate;
import Model.Location.Location;
import Network.Events.LoginEvent;
import javafx.concurrent.Task;

import java.io.IOException;
import java.util.Objects;

public class UserController implements Controller {

	private ClientMediator clientMediator;
	private String type;
	private String uName;
	
	public UserController(ClientMediator clientMediator){
		
	    this.clientMediator = clientMediator;
	}

	public ClientMediator getClientMediator() {
		return clientMediator;
	}

	public void setClientMediator(ClientMediator clientMediator) {
		this.clientMediator = clientMediator;
	}

	public void startGame(String type, String uName, String IP) throws IOException, ClassNotFoundException {
		this.type = type;
		this.uName = uName;
		if (clientMediator.getClientUpdater().connectToServer(IP)) {
			clientMediator.getClientUpdater().getWorld();
		} else {
			clientMediator.getIndexView().showMessage("Cannot Connect to the server");
		}
	}

	public void enterGame() throws IOException, ClassNotFoundException{
		Task<Void> progressTask = new Task<Void>(){
			boolean result = false;
			@Override
			protected Void call() throws Exception {
				result = isUserExist(uName);
				return null;
			}

			@Override
			protected void succeeded() {
				//super.succeeded();
				if(type.equals("new")) {
					if(!result){
						clientMediator.setUserName(uName);
						try {
							clientMediator.getClientUpdater().login(uName);
							clientMediator.enterGame();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}else{
						clientMediator.getIndexView().showMessage("Username is exist!");
					}
				}else if (type.equals("continue")) {
					if (result){
						clientMediator.setUserName(uName);
						try {
							clientMediator.getClientUpdater().login(uName);
							clientMediator.enterGame();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}else{
						clientMediator.getIndexView().showMessage("Username is not exist!");
					}
				}
			}

		};

		new Thread(progressTask).start();




	}

	public Boolean isUserExist(String uName) {
		boolean result = false;
		if(clientMediator.getWorld().getEntity(uName)instanceof User){
			result = true;
		}else
			System.out.println("User "+uName+" is not exist!");

		return result;
	}
}
