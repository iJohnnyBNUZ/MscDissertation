package View;

import Controller.Command.Command;
import Controller.Command.PostCommand;
import Controller.CommunicationController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatView{

	private PostCommand postCommand = null;

	private AnchorPane chatView = null;
	private Button closeChatView = null;
	private TextField messageWindow = null;
	private Button send = null;
	private VBox messageBox = null;


	public ChatView(View view) {
		this.chatView = view.getChatView();
		this.closeChatView = view.getCloseChatView();
		this.messageWindow = view.getMessageWindow();
		this.send = view.getSend();
		this.messageBox = view.getMessageBox();

		closeChatView.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				chatView.setVisible(false);
			}
		});

		send.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if(messageWindow.getText() != null){
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					postCommand.execute(messageWindow.getText(),df.format(new Date()).toString());
					messageWindow.setText("");
				}
			}
		});


	}

	public void updateChat(List<String> messageList){
		messageBox.getChildren().clear();
		//add messages to chatView
		int size = messageList.size();
		for(int i =0 ; i< size; i++){
			Label messageLabel = new Label();
			messageLabel.setPrefWidth(288);
			messageLabel.setPrefHeight(27);
			messageLabel.setText(messageList.get(i));
			messageBox.getChildren().add(messageLabel);
		}

	}

	public void setPostCommand(Command command){
		this.postCommand = (PostCommand) command;
	}
}
