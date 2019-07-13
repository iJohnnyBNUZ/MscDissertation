package Controller;

public class UserController extends Controller {
	private static final String String = null;
	private GameMediator gameMediator;
	
	private UserController(GameMediator gameMediator){
		
	    this.gameMediator = gameMediator;
	}
	
	public void startGame(String type, String uName, String IP) {
		Boolean result = isUserExist(uName);
		
		if(type=="new") {
			if(result == true) {
				System.out.println("User is exist, please use another name!");
			}else {
				System.out.println("Create new user," + uName);
			}
		}else if(type == "continue") {
			if(result == true) {
				System.out.println("Start the game:"+ uName +IP);
			}else {
				System.out.println("User is not exist, please try again!");
			}
		}
		
	}
	
	public Boolean isUserExist(String uName) {
		Boolean result = false;
		return result;
	}
}
