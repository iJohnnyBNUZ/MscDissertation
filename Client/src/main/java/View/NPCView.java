package View;

import javafx.scene.control.Label;

public class NPCView{

	private Label notifyWindow;

	public NPCView(View view) {
		// TODO Auto-generated constructor stub
		this.notifyWindow = view.getNotifyWindow();
	}

	public void updateNpcView(String sentence){
		notifyWindow.setText(sentence);
	}
}
