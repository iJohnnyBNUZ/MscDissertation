package Controller;

import Model.Entity.Entity;
import Model.Entity.User;
import Model.Location.*;
import Model.World;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class LocationControllerTest {
    private LocationController locationController;
    private GameMediator gameMediator;


    @Before
    public void setUp() throws Exception {
        //setup a world
        World world = new World();
        Location location = new Location("location0");
        world.addLocation(location);

        List<Tile> tileslist = new ArrayList<Tile>();
        Tile t1 = new Grass(true,"grass",1); //(0,0)
        Tile t2 = new Grass(true,"grass",1);
        Tile t3 = new Stone(true,"stone",2);
        Tile t4 = new Grass(true,"grass",1); //(1,0)
        Tile t5 = new Water(true,"water",3);
        Tile t6 = new Grass(true,"grass",1);
        Tile t7 = new Grass(true,"grass",1); //(2,0)
        Tile t8 = new Stone(true,"stone",2);
        Tile t9 = new Door(true,"door",2);
        tileslist.add(t1);
        tileslist.add(t2);
        tileslist.add(t3);
        tileslist.add(t4);
        tileslist.add(t5);
        tileslist.add(t6);
        tileslist.add(t7);
        tileslist.add(t8);
        tileslist.add(t9);

        List<Coordinate> clist = new ArrayList<Coordinate>();

        User user0 = new User("user0");
        for(int i=0;i<3;i++){
            for (int j = 0;j<3;j++){
                int num = 1;
                Coordinate c = new Coordinate(i,j);
                clist.add(c);
            }
        }

        for (int i =0;i<clist.size();i++){
            location.addTile(clist.get(i),tileslist.get(i));
        }

        //User now is in (0,0)
        Entity user = new User("user1");
        world.addEntity(user);
        location.addEntity(user.getEntityID(),clist.get(0));

        gameMediator = new GameMediator() {
            @Override
            public World getWorld() {
                return world;
            }
        };

        locationController = new LocationController(gameMediator);
    }

    @org.junit.Test
    public void moveTo() {
        String userName = "user1";
        //move right test,user now is in (0,0),right y+1
        locationController.moveTo(userName,"right");
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getxPostion(),0);
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getyPosition(),1);

        //move down test,user now is in (0,1),down x+1;
        locationController.moveTo(userName,"down");
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getxPostion(),1);
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getyPosition(),1);

        //move left test,user now is in (1,1),left y-1
        locationController.moveTo(userName,"left");
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getxPostion(),1);
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getyPosition(),0);

        //move up test, user now is in (1,0),up x-1
        locationController.moveTo(userName,"up");
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getxPostion(),0);
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getyPosition(),0);

        //boundary test, if user is in boundray, then they can not move beyond the boundary
        locationController.moveTo(userName,"up");
        locationController.moveTo(userName,"left");
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getxPostion(),0);
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getyPosition(),0);

        //test the wrong user name, there will no exception
        locationController.moveTo("0000","right");
  }

    @Test
    public void changeUserCoordinate() {
        String userName = "user1";
        //user now is in (0,0),change it to (2,2)
        locationController.changeUserCoordinate(2,2,userName);
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getxPostion(),2);
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getyPosition(),2);

        //test the energy cost,(2,2) is a door, should cost 2 energy
        assertEquals(gameMediator.getWorld().getEntity(userName).getEnergy(),98);

        //if the change coordinate is out of boundary, then it will not change at all
        locationController.changeUserCoordinate(3,4,userName);
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getxPostion(),2);
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getyPosition(),2);

        //test the energy cost, can not move, the energy should not cost
        assertEquals(gameMediator.getWorld().getEntity(userName).getEnergy(),98);
    }

    @Test
    public void openDoor() {
    }

    @Test
    public void moveUserToNextLocation() {
    }


}