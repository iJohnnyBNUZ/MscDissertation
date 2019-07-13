package Controller;

import java.io.IOException;

import Model.Entity.User;

public class UserController implements Controller {
	private static final String String = null;
	private GameMediator gameMediator;
	
	public UserController(GameMediator gameMediator){
		
	    this.gameMediator = gameMediator;
	}
	
	public void startGame(String type, String uName, String IP) throws IOException, ClassNotFoundException {
		if(gameMediator.getClient().connectToServer(IP)) {
			Boolean result = isUserExist(uName);
			if(type=="new") {
				if(result == true) {
					System.out.println("User is exist, please use another name!");
					gameMediator.getIndexView().showMessage("User is exist, please use another name!");
				}else {
					System.out.println("Create new user," + uName);
					gameMediator.getClient().login("new",uName);
					gameMediator.enterGame();
				}
			}else if(type == "continue") {
				if(result == true) {
					System.out.println("Start the game:"+ uName +IP);
					gameMediator.getClient().login("continue",uName);
					gameMediator.enterGame();
				}else {
					System.out.println("User is not exist, please try again!");
					gameMediator.getIndexView().showMessage("User is not exist, please try again!");
				}
			}
		}else {
			gameMediator.getIndexView().showMessage("Cannot Connect to the server");
		}
		
		
	}
	

	public Boolean isUserExist(String uName) {
		Boolean result = false;
		if (gameMediator.getWorld().getEntity(uName) instanceof User){
			result = true;
		}else {
			System.out.println("User "+uName+" is already online!");
		}
		return result;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
