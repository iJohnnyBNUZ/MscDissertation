package View;

import Model.Location.Coordinate;
import Model.Location.Location;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.api.FxAssert;
import org.testfx.matcher.base.NodeMatchers;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ViewTest extends ApplicationTest {
    private View view = null;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL location = View.class.getResource("/view/sample.fxml");
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
    public void showTransactionTest() {
        clickOn("#Transaction");
        clickOn("#openTransaction");
        FxAssert.verifyThat("#newTransaction", NodeMatchers.isVisible());
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
}