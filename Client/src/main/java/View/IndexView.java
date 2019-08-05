package View;

import Controller.Command.StartGameCommand;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class IndexView {
	@FXML
	private AnchorPane page;
	
	@FXML
	private AnchorPane userInfo;
	
	@FXML
	private AnchorPane index;
	
	@FXML 
	private TextField userName;
	
	@FXML 
	private TextField IPAddress;
	
	@FXML
	private Label userStatus;
	
	private String gameType = null;
	
	private StartGameCommand startGame= null;
	
	public void newGame() {
		gameType = "new";
		userStatus.setText("");
		userInfo.setVisible(true);
		index.setVisible(false);
	}
	
	public void continueGame() {
		gameType = "continue";
		userStatus.setText("");
		userInfo.setVisible(true);
		index.setVisible(false);
	}
	
	public void startGame() throws IOException, ClassNotFoundException {
		String uName = userName.getText();
		String IP = IPAddress.getText();
		if(!uName.equals("")&& !IP.equals("")) {
			showMessage("Connecting.......");
			switch(gameType) {
			case "new" : startGame.execute("new", uName, IP); break;
			case "continue":startGame.execute("continue", uName, IP);  break;
			default: showMessage("The game type is wrong");break;
			}
		}else {
			showMessage("Please input your info!");
		}
		
		
	}
	
	public void back() {
		gameType = null;
		userInfo.setVisible(false);
		index.setVisible(true);
		userStatus.setText("");
	}
	public void exit() {
		Stage stage = (Stage) page.getScene().getWindow();
		stage.close();
	}
	
	
	public void showMessage(String message) {
		userStatus.setText(message);
	}

	public void setStartGame(StartGameCommand startGame) {
		this.startGame = startGame;
	}

	
}
