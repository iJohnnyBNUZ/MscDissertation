package Controller;

import Model.Entity.Entity;
import Model.Entity.User;
import Model.Item.Key;
import Model.Location.*;
import Model.World;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class LocationControllerTest {
    private LocationController locationController;
    private GameMediator gameMediator;
    private Door t9;
    private Door nextLocationDoor;

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
        t9 = new Door(true,"door",2,"location0","location1");

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

        Location location1 = new Location("location1");
        List<Coordinate> clistlocation1 = new ArrayList<Coordinate>();
        for(int i=0;i<4;i++){
            for (int j = 0;j<4;j++){
                Coordinate c = new Coordinate(i,j);
                clistlocation1.add(c);
            }
        }
        nextLocationDoor = new Door(true,"door",2,"location1","location0");
        location1.addTile(clistlocation1.get(0),nextLocationDoor);
        int num = 1;
        for (int i =0;num<clistlocation1.size();i++,num++){
            location1.addTile(clistlocation1.get(num),tileslist.get(i));
            if (i==7) i=0;
        }

        world.addLocation(location1);
        Key key = new Key("key0",1,"key");
        user.pickUp(key);
    }

    @org.junit.Test
    public void moveTo() {
        String userName = "user1";
        //move right test,user now is in (0,0),right y+1
        locationController.moveTo(userName,"right");
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getxPostion(),0);
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getyPosition(),1);

        //test the energy cost,(0,1) is grass, should cost 1 energy
        assertEquals(gameMediator.getWorld().getEntity(userName).getEnergy(),99);

        //move down test,user now is in (0,1),down x+1;
        locationController.moveTo(userName,"down");
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getxPostion(),1);
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getyPosition(),1);

        //test the energy cost,(1,0) is grass, should cost 1 energy
        assertEquals(gameMediator.getWorld().getEntity(userName).getEnergy(),96);

        //move left test,user now is in (1,1),left y-1
        locationController.moveTo(userName,"left");
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getxPostion(),1);
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getyPosition(),0);

        //test the energy cost, is water, should cost 3 energy
        assertEquals(gameMediator.getWorld().getEntity(userName).getEnergy(),95);

        //move up test, user now is in (1,0),up x-1
        locationController.moveTo(userName,"up");
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getxPostion(),0);
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getyPosition(),0);

        //test the energy cost, is grass, should cost 1 energy
        assertEquals(gameMediator.getWorld().getEntity(userName).getEnergy(),94);

        //boundary test, if user is in boundray, then they can not move beyond the boundary
        locationController.moveTo(userName,"up");
        locationController.moveTo(userName,"left");
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getxPostion(),0);
        assertEquals(gameMediator.getWorld().getEntityLocation(userName).getEntities().get(userName).getyPosition(),0);

        //can not move, do not cost energy
        assertEquals(gameMediator.getWorld().getEntity(userName).getEnergy(),94);

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
        String userName = "user1";
        locationController.changeUserCoordinate(2,2,userName);

        //energy cost test
        assertEquals(gameMediator.getWorld().getEntity(userName).getEnergy(),98);

        locationController.openDoor(userName);
        //now user1 moves from location0 to location1
        assertEquals(gameMediator.getWorld().getEntityLocation(userName),gameMediator.getWorld().getLocation("location1"));

        //key in bag has used
        assertEquals(((Key)gameMediator.getWorld().getEntity(userName).getBag().get(0)).isUsed(),true);

        //location0 already as no entity
        assertEquals(gameMediator.getWorld().getLocation("location0").getEntities(),new HashMap<String, Coordinate>());

        //user1 has add to the location1 Entities
        assertNotNull(gameMediator.getWorld().getLocation("location1").getEntities());

        //the new coordinate for user in new location should next to the door, door(index8) y+1,should be (0,1)
        assertEquals(gameMediator.getWorld().getLocation("location1").getEntities().get(userName).getxPostion(),0);
        assertEquals(gameMediator.getWorld().getLocation("location1").getEntities().get(userName).getyPosition(),1);

        //user1 has alreay get the openDoor t9
        assertEquals(((User)gameMediator.getWorld().getEntity(userName)).getOpenedDoors().get(0),t9.getCurrentLocationId());

        //energy cost test
        assertEquals(gameMediator.getWorld().getEntity(userName).getEnergy(),97);

        /*
        user can also go back to the last location
         */
        locationController.changeUserCoordinate(0,0,userName);
        //energy cost test
        assertEquals(gameMediator.getWorld().getEntity(userName).getEnergy(),95);

        locationController.openDoor(userName);
        //energy cost test,stone cost 2
        assertEquals(gameMediator.getWorld().getEntity(userName).getEnergy(),93);

        //now user1 moves from location1 to location0
        assertEquals(gameMediator.getWorld().getEntityLocation(userName),gameMediator.getWorld().getLocation("location0"));

        //key in bag has used
        assertEquals(((Key)gameMediator.getWorld().getEntity(userName).getBag().get(0)).isUsed(),true);

        //location1 already as no entity
        assertEquals(gameMediator.getWorld().getLocation("location1").getEntities(),new HashMap<String, Coordinate>());

        //user1 has add to the location0 Entities
        assertNotNull(gameMediator.getWorld().getLocation("location0").getEntities());

        //the new coordinate for user in new location should next to the door, door(index8) y+1 or y-1,should be (0)
        assertEquals(gameMediator.getWorld().getLocation("location0").getEntities().get(userName).getxPostion(),2);
        assertEquals(gameMediator.getWorld().getLocation("location0").getEntities().get(userName).getyPosition(),1);

    }

    @Test
    public void moveUserToNextLocation() {
        String userName = "user1";

        //Door not null
        assertNotNull(t9);

        //move user
        locationController.moveUserToNextLocation(t9,userName);
        //now user1 moves from location0 to location1
        assertEquals(gameMediator.getWorld().getEntityLocation(userName),gameMediator.getWorld().getLocation("location1"));

        //location0 already as no entity
        assertEquals(gameMediator.getWorld().getLocation("location0").getEntities(),new HashMap<String, Coordinate>());

        //user1 has add to the location1 Entities
        assertNotNull(gameMediator.getWorld().getLocation("location1").getEntities());

        //the new coordinate for user in new location should next to the door, door(index8) y+1,should be (0,1)
        assertEquals(gameMediator.getWorld().getLocation("location1").getEntities().get(userName).getxPostion(),0);
        assertEquals(gameMediator.getWorld().getLocation("location1").getEntities().get(userName).getyPosition(),1);

        //energy cost test
        assertEquals(gameMediator.getWorld().getEntity(userName).getEnergy(),99);

    }


}