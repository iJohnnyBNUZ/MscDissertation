package Controller;

import Model.Entity.User;
import Model.Item.Food;
import Model.Item.Item;
import Model.Item.Key;
import Model.World;
import View.View;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.api.FxAssert;

import java.net.URL;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MessageControllerTest extends ApplicationTest{

    private ClientMediator clientMediator;
    private MessageController messageController;
    private ItemController itemController;
    private View view;
    private List<Item> usershopBag;
    private List<Item> userBag;
    private HashMap<String, Integer> list = new HashMap<String,Integer>();

    @Before
    public void setUp() throws  Exception{
        View view = new View();
        clientMediator = new ClientMediator();
        itemController = new ItemController(clientMediator);
        clientMediator.setItemController(itemController);
        clientMediator.setView(view);
        messageController = new MessageController(clientMediator);
        World world = new World();
        clientMediator.setWorld(world);
        clientMediator.setView(view);
        System.out.println(clientMediator.getView());

        User user = new User("user");
        clientMediator.getWorld().addEntity(user);
        clientMediator.getWorld().getEntity("user").addToBag(new Food("apple0",15,15,"food"));
        clientMediator.getWorld().getEntity("user").addToBag(new Key("key0",15,"key"));
        usershopBag = clientMediator.getWorld().getEntity("user").getBag();

        User currUser = new User("currUser");
        clientMediator.getWorld().addEntity(currUser);
        clientMediator.getWorld().getEntity("currUser").addToBag(new Food("apple1",15,15,"food"));
        userBag = clientMediator.getWorld().getEntity("currUser").getBag();

        list.put("apple",1);
        clientMediator.setUserName("currUser");
        clientMediator.getWorld().getEntity("currUser").setCoin(100);
        clientMediator.getWorld().getEntity("user").setCoin(100);
        System.out.println(clientMediator.getWorld().getEntity("currUser").getCoin());
    }


    @Test
    public void buyMessageTest() {
        //assertEquals(clientMediator.getView().showAlert("test showalert"));
        //clientMediator.getWorld().getEntity("user0").setCoin(120);
        messageController.buyMessage("user",list,120);
        assertEquals(clientMediator.getWorld().getEntity("user").getCoin(),100);
        assertEquals(clientMediator.getWorld().getEntity("currUser").getCoin(),100);
        messageController.buyMessage("user",list,100);
        assertNotEquals(clientMediator.getWorld().getEntity("user").getCoin(),100);
        assertNotEquals(clientMediator.getWorld().getEntity("currUser").getCoin(),100);
        messageController.buyMessage("user",list,90);
        assertNotEquals(clientMediator.getWorld().getEntity("user").getCoin(),100);
        assertNotEquals(clientMediator.getWorld().getEntity("currUser").getCoin(),100);
    }

    @Test
    public void showAlertTest() {
        Boolean bool = messageController.showMessage("hello hello hello");
        assertEquals(true,bool);
    }

}

