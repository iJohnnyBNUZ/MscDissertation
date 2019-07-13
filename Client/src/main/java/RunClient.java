import Controller.GameMediator;
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
		System.out.println("start");
		// TODO Auto-generated method stub
		URL location = View.class.getResource("start.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        IndexView indexView = (IndexView) fxmlLoader.getController();
        primaryStage.setTitle("University of Edinburgh Dissertation");
        primaryStage.setScene(new Scene(root, 900, 720));
        primaryStage.show();
        System.out.println("Finished Loading index");
        
        GameMediator gameMediator = new GameMediator();
        gameMediator.setIndexView(indexView);
        gameMediator.setPrimaryStage(primaryStage);
        gameMediator.initialController();
        gameMediator.bindIndexCommand();
        gameMediator.testClient(gameMediator);

	}
	
	


	public static void main(String[] args) throws Exception {
       
        //gameMediator.testClient();
        launch(args);
    }
}
