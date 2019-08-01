package Controller;

import Model.Entity.User;
import Model.Item.Food;
import Model.Item.Item;
import Model.Item.Key;
import Model.World;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TransactionMessageControllerTest extends ApplicationTest{

    private ClientMediator clientMediator;
    private TransactionMessageController transactionMessageController;
    private List<Item> usershopBag;
    private List<Item> userBag;
    private HashMap<String, Integer> list = new HashMap<String,Integer>();

    @Before
    public void setUp() throws  Exception{
        clientMediator = new ClientMediator();
        transactionMessageController = new TransactionMessageController(clientMediator);
        World world = new World();
        clientMediator.setWorld(world);


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
        Boolean bool;
        bool = transactionMessageController.buyMessage("user",list,120);
        assertEquals(false,bool);
        bool = transactionMessageController.buyMessage("user",list,100);
        assertEquals(true,bool);
        bool = transactionMessageController.buyMessage("user",list,90);
        assertEquals(true,bool);
    }

    @Test
    public void sellMessageTest() {
        //assertEquals(clientMediator.getView().showAlert("test showalert"));
        //clientMediator.getWorld().getEntity("user0").setCoin(120);
        Boolean bool;
        bool = transactionMessageController.sellMessage("user",list,120);
        assertEquals(false,bool);
        bool = transactionMessageController.sellMessage("user",list,100);
        assertEquals(true,bool);
        bool = transactionMessageController.sellMessage("user",list,90);
        assertEquals(true,bool);
    }


}

