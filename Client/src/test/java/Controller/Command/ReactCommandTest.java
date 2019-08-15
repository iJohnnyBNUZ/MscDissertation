package Controller.Command;

import Controller.ClientMediator;
import Controller.PostController;
import Controller.ReactToController;
import Model.Entity.NPC;
import Model.Entity.Shop;
import Model.Entity.User;
import Model.Location.Coordinate;
import Model.Location.Location;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReactCommandTest {

    private ClientMediator clientMediator;
    private ReactToController reactToController;
    private ReactToCommand reactToCommand;

    @Before
    public void setUp(){
        clientMediator = new ClientMediator();
        reactToController = new ReactToController(clientMediator);
        reactToCommand = new ReactToCommand(reactToController,clientMediator);
        User user0 = new User("user0");
        User user1 = new User("user1");
        clientMediator.getWorld().addEntity(user0);
        clientMediator.getWorld().addEntity(user1);
        Location currLocation = new Location("currLocation");
        clientMediator.getWorld().addLocation(currLocation);
        Shop shop0 = new Shop("shop0");
        Coordinate user0_coor = new Coordinate(0,0);
        Coordinate shop0_coor = new Coordinate(0,0);
        Coordinate user1_coor = new Coordinate(0,1);
        currLocation.addEntity(user1,user1_coor);
        currLocation.addEntity(user0,user0_coor);
        currLocation.addEntity(shop0,shop0_coor);

    }

    @Test
    public void executeTest(){

        clientMediator.setUserName("user0");
        clientMediator.setReactTo(null);
        clientMediator.setReactResult(null);
        assertEquals("user0",clientMediator.getUserName());
        assertEquals("shop0",reactToController.communicateWith("user0"));
        reactToCommand.execute();
        assertEquals("shop0",clientMediator.getReactTo());
        assertNotNull(clientMediator.getReactResult());
        assertEquals(1,clientMediator.getEventQueue().size());

        clientMediator.setUserName("user1");
        clientMediator.setReactTo(null);
        clientMediator.setReactResult(null);
        clientMediator.getEventQueue().clear();
        assertEquals("user1",clientMediator.getUserName());
        assertEquals(null,reactToController.communicateWith("user1"));
        reactToCommand.execute();
        assertEquals(null,clientMediator.getReactTo());
        assertNull(clientMediator.getReactResult());
        assertEquals(0,clientMediator.getEventQueue().size());


    }
}
