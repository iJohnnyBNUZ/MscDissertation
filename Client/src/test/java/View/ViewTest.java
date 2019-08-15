package View;

import Controller.ClientMediator;
import Controller.Command.CloseReactToCommand;
import Model.Item.Food;
import Model.Item.Item;
import Model.Item.Key;
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
import java.util.*;

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
    public void showPostTest() {
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


    @Test
    public void updateBagViewTest(){

        Task<Void> progressTask = new Task<Void>(){

            List<Item> bag = new LinkedList<Item>();
            int money = 100;
            BagView bagView = new BagView(view);

            @Override
            protected Void call() throws Exception{
                bag.add(new Food("apple1",10,10,"food"));
                bag.add(new Food("apple2",10,10,"food"));
                bag.add(new Food("lemon1",10,10,"food"));
                bag.add(new Food("orange1",10,10,"food"));
                bag.add(new Key("key_blue1",10,"key"));
                bag.add(new Key("key_red0",10,"key"));
                bag.add(new Key("key_red1",10,"key"));
                return null;
            }

            @Override
            protected void succeeded() {
                bagView.updateBag(bag,money);
                assertEquals(3,bagView.getGridPaneFoodListSize());
                assertEquals(2,bagView.getGridPaneKeyListSize());
            }

        };

        new Thread(progressTask).start();

    }


    @Test
    public void updatePostViewTest(){

        Task<Void> progressTask = new Task<Void>(){

            PostView postView = new PostView(view);
            List<String> messageList = new LinkedList<String>();
            ArrayList<String> list = new ArrayList<String>();

            @Override
            protected Void call() throws Exception{

                messageList.add("hello world");
                messageList.add("hello Java");
                messageList.add("hello edinburgh");
                list.add("user0");
                list.add("user1");
                return null;
            }

            @Override
            protected void succeeded() {
                postView.updatePost(messageList,list);
                assertEquals(3,postView.getMessageListViewSize());
            }

        };

        new Thread(progressTask).start();

    }

    @Test
    public void updateTransactionViewTest(){


        Task<Void> progressTask = new Task<Void>(){

            TransactionView transactionView = new TransactionView(view);
            List<Item> userShopBag = new LinkedList<Item>();
            String userShopName = "user1";
            List<Item> myBag = new LinkedList<Item>();
            int money = 100;

            @Override
            protected Void call() throws Exception{

                userShopBag.add(new Food("apple1",10,10,"food"));
                userShopBag.add(new Food("apple2",10,10,"food"));
                userShopBag.add(new Food("orange1",10,10,"food"));
                userShopBag.add(new Food("key_blue1",10,10,"food"));
                myBag.add(new Food("apple3",10,10,"food"));
                myBag.add(new Food("lemon2",10,10,"food"));
                return null;
            }

            @Override
            protected void succeeded() {
                transactionView.updateTransaction(userShopBag,userShopName,myBag,money);
                assertEquals(3,transactionView.getUserShopVBoxSize());
                assertEquals(2,transactionView.getMyBagVBoxSize());
            }

        };

        new Thread(progressTask).start();

    }
}