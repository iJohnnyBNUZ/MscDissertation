package View;

import Controller.Command.Command;
import Controller.Command.PostCommand;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.print.DocFlavor;
import java.text.SimpleDateFormat;
import java.util.*;

public class PostView {

	private PostCommand postCommand = null;

	private AnchorPane chatView;
	private TextField messageWindow;
	private ChoiceBox atList;
	private Button atBtn;
	private Button closeChatView;
	private Button send;
	private List<String> atUser = new LinkedList<String>();
	private Boolean result;
	private ScrollPane scrollPane;
	private ListView<String> messageListView;

	public PostView(View view) {
		this.chatView = view.getChatView();
		this.closeChatView = view.getCloseChatView();
		this.messageWindow = view.getMessageWindow();
		this.send = view.getSend();
		this.atList = view.getAtList();
		this.atBtn = view.getAtBtn();
		this.scrollPane = view.getScrollPane();
		this.messageListView = view.getMessageListView();
	}

	public void setPostCommand(Command command){
		this.postCommand = (PostCommand) command;
	}



	/**
	 * Show the view of post window and provides a list of other online users that can be @
	 * @param messageList A list that contains all the messages that needs to be shown
	 * @param atUserList A list that contains other online users that can be @
	 */
	public void updatePost(List<String> messageList, ArrayList<String> atUserList){
		messageListView.setItems(null);

		int size = messageList.size();
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0 ; i < size; i++){
			list.add("   " + messageList.get(i));
		}
		messageListView.setItems(FXCollections.observableArrayList(list));

		atBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if(atUserList.isEmpty()){
					messageWindow.setText("Sorry there are no online users, so you cannot @");
				}
				else{
					atList.setVisible(true);
					atList.setItems(FXCollections.observableArrayList(atUserList));
					atList.show();
				}
			}
		});

		atList.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if(atList.getValue()!=null){
					if(!atUser.contains(atList.getValue())){
						atUser.add(String.valueOf(atList.getValue()));
					}
					atList.setVisible(false);
					messageWindow.setText(messageWindow.getText()+ "@" + atList.getValue());
				}
			}
		});


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
					for(int i = 0; i < atUser.size(); i++){
						result = messageWindow.getText().contains("@" + atUser.get(i));
						if(result ==  false){
							atUser.remove(i);
							i--;
						}
					}
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					postCommand.execute(messageWindow.getText(),df.format(new Date()).toString(), atUser);
					messageWindow.setText("");
					atList.setValue(null);
				}
			}
		});



	}



	/**
	 * Return the number of messages in the post window
	 * @return the number of messages in the post window
	 */
	public int getMessageListViewSize(){
		return messageListView.getItems().size();
	}


}
