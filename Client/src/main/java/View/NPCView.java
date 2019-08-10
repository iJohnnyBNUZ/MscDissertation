package View;

import Controller.Command.CloseReactToCommand;

public class NPCView{

	private View view;
	private CloseReactToCommand closeReactToCommand;

	public NPCView(View view) {
		this.view = view;
	}

	public void updateNpcView(String sentence){
		view.showAlert(sentence,null);
		closeReactToCommand.execute();
	}

	public void setCloseReactToCommand(CloseReactToCommand closeReactToCommand){
		this.closeReactToCommand = closeReactToCommand;
	}
}
