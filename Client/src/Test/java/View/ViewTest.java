package View;

import Model.Location.Coordinate;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ViewTest extends ApplicationTest {
    private View view = null;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL location = View.class.getResource("/view/main.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        primaryStage.setScene(new Scene(root, 900, 720));
        view = (View) fxmlLoader.getController();
        //Used for get the screen size and set to canvas.
        view.bindScene(primaryStage.getScene());
        view.setWindowsCloseAction(primaryStage);
        primaryStage.show();
    }

    @Test
    public void showBagTest() {
        clickOn("#Bag");
        clickOn("#openBag");
        FxAssert.verifyThat("#tabBagView", NodeMatchers.isVisible());
    }

    @Test
    public void showChatTest() {
        clickOn("#Chat");
        clickOn("#openChat");
        FxAssert.verifyThat("#chatView", NodeMatchers.isVisible());
    }


    @Test
    public void updateLocation() {
        Map<Coordinate, String> tmp = new HashMap<Coordinate, String>();
        int num = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Coordinate tmp_cor = new Coordinate(i, j);
                if (j % 2 == 0 && i % 2 == 0) {
                    tmp.put(tmp_cor, "water");
                } else {
                    tmp.put(tmp_cor, "grass");
                }

                num++;
            }
        }
        LocationView locationView = new LocationView(view);
        assertEquals(tmp.size(), locationView.update(tmp) );
    }

    @Test
    public void updateItemTest() {

        Task<Void> progressTask = new Task<Void>(){
            Map<String,Coordinate> items = new HashMap<String,Coordinate>();
            @Override
            protected Void call() throws Exception {

                items.put("apple1",new Coordinate(0,1));
                items.put("orange2",new Coordinate(1,1));
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                ItemView itemView = new ItemView(view);
                itemView.update(items);
                assertEquals(items.size(), view.getForItem().getChildren().size() );
            }

        };

        new Thread(progressTask).start();

    }

    @Test
    public void updateUserTest() {

    }

    @Test
    public void updateNPCTest() {

    }

    @Test
    public void updateStoreTest() {

    }

    @Test
    public void updateEnergyTest() {

    }

    @Test
    public void updateCoinTest() {

    }
}