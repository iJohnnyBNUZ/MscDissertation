package Controller;

import Model.Entity.Shop;
import Model.Entity.User;
import Model.Item.Food;
import Model.Item.Key;
import Model.Location.Location;
import Model.World;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;

public class TransactionMessageControllerTest {

    private GameMediator gameMediator;
    private TransactionMessageController transactionMessageController;
    private HashMap<String, Integer> tranList = new HashMap<String, Integer>();

    @Before
    public void setUp() throws  Exception{

        World world = new World();

        gameMediator = new GameMediator() {
            @Override
            public World getWorld() {
                return world;
            }
        };

        transactionMessageController = new TransactionMessageController(gameMediator);

        User userBuyer = new User("userBuyer");
        gameMediator.getWorld().addEntity(userBuyer);
        gameMediator.getWorld().getEntity("userBuyer").addToBag(new Food("apple0",15,15,"food"));
        gameMediator.getWorld().getEntity("userBuyer").addToBag(new Key("key0",15,"key"));
        gameMediator.getWorld().getEntity("userBuyer").setCoin(100);

        User seller = new User("seller");
        gameMediator.getWorld().addEntity(seller);
        gameMediator.getWorld().getEntity("seller").addToBag(new Food("apple1",15,15,"food"));
        gameMediator.getWorld().getEntity("seller").addToBag(new Key("key1",15,"food"));
        gameMediator.getWorld().getEntity("seller").setCoin(80);

        tranList.put("apple",0);
    }


    @Test
    public void tranMessageTest(){
        Boolean bool;
        bool = transactionMessageController.tranMessage("userBuyer", "seller", tranList, 120, "userBuyer");
        assertEquals(false,bool);
        bool = transactionMessageController.tranMessage("userBuyer", "seller", tranList, 100, "userBuyer");
        assertEquals(true,bool);
        bool = transactionMessageController.tranMessage("userBuyer", "seller", tranList, 90, "userBuyer");
        assertEquals(true,bool);
    }
}
