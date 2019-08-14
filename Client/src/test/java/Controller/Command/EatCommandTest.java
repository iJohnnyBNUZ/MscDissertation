package Controller.Command;

import Controller.ClientMediator;
import Controller.ItemController;
import Model.Entity.User;
import Model.Item.Food;
import Model.Item.Item;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class EatCommandTest {

    private ClientMediator clientMediator;
    private ItemController itemController;
    private EatCommand eatCommand;

    @Before
    public void setUp(){
        clientMediator = new ClientMediator();
        itemController = new ItemController(clientMediator);
        eatCommand = new EatCommand(itemController,clientMediator);
        User user0 = new User("user0");
        clientMediator.getWorld().addEntity(user0);
        Food food = new Food("apple0",10,10,"food");
        clientMediator.getWorld().getEntity("user0").addToBag(food);
        clientMediator.setUserName("user0");
    }

    @Test
    public void executeTest(){
        eatCommand.execute("apple0");
        assertEquals(1,clientMediator.getEventQueue().size());
        assertEquals(0,clientMediator.getWorld().getEntity("user0").getBag().size());
    }

}
