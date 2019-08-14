package Controller;

import Model.Entity.NPC;
import Model.Entity.Shop;
import Model.Entity.User;
import Model.Location.Coordinate;
import Model.Location.Location;
import Model.World;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ReactToControllerTest {

    private GameMediator gameMediator;
    private ReactToController reactToController;

    @Before
    public void setUp() throws Exception {

        World world = new World();
        gameMediator = new GameMediator() {
            @Override
            public World getWorld() {
                return world;
            }
        };

        User user_1  = new User("user_1");
        User user_2  = new User("user_2");
        User user_3  = new User("user_3");
        User user_4  = new User("user_4");
        User user_5  = new User("user_5");
        User user0 = new User("user0");
        User user1 = new User("user1");
        User user2 = new User("user2");
        Shop shop0 = new Shop("shop0");
        Shop shop1 = new Shop("shop1");
        NPC  npc0  = new NPC("npc0",true);
        NPC  npc1  = new NPC("npc1",false);
        world.addEntity(user_1);
        world.addEntity(user_2);
        world.addEntity(user_3);
        world.addEntity(user_4);
        world.addEntity(user_5);
        world.addEntity(user0);
        world.addEntity(user1);
        world.addEntity(user2);
        world.addEntity(shop0);
        world.addEntity(shop1);
        world.addEntity(npc0);
        world.addEntity(npc1);
        Coordinate user_1_coor = new Coordinate(0,0);
        Coordinate user_2_coor = new Coordinate(0,1);
        Coordinate user_3_coor = new Coordinate(0,2);
        Coordinate user_4_coor = new Coordinate(1,0);
        Coordinate user_5_coor = new Coordinate(1,1);
        Coordinate user0_coor = new Coordinate(0,0);
        Coordinate user1_coor = new Coordinate(1,0);
        Coordinate user2_coor = new Coordinate(1,1);
        Coordinate shop0_coor = new Coordinate(0,1);
        Coordinate shop1_coor = new Coordinate(1,0);
        Coordinate npc0_coor = new Coordinate(0,2);
        Coordinate npc1_coor = new Coordinate(1,1);
        Location location = new Location("currLocation");
        world.addLocation(location);
        location.addEntity(user_1,user_1_coor);
        location.addEntity(user_2,user_2_coor);
        location.addEntity(user_3,user_3_coor);
        location.addEntity(user_4,user_4_coor);
        location.addEntity(user_5,user_5_coor);
        location.addEntity(user0,user0_coor);
        location.addEntity(user1,user1_coor);
        location.addEntity(user2,user2_coor);
        location.addEntity(shop0,shop0_coor);
        location.addEntity(shop1,shop1_coor);
        location.addEntity(npc0,npc0_coor);
        location.addEntity(npc1,npc1_coor);

        reactToController = new ReactToController(gameMediator);

    }

    @Test
    public void communicateWithTest(){

        assertEquals("user0",reactToController.communicateWith("user_1"));
        assertEquals("shop0",reactToController.communicateWith("user_2"));
        assertEquals("npc0",reactToController.communicateWith("user_3"));
        assertEquals("shop1",reactToController.communicateWith("user_4"));
        assertEquals("npc1",reactToController.communicateWith("user_5"));




    }


    @Test
    public void reactToEntityTest(){

        String message;
        message = reactToController.reactToEntity("user0","user_1");
        assertEquals("transaction with user",message);
        message = reactToController.reactToEntity("shop0","user_2");
        assertEquals("transaction with shop",message);
        message = reactToController.reactToEntity("npc0","user_3");
        assertEquals("increase 20 energy",message);
        message = reactToController.reactToEntity("shop1","user_4");
        assertEquals("transaction with shop",message);
        message = reactToController.reactToEntity("npc1","user_5");
        assertEquals("decrease 20 energy",message);
    }

}
