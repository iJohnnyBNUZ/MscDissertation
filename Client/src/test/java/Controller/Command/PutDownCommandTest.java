package Controller.Command;

import Controller.ClientMediator;
import Controller.ItemController;
import Model.Entity.User;
import Model.Item.Food;
import Model.Location.Coordinate;
import Model.Location.Location;
import View.View;
import javafx.concurrent.Task;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PutDownCommandTest {

    private ClientMediator clientMediator;
    private ItemController itemController;
    private PutDownCommand putDownCommand;
    private View view;

    @Before
    public void setUp(){
        clientMediator = new ClientMediator();
        view = new View();
        clientMediator.setView(view);
        itemController = new ItemController(clientMediator);
        putDownCommand = new PutDownCommand(itemController,clientMediator);
        User user0 = new User("user0");
        User user1 = new User("user1");
        clientMediator.getWorld().addEntity(user0);
        clientMediator.getWorld().addEntity(user1);
        Food food0 = new Food("apple0",10,10,"food");
        clientMediator.getWorld().getEntity("user0").addToBag(food0);
        Food food1 = new Food("apple1",10,10,"food");
        clientMediator.getWorld().getEntity("user1").addToBag(food1);
        Location currLocation = new Location("currLocation");
        clientMediator.getWorld().addLocation(currLocation);
        Coordinate user0_coor = new Coordinate(0,0);
        Coordinate user1_coor = new Coordinate(1,1);
        clientMediator.getWorld().getLocation("currLocation").addEntity(user0,user0_coor);
        clientMediator.getWorld().getLocation("currLocation").addEntity(user1,user1_coor);
        Food food = new Food("apple",10,10,"food");
        Coordinate food_coor = new Coordinate(0,0);
        clientMediator.getWorld().getLocation("currLocation").addItem(food_coor,food);

    }


    @Test
    public void executeTest(){

        Task<Void> progressTask = new Task<Void>(){

            @Override
            protected Void call() throws Exception{
                clientMediator.setUserName("user1");
                clientMediator.getEventQueue().clear();
                assertEquals("user1",clientMediator.getUserName());
                assertNull(itemController.drop("user1","apple1"));
                putDownCommand.execute("apple1");
                return null;
            }

            @Override
            protected void succeeded(){
                assertEquals(1,clientMediator.getEventQueue().size());
                clientMediator.setUserName("user0");
                clientMediator.getEventQueue().clear();
                assertEquals("user0",clientMediator.getUserName());
                assertNotNull(itemController.drop("user0","apple0"));
                putDownCommand.execute("apple0");
                assertEquals(0,clientMediator.getEventQueue().size());
            }
        };

        new Thread(progressTask).start();

    }




}









