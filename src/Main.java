import java.net.URL;

import View.LocationView;
import View.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
    	
    	URL location = getClass().getResource("View/sample.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("University of Edinburgh Dissertation");
        primaryStage.setScene(new Scene(root, 900, 720));
        
        //The controller specified in the fxml file is LocationView,
        //I know it should be View, but if I do that the super.getMap() can not take effect.
        //May be in the future I can find some appropriate solutions.
        View view = (View) fxmlLoader.getController();
        primaryStage.show();
        
        System.out.println("Finished Loading");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
