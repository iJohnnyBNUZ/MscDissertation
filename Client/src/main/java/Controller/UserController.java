package Controller;

import Model.Entity.User;

import java.io.IOException;

public class UserController implements Controller {
	private static final String String = null;
	private ClientMediator clientMediator;
	
	public UserController(ClientMediator clientMediator){
		
	    this.clientMediator = clientMediator;
	}
	
	public void startGame(String type, String uName, String IP) throws IOException, ClassNotFoundException {
		if(clientMediator.getClient().connectToServer(IP)) {
			Boolean result = isUserExist(uName);
			if(type=="new") {
				if(result == true) {
					System.out.println("User is exist, please use another name!");
					clientMediator.getIndexView().showMessage("User is exist, please use another name!");
				}else {
					System.out.println("Create new user," + uName);
					clientMediator.setUserName(uName);
					clientMediator.getClient().login("new",uName);
					clientMediator.enterGame();
				}
			}else if(type == "continue") {
				if(result == true) {
					System.out.println("Start the game:"+ uName +IP);
					clientMediator.setUserName(uName);
					clientMediator.getClient().login("continue",uName);
					clientMediator.enterGame();
				}else {
					System.out.println("User is not exist, please try again!");
					clientMediator.getIndexView().showMessage("User is not exist, please try again!");
				}
			}
		}else {
			clientMediator.getIndexView().showMessage("Cannot Connect to the server");
		}
		
		
	}
	

	public Boolean isUserExist(String uName) {
		Boolean result = false;
		if (clientMediator.getWorld().getEntity(uName) instanceof User){
			result = true;
		}else {
			System.out.println("User "+uName+" is not exist!");
		}
		return result;
	}
}
