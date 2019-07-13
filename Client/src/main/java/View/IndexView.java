package View;

import java.io.IOException;
import java.net.URL;

import Controller.Command.StartGameCommand;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
		showMessage("");
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
			case "continue":startGame.execute("continue", uName, IP); ; break;
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
	public void	enterGame() throws IOException {
		Stage stage = (Stage) page.getScene().getWindow();
		URL location = getClass().getResource("sample.fxml");
		System.out.println(location.toString());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root, 900, 720));
        View view = (View) fxmlLoader.getController();
        //Used for get the screen size and set to canvas.
        //Test is written in that method either.
        view.bindScene(stage.getScene());
        stage.show();
	}

	public void setStartGame(StartGameCommand startGame) {
		this.startGame = startGame;
	}

	
}
