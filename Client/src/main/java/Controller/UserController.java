package Controller;

import Model.Entity.User;

import java.io.IOException;
import java.util.Objects;

public class UserController implements Controller {

	private ClientMediator clientMediator;
	
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
		if(clientMediator.getClientUpdater().connectToServer(IP)) {
			Boolean result = isUserExist(uName);
			clientMediator.setUserName(uName);
			clientMediator.enterGame();
			clientMediator.getClientUpdater().login(uName);
		}
		else {
			clientMediator.getIndexView().showMessage("Cannot Connect to the server");
		}
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
