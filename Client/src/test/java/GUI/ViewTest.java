package GUI;

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

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import Model.Item.Food;

import Model.Item.Item;

import Model.Item.Key;
import org.testfx.matcher.base.NodeMatchers;


import java.util.*;

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
    public void drawTest(){
        Boolean result = view.draw("water",new Coordinate(0,1));
        assertEquals(true,result);

    }

    @Test
    public void drawInteractiveTest(){
        Task<Void> progressTask = new Task<Void>(){
            Map<String,Coordinate> items = new HashMap<String,Coordinate>();
            @Override
            protected Void call() throws Exception {
                return null;
            }

            @Override
            protected void succeeded() {
                int before=view.getForItem().getChildren().size();
                view.drawInteractive("apple",new Coordinate(0,1),true);
                int after = view.getForItem().getChildren().size();
                assertEquals(after,before+1);

                before=view.getForEntity().getChildren().size();
                view.drawInteractive("store",new Coordinate(0,1),false);
                view.drawInteractive("npc",new Coordinate(1,1),false);
                after = view.getForEntity().getChildren().size();

                assertEquals(after,before+2);
            }

        };

        new Thread(progressTask).start();

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
                int result = itemView.update(items);
                assertEquals(items.size(), result);
            }

        };

        new Thread(progressTask).start();

    }

    @Test
    public void updateUserTest() {
        Task<Void> progressTask = new Task<Void>(){
            Map<String,Coordinate> users = new HashMap<String,Coordinate>();
            @Override
            protected Void call() throws Exception {

                users.put("me",new Coordinate(0,1));
                users.put("player",new Coordinate(1,1));
                return null;
            }

            @Override
            protected void succeeded() {
                EntityView entityView = new EntityView(view);
                int result = entityView.updateUser(users);
                assertEquals(users.size(), result);
            }

        };

        new Thread(progressTask).start();
    }

    @Test
    public void updateNPCTest() {
        Task<Void> progressTask = new Task<Void>(){
            Map<String,Coordinate> npcs = new HashMap<String,Coordinate>();
            @Override
            protected Void call() throws Exception {

                npcs.put("npc0",new Coordinate(0,1));
                npcs.put("npc1",new Coordinate(1,1));
                return null;
            }

            @Override
            protected void succeeded() {
                EntityView entityView = new EntityView(view);
                int result = entityView.updateNPC(npcs);
                assertEquals(npcs.size(), result);
            }

        };

        new Thread(progressTask).start();
    }

    @Test
    public void updateStoreTest() {
        Task<Void> progressTask = new Task<Void>(){
            Map<String,Coordinate> stores = new HashMap<String,Coordinate>();
            @Override
            protected Void call() throws Exception {

                stores.put("store0",new Coordinate(0,1));
                stores.put("store1",new Coordinate(1,1));
                return null;
            }

            @Override
            protected void succeeded() {
                EntityView entityView = new EntityView(view);
                int result = entityView.updateStore(stores);
                assertEquals(stores.size(), result);
            }

        };

        new Thread(progressTask).start();
    }

    @Test
    public void updateEnergyTest() {
        Task<Void> progressTask = new Task<Void>(){
            int n=90;
            @Override
            protected Void call() throws Exception {

                return null;
            }

            @Override
            protected void succeeded() {
                EntityView entityView = new EntityView(view);
                entityView.updateEnergy(n);
                assertEquals(0.9, view.getEnergy().getProgress(),0.0);
            }

        };

        new Thread(progressTask).start();
    }

    @Test
    public void updateCoinTest() {
        Task<Void> progressTask = new Task<Void>(){
            int n =120;
            @Override
            protected Void call() throws Exception {
                return null;
            }

            @Override
            protected void succeeded() {
                EntityView entityView = new EntityView(view);
                entityView.updateCoin(n);
                assertEquals(120, n);
            }

        };

        new Thread(progressTask).start();
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