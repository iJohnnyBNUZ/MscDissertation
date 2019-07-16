package Controller.Command;

import Controller.Save.SaveUser;

public class SaveGameCommand implements Command{
	private SaveUser saveUser = null;
	
	public SaveGameCommand(SaveUser saveUser) {
		this.saveUser = saveUser;
	}
	
	public void execute() {
		System.out.println("Saveeeeeeeeeeeeeeeee!!!!!");
	}
}
