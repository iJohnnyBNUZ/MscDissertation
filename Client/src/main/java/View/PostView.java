package View;

import Controller.Command.Command;
import Controller.Command.PostCommand;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PostView {

	private PostCommand postCommand = null;

	private AnchorPane chatView;
	private TextField messageWindow;
	private AnchorPane chatScrollPane;

	public PostView(View view) {
		this.chatView = view.getChatView();
		Button closeChatView = view.getCloseChatView();
		this.messageWindow = view.getMessageWindow();
		Button send = view.getSend();
		this.chatScrollPane = view.getChatScrolPane();

		closeChatView.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				chatView.setVisible(false);
			}
		});

		send.setOnAction(actionEvent -> {
			if(messageWindow.getText() != null){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				postCommand.execute(messageWindow.getText(),df.format(new Date()).toString());
				messageWindow.setText("");
			}
		});


	}

	public void updatePost(List<String> messageList){
		chatScrollPane.getChildren().clear();
		//messageBox.getChildren().clear();
		//add messages to chatView
		int size = messageList.size();
		for(int i = 0 ; i < size; i++){
			Label messageLabel = new Label();
			messageLabel.setPrefWidth(288);
			messageLabel.setPrefHeight(27);
			messageLabel.setText(messageList.get(i));
			messageLabel.setLayoutX(20);
			messageLabel.setLayoutY(10+30*i);
			messageLabel.setWrapText(true);
			//messageBox.getChildren().add(messageLabel);
			chatScrollPane.getChildren().add(messageLabel);
		}


	}

	public void setPostCommand(Command command){
		this.postCommand = (PostCommand) command;
	}
}
