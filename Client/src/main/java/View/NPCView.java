package View;

import javafx.scene.control.Label;

public class NPCView{

	private View view;

	public NPCView(View view) {
		this.view = view;
	}

	public void updateNpcView(String sentence){
		view.showAlert(sentence);
	}
}
