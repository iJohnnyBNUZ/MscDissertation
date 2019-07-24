package Controller;

import Model.Entity.User;
import Model.Location.*;
import Model.World;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserControllerTest {
    private UserController userController;
    private ClientMediator clientMediator;
    private World world;

    @Before
    public void setUp() throws Exception {
        //setup a world
        world = new World();
        Location location = new Location("location0");
        world.addLocation(location);

        List<Tile> tileslist = new ArrayList<Tile>();
        Tile t1 = new Grass(true,"grass",1); //(0,0)
        Tile t2 = new Grass(true,"grass",1);
        Tile t3 = new Stone(true,"stone",2);

        tileslist.add(t1);
        tileslist.add(t2);
        tileslist.add(t3);

        List<Coordinate> clist = new ArrayList<Coordinate>();

        for(int i=0;i<3;i++){
                int num = 1;
                Coordinate c = new Coordinate(i,0);
                clist.add(c);
        }

        for (int i =0;i<clist.size();i++){
            location.addTile(clist.get(i),tileslist.get(i));
        }

        //User now is in (0,0)
        User user = new User("user1");
        world.addEntity(user);
        location.addEntity(user,clist.get(0));

        clientMediator = new ClientMediator();
        clientMediator.setWorld(world);

        userController = new UserController(clientMediator);
    }

    @Test
    public void getClientMediator() {
        assertEquals(userController.getClientMediator(),clientMediator);
    }

    @Test
    public void setClientMediator() {
        ClientMediator c2 = new ClientMediator();
        userController.setClientMediator(c2);
        assertNotEquals(userController.getClientMediator(),clientMediator);
    }

    @Test
    public void isUserExist() {
        assertFalse(userController.isUserExist("user99999"));
        assertTrue(userController.isUserExist("user1"));
    }
}