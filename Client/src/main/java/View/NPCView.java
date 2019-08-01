package View;

import Controller.Command.CloseReactToCommand;

public class NPCView{

	private View view;
	private CloseReactToCommand closeReactToCommand = null;

	public NPCView(View view) {
		this.view = view;
	}

	public void updateNpcView(String sentence){
		view.showAlert(sentence);
		closeReactToCommand.execute();
	}

	public void setCloseReactToCommand(CloseReactToCommand closeReactToCommand){
		this.closeReactToCommand = closeReactToCommand;
	}
}
