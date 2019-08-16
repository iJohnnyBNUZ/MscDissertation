package GUI;

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

	/**
	 * Record the user's selection and show the page used to input information.
	 */
	public void newGame() {
		this.gameType = "new";
		userStatus.setText("");
		userInfo.setVisible(true);
		index.setVisible(false);
	}

	/**
	 * Record the user's selection and show the page used to input information.
	 */
	public void continueGame() {
		this.gameType = "continue";
		userStatus.setText("");
		userInfo.setVisible(true);
		index.setVisible(false);
	}

	/**
	 * Get the user input and invoke the start game command.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void startGame() throws IOException, ClassNotFoundException {
		String uName = userName.getText();
		String IP = IPAddress.getText();

		//ensure the input cannot be empty.
		if(!uName.equals("")&& !IP.equals("")) {
			showMessage("Connecting.......");
			if(!gameType.equals(null)) {
				startGame.execute(gameType, uName, IP);
			}
			else
				showMessage("The game type is wrong");

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


    public String getGameType() {
		return gameType;
    }
}
