import Controller.ClientMediator;
import View.IndexView;
import View.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class RunClient extends Application {


	public void start(Stage primaryStage) throws Exception {
		
		//show index page
		URL location = View.class.getResource("/view/index.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		Parent root = fxmlLoader.load();
		IndexView indexView = (IndexView) fxmlLoader.getController();
		primaryStage.setTitle("University of Edinburgh Dissertation");
		primaryStage.setScene(new Scene(root, 900, 720));
		primaryStage.show();
		System.out.println("Finished Loading index");

		ClientMediator clientMediator = new ClientMediator();
		clientMediator.setIndexView(indexView);
		clientMediator.setPrimaryStage(primaryStage);
		
		//initialize the controllers, commands, and observers.
		clientMediator.initialController();

		//bind the commands (related to the index view) with index view.
		clientMediator.bindIndexCommand();
		
		//create client used for network communication.
		clientMediator.createClient();
	}




	public static void main(String[] args) throws Exception {
       
        launch(args);
    }
}
