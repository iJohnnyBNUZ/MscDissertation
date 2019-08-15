package View;

import Controller.Command.CloseReactToCommand;

public class NPCView{

	private View view;
	private CloseReactToCommand closeReactToCommand;

	public NPCView(View view) {
		this.view = view;
	}


	public void setCloseReactToCommand(CloseReactToCommand closeReactToCommand){
		this.closeReactToCommand = closeReactToCommand;
	}


	/**
	 * Show a dialog box when User interacts with NPC
	 * @param sentence A sentence that needs to be shown in the dialog box
	 */
	public void updateNpcView(String sentence){
		view.showAlert(sentence,null);
		closeReactToCommand.execute();
	}


}
