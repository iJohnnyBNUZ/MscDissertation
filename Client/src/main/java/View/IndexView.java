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
		userInfo.setVisible(true);
		index.setVisible(false);
	}
	
	public void continueGame() {
		gameType = "continue";
		userInfo.setVisible(true);
		index.setVisible(false);
	}
	
	public void startGame() throws IOException {
		System.out.println(userName.getText().equals(""));
		if(!userName.getText().equals("")&& !IPAddress.getText().equals("")) {
			switch(gameType) {
			case "new" : System.out.println("start new");enterGame(); break;
			case "continue":System.out.println("start continues"); break;
			default: System.out.println("Wrong!");break;
			}
		}else {
			userStatus.setText("Please input your info!");
		}
		
		
	}
	
	public void back() {
		gameType = null;
		userInfo.setVisible(false);
		index.setVisible(true);
		userStatus.setText("");
	}
	public void exit() {
		
	}
	
	
	public void showError(String message) {
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
